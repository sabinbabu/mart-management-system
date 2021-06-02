/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import java.util.List;
import javafx.scene.control.Alert;
import model.Employee;
import model.IConnect;
import model.IQuery;
import model.Item;
import static model.MartManagementSystemModel.Query.ALL;
import static model.MartManagementSystemModel.Query.AUTHNAME;
import static model.MartManagementSystemModel.Query.DELETE;
import static model.MartManagementSystemModel.Query.DELETEITEM;
import static model.MartManagementSystemModel.Query.DELETESUPPLIER;
import static model.MartManagementSystemModel.Query.DISPLAYALLITEM;
import static model.MartManagementSystemModel.Query.DISPLAYALLSUPPLIER;
import static model.MartManagementSystemModel.Query.EMPLOYEENAME;
import static model.MartManagementSystemModel.Query.INSERT;
import static model.MartManagementSystemModel.Query.INSERTITEM;
import static model.MartManagementSystemModel.Query.INSERTSUPPLIER;
import static model.MartManagementSystemModel.Query.ITEMNAME;
import static model.MartManagementSystemModel.Query.SUPPLIERNAME;
import static model.MartManagementSystemModel.Query.UPDATE;
import static model.MartManagementSystemModel.Query.UPDATEITEM;
import static model.MartManagementSystemModel.Query.UPDATESUPPLIER;
import model.QueryException;
import model.Supplier;
import view.IView;

/**
 *
 * @author sabin
 */
public class EmployeePresenter {
    
      IView view;
      IQuery queries;
      IConnect connector;
      ViewModel viewmodel;
      
       public EmployeePresenter(IView iv, IQuery iq, IConnect ic) {
        // initialise controller access
        view = iv;
        // intialise model access
        queries = iq;
        connector = ic;
        viewmodel = new ViewModel();
    }
       
       //EMPLOYEE
    
     public void insert(String employeeName, String employeeEmail, String employeeNumber, String employeePassword) throws IllegalArgumentException {

        if (employeeName.equals("") || employeeEmail.equals("") || employeeNumber.equals("") || employeePassword.equals("")) {
            throw new IllegalArgumentException("Arguments must not contain an empty string");
        }
        try {

            Employee emp = new Employee(employeeName, employeeEmail, employeeNumber, employeePassword);
            int result = queries.command(INSERT, emp);
            if (result == 1) {               
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Employee Added");
            alert.show();
            } else {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Error");
            alert.show();
            }

        } catch (QueryException e) {
            System.exit(1);
        }
    }
     
   
      public void update(int employeeId,String employeeName, String employeeEmail, String employeeNumber, String employeePassword) throws IllegalArgumentException {

       if (employeeName.equals("") || employeeEmail.equals("") || employeeNumber.equals("") || employeePassword.equals("")) {
            throw new IllegalArgumentException("Arguments must not contain an empty string");
        }
        try {

            Employee emp = new Employee(employeeId, employeeName, employeeEmail, employeeNumber, employeePassword);
            int result = queries.command(UPDATE, emp);
            
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Employee Updated");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Employee not updated");
                alert.show();
            }
          

        } catch (QueryException e) {
            view.displayError(e.getMessage());
            System.exit(1);
        }
    }
      
      
      public void delete(int employeeId) throws IllegalArgumentException {
        try {

            Employee emp = new Employee(employeeId, "", "", "", "");
            int result = queries.command(DELETE, emp);
            
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Employee Deleted");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Employee not deleted");
                alert.show();
            }
          

        } catch (QueryException e) {
            view.displayError(e.getMessage());
           // System.exit(1);
        }
    }
     
     
      public void selectAll() {
        try {
            List results = queries.select(ALL);
            viewmodel.set(results);
             displayCurrentRecord(results);
        } catch (QueryException e) {
            view.displayError(e.getMessage());
            System.exit(1);
        }
    }
      
      
      private void displayCurrentRecord(List results) {
        if (results.isEmpty()) {
            view.displayMessage("No Employee found");
            return;
        }
        view.displayRecord(viewmodel.all());
      
    }          
      
      
      public void populateEmployeeCombobox(){
          try{
            List results = queries.select(ALL);
             viewmodel.set(results);
             if (results.isEmpty()) {
        //    view.displayMessage("No records found");
             }
             view.populateCombobox(viewmodel.all());
             
          }catch(QueryException e){
            view.displayError(e.getMessage());
            System.exit(1);
          }
      }
      
      
       public void findByName(String name,String number) {

        if (name.equals("")) {
            throw new IllegalArgumentException("Argument must not be an empty string");
        }
            try {
                List results = queries.select(EMPLOYEENAME, name, number);
                if (results.isEmpty()) {
                    view.displayMessage("No records found");
                } else {
                    viewmodel.set(results);
                    displayCurrentRecord(results);
                }

            } catch (QueryException e) {
                view.displayError(e.getMessage());
                System.exit(1);
            }
    }
      
       
       
       //SUPPLIER
       
        public void insertSupplier(String supplierName, String supplierEmail, String supplierNumber, String supplierAddress) throws IllegalArgumentException {

        if (supplierName.equals("") || supplierEmail.equals("") || supplierNumber.equals("") || supplierAddress.equals("")) {
            throw new IllegalArgumentException("Arguments must not contain an empty string");
        }
        try {

            Supplier sup = new Supplier(supplierName, supplierEmail, supplierNumber, supplierAddress);
            int result = queries.supplierCommand(INSERTSUPPLIER, sup);
            if (result == 1) {               
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Supplier Added");
            alert.show();
            } else {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Error");
            alert.show();
            }

        } catch (QueryException e) {
            System.exit(1);
        }
    }
     
   
     public void updateSupplier(int supplierId ,String supplierName, String supplierEmail, String supplierNumber, String supplierAddress) throws IllegalArgumentException {

      if (supplierName.equals("") || supplierEmail.equals("") || supplierNumber.equals("") || supplierAddress.equals("")) {
            throw new IllegalArgumentException("Arguments must not contain an empty string");
        }
        try {

            Supplier sup = new Supplier(supplierId, supplierName, supplierEmail, supplierNumber, supplierAddress);
            int result = queries.supplierCommand(UPDATESUPPLIER, sup);
            
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Supplier Updated");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Supplier not updated");
                alert.show();
            }
          
        } catch (QueryException e) {
            view.displayError(e.getMessage());
            System.exit(1);
        }
    }
      
      
      public void deleteSupplier(int supplierId) throws IllegalArgumentException {
        try {

            Supplier sup = new Supplier(supplierId, "", "", "", "");
            int result = queries.supplierCommand(DELETESUPPLIER, sup);
            
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Supplier Deleted");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Supplier not deleted");
                alert.show();
            }
          
        } catch (QueryException e) {
            view.displayError(e.getMessage());
           // System.exit(1);
        }
    }
     
     
      public void selectAllSupplier() {
        try {
            List results = queries.selectSupplier(DISPLAYALLSUPPLIER);            
            viewmodel.setSupplier(results);
             displayCurrentSupplierRecord(results);
        } catch (QueryException e) {
            view.displayError(e.getMessage());
            System.exit(1);
        }
    }
      
      
      private void displayCurrentSupplierRecord(List results) {
        if (results.isEmpty()) {
            view.displaySupplierMessage("No Suppliers found");
            return;
        }
        view.displaySupplierRecord(viewmodel.allSupplier());
      
    }          
      
      
      public void populateSupplierCombobox(){
          try{
            List results = queries.selectSupplier(DISPLAYALLSUPPLIER);
             viewmodel.setSupplier(results);
             if (results.isEmpty()) {
          //  view.displayMessage("No records found");
             }
             view.populateSupplierCombobox(viewmodel.allSupplier());
             
          }catch(QueryException e){
            view.displayError(e.getMessage());
            System.exit(1);
          }
      }
      
      
       public void findBySupplier(String name,String number) {

        if (name.equals("")) {
            throw new IllegalArgumentException("Argument must not be an empty string");
        }
            try {
                List results = queries.selectSupplier(SUPPLIERNAME, name, number);
                if (results.isEmpty()) {
                    view.displayMessage("No records found");
                } else {
                    viewmodel.setSupplier(results);
                    displayCurrentSupplierRecord(results);
                }

            } catch (QueryException e) {
                view.displayError(e.getMessage());
                System.exit(1);
            }
    }
       
       
       
       
       
          //ITEM
       
        public void insertItem(String itemName, String itemQuantity, String itemPrice, String itemBarcode, String itemSupplier, String itemExpiryDate) throws IllegalArgumentException {

        if (itemName.equals("") || itemQuantity.equals("") || itemPrice.equals("") || itemBarcode.equals("")|| itemSupplier.equals("") || itemExpiryDate.equals("")) {
            throw new IllegalArgumentException("Arguments must not contain an empty string");
        }
        try {

            Item item = new Item(itemName, itemQuantity, itemPrice, itemBarcode,itemSupplier,itemExpiryDate);
            int result = queries.itemCommand(INSERTITEM, item);
            if (result == 1) {               
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Item Added");
            alert.show();
            } else {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Error");
            alert.show();
            }

        } catch (QueryException e) {
            System.exit(1);
        }
    }
     
   
     public void updateItem(int itemID,String itemName, String itemQuantity, String itemPrice, String itemBarcode, String itemSupplier, String itemExpiryDate) throws IllegalArgumentException {

      if (itemName.equals("") || itemQuantity.equals("") || itemPrice.equals("") || itemBarcode.equals("")|| itemSupplier.equals("") || itemExpiryDate.equals("")) {
            throw new IllegalArgumentException("Arguments must not contain an empty string");
        }
        try {

            Item item = new Item(itemID, itemName, itemQuantity, itemPrice, itemBarcode,itemSupplier,itemExpiryDate);
            int result = queries.itemCommand(UPDATEITEM, item);
            
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Item Updated");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Item not updated");
                alert.show();
            }
          
        } catch (QueryException e) {
            view.displayError(e.getMessage());
            System.exit(1);
        }
    }
      
      
      public void deleteItem(int itemId) throws IllegalArgumentException {
        try {

            Item item = new Item(itemId, "", "", "", "","","");
            int result = queries.itemCommand(DELETEITEM, item);
            
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Item Deleted");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Item not deleted");
                alert.show();
            }
          
        } catch (QueryException e) {
            view.displayError(e.getMessage());
           // System.exit(1);
        }
    }
     
     
      public void selectAllItem() {                 
        try {
            List results = queries.selectItem(DISPLAYALLITEM);            
            viewmodel.setItem(results);
            displayCurrentItemRecord(results);
          
        } catch (QueryException e) {
            System.out.println(e);
          
         //   System.exit(1);
        }
    }
      
      
      private void displayCurrentItemRecord(List results) {
        if (results.isEmpty()) {
            view.displayItemMessage("No Items found");
            return;
        }
        view.displayItemRecord(viewmodel.allItem());
      
    }          
      
      
      public void populateItemCombobox(){
          try{
            List results = queries.selectItem(DISPLAYALLITEM);
             viewmodel.setItem(results);
             if (results.isEmpty()) {
          //  view.displayMessage("No records found");
             }
             view.populateItemCombobox(viewmodel.allItem());
             
          }catch(QueryException e){
            view.displayError(e.getMessage());
            System.exit(1);
          }
      }
      
      
       public void findByItem(String name,String number) {

        if (name.equals("")) {
            throw new IllegalArgumentException("Argument must not be an empty string");
        }
            try {
                List results = queries.selectItem(ITEMNAME, name, number);
                if (results.isEmpty()) {
                    view.displayMessage("No records found");
                } else {
                    viewmodel.setItem(results);
                    displayCurrentItemRecord(results);
                }

            } catch (QueryException e) {
                view.displayError(e.getMessage());
                System.exit(1);
            }
    }
       
       
       
       
       //FOR LOGIN EMPLOYEE
       
        public void findAuthByName(String name) {

             if (name.equals("")) {
            throw new IllegalArgumentException("Argument must not be an empty string");
        }
            try {
                List results = queries.select(AUTHNAME, name);
                if (results.isEmpty()) {
                    view.displayMessage("No records found");
                } else {
                    viewmodel.set(results);
                    displayCurrentRecord(results);
                }

            } catch (QueryException e) {
                view.displayError(e.getMessage());
                System.exit(1);
            }
            
    }
      
}
