/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InvoiceHeaderLineTableModel;
import Model.InvoiceHeaderTableModel;
import Model.InvoiceHeader;
import Model.InvoiceHeaderLine;
import View.InvoiceFrame;
import View.InvoiceHeaderDialog;
import View.InvoiceHeaderLineDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Reem
 */
public class InvoiceActionListener implements ActionListener{
    private InvoiceFrame frame;
    private InvoiceHeaderDialog invoiceHeaderDialog;
    private InvoiceHeaderLineDialog invoiceHeaderLineDialog;
    private ArrayList<InvoiceHeaderLine> invoiceLines;


    public InvoiceActionListener(InvoiceFrame frame) {
        this.frame = frame;
        invoiceLines = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("createNewInvoice")) {
              createNewInvoice();
        } else if (e.getActionCommand().equals("createOk")) {
            createInvoiceOk();
        } else if (e.getActionCommand().equals("createCancel")) {
            createInvoiceCancel();
        } else if (e.getActionCommand().equals("deleteInvoice")) {
            deleteInvoice();
        } else if (e.getActionCommand().equals("itemOK")) {
            saveItemOk();
        } else if (e.getActionCommand().equals("itemCancel")) {
           saveItemCancel();
        } else if (e.getActionCommand().equals("save")) {
           saveChanges();
        } else if (e.getActionCommand().equals("cancel")) {
           cancelChanges();
        } else if (e.getActionCommand().equals("loadFile")) {
            try {
                loadFile();
            } catch (Exception ex) {
                //Logger.getLogger(InvoiceActionListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getActionCommand().equals("saveFile")) {
           saveFile();
        } 
    }
    
     private void createNewInvoice() {
        invoiceHeaderDialog = new InvoiceHeaderDialog(frame);
        invoiceHeaderDialog.setVisible(true);
    }
     private void createInvoiceOk() {
        String customerName = invoiceHeaderDialog.getCustomerNameTF().getText();
        String invoiceDate = invoiceHeaderDialog.getDateTF().getText();
        Date date = new Date();
        try {
            date = frame.df.parse(invoiceDate);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Wrong Date Format!", "Error", JOptionPane.WARNING_MESSAGE);
            
        }
       
        invoiceHeaderDialog.setVisible(false);
        int number = getInvoiceNumber();
        InvoiceHeader invoiceHeader = new InvoiceHeader(number, date, customerName);
        frame.getInvoices().add(invoiceHeader);
        frame.setInvoiceTableModel(new InvoiceHeaderTableModel(frame.getInvoices()));
        frame.getInvoiceTable().setModel(frame.getInvoiceTableModel());
        frame.getInvoiceTableModel().fireTableDataChanged();
    } 

    private int getInvoiceNumber() {
       int number = 0;
        for (InvoiceHeader header : frame.getInvoices()) {
            if (header.getNumber() > number) {
                number = header.getNumber();
            }
        }
        return number + 1;
    }
    
    private void createInvoiceCancel() {
        invoiceHeaderDialog.setVisible(false);
    }
    
      private void deleteInvoice() {
        int row = frame.getInvoiceTable().getSelectedRow();
        frame.getInvoiceTableModel().removeRow(row);
        frame.getInvoiceTable().setModel(frame.getInvoiceTableModel());
        frame.getInvoiceTableModel().fireTableDataChanged();
        frame.setInvoiceLineTableModel(new InvoiceHeaderLineTableModel(frame.getInvoiceLines()));
        frame.getInvoiceItemsTable().setModel(frame.getInvoiceLineTableModel());
        frame.getInvoiceLineTableModel().fireTableDataChanged();
        frame.getInvoiceNumber().setText("");
        frame.getInvoiceDate().setText("");
        frame.getCustomerName().setText("");
        frame.getInvoiceTotal().setText("");
      }
      
     private void saveItemOk() {
        String itemName = invoiceHeaderLineDialog.getItemNameTF().getText();        
        int count = Integer.parseInt(invoiceHeaderLineDialog.getCountTF().getText());
        double itemPrice = Double.parseDouble(invoiceHeaderLineDialog.getItemPriceTF().getText());
        invoiceHeaderLineDialog.setVisible(false);

        int row = frame.getInvoiceTable().getSelectedRow();
       
        InvoiceHeader invoiceHeader = frame.getInvoices().get(row);
        InvoiceHeaderLine headerLine = new InvoiceHeaderLine(itemName, itemPrice, count, invoiceHeader);
        invoiceHeader.addInvoiceLine(headerLine);
        frame.getInvoiceTotal().setText(String.valueOf(invoiceHeader.getTotal()));
        frame.getInvoiceTableModel().fireTableDataChanged();
        frame.getInvoiceLineTableModel().fireTableDataChanged();
        
    }
  
    private void saveItemCancel() {
         invoiceHeaderLineDialog.setVisible(false);
    }
    private void saveChanges() {
       invoiceHeaderLineDialog = new InvoiceHeaderLineDialog(frame);
       invoiceHeaderLineDialog.setVisible(true);
    }
    private void cancelChanges() {
        int selectedRow = frame.getInvoiceTable().getSelectedRow();
        InvoiceHeader row = frame.getInvoiceTableModel().getList().get(selectedRow);
        int lines = row.getInvoiceLine().size();
        int size = findSize(invoiceLines, row);
        System.out.println(lines);
        System.out.println(size);
       
        while(lines>size)
        {
            frame.getInvoiceLineTableModel().getList().remove(lines-1);
            lines--;
        }
        InvoiceHeader invoiceHeader = frame.getInvoiceTableModel().getList().get(frame.getInvoiceTable().getSelectedRow());
        frame.getInvoiceTotal().setText(String.valueOf(invoiceHeader.getTotal()));
        frame.getInvoiceLineTableModel().fireTableDataChanged();
        frame.getInvoiceTableModel().fireTableDataChanged();
    }  
    
    private void loadFile() throws Exception{
        frame.getInvoices().clear();
        frame.getInvoiceLines().clear();
        
        JOptionPane.showMessageDialog(frame, "Please select Invoice Header", "Invoice Header", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            FileReader fr = null;
            BufferedReader br = null;
            try {
                File file = fc.getSelectedFile();
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    String[] headerLine = line.split(",");
                    if (headerLine.length < 3) {
                        JOptionPane.showMessageDialog(frame, "Wrong file format!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                    int number = Integer.parseInt(headerLine[0]);
                    Date date = frame.df.parse(headerLine[1]);
                    String customerName = headerLine[2];
                    InvoiceHeader header = new InvoiceHeader(number, date, customerName);
                    frame.getInvoices().add(header);
                }
                JOptionPane.showMessageDialog(frame, "Please select Invoice Line", "Invoice Line", JOptionPane.INFORMATION_MESSAGE);
                result = fc.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                    fr = new FileReader(file);
                    br = new BufferedReader(fr);
                    line = null;
                    while ((line = br.readLine()) != null) {
                        String[] InvoiceLines = line.split(",");
                        if (InvoiceLines.length < 4) {
                        JOptionPane.showMessageDialog(frame, "Wrong file format!", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                        int number = Integer.parseInt(InvoiceLines[0]);
                        String customerName = InvoiceLines[1];
                        double price = Double.parseDouble(InvoiceLines[2]);
                        int count = Integer.parseInt(InvoiceLines[3]);
                        InvoiceHeader header = findInvoiceByNumber(number);
                        InvoiceHeaderLine invoiceLine = new InvoiceHeaderLine(customerName, price, count, header);
                        header.addInvoiceLine(invoiceLine);
                        invoiceLines.add(invoiceLine);
                    }
                    InvoiceHeaderTableModel headerTableModel = new InvoiceHeaderTableModel(frame.getInvoices());
                    frame.getInvoiceTable().setModel(headerTableModel);
                    frame.getInvoiceTable().validate();
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, "File not found!", "Error", JOptionPane.WARNING_MESSAGE);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(frame, "Wrong date format!", "Error", JOptionPane.WARNING_MESSAGE);
            } finally {
                try {
                    br.close();
                    fr.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        }
        printInvoices();

    }
    private void saveFile() {
        JOptionPane.showMessageDialog(frame, "Please choose Invoice Header to Save", "Invoice Header", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            PrintWriter pr = null;
            try {
                File file = fc.getSelectedFile();
                pr = new PrintWriter(file);
                for (InvoiceHeader header : frame.getInvoices()) {
                    pr.printf("%d,%s,%s",header.getNumber(),frame.df.format(header.getDate()),header.getName());
                    pr.println();
                }
                JOptionPane.showMessageDialog(frame, "Invoice Header saved successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, "File not found!", "Error", JOptionPane.WARNING_MESSAGE);
            } finally {
                pr.close();
            }
        }
        JOptionPane.showMessageDialog(frame, "Please choose Invoice Line to Save", "Invoice Line", JOptionPane.INFORMATION_MESSAGE);
        fc = new JFileChooser();
        result = fc.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            PrintWriter pr = null;
            try {
                File file = fc.getSelectedFile();
                pr = new PrintWriter(file);
                for (InvoiceHeader header : frame.getInvoices()) {
                    for(InvoiceHeaderLine line :header.getInvoiceLine()){
                        pr.printf("%s,%s,%s,%d",header.getNumber(),line.getName(),line.getPrice(),line.getCount());
                        pr.println();
                    }
                }   JOptionPane.showMessageDialog(frame, "Invoice Line saved successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                pr.close();
            }
        }
    }
    private InvoiceHeader findInvoiceByNumber(int number) {
       for (InvoiceHeader header : frame.getInvoices()) {
            if (header.getNumber() == number) {
                return header;
            }
        }
        return null;  
    }
    
     private void printInvoices() {
        for (InvoiceHeader invoiceHeader : frame.getInvoices()) {
            System.out.println(invoiceHeader);
        }
    }
     
    private int findSize(ArrayList<InvoiceHeaderLine> lines, InvoiceHeader header)
    {
        int size = 0;
        for(int i = 0; i<lines.size(); i++)
        {
            if(lines.get(i).getHeader().getNumber() == header.getNumber())
            {
                size++;
            }
        }
        return size;
    }

}