/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 *
 * @author sabin
 */
public class MartManagementSystemModel implements  IConnect, IQuery<MartManagementSystemModel.Query, Employee, Supplier, Item> {
    
     public static enum Query {
        INSERT,ALL,EMPLOYEENAME,UPDATE,DELETE,
        INSERTSUPPLIER,DISPLAYALLSUPPLIER,SUPPLIERNAME,UPDATESUPPLIER,DELETESUPPLIER,
        INSERTITEM,DISPLAYALLITEM,ITEMNAME,UPDATEITEM,DELETEITEM,
        AUTHNAME
    };
     

    private Connection connection = null;
    private static final String URL = "jdbc:derby://localhost:1527/mmsdb";
    private static final String USERNAME = "mmsdb";
    private static final String PASSWORD = "mmsdb";

    
    
    private final EnumMap<MartManagementSystemModel.Query, String> sqlCommands = new EnumMap<>(MartManagementSystemModel.Query.class);
    private final EnumMap<MartManagementSystemModel.Query, PreparedStatement> statements = new EnumMap<>(MartManagementSystemModel.Query.class);
    
    
    
     public MartManagementSystemModel() {
        // Specify the queries that are supported
        //insert into database
        //EMPLOYEE
        sqlCommands.put(MartManagementSystemModel.Query.INSERT,
                "INSERT INTO EMPLOYEE (EMPLOYEENAME, EMPLOYEEEMAIL, EMPLOYEENUMBER, EMPLOYEEPASSWORD ) VALUES ( ?, ?, ?, ? )");
         //get records of all EMPLOYEES
        sqlCommands.put(MartManagementSystemModel.Query.ALL,
                "SELECT * FROM EMPLOYEE");
        //select employee with particular name
         sqlCommands.put(Query.EMPLOYEENAME,
                "SELECT * FROM EMPLOYEE WHERE EMPLOYEENAME = ? AND EMPLOYEENUMBER = ?");
         // update EMPLOYEE
        sqlCommands.put(Query.UPDATE,
                "UPDATE EMPLOYEE SET EMPLOYEENAME = ? , EMPLOYEEEMAIL =?, EMPLOYEENUMBER = ? , EMPLOYEEPASSWORD = ? WHERE EMPLOYEEID = ?");
        //delete employee
        sqlCommands.put(Query.DELETE,
                "DELETE FROM EMPLOYEE WHERE EMPLOYEEID = ?");
        
        
        //SUPPLIER 
         sqlCommands.put(MartManagementSystemModel.Query.INSERTSUPPLIER,
                "INSERT INTO SUPPLIER (SUPPLIERNAME, SUPPLIEREMAIL, SUPPLIERNUMBER, SUPPLIERADDRESS ) VALUES ( ?, ?, ?, ? )");
         //get records of all SUPPLIERS
        sqlCommands.put(MartManagementSystemModel.Query.DISPLAYALLSUPPLIER,
                "SELECT * FROM SUPPLIER");
        //select employee with particular name
         sqlCommands.put(Query.SUPPLIERNAME,
                "SELECT * FROM SUPPLIER WHERE SUPPLIERNAME = ? AND SUPPLIERNUMBER = ?");
         // update EMPLOYEE
        sqlCommands.put(Query.UPDATESUPPLIER,
                "UPDATE SUPPLIER SET SUPPLIERNAME = ? , SUPPLIEREMAIL =?, SUPPLIERNUMBER = ? , SUPPLIERADDRESS = ? WHERE SUPPLIERID = ?");
        //delete employee
        sqlCommands.put(Query.DELETESUPPLIER,
                "DELETE FROM SUPPLIER WHERE SUPPLIERID = ?");
        
        
         //ITEM 
         sqlCommands.put(MartManagementSystemModel.Query.INSERTITEM,
                "INSERT INTO ITEM (ITEMNAME, ITEMQUANTITY, ITEMPRICE, BARCODE, ITEMSUPPLIER, EXPIRYDATE ) VALUES ( ?, ?, ?, ?, ?, ? )");
         //get records of all SUPPLIERS
        sqlCommands.put(MartManagementSystemModel.Query.DISPLAYALLITEM,
                "SELECT * FROM ITEM ORDER BY ITEMQUANTITY");
        //select employee with particular name
         sqlCommands.put(Query.ITEMNAME,
                "SELECT * FROM ITEM WHERE ITEMNAME = ? AND ITEMQUANTITY = ?");
         // update EMPLOYEE
        sqlCommands.put(Query.UPDATEITEM,
                "UPDATE ITEM SET ITEMNAME = ? , ITEMQUANTITY =?, ITEMPRICE = ? , BARCODE = ? , ITEMSUPPLIER = ?, EXPIRYDATE = ?  WHERE ITEMID = ?");
        //delete employee
        sqlCommands.put(Query.DELETEITEM,
                "DELETE FROM ITEM WHERE ITEMID = ?");
        
        
        //AUTH_NAME
         sqlCommands.put(Query.AUTHNAME,
                "SELECT * FROM EMPLOYEE WHERE EMPLOYEENAME = ?");
        
       
    }
     
     
     
      @Override
    public void connect() throws ConnectionException {
        // Connect to the address book database
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (SQLException e) {
            throw new ConnectionException("Unable to open data source", e);
        }
    }
    
    
    
     @Override
    public void initialise() throws ConnectionException {
        // Create prepared statements for each query
        try {
            for (Query q : Query.values()) {
                statements.put(q, connection.prepareStatement(sqlCommands.get(q)));
            }
        } catch (SQLException e) {
            throw new ConnectionException("Unable to initialise data source", e);
        }
    }

    /**
     * Disconnect from the grade book
     *
     * @throws ConnectionException
     */
    @Override
    public void disconnect() throws ConnectionException {
        // Close the connection 

        try (Connection c = connection) {
            // connection is closed automatically with try with resources
            // close prepared statements first
            for (Query q : Query.values()) {
                statements.get(q).close();
            }
        } catch (SQLException e) {
            throw new ConnectionException("Unable to close data source", e);
        }
    }
    
    
    @Override
    public int command(Query query, Employee employee) throws QueryException {
        switch (query) {
            case INSERT:
                return addEmployee(employee);
            case UPDATE:
                return updateEmployee(employee);
            case DELETE:
                return deleteEmployee(employee);           
        }
        // Should never happen
        return -1;
    }
    

    @Override
    public int supplierCommand(Query query, Supplier supplier) throws QueryException {
        switch (query) {
            case INSERTSUPPLIER:
                return addSupplier(supplier);
            case UPDATESUPPLIER:
                return updateSupplier(supplier);
            case DELETESUPPLIER:
                return deleteSupplier(supplier);
        }
        // Should never happen
        return -1;
    }
    
    
     @Override
    public int itemCommand(Query query, Item item) throws QueryException {
        switch (query) {
            case INSERTITEM:
                return addItem(item);
            case UPDATEITEM:
                return updateItem(item);
            case DELETEITEM:
                return deleteItem(item);
        }
        // Should never happen
        return -1;
    }
    
    
    
    
    //EMPLOYEE
    
    
    @Override
    public List<Employee> select(Query q, Object... o) throws QueryException {
        switch (q) {
             case ALL:
                return getAllEmployee();
             case EMPLOYEENAME:
                return getEmployeeByName((String) o[0], (String) o[1]);
             case AUTHNAME:
                 return getEmployeeAuthByName((String) o[0]);
        }
        // Should never happen
        return null;
    }

    private Employee createEmployee(ResultSet rs) throws QueryException {
        Employee emp = null;
        try {
            emp = new Employee(
                    rs.getInt("employeeID"),
                    rs.getString("employeeName"),
                    rs.getString("employeeEmail"),
                    rs.getString("employeeNumber"),
                    rs.getString("employeePassword")                   
            );
        } catch (SQLException e) {
            throw (new QueryException("Unable to process the result of selection query", e));
        }
        return emp;
    }

    /*
     * Add a record to the grade book. Record fields are extracted from the method
     * parameter, which is a Student object. 
     */
    private int addEmployee(Employee emp) throws QueryException {
        // Look up prepared statement
        PreparedStatement ps = statements.get(Query.INSERT);

        // insert student attributes into prepared statement
        try {
            ps.setString(1, emp.getEmployeeName());
            ps.setString(2, emp.getEmployeeEmail());
            ps.setString(3, emp.getEmployeeNumber());
            ps.setString(4, emp.getEmployeePassword());


           
        } catch (SQLException e) {
            throw (new QueryException("Unable to paramaterise selection query", e));
        }

        try {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw (new QueryException("Unable to perform insert command", e));
        }
    }

    private int updateEmployee(Employee emp) throws QueryException {
        // Look up prepared statement
        PreparedStatement ps = statements.get(Query.UPDATE);

        // update student attributes into prepared statement
        try {

            ps.setString(1, emp.getEmployeeName());
            ps.setString(2, emp.getEmployeeEmail());
            ps.setString(3, emp.getEmployeeNumber());
            ps.setString(4, emp.getEmployeePassword());
            ps.setInt(5, emp.getEmployeeID());
            
        } catch (SQLException e) {
            throw (new QueryException("Unable to paramaterise selection query", e));
        }

        try {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw (new QueryException("Unable to perform update command", e));
        }
    }
    
     private int deleteEmployee(Employee emp) throws QueryException {
        // Look up prepared statement
        PreparedStatement ps = statements.get(Query.DELETE);

        // update student attributes into prepared statement
        try {
            ps.setInt(1, emp.getEmployeeID());
            
        } catch (SQLException e) {
            throw (new QueryException("Unable to paramaterise selection query", e));
        }

        try {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw (new QueryException("Unable to perform update command", e));
        }
    }
    

    /*
     * Select list of all employees
     */
    private List<Employee> getAllEmployee() throws QueryException {
        // get prepared statement
        PreparedStatement ps = statements.get(Query.ALL);

        try (ResultSet resultSet = ps.executeQuery()) {
            List<Employee> results = new ArrayList<>();
            while (resultSet.next()) {
                Employee emp = createEmployee(resultSet);
                results.add(emp);
            }
            return results;
        } catch (SQLException e) {
            throw (new QueryException("Unable to execute selection query", e));
        }
    }
    
    
    
     private List<Employee> getEmployeeByName(String grade, String number) throws QueryException {
        // Look up prepared statement
        PreparedStatement ps = statements.get(Query.EMPLOYEENAME);
        try {
            // Insert grade into prepared statement
            ps.setString(1, grade);
            ps.setString(2, number );
        } catch (SQLException e) {
            throw (new QueryException("Unable to paramaterise selection query", e));
        }

        try (ResultSet resultSet = ps.executeQuery()) {
            List<Employee> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(createEmployee(resultSet));
            }
            return results;
        } catch (SQLException e) {
            throw (new QueryException("Unable to execute selection query", e));
        }
    }
     
     
     //AUTH
     
       private List<Employee> getEmployeeAuthByName(String name) throws QueryException {
        // Look up prepared statement
        PreparedStatement ps = statements.get(Query.AUTHNAME);
        try {
            // Insert grade into prepared statement
            ps.setString(1, name);
        } catch (SQLException e) {
            throw (new QueryException("Unable to paramaterise selection query", e));
        }

        try (ResultSet resultSet = ps.executeQuery()) {
            List<Employee> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(createEmployee(resultSet));
            }
            return results;
        } catch (SQLException e) {
            throw (new QueryException("Unable to execute selection query", e));
        }
    }
     
         
     
     
     //SUPPLIER
     
     
     
      @Override
    public List<Supplier> selectSupplier(Query q, Object... o) throws QueryException {
        switch (q) {
             case DISPLAYALLSUPPLIER:
                return getAllSupplier();
             case SUPPLIERNAME:
                return getSupplierByName((String) o[0], (String) o[1]);
        }
        // Should never happen
        return null;
    }

    private Supplier createSupplier(ResultSet rs) throws QueryException {
        Supplier sup = null;
        try {
            sup = new Supplier(
                    rs.getInt("supplierID"),
                    rs.getString("supplierName"),
                    rs.getString("supplierEmail"),
                    rs.getString("supplierNumber"),
                    rs.getString("supplierAddress")                   
            );
        } catch (SQLException e) {
            throw (new QueryException("Unable to process the result of selection query", e));
        }
        return sup;
    }

    /*
     * Add a record to the database. Record fields are extracted from the method
     * parameter, which is a Supplier object. 
     */
    private int addSupplier(Supplier sup) throws QueryException {
        // Look up prepared statement
        PreparedStatement ps = statements.get(Query.INSERTSUPPLIER);

        // insert student attributes into prepared statement
        try {
            ps.setString(1, sup.getSupplierName());
            ps.setString(2, sup.getSupplierEmail());
            ps.setString(3, sup.getSupplierNumber());
            ps.setString(4, sup.getSupplierAddress());
           
        } catch (SQLException e) {
            throw (new QueryException("Unable to paramaterise selection query", e));
        }

        try {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw (new QueryException("Unable to perform insert command", e));
        }
    }

    private int updateSupplier(Supplier sup) throws QueryException {
        // Look up prepared statement
        PreparedStatement ps = statements.get(Query.UPDATESUPPLIER);

        // update student attributes into prepared statement
        try {

            ps.setString(1, sup.getSupplierName());
            ps.setString(2, sup.getSupplierEmail());
            ps.setString(3, sup.getSupplierNumber());
            ps.setString(4, sup.getSupplierAddress());
            ps.setInt(5, sup.getSupplierID());
            
        } catch (SQLException e) {
            throw (new QueryException("Unable to paramaterise selection query", e));
        }

        try {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw (new QueryException("Unable to perform update command", e));
        }
    }
    
     private int deleteSupplier(Supplier sup) throws QueryException {
        // Look up prepared statement
        PreparedStatement ps = statements.get(Query.DELETESUPPLIER);

        // update student attributes into prepared statement
        try {
            ps.setInt(1, sup.getSupplierID());
            
        } catch (SQLException e) {
            throw (new QueryException("Unable to paramaterise selection query", e));
        }

        try {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw (new QueryException("Unable to perform update command", e));
        }
    }
    

    /*
     * Select list of all supplier
     */
    private List<Supplier> getAllSupplier() throws QueryException {
        // get prepared statement
        PreparedStatement ps = statements.get(Query.DISPLAYALLSUPPLIER);

        try (ResultSet resultSet = ps.executeQuery()) {
            List<Supplier> results = new ArrayList<>();
            while (resultSet.next()) {
                Supplier sup = createSupplier(resultSet);
                results.add(sup);
            }
            return results;
        } catch (SQLException e) {
            throw (new QueryException("Unable to execute selection query", e));
        }
    }
    
    
    
     private List<Supplier> getSupplierByName(String name, String id) throws QueryException {
        // Look up prepared statement
        PreparedStatement ps = statements.get(Query.SUPPLIERNAME);
        try {
            // Insert grade into prepared statement
            ps.setString(1, name);
            ps.setString(2, id );
        } catch (SQLException e) {
            throw (new QueryException("Unable to paramaterise selection query", e));
        }

        try (ResultSet resultSet = ps.executeQuery()) {
            List<Supplier> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(createSupplier(resultSet));
            }
            return results;
        } catch (SQLException e) {
            throw (new QueryException("Unable to execute selection query", e));
        }
    }
     
     
     
     
     
       //ITEM
     
     
     
      @Override
    public List<Item> selectItem(Query q, Object... o) throws QueryException {
        switch (q) {
             case DISPLAYALLITEM:
                return getAllItem();
             case ITEMNAME:
                return getItemByName((String) o[0], (String) o[1]);
        }
        // Should never happen
        return null;
    }

    
    
    private Item createItem(ResultSet rs) throws QueryException {
        Item ite = null;
        try {
            ite = new Item(
                    rs.getInt("itemID"),
                    rs.getString("itemName"),
                    rs.getString("itemQuantity"),
                    rs.getString("itemPrice"),
                    rs.getString("Barcode"),
                    rs.getString("itemSupplier"),
                    rs.getString("ExpiryDate")
                    
            );
        } catch (SQLException e) {
            System.out.println(e);
            throw (new QueryException("Unable to process the result of selection query", e));
        }
        return ite;
    }

    /*
     * Add a record to the database. Record fields are extracted from the method
     * parameter, which is a Supplier object. 
     */
    private int addItem(Item item) throws QueryException {
        // Look up prepared statement
        PreparedStatement ps = statements.get(Query.INSERTITEM);

        // insert student attributes into prepared statement
        try {
            ps.setString(1, item.getItemName());
            ps.setString(2, item.getItemQuantity());
            ps.setString(3, item.getItemPrice());
            ps.setString(4, item.getItemBarcode());
            ps.setString(5, item.getItemSupplier());
            ps.setString(6, item.getItemExpiryDate());

           
        } catch (SQLException e) {
            throw (new QueryException("Unable to paramaterise selection query", e));
        }

        try {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw (new QueryException("Unable to perform insert command", e));
        }
    }

    private int updateItem(Item item) throws QueryException {
        // Look up prepared statement
        PreparedStatement ps = statements.get(Query.UPDATEITEM);

        // update student attributes into prepared statement
        try {

            ps.setString(1, item.getItemName());
            ps.setString(2, item.getItemQuantity());
            ps.setString(3, item.getItemPrice());
            ps.setString(4, item.getItemBarcode());
            ps.setString(5, item.getItemSupplier());
            ps.setString(6, item.getItemExpiryDate());
            ps.setInt(7, item.getItemID());
            
        } catch (SQLException e) {
            throw (new QueryException("Unable to paramaterise selection query", e));
        }

        try {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw (new QueryException("Unable to perform update command", e));
        }
    }
    
     private int deleteItem(Item item) throws QueryException {
        // Look up prepared statement
        PreparedStatement ps = statements.get(Query.DELETEITEM);

        // update student attributes into prepared statement
        try {
            ps.setInt(1, item.getItemID());
            
        } catch (SQLException e) {
            throw (new QueryException("Unable to paramaterise selection query", e));
        }

        try {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw (new QueryException("Unable to perform update command", e));
        }
    }
    

    /*
     * Select list of all items
     */
    private List<Item> getAllItem() throws QueryException {
        // get prepared statement
        PreparedStatement ps = statements.get(Query.DISPLAYALLITEM);

        try (ResultSet resultSet = ps.executeQuery()) {
            List<Item> results = new ArrayList<>();
            while (resultSet.next()) {
                Item item = createItem(resultSet);
                results.add(item);
            }
            return results;
        } catch (SQLException e) {
            throw (new QueryException("Unable to execute selection query", e));
        }
    }
    
    
    
     private List<Item> getItemByName(String name, String id) throws QueryException {
        // Look up prepared statement
        PreparedStatement ps = statements.get(Query.ITEMNAME);
        try {
            // Insert grade into prepared statement
            ps.setString(1, name);
            ps.setString(2, id );
        } catch (SQLException e) {
            throw (new QueryException("Unable to paramaterise selection query", e));
        }

        try (ResultSet resultSet = ps.executeQuery()) {
            List<Item> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(createItem(resultSet));
            }
            return results;
        } catch (SQLException e) {
            throw (new QueryException("Unable to execute selection query", e));
        }
    }
     
}
