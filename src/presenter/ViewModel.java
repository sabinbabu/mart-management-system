/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import java.util.List;
import model.Employee;
import model.Supplier;

/**
 *
 * @author PC
 */
public class ViewModel {

    List<Employee> model;
    Employee current;
    int index;
    int n;
    
    
    List<Supplier> modelSupplier;
    Supplier currentSupplier;
    int indexSupplier;
    int nSupplier;

    ViewModel() { }

    void set(List<Employee> m) {
        model = m;
//        index = 0;
//        n = model.size();
//        current = model.get(index);
    }

    
    IndexedEmployee all(){
    return new IndexedEmployee(model);
            }
    
    
    
    // SUPPLIER
    
    void setSupplier(List<Supplier> m) {
        modelSupplier = m;
      //  indexSupplier = 0;
      //  nSupplier = modelSupplier.size();
       // currentSupplier = modelSupplier.get(indexSupplier);
    }

 
    IndexedSupplier allSupplier(){
              return new IndexedSupplier(modelSupplier);
     }
}
