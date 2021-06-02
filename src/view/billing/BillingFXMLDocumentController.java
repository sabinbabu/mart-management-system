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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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
import view.login.LoginPageFXMLDocumentController;

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
    
    
    public TextArea empDisplay;
    @FXML
    private ComboBox<String> employeeSearch;
    public List<Employee> employeeList;
    public static String selectedComboValue ;
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> employee_name;
    @FXML
    private TableColumn<Employee, String> employee_email;
    @FXML
    private TableColumn<Employee, String> employee_number;

    
    
    private TextArea supDisplay;
    @FXML
    private ComboBox<String> suppllierSearch;
    private List<Supplier> supplierList;
    public static String selectedSupplierComboValue;
    @FXML
    private TableView<Supplier> supplierTable;
    @FXML
    private TableColumn<Supplier, String> sup_name;
    @FXML
    private TableColumn<Supplier, String> sup_email;
    @FXML
    private TableColumn<Supplier, String> sup_number;
    @FXML
    private TableColumn<Supplier, String> sup_address;

    
    
    private TextArea itemDisplay;
    @FXML
    private ComboBox<String> itemSearch;
    private List<Item> itemList;
    public static String selectedItemComboValue;
    @FXML
    private TableView<Item> itemTable;
    @FXML
    private TableColumn<Item, String> item_name;
    @FXML
    private TableColumn<Item, String> item_quantity;
    @FXML
    private TableColumn<Item, String> item_price;
    @FXML
    private TableColumn<Item, String> item_supplier;
    @FXML
    private TableColumn<Item, String> item_expiryDate;

   
    
    
    @FXML
    private Tab tabBilling;
    @FXML
    private Tab tabMyProfile;
    @FXML
    private TextArea testTextBilling;
    @FXML
    private TextArea textTextReport;
    
    
    @FXML
    private Text login_username;
    @FXML
    private Text login_email;
    @FXML
    private Text login_number;
    @FXML
    private Tab tabReport;
    @FXML
    private Tab tabInventory;
    @FXML
    private Button addItem;
    @FXML
    private Button btnSearchItem;
    @FXML
    private Tab tabEmployee;
    @FXML
    private Button addEmployee;
    @FXML
    private Button btnSearchEmp;
    @FXML
    private Tab tabSupplier;
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
        login_username.setText("Admin");
        login_email.setText("admin@hoolamart.com");
        login_number.setText("0425896578");
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
        
        employeeTable.getItems().clear();
        employee_name.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        employee_email.setCellValueFactory(new PropertyValueFactory<>("employeeEmail"));
        employee_number.setCellValueFactory(new PropertyValueFactory<>("employeeNumber"));
      
         employeeList = indexedEmployee.getAllEmployee();
         ObservableList<Employee> list = FXCollections.observableArrayList();
         for (Employee emp : employeeList) {
            list.add(emp);
        }
        
       employeeTable.setItems(list);
        
//        empDisplay.clear();
//        employeeList = indexedEmployee.getAllEmployee();
//        empDisplay.appendText("\n\tName\t\t\tPhone\t\tEmail\n");
//        empDisplay.appendText("--------------------------------------------------------------------------\n");
//        for (Employee employee : employeeList) {
//            empDisplay.appendText("\t" + employee.getEmployeeName() + "\t\t\t" + employee.getEmployeeEmail() + "\t\t" + employee.getEmployeeNumber() + "\n");
//        }
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
        
        supplierTable.getItems().clear();
        sup_name.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        sup_email.setCellValueFactory(new PropertyValueFactory<>("supplierEmail"));
        sup_number.setCellValueFactory(new PropertyValueFactory<>("supplierNumber"));
        sup_address.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
      
         supplierList = indexedSupplier.getAllSupplier();
         ObservableList<Supplier> list = FXCollections.observableArrayList();
         for (Supplier sup : supplierList) {
            list.add(sup);
        }
        
       supplierTable.setItems(list);
        
//        supDisplay.clear();
//        supplierList = indexedSupplier.getAllSupplier();
//        supDisplay.appendText("\n\tName\t\t\tPhone\t\tEmail\t\tAddress\n");
//        supDisplay.appendText("--------------------------------------------------------------------------\n");
//        for (Supplier supplier : supplierList) {
//            supDisplay.appendText("\t" + supplier.getSupplierName() + "\t\t\t" + supplier.getSupplierEmail() + "\t\t" + supplier.getSupplierNumber() + "\t\t" + supplier.getSupplierAddress()+ "\n");
//        }
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
        itemTable.getItems().clear();
        item_name.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        item_quantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        item_price.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        item_supplier.setCellValueFactory(new PropertyValueFactory<>("itemSupplier"));
        item_expiryDate.setCellValueFactory(new PropertyValueFactory<>("itemExpiryDate"));
         itemList = indexedItem.getAllItems();
         ObservableList<Item> list = FXCollections.observableArrayList();
         for (Item item : itemList) {
            list.add(item);
        }
        
       itemTable.setItems(list);
//        itemDisplay.clear();
//        itemList = indexedItem.getAllItems();
//        itemDisplay.appendText("\n\tName\t\t\tPrice\t\tQuantity\t\tSupplier\t\tExpiry Date\n");
//        itemDisplay.appendText("--------------------------------------------------------------------------\n");
//        for (Item item : itemList) {
//            itemDisplay.appendText("\t" + item.getItemName() + "\t\t\t" + item.getItemPrice() + "\t\t" + item.getItemQuantity() + "\t\t" + item.getItemSupplier()+ "\t\t" + item.getItemExpiryDate()+ "\n");
//        }
        employeePresenter.populateItemCombobox();
    }

    
    @Override
    public void populateItemCombobox(IndexedItem indexedItem) {
        itemList = indexedItem.getAllItems();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Item item : itemList) {
            list.add(item.getItemName()+",("+item.getItemQuantity()+")");
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

    
    
    // LOGOUT
    
    @FXML
    private void btn_logout(ActionEvent event) {     
        
        
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login/LoginPageFXMLDocument.fxml"));
            Parent root = loader.load();
            LoginPageFXMLDocumentController controller = loader.getController();
            Stage stage = new Stage();
            this.stg = stage;
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

    
}
   