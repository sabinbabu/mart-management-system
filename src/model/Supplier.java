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
public class Supplier {
    private int supplierID;
    private String supplierName;
    private String supplierEmail;
    private String supplierNumber;
    private String supplierAddress;

        
     public Supplier( String supplierName, String supplierEmail, String supplierNumber, String supplierAddress) {
        this.supplierName = supplierName;
        this.supplierEmail = supplierEmail;
        this.supplierNumber = supplierNumber;
        this.supplierAddress = supplierAddress;
    }
    
     public Supplier( int supplierID ,String supplierName, String supplierEmail, String supplierNumber, String supplierAddress) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.supplierEmail = supplierEmail;
        this.supplierNumber = supplierNumber;
        this.supplierAddress = supplierAddress;
    }
    
     
    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }
    
}
