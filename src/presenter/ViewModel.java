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
public class ViewModel {

    List<Employee> model;
    Employee current;
    int index;
    int n;

    ViewModel() {
    }

    void set(List<Employee> m) {
        model = m;
        index = 0;
        n = model.size();
        current = model.get(index);
    }

    IndexedEmployee previous() {
        if (--index < 0) {
            index = n - 1;
        }
        return new IndexedEmployee(model.get(index), index + 1, n);
    }

    IndexedEmployee next() {
        if (++index > n - 1) {
            index = 0;
        }
        return new IndexedEmployee(model.get(index), index + 1, n);
    }

    IndexedEmployee current() {
        return new IndexedEmployee(model.get(index), index + 1, n);
    }
    
    IndexedEmployee all(){
    return new IndexedEmployee(model);
            }
}
