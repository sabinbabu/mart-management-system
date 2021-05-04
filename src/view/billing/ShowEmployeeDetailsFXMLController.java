/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.billing;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Employee;
import presenter.EmployeePresenter;
import presenter.IndexedEmployee;
import view.IView;

/**
 * FXML Controller class
 *
 * @author sabin
 */
public class ShowEmployeeDetailsFXMLController implements Initializable, IView<IndexedEmployee> {

    EmployeePresenter employeePresenter;
    @FXML
    private TextField empNameTextField;
    @FXML
    private TextField empPhoneTextField;
    @FXML
    private TextField empEmailTextField;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField empPasswordTextField;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    
    public int selectedEmployeeId;
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }   
    
        
    public void bind(EmployeePresenter pp) {
        employeePresenter = pp;
        String name = BillingFXMLDocumentController.selectedComboValue.split(",")[0];
        String number = BillingFXMLDocumentController.selectedComboValue.split(",")[1];
        employeePresenter.findByName(name, number);
    }
    @Override
    public void displayRecord(IndexedEmployee indexedEmployee) {
        List<Employee> employeeList = indexedEmployee.getAllEmployee();
        empNameTextField.setText(employeeList.get(0).getEmployeeName());
        empEmailTextField.setText(employeeList.get(0).getEmployeeEmail());
        empPhoneTextField.setText(employeeList.get(0).getEmployeeNumber());
        empPasswordTextField.setText(employeeList.get(0).getEmployeePassword());
        selectedEmployeeId = employeeList.get(0).getEmployeeID();
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
            alert.show();
    }

    @FXML
    private void onCancelPressed(ActionEvent event) {
         BillingFXMLDocumentController.stg.close();
    }

    @FXML
    private void onUpdatePressed(ActionEvent event) {
         employeePresenter.update(
                selectedEmployeeId, 
                empNameTextField.getText(),
                empEmailTextField.getText(),
                empPhoneTextField.getText(),
                empPasswordTextField.getText()          
        );
         BillingFXMLDocumentController.stg.close();
    }

    @FXML
    private void onDeletePressed(ActionEvent event) {
        employeePresenter.delete(
                selectedEmployeeId
        );
         BillingFXMLDocumentController.stg.close();
    }

    @Override
    public void populateCombobox(IndexedEmployee r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
