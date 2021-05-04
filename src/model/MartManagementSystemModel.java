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
public class MartManagementSystemModel implements  IConnect, IQuery<MartManagementSystemModel.Query, Employee> {
    
     public static enum Query {
        INSERT,ALL,EMPLOYEENAME,UPDATE,DELETE
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
        sqlCommands.put(MartManagementSystemModel.Query.INSERT,
                "INSERT INTO EMPLOYEE (EMPLOYEENAME, EMPLOYEEEMAIL, EMPLOYEENUMBER, EMPLOYEEPASSWORD ) VALUES ( ?, ?, ?, ? )");
         //get records of all students
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
    public List<Employee> select(Query q, Object... o) throws QueryException {
        switch (q) {
             case ALL:
                return getAllEmployee();
             case EMPLOYEENAME:
                return getEmployeeByName((String) o[0], (String) o[1]);
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
    
}
