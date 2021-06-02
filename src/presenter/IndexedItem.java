/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import java.util.List;
import model.Item;

/**
 *
 * @author sabin
 */
public class IndexedItem {
    private List<Item> itemlist;
    private Item item;
    private int i;
    private int n;

    /**
     * Create a wrapper for a Student object
     *
     * @param item
     * @param i the position of the object in the browsing context
     * @param n the number of objects in the browsing context
     */
    public IndexedItem(Item item, int i, int n) {
        this.item = item;
        this.i = i;
        this.n = n;

    }
    
    public IndexedItem(List<Item> item){
    this.itemlist = item;}

    
    public List<Item> getAllItems(){
        return itemlist;
    }
    /**
     * @return the supplier object being wrapped
     */
    public Item getItem() {
        return item;
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
