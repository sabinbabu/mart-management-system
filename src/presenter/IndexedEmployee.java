/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import java.util.List;
import model.Employee;

/**
 *
 * @author PC
 */
public class IndexedEmployee {
    
    private List<Employee> emplist;
    private Employee emp;
    private int i;
    private int n;

    /**
     * Create a wrapper for a Student object
     *
     * @param s the object to be wrapped
     * @param i the position of the object in the browsing context
     * @param n the number of objects in the browsing context
     */
    public IndexedEmployee(Employee emp, int i, int n) {
        this.emp = emp;
        this.i = i;
        this.n = n;

    }
    
    public IndexedEmployee(List<Employee> emp){
    this.emplist = emp;}

    
    public List<Employee> getAllEmployee(){
        return emplist;
    }
    /**
     * @return the student object being wrapped
     */
    public Employee getEmployee() {
        return emp;
    }

    /**
     * @return the position of the wrapped student object in the browsing
     * context
     */
    public int getIndex() {
        return i;
    }

    /**
     * @return the number of objects in the browsing context
     */
    public int getSize() {
        return n;
    }

}
