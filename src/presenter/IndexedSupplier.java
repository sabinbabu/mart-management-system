/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import java.util.List;
import model.Supplier;

/**
 *
 * @author sabin
 */
public class IndexedSupplier {
    
    private List<Supplier> suplist;
    private Supplier sup;
    private int i;
    private int n;

    /**
     * Create a wrapper for a Student object
     *
     * @param s the object to be wrapped
     * @param i the position of the object in the browsing context
     * @param n the number of objects in the browsing context
     */
    public IndexedSupplier(Supplier sup, int i, int n) {
        this.sup = sup;
        this.i = i;
        this.n = n;

    }
    
    public IndexedSupplier(List<Supplier> sup){
    this.suplist = sup;}

    
    public List<Supplier> getAllSupplier(){
        return suplist;
    }
    /**
     * @return the supplier object being wrapped
     */
    public Supplier getSupplier() {
        return sup;
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
