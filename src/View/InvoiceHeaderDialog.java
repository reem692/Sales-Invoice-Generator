/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Reem
 */
public class InvoiceHeaderDialog extends JDialog {
    
    private JTextField customerNameTF;
    private JTextField dateTF;
    private JLabel customerName;
    private JLabel date;
    private JButton okButton;
    private JButton cancelButton;


    public InvoiceHeaderDialog(InvoiceFrame frame) {
        customerName = new JLabel("Customer Name:");
        customerNameTF = new JTextField(20);
        add(customerName);
        add(customerNameTF);

        date = new JLabel("Invoice Date:");
        dateTF = new JTextField(20);
        add(date);
        add(dateTF);

        okButton = new JButton("OK");
        okButton.setActionCommand("createOk");
        add(okButton);
        okButton.addActionListener(frame.getActionListener());

        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("createCancel");
        add(cancelButton);
        cancelButton.addActionListener(frame.getActionListener());

        setLayout(new GridLayout(3, 2));

        pack();
    }

    public JTextField getCustomerNameTF() {
        return customerNameTF;
    }

    public JTextField getDateTF() {
        return dateTF;
    }
    
}
