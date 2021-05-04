/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.billing;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import presenter.EmployeePresenter;
import presenter.IndexedEmployee;
import view.IView;

/**
 * FXML Controller class
 *
 * @author sabin
 */
public class AddEmployeeFXMLDocumentController implements Initializable, IView<IndexedEmployee> {
    
    EmployeePresenter employeePresenter;

    @FXML
    private TextField empNameTextField;
    @FXML
    private TextField empEmailTextField;
    @FXML
    private TextField empPhoneTextField;   
    @FXML
    private Button btnCancel;
    @FXML
    private TextField empPasswordTextField;
    @FXML
    private Button btnAdd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     public void bind(EmployeePresenter pp) {
        employeePresenter = pp;
    }

    @FXML
    private void onCancelPressed(ActionEvent event) {
        BillingFXMLDocumentController.stg.close();
    }

    @FXML
    private void onAddPressed(ActionEvent event) {
  
         employeePresenter.insert(
                empNameTextField.getText(),          
                empEmailTextField.getText(),
                empPhoneTextField.getText(),
                empPasswordTextField.getText()
        );
        BillingFXMLDocumentController.stg.close();
    }


    @Override
    public void displayRecord(IndexedEmployee r) {}   

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

    @Override
    public void populateCombobox(IndexedEmployee r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
