/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.InvoiceHeader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Reem
 */
public class InvoiceHeaderTableModel extends AbstractTableModel {
    private ArrayList<InvoiceHeader> list;

    public InvoiceHeaderTableModel(ArrayList<InvoiceHeader> list) {
        this.list = list;
    }

    public ArrayList<InvoiceHeader> getList() {
        return list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getNumber();
            case 1:
                return new SimpleDateFormat("dd-MM-yyyy").format(list.get(rowIndex).getDate());
            case 2:
                return list.get(rowIndex).getName();
            case 3:
                return list.get(rowIndex).getTotal();
            default:
                return "";
        }

    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "No.";
            case 1:
                return "Date";
            case 2:
                return "Customer";
            case 3:
                return "Total";
            default:
                return "";
        }
    }
    public void removeRow(int row) {
        list.remove(row);
    }
}
