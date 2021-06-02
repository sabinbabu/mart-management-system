/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.login;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import martmanagementsystem.MartManagementSystem;
import model.ConnectionException;
import model.Employee;
import model.MartManagementSystemModel;
import presenter.EmployeePresenter;
import presenter.IndexedEmployee;
import presenter.IndexedItem;
import presenter.IndexedSupplier;
import view.IView;
import view.billing.AddEmployeeFXMLDocumentController;
import view.billing.BillingFXMLDocumentController;
import view.billing.EmployeeHomeFXMLDocumentController;

/**
 *
 * @author sabin
 */
public class LoginPageFXMLDocumentController implements Initializable,IView<IndexedEmployee,IndexedSupplier,IndexedItem> {

     public static Stage stg;
     EmployeePresenter employeePresenter;
     public List<Employee> employeeList;
     ObservableList<String> list;
     public static String auth_user;
    @FXML
    private Label label;
    @FXML
    private TextField lognUsername;
    @FXML
    private TextField loginPassword;
    @FXML
    private Button btnLogin;

    @FXML
    private void onLoginClicked(ActionEvent event) {
// goToAdminHomepage();
       
        
        String username = lognUsername.getText().trim();
        String password = loginPassword.getText().trim();
        
        
        if(list.contains(username+","+password)){
            this.auth_user = username;
            goToEmployeeHomePage();
        } else if ("admin".equals(username) && "admin".equals(password)) {
          goToAdminHomepage();
        } else {
            Alert alert = new Alert(AlertType.NONE);
            alert.setAlertType(AlertType.ERROR);
            alert.setContentText("Incorrect username or password");
            alert.show();
        }
    }
    
     public void bind(EmployeePresenter pp) {
        employeePresenter = pp;
        employeePresenter.selectAll();
        list = FXCollections.observableArrayList();
        for (Employee emp : employeeList) {
            list.add(emp.getEmployeeName()+","+emp.getEmployeePassword());
        }
        
        System.out.println(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }

    private void goToAdminHomepage() {
          try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/billing/billingFXMLDocument.fxml"));
                Parent root = loader.load();
                BillingFXMLDocumentController controller = loader.getController();
                Stage stage = new Stage();
                this.stg = stage;
                stage.setTitle("Mart Management System");
                MartManagementSystemModel martManagementSystemModel = new MartManagementSystemModel();
                try {
                    // connecting to database   
                    martManagementSystemModel.connect();
                    martManagementSystemModel.initialise();
                } catch (ConnectionException e) {
                    System.err.println(e.getMessage());
                    e.getCause().printStackTrace();
                    System.exit(1);
                }
                EmployeePresenter employeePresenter = new EmployeePresenter((IView) controller, martManagementSystemModel, martManagementSystemModel);
                controller.bind(employeePresenter);
                stage.setScene(new Scene(root));
                stage.show();
                MartManagementSystem.stg.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
    
        private void goToEmployeeHomePage() {
                try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/billing/employeeHomeFXMLDocument.fxml"));
                Parent root = loader.load();
                EmployeeHomeFXMLDocumentController controller = loader.getController();
                Stage stage = new Stage();
                this.stg = stage;
                stage.setTitle("Mart Management System");
                MartManagementSystemModel martManagementSystemModel = new MartManagementSystemModel();
                try {
                    // connecting to database   
                    martManagementSystemModel.connect();
                    martManagementSystemModel.initialise();
                } catch (ConnectionException e) {
                    System.err.println(e.getMessage());
                    e.getCause().printStackTrace();
                    System.exit(1);
                }
                EmployeePresenter employeePresenter = new EmployeePresenter((IView) controller, martManagementSystemModel, martManagementSystemModel);
                controller.bind(employeePresenter);
                stage.setScene(new Scene(root));
                stage.show();
                MartManagementSystem.stg.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    @Override
    public void displayRecord(IndexedEmployee indexedEmployee) {
          employeeList = indexedEmployee.getAllEmployee();
          
    }

    @Override
    public void displayMessage(String m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displayError(String e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void populateCombobox(IndexedEmployee r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displaySupplierRecord(IndexedSupplier s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void populateSupplierCombobox(IndexedSupplier s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displaySupplierMessage(String m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displayItemRecord(IndexedItem i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void populateItemCombobox(IndexedItem i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displayItemMessage(String m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
