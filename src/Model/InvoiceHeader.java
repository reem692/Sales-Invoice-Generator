/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Reem
 */
public class InvoiceHeader {
     private int number;
    private Date date;
    private String name;
    private double total;
    private ArrayList<InvoiceHeaderLine> invoiceLine;

    public InvoiceHeader(int number, Date date, String name) {
        this.number = number;
        this.date = date;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotal() {
        double total = 0.0;
        for (InvoiceHeaderLine invoiceHeaderLine : getInvoiceLine()) {
            total += invoiceHeaderLine.getTotal();
        }
        return total;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public ArrayList<InvoiceHeaderLine> getInvoiceLine() {

        if (invoiceLine == null) {
            invoiceLine = new ArrayList<>();
        }
        return invoiceLine;
    }

    public void setInvoiceLine(ArrayList<InvoiceHeaderLine> invoiceLine) {
        this.invoiceLine = invoiceLine;
    }
    public void addInvoiceLine(InvoiceHeaderLine line) {
        getInvoiceLine().add(line);
        setTotal(getTotal()+line.getTotal());
    }

    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
        String invoice = "Invoice No.:" + number + ", Invoice Date: " + "" + df.format(date) + ", Customer Name: " + name +", Invoice Total: " + total;
        
        for (InvoiceHeaderLine line : getInvoiceLine()) {
            invoice += "\n\t" + line;
        }
        return invoice;

    }
    


    
}
