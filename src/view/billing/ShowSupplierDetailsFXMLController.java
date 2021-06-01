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
import model.Supplier;
import presenter.EmployeePresenter;
import presenter.IndexedEmployee;
import presenter.IndexedSupplier;
import view.IView;

/**
 * FXML Controller class
 *
 * @author sabin
 */
public class ShowSupplierDetailsFXMLController implements Initializable, IView<IndexedEmployee,IndexedSupplier> {

        EmployeePresenter employeePresenter;
  public int selectedSupplierId;
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
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     public void bind(EmployeePresenter pp) {
        employeePresenter = pp;
        String name = BillingFXMLDocumentController.selectedSupplierComboValue.split(",")[0];
        String number = BillingFXMLDocumentController.selectedSupplierComboValue.split(",")[1];
        employeePresenter.findBySupplier(name, number);
    }

    @FXML
    private void onCancelPressed(ActionEvent event) {
         BillingFXMLDocumentController.stg.close();
    }

    @FXML
    private void onUpdatePressed(ActionEvent event) {
         employeePresenter.updateSupplier(
                selectedSupplierId, 
                supNameTextField.getText(),
                supEmailTextField.getText(),
                supPhoneTextField.getText(),
                supAddressTextField.getText()          
        );
         BillingFXMLDocumentController.stg.close();
    }

    @FXML
    private void onDeletePressed(ActionEvent event) {
         employeePresenter.deleteSupplier(
                selectedSupplierId
        );
         BillingFXMLDocumentController.stg.close();
    }

    @Override
    public void displayRecord(IndexedEmployee r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
    @Override
    public void populateCombobox(IndexedEmployee r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displaySupplierRecord(IndexedSupplier indexedSupplier) {
          List<Supplier> supplierList = indexedSupplier.getAllSupplier();
        supNameTextField.setText(supplierList.get(0).getSupplierName());
        supEmailTextField.setText(supplierList.get(0).getSupplierEmail());
        supPhoneTextField.setText(supplierList.get(0).getSupplierNumber());
        supAddressTextField.setText(supplierList.get(0).getSupplierAddress());
        selectedSupplierId = supplierList.get(0).getSupplierID();
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

    @Override
    public void populateSupplierCombobox(IndexedSupplier s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displaySupplierMessage(String m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
