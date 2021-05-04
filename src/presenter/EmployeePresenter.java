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
import static model.MartManagementSystemModel.Query.ALL;
import static model.MartManagementSystemModel.Query.DELETE;
import static model.MartManagementSystemModel.Query.EMPLOYEENAME;
import static model.MartManagementSystemModel.Query.INSERT;
import static model.MartManagementSystemModel.Query.UPDATE;
import model.QueryException;
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
            view.displayMessage("No records found");
            return;
        }
        view.displayRecord(viewmodel.all());
      
    }          
      
      
      public void populateEmployeeCombobox(){
          try{
            List results = queries.select(ALL);
             viewmodel.set(results);
             if (results.isEmpty()) {
            view.displayMessage("No records found");
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
      
      
}
