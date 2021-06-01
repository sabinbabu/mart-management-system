/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.billing;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import presenter.EmployeePresenter;
import presenter.IndexedEmployee;
import presenter.IndexedSupplier;
import view.IView;

/**
 * FXML Controller class
 *
 * @author sabin
 */
public class AddSupplierFXMLDocumentController implements Initializable, IView<IndexedSupplier,IndexedEmployee> {
    
       EmployeePresenter employeePresenter;

    @FXML
    private TextField supNameTextField;
    @FXML
    private TextField supPhoneTextField;
    @FXML
    private TextField supEmailTextField;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField supAddressTextField;
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
         employeePresenter.insertSupplier(
                supNameTextField.getText(),          
                supEmailTextField.getText(),
                supPhoneTextField.getText(),
                supAddressTextField.getText()
        );
        BillingFXMLDocumentController.stg.close();
    }

    @Override
    public void displayRecord(IndexedSupplier r) { }

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
    public void populateCombobox(IndexedSupplier r) {    }

    @Override
    public void displaySupplierRecord(IndexedEmployee s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void populateSupplierCombobox(IndexedEmployee s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displaySupplierMessage(String m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
