/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InvoiceHeader;
import Model.InvoiceHeaderLine;
import Model.InvoiceHeaderLineTableModel;
import Model.InvoiceHeaderTableModel;
import View.InvoiceFrame;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Reem
 */
public class TableActionListener implements ListSelectionListener{
    private InvoiceFrame frame;
    public TableActionListener(InvoiceFrame frame) {
        this.frame = frame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
         int selectedRow = frame.getInvoiceTable().getSelectedRow();
        if (selectedRow >= 0) {
            frame.setInvoiceTableModel(new InvoiceHeaderTableModel(frame.getInvoices()));
            InvoiceHeader row = frame.getInvoiceTableModel().getList().get(selectedRow);
            frame.getCustomerName().setText(row.getName());
            frame.getInvoiceDate().setText(frame.df.format(row.getDate()));
            frame.getInvoiceNumber().setText(String.valueOf(row.getNumber()));
            frame.getInvoiceTotal().setText(String.valueOf(row.getTotal()));
            ArrayList<InvoiceHeaderLine> invoiceLine = row.getInvoiceLine();
            frame.setInvoiceLineTableModel(new InvoiceHeaderLineTableModel(invoiceLine));
            frame.getInvoiceItemsTable().setModel(frame.getInvoiceLineTableModel());
            frame.getInvoiceLineTableModel().fireTableDataChanged();
        }
    
    }
}
