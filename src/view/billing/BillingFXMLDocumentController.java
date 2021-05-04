/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.billing;

import java.io.IOException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.ConnectionException;
import model.Employee;
import model.MartManagementSystemModel;
import presenter.EmployeePresenter;
import presenter.IndexedEmployee;
import view.IView;

/**
 * FXML Controller class
 *
 * @author sabin
 */
public class BillingFXMLDocumentController implements Initializable, IView<IndexedEmployee> {

    public static Stage stg;
    EmployeePresenter employeePresenter;
    @FXML
    private ImageView companyLogo;
    @FXML
    private Label companyAddress;
    @FXML
    private Label welcomeUser;
    @FXML
    private Tab tabBilling;
    @FXML
    private Tab tabReport;
    @FXML
    private Tab tabInventory;
    @FXML
    private Tab tabEmployee;
    @FXML
    private Tab tabSupplier;
    @FXML
    private Tab tabMyProfile;
    @FXML
    private TextArea testTextBilling;
    @FXML
    private TextArea textTextReport;
    @FXML
    private Button addEmployee;
    @FXML
    private TextField empSearch;
    @FXML
    private Button btnSearchEmp;
    @FXML
    public TextArea empDisplay;
    @FXML
    private ComboBox<String> employeeSearch;
    public List<Employee> employeeList;
    public static String selectedComboValue ;
    /**
     * Initializes the controller class.
     * @param pp
     */
    public void bind(EmployeePresenter pp) {
        employeePresenter = pp;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        welcomeUser.setText("Welcome" + " " + "admin");        
    }

    @FXML
    private void tabBilling(Event event) {
        testTextBilling.setText("billing");
    }

    @FXML
    private void tabReport(Event event) {
        textTextReport.setText("report");
    }

    @FXML
    private void onEmpSearchPressed(ActionEvent event) {
   
        if(!employeeSearch.getValue().isEmpty()){
           goToShowEmployeeDetail();
        }
    }

    @FXML
    public void onAddEmployee(ActionEvent event) {
        goToAddEmployee();
    }

    @FXML
    public void tabEmployee(Event event) {
         employeePresenter.selectAll();
         employeePresenter.populateEmployeeCombobox();
    }

    @FXML
    private void onDisplayAllEmployeePressed(ActionEvent event) {
        employeePresenter.selectAll();

    }

    @Override
    public void displayRecord(IndexedEmployee indexedEmployee) {
        empDisplay.clear();
        employeeList = indexedEmployee.getAllEmployee();
        empDisplay.appendText("\n\tName\t\t\tPhone\t\tEmail\n");
        empDisplay.appendText("--------------------------------------------------------------------------\n");
        for (Employee employee : employeeList) {
            empDisplay.appendText("\t" + employee.getEmployeeName() + "\t\t\t" + employee.getEmployeeEmail() + "\t\t" + employee.getEmployeeNumber() + "\n");
        }
        employeePresenter.populateEmployeeCombobox();
    }


    public void goToAddEmployee() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/billing/AddEmployeeFXMLDocument.fxml"));
            Parent root = loader.load();
            AddEmployeeFXMLDocumentController controller = loader.getController();
            Stage stage = new Stage();
            this.stg = stage;
            stage.setTitle("ADD EMPLOYEE");
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
            EmployeePresenter employeePresenter = new EmployeePresenter(controller, martManagementSystemModel, martManagementSystemModel);
            controller.bind(employeePresenter);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayMessage(String m) {
           errorMessage(m);
    }

    @Override
    public void displayError(String e) {
        errorMessage(e);
    }
    
    public void errorMessage(String m){
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(m);
            alert.show();}

    private void goToShowEmployeeDetail() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/billing/ShowEmployeeDetailsFXML.fxml"));
            Parent root = loader.load();
            ShowEmployeeDetailsFXMLController controller = loader.getController();
            Stage stage = new Stage();
            this.stg = stage;
            stage.setTitle("UPDATE EMPLOYEE");
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
            EmployeePresenter employeePresenter = new EmployeePresenter(controller, martManagementSystemModel, martManagementSystemModel);
            controller.bind(employeePresenter);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onEmployeeSearchPressed(ActionEvent event) {      
        this.selectedComboValue = employeeSearch.getValue();
    
    }

    @Override
    public void populateCombobox(IndexedEmployee indexedEmployee) {     
       
        employeeList = indexedEmployee.getAllEmployee();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Employee employee : employeeList) {
            list.add(employee.getEmployeeName()+","+employee.getEmployeeNumber());
        }
       employeeSearch.setItems(list);
    }
}
