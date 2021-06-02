/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.billing;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ConnectionException;
import model.Employee;
import model.MartManagementSystemModel;
import presenter.EmployeePresenter;
import presenter.IndexedEmployee;
import presenter.IndexedItem;
import presenter.IndexedSupplier;
import view.IView;
import view.login.LoginPageFXMLDocumentController;

/**
 * FXML Controller class
 *
 * @author sabin
 */
public class EmployeeHomeFXMLDocumentController implements Initializable, IView<IndexedEmployee,IndexedSupplier,IndexedItem> {

     EmployeePresenter employeePresenter;
         public List<Employee> employeeList;
    @FXML
    private ImageView companyLogo;
    @FXML
    private Label companyAddress;
    @FXML
    private Label welcomeUser;
    @FXML
    private Tab tabBilling;
    @FXML
    private TextArea testTextBilling;
    @FXML
    private Tab tabMyProfile;
    @FXML
    private Text login_username;
    @FXML
    private Text login_email;
    @FXML
    private Text login_number;
    
    String authEmail;
    String authNumber;
     public void bind(EmployeePresenter pp) {
         employeePresenter = pp;
        welcomeUser.setText("Welcome" + " " + LoginPageFXMLDocumentController.auth_user);  
        login_username.setText(LoginPageFXMLDocumentController.auth_user);                
        employeePresenter.findAuthByName(LoginPageFXMLDocumentController.auth_user);                
        login_email.setText(authEmail);
        login_number.setText(authNumber);
       }
       
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void tabBilling(Event event) {
    }

    @FXML
    private void btn_logout(ActionEvent event) {
        
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login/LoginPageFXMLDocument.fxml"));
            Parent root = loader.load();
            LoginPageFXMLDocumentController controller = loader.getController();
            Stage stage = new Stage();
           // this.stg = stage;
            stage.setTitle("Login");
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
            LoginPageFXMLDocumentController.stg.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
                
    }

    @Override
    public void displayRecord(IndexedEmployee indexedEmployee) {
         employeeList = indexedEmployee.getAllEmployee();
       
         authEmail = employeeList.get(0).getEmployeeEmail();
         authNumber = employeeList.get(0).getEmployeeNumber();
         
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
