/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 * IView provides a generic interface for the display of browsable records
 *
 * @author PC
 */
public interface IView<T,S,I> {

    void displayRecord(T r);
  void displayMessage(String m);
   void displayError(String e);
  //  void displayTextArea(String t);
   // void notifySize(T r);
   void populateCombobox(T r);
  
   
   
   void displaySupplierRecord(S s);
   void populateSupplierCombobox(S s);
   void displaySupplierMessage(String m);
   
   
   void displayItemRecord(I i);
   void populateItemCombobox(I i);
   void displayItemMessage(String m);
}
