/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sig.controller;

import com.sig.model.InvoiceHeader;
import com.sig.model.InvoiceHeader;
import com.sig.model.InvoiceLine;
import com.sig.model.InvoiceLine;
import com.sig.model.InvoiceLineTableModel;
import com.sig.view.InvoiceFrame;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author shassan
 */
public class InvoiceTableSelectionListener implements ListSelectionListener {
 private InvoiceFrame frame;

    public InvoiceTableSelectionListener(InvoiceFrame frame) {
        this.frame = frame;
    }
    
 
 
 @Override
    public void valueChanged(ListSelectionEvent e) {
        int SelectedInvIndex=frame.getInvHTbl().getSelectedRow();
        System.out.println(" Invoice Selected :"+ SelectedInvIndex);
        if(SelectedInvIndex !=-1)
        {
        InvoiceHeader SelectedInv=frame.getInvoicesArray().get(SelectedInvIndex);
        ArrayList <InvoiceLine> lines=SelectedInv.getLines();
        InvoiceLineTableModel  linetablemodel=new InvoiceLineTableModel(lines);
        frame.setLinesArray(lines);
        frame.getInvLTbl().setModel(linetablemodel);
        frame.getCusnamelbl().setText(SelectedInv.getCustomer());
        frame.getInvnumlbl().setText(" "+SelectedInv.getNum());
        frame.getInvdatelbl().setText(InvoiceFrame.dateformat.format(SelectedInv.getInvDate()));
        frame.getInvtotallbl().setText(" "+SelectedInv.getInvoiceTotal());
        
    }
    }
    
}
