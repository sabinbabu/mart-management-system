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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.ConnectionException;
import model.Employee;
import model.Item;
import model.MartManagementSystemModel;
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
public class BillingFXMLDocumentController implements Initializable, IView<IndexedEmployee,IndexedSupplier,IndexedItem> {

    public static Stage stg;
    EmployeePresenter employeePresenter;
    @FXML
    private ImageView companyLogo;
    @FXML
    private Label companyAddress;
    @FXML
    private Label welcomeUser;
    
    
     @FXML
    public TextArea empDisplay;
    @FXML
    private ComboBox<String> employeeSearch;
    public List<Employee> employeeList;
    public static String selectedComboValue ;
    
    
    @FXML
    private TextArea supDisplay;
    @FXML
    private ComboBox<String> suppllierSearch;
    private List<Supplier> supplierList;
    public static String selectedSupplierComboValue;
    
    
    @FXML
    private TextArea itemDisplay;
    @FXML
    private ComboBox<String> itemSearch;
    private List<Item> itemList;
    public static String selectedItemComboValue;

    
    
    
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
    private Button btnSearchEmp;
   
    @FXML
    private Button addItem;
    @FXML
    private Button btnSearchItem;
    @FXML
    private Button addSupplier;
    @FXML
    private Button btnSearchSup;
  
   
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
    
    //EMPLOYEE
 
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
            EmployeePresenter employeePresenter = new EmployeePresenter((IView) controller, martManagementSystemModel, martManagementSystemModel);
            controller.bind(employeePresenter);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

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
            EmployeePresenter employeePresenter = new EmployeePresenter((IView) controller, martManagementSystemModel, martManagementSystemModel);
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
       System.out.println(list);
    }

    
    
    //SUPPLIER
    
    @FXML
    private void onAddSupplier(ActionEvent event) {
        goToAddSupplier();
    }
    
    public void goToAddSupplier() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/billing/AddSupplierFXMLDocument.fxml"));
            Parent root = loader.load();
            AddSupplierFXMLDocumentController controller = loader.getController();
            Stage stage = new Stage();
            this.stg = stage;
            stage.setTitle("ADD SUPPLIER");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void tabSupplier(Event event) {
         employeePresenter.selectAllSupplier();
         employeePresenter.populateSupplierCombobox();
    }
    
     @FXML
    private void onDisplayAllSupplierPressed(ActionEvent event) {
    employeePresenter.selectAllSupplier();
    }
    
    
    @Override
    public void displaySupplierRecord(IndexedSupplier indexedSupplier) {
        supDisplay.clear();
        supplierList = indexedSupplier.getAllSupplier();
        supDisplay.appendText("\n\tName\t\t\tPhone\t\tEmail\t\tAddress\n");
        supDisplay.appendText("--------------------------------------------------------------------------\n");
        for (Supplier supplier : supplierList) {
            supDisplay.appendText("\t" + supplier.getSupplierName() + "\t\t\t" + supplier.getSupplierEmail() + "\t\t" + supplier.getSupplierNumber() + "\t\t" + supplier.getSupplierAddress()+ "\n");
        }
        employeePresenter.populateSupplierCombobox();
    }
    
    @Override
    public void populateSupplierCombobox(IndexedSupplier indexedSupplier) {     
       
        supplierList = indexedSupplier.getAllSupplier();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Supplier supplier : supplierList) {
            list.add(supplier.getSupplierName()+","+supplier.getSupplierNumber());
        }
       suppllierSearch.setItems(list);
    }
    
    
     @FXML
    private void onSupEditPressed(ActionEvent event) {
        if(!suppllierSearch.getValue().isEmpty()){
           goToShowSupplierDetail();
        }
    }
    
    
      private void goToShowSupplierDetail() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/billing/ShowSupplierDetailsFXML.fxml"));
            Parent root = loader.load();
            ShowSupplierDetailsFXMLController controller = loader.getController();
            Stage stage = new Stage();
            this.stg = stage;
            stage.setTitle("UPDATE SUPPLIER");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      
    @FXML
    private void onSupplierSelectCombobox(ActionEvent event) {
           BillingFXMLDocumentController.selectedSupplierComboValue = suppllierSearch.getValue();
    }


    
    //ITEM
    
    
    @FXML
    private void onAddItem(ActionEvent event) {
        goToAddItem();
    }
    
     public void goToAddItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/billing/AddItemFXMLDocument.fxml"));
            Parent root = loader.load();
            AddItemFXMLDocumentController controller = loader.getController();
            Stage stage = new Stage();
            this.stg = stage;
            stage.setTitle("ADD ITEM");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     
    @FXML
    private void tabInventory(Event event) {
         employeePresenter.selectAllItem();
         employeePresenter.populateItemCombobox();
    }
    
    
    @FXML
    private void onDisplayAllIteemPressed(ActionEvent event) {
        employeePresenter.selectAllItem();
    }

    @Override
    public void displayItemRecord(IndexedItem indexedItem) {
        itemDisplay.clear();
        itemList = indexedItem.getAllItems();
        itemDisplay.appendText("\n\tName\t\t\tPrice\t\tQuantity\t\tSupplier\t\tExpiry Date\n");
        itemDisplay.appendText("--------------------------------------------------------------------------\n");
        for (Item item : itemList) {
            itemDisplay.appendText("\t" + item.getItemName() + "\t\t\t" + item.getItemPrice() + "\t\t" + item.getItemQuantity() + "\t\t" + item.getItemSupplier()+ "\t\t" + item.getItemExpiryDate()+ "\n");
        }
        employeePresenter.populateItemCombobox();
    }

    
    @Override
    public void populateItemCombobox(IndexedItem indexedItem) {
        itemList = indexedItem.getAllItems();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Item item : itemList) {
            list.add(item.getItemName()+","+item.getItemBarcode());
        }
       itemSearch.setItems(list);
    }

    
    @FXML
    private void onItemSearchPressed(ActionEvent event) {
         if(!itemSearch.getValue().isEmpty()){
           goToShowItemDetail();
        }
    }
    
    
       private void goToShowItemDetail() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/billing/ShowItemDetailsFXML.fxml"));
            Parent root = loader.load();
            ShowItemDetailsFXMLController controller = loader.getController();
            Stage stage = new Stage();
            this.stg = stage;
            stage.setTitle("UPDATE ITEM");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



       @FXML
    private void onItemSelectCombobox(ActionEvent event) {
         BillingFXMLDocumentController.selectedItemComboValue = itemSearch.getValue();
    }


   
    
    
    

   
    
    
   @FXML
    private void tabBilling(Event event) {
        testTextBilling.setText("billing");
    }

    @FXML
    private void tabReport(Event event) {
        textTextReport.setText("report");
    }


   @Override
    public void displayMessage(String m) {
         //  errorMessage(m);
          empDisplay.setText(m);
    }

    @Override
    public void displayError(String e) {
        errorMessage(e);
    }
    
    public void errorMessage(String m){
         supDisplay.setText(m);
//            Alert alert = new Alert(Alert.AlertType.NONE);
//            alert.setAlertType(Alert.AlertType.ERROR);
//            alert.setContentText(m);
//            alert.show();
    }

    @Override
    public void displaySupplierMessage(String m) {
        supDisplay.setText(m);
    }



    @Override
    public void displayItemMessage(String m) {
        itemDisplay.setText(m);
    }

    
}
   