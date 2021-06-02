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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Item;
import model.Supplier;
import presenter.EmployeePresenter;
import presenter.IndexedEmployee;
import presenter.IndexedItem;
import presenter.IndexedSupplier;
import view.IView;

/**
 * FXML Controller class
 *
 * @author sabin
 */
public class ShowItemDetailsFXMLController implements Initializable, IView<IndexedEmployee,IndexedSupplier,IndexedItem>  {

            EmployeePresenter employeePresenter;
  public int selectedItemId;
  private List<Supplier> supplierList;

    @FXML
    private TextField itemNameTextField;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField itemBarcodeTextField;
    @FXML
    private TextField itemQuantityTextField;
    @FXML
    private TextField itemExpiryTextField;
    @FXML
    private TextField itemPriceTextField;
    @FXML
    private ComboBox<String> supplierSelect;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     public void bind(EmployeePresenter pp) {
        employeePresenter = pp;
        String name = BillingFXMLDocumentController.selectedItemComboValue.split(",")[0];
        String number = BillingFXMLDocumentController.selectedItemComboValue.split(",")[1];
        employeePresenter.findByItem(name, number);
        employeePresenter.populateSupplierCombobox();
    }

    @FXML
    private void onCancelPressed(ActionEvent event) {
          BillingFXMLDocumentController.stg.close();
    }

    @FXML
    private void onUpdatePressed(ActionEvent event) {
         employeePresenter.updateItem( 
                  selectedItemId,
                  itemNameTextField.getText(), 
                  itemQuantityTextField.getText(), 
                  itemPriceTextField.getText(), 
                  itemBarcodeTextField.getText(), 
                  supplierSelect.getValue(), 
                  itemExpiryTextField.getText());
                                                    
        BillingFXMLDocumentController.stg.close();
    }

    @FXML
    private void onDeletePressed(ActionEvent event) {
         employeePresenter.deleteItem(
                selectedItemId
        );
         BillingFXMLDocumentController.stg.close();
    }

    @FXML
    private void onSupplierSelectPressed(ActionEvent event) {
    }

    @Override
    public void displayRecord(IndexedEmployee r) { }

    @Override
    public void displayMessage(String m) {
  errorMessage(m);
    }

    @Override
    public void displayError(String e) {
  errorMessage(e);
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
    public void populateSupplierCombobox(IndexedSupplier indexedSupplier) {
        supplierList = indexedSupplier.getAllSupplier();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Supplier supplier : supplierList) {
            list.add(supplier.getSupplierName());
        }
       supplierSelect.setItems(list);
    }

    @Override
    public void displaySupplierMessage(String m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displayItemRecord(IndexedItem indexedItem) {
        
        List<Item> itemList = indexedItem.getAllItems();        
        itemNameTextField.setText(itemList.get(0).getItemName());
        itemQuantityTextField.setText(itemList.get(0).getItemQuantity());
        itemPriceTextField.setText(itemList.get(0).getItemPrice());
        itemBarcodeTextField.setText(itemList.get(0).getItemBarcode());       
        supplierSelect.setValue(itemList.get(0).getItemSupplier());
        itemExpiryTextField.setText(itemList.get(0).getItemExpiryDate());
        selectedItemId = itemList.get(0).getItemID();
    }

    
     public void errorMessage(String m){
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(m);
            alert.show();
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
