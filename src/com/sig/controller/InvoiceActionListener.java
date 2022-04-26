/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sig.controller;

import com.sig.model.InvoiceHeader;
import com.sig.model.InvoiceHeaderTableModel;
import com.sig.model.InvoiceLine;
import com.sig.model.InvoiceLineTableModel;
import com.sig.view.InvoiceFrame;
import com.sig.view.InvoiceHeaderDialog;
import com.sig.view.InvoiceLineDialog;
import com.sun.webkit.Timer;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shassan
 */
public class InvoiceActionListener implements ActionListener {

    private InvoiceLineDialog lineDialog;
    private InvoiceHeaderDialog headerDialog;
    private InvoiceFrame frame;
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    
    public InvoiceActionListener(InvoiceFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Load Files":
                loadfiles();
                break;
            case "Save Files":
                savefiles();
                break;

            case "New Invoice":
                createnewinvoice();
                break;

            case "Delete Invoice":
                deleteinvoice();
                break;

            case "New Line":
                createnewline();
                break;

            case "Delete Line":
                deleteline();
                break;
                
                
                case "NewInvoiceCancel":
                   InvoiceDialogCancel();
                   break;
                   
                   
            case "NewInvoiceOK":
                InvoiceDialogOk();
                break;
                
            case "newLineOK":
                newLinedialogOk();
                break;
                
                
            case "newLineCancel":
                newLinedailogCancel();
                break;
        }
    }

    private void loadfiles() {
        JFileChooser filechooser = new JFileChooser();
        try {
            int result = filechooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = filechooser.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                ArrayList<InvoiceHeader> invoiceHeaders = new ArrayList<>();
                for (String headerLine : headerLines) {
                    String[] arry = headerLine.split(",");
                    String st1 = arry[0];
                    String st2 = arry[1];
                    String st3 = arry[2];
                    int code = Integer.parseInt(st1);
                    Date invoiceDate = dateFormat.parse(st2);
                    //WHEN WRITE THIS LINE:
                    //Date invoiceDate=InvoiceFrame.dateformat.parse(st2);

                    //BDL THIS LINE :Date invoiceDate=dateFormat.parse(st2);
                    //DO PROBLEM 
                    InvoiceHeader header = new InvoiceHeader(code, st3, invoiceDate);
                    invoiceHeaders.add(header);
                }
                frame.setInvoicesArray(invoiceHeaders);
                result = filechooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File linefile = filechooser.getSelectedFile();
                    Path linepath = Paths.get(linefile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linepath);
                    ArrayList<InvoiceLine> invoicelines = new ArrayList<>();
                    for (String lineLine : lineLines) {
                        String[] arry = lineLine.split(",");
                        String st1 = arry[0];   //invoice id int
                        String st2 = arry[1];   //item name string
                        String st3 = arry[2];   //price double
                        String st4 = arry[3];   //amount  int
                        int invcode = Integer.parseInt(st1);
                        double price = Double.parseDouble(st3);
                        int amount = Integer.parseInt(st4);
                        InvoiceHeader inv = frame.getInvObject(invcode);
                        InvoiceLine line = new InvoiceLine(st2, price, amount, inv);
                        inv.getLines().add(line);
                    }
                }

                InvoiceHeaderTableModel headerTableModel = new InvoiceHeaderTableModel(invoiceHeaders);
                frame.setHeaderTableModel(headerTableModel);
                frame.getInvHTbl().setModel(headerTableModel);
                System.out.println("files read");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                     }
    frame.displayInvoices();
    }
/////////////////////////////////////////////////////////
    
    
    private void savefiles() {
        ArrayList<InvoiceHeader> invoicesarray=frame.getInvoicesArray();
        JFileChooser filechooser =new  JFileChooser();
        try{
            int result=filechooser.showSaveDialog(frame);
            if(result == JFileChooser.APPROVE_OPTION)
            {
                File headerfile=filechooser.getSelectedFile();
                FileWriter headerfw=new FileWriter(headerfile);
                String headers="";
                String Lines="";
                for(InvoiceHeader invoice:invoicesarray)
                {
                    headers+=invoice.toString();
                    headers+="\n";
                    for(InvoiceLine line:invoice.getLines())
                    {
                        Lines+=line.toString();
                        Lines+="\n";
                    }
                }
                headers=headers.substring(0,headers.length()-1);
                Lines=Lines.substring(0,Lines.length()-1);
                 result=filechooser.showSaveDialog(frame);
                 File lineFile=filechooser.getSelectedFile();
                 FileWriter linefw=new FileWriter(lineFile);
                 headerfw.write(headers);
                 linefw.write(Lines);
                        
                 headerfw.close();
                 linefw.close();
               
               // for()
               //headers.substring(0,headers.length()-1);
               
            }
                        frame.displayInvoices();

    }
       
       
        catch (IOException ex)
        {
                        JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }
    

    //////////////////////////////
    
    private void createnewinvoice() {
        headerDialog=new InvoiceHeaderDialog(frame);
        headerDialog.setVisible(true);
    }

    ///////////////////////////////

    private void createnewline() {
        lineDialog=new InvoiceLineDialog(frame);
        lineDialog.setVisible(true);
    }

    
    /////////////////////////////////////////////////////////
    
    
    private void deleteline() {
        int selectlineindex=frame.getInvLTbl().getSelectedRow();
        int selectinvoiceindex=frame.getInvLTbl().getSelectedRow();
        if(selectlineindex != -1){
            frame.getLinesArray().remove(selectlineindex);
            InvoiceLineTableModel linetablemodel=(InvoiceLineTableModel)frame.getInvLTbl().getModel();
            linetablemodel.fireTableDataChanged();
            frame.getInvtotallbl().setText(""+frame.getInvoicesArray().get(frame.getInvHTbl().getSelectedRow()).getInvoiceTotal());
            frame.getHeaderTableModel().fireTableDataChanged();
            frame.getInvHTbl().setRowSelectionInterval(selectinvoiceindex, selectinvoiceindex);
        }
                    frame.displayInvoices();

    }
    
    
   //////////////////////////////////////////////// 
    
    
    private void deleteinvoice() {
        int selectinvindex =frame.getInvHTbl().getSelectedRow();
        if(selectinvindex!=-1){
            frame.getInvoicesArray().remove(selectinvindex);
            frame.getHeaderTableModel().fireTableDataChanged();
            
            
        frame.getInvLTbl().setModel(new InvoiceLineTableModel(null));
        frame.setLinesArray(null);
        frame.getCusnamelbl().setText("");
        frame.getInvnumlbl().setText(" ");
        frame.getInvdatelbl().setText("");
        frame.getInvtotallbl().setText(" ");
        
    
        }
            frame.displayInvoices();
    }
 ////////////////////////////////////////////////
    
    
    private void InvoiceDialogCancel() {
        headerDialog.setVisible(false);
        headerDialog.dispose();
        headerDialog = null;
    }
 ////////////////////////////////////
    
    private void InvoiceDialogOk() 
    {
        headerDialog.setVisible(true);
        String cusName=headerDialog.getCustNameField().getText();
        String st=headerDialog.getInvDateField().getText();
        Date dt=new Date();
        try{
            dt=InvoiceFrame.dateformat.parse(st);
        
        } catch (ParseException ex) {
            Logger.getLogger(InvoiceActionListener.class.getName()).log(Level.SEVERE, null, ex);//ask to eng ahmed
            JOptionPane.showMessageDialog(frame, "Can't Parse this Date", "Wrong Date Format", JOptionPane.ERROR_MESSAGE);
        } 
        int invoicenumber = 0;
        for(InvoiceHeader invhdr:frame.getInvoicesArray()){
            if(invhdr.getNum()>invoicenumber)invoicenumber=invhdr.getNum();
        }
        invoicenumber++;
        InvoiceHeader inv=new InvoiceHeader(invoicenumber, cusName, dt);
        frame.getInvoicesArray().add(inv);
        frame.getHeaderTableModel().fireTableDataChanged();
        headerDialog.dispose();
        headerDialog=null;
                    frame.displayInvoices();

    } 
    
    
 ////////////////////////////////////////////////
    
    private void newLinedialogOk() {
       lineDialog.setVisible(false);
       String name=lineDialog.getItemNameField().getText();
       String str1=lineDialog.getItemCountField().getText();
       String str2=lineDialog.getItemPriceField().getText();
       int cunt=1;
       double prc=1;
       try{
           cunt=Integer.parseInt(str1);
       }catch(NumberFormatException ex){
           JOptionPane.showMessageDialog(frame, "Can't Convert This Number", "Error in Number Format",JOptionPane.ERROR_MESSAGE);
       }
       
       
       try{
           prc= Double.parseDouble(str2);
       }catch(NumberFormatException ex){
           JOptionPane.showMessageDialog(frame, "Can't Convert This Price", "Error in Number Format",JOptionPane.ERROR_MESSAGE);
       }
       
       int selectedInvoiceHeader=frame.getInvHTbl().getSelectedRow();
       if(selectedInvoiceHeader != -1){
           InvoiceHeader invheader=frame.getInvoicesArray().get(selectedInvoiceHeader);
           InvoiceLine line=new InvoiceLine(name, prc, cunt, invheader);
           frame.getLinesArray().add(line);
           InvoiceLineTableModel linetablemodel=(InvoiceLineTableModel) frame.getInvLTbl().getModel();
          linetablemodel.fireTableDataChanged();
          frame.getHeaderTableModel().fireTableDataChanged();
       }
       frame.getInvHTbl().setRowSelectionInterval(selectedInvoiceHeader, selectedInvoiceHeader);
        lineDialog.dispose();
        lineDialog = null;
                    frame.displayInvoices();

    }

    
    ///////////////////////////////////
    
    
    private void newLinedailogCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }
   
    }
        

