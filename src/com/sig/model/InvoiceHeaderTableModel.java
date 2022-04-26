/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sig.model;

import com.sig.view.InvoiceFrame;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author shassan
 */
public class InvoiceHeaderTableModel extends AbstractTableModel{

    private ArrayList<InvoiceHeader> invoicesArray;
    private String [] columns ={ "Invoice Num", "Invoice Date","customer Name","Invoice Total"};
    public InvoiceHeaderTableModel(ArrayList<InvoiceHeader> invoicesArray) {
        this.invoicesArray = invoicesArray;
    }
    
    @Override
    public int getRowCount() {
        return invoicesArray.size();
    }

    @Override
    public int getColumnCount() {
             return columns.length;
    }

    /**
     *
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
           InvoiceHeader inv=invoicesArray.get(rowIndex);
           switch (columnIndex){
               case 0:
                 return inv.getNum();
                           case 1:
                               return InvoiceFrame.dateformat.format(inv.getInvDate());
                           case 2:
                               return inv.getCustomer();
                           case 3:
                               return inv.getInvoiceTotal();
           }
        return "";

    }
    @Override
    public String getColumnName(int column){
        {
        return columns[column];
    }
    }
}
    
    

