/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.InvoiceHeaderLine;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Reem
 */
public class InvoiceHeaderLineTableModel extends AbstractTableModel {
    private ArrayList<InvoiceHeaderLine> list;

    public InvoiceHeaderLineTableModel(ArrayList<InvoiceHeaderLine> list) {
        this.list = list;
    }

    public ArrayList<InvoiceHeaderLine> getList() {
        return list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0:
                return rowIndex+1;
            case 1:
                return list.get(rowIndex).getName();
            case 2:
                return list.get(rowIndex).getPrice();
            case 3:
                return list.get(rowIndex).getCount();
            case 4:
                return list.get(rowIndex).getTotal();
            default:
                return "";
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 1:
                list.get(rowIndex).setName((String) aValue);
                break;
            case 2:
                list.get(rowIndex).setPrice((Double) aValue);
                break;
            case 3:
                list.get(rowIndex).setCount((Integer) aValue);
                break;
            default:
                break;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
                case 0:
                    return "No.";
                case 1:
                    return "Item Name";
                case 2:
                    return "Item Price";
                case 3:
                    return "Item Count";
                case 4:
                    return "Item Total";
                default:
                    return "";
            }
        }

    
}
