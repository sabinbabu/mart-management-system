/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author sabin
 */
public class Item {
    private int itemID;
    private String itemName;
    private String itemQuantity;
    private String itemPrice;
    private String itemBarcode;
    private String itemSupplier;
    private String itemExpiryDate;
    
      public Item( String itemName, String itemQuantity, String itemPrice, String itemBarcode, String itemSupplier, String itemExpiryDate) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.itemBarcode = itemBarcode;
        this.itemSupplier = itemSupplier;
        this.itemExpiryDate = itemExpiryDate;
    }
    
     public Item( int itemID,String itemName, String itemQuantity, String itemPrice, String itemBarcode, String itemSupplier, String itemExpiryDate) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.itemBarcode = itemBarcode;
        this.itemSupplier = itemSupplier;
        this.itemExpiryDate = itemExpiryDate;
     }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public String getItemSupplier() {
        return itemSupplier;
    }

    public void setItemSupplier(String itemSupplier) {
        this.itemSupplier = itemSupplier;
    }

    public String getItemExpiryDate() {
        return itemExpiryDate;
    }

    public void setItemExpiryDate(String itemExpiryDate) {
        this.itemExpiryDate = itemExpiryDate;
    }
    
}
