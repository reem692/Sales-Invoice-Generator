/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author Reem
 */
public class InvoiceHeaderLineDialog extends JDialog{
    private JTextField itemNameTF;
    private JLabel itemName;

    private JTextField countTF;
    private JLabel count;

    private JTextField itemPriceTF;
    private JLabel itemPrice;

    private JButton okButton;
    private JButton cancelButton;

    public InvoiceHeaderLineDialog(InvoiceFrame frame) {

        itemName = new JLabel("Item Name:");
        itemNameTF = new JTextField(20);
        add(itemName);
        add(itemNameTF);

        count = new JLabel("Count:");
        countTF = new JTextField(20);
        add(count);
        add(countTF);

        itemPrice = new JLabel("Item Price:");
        itemPriceTF = new JTextField(20);
        add(itemPrice);
        add(itemPriceTF);

        okButton = new JButton("OK");
        okButton.setActionCommand("itemOK");
        add(okButton);
        okButton.addActionListener(frame.getActionListener());

        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("itemCancel");
        add(cancelButton);
        cancelButton.addActionListener(frame.getActionListener());

        setLayout(new GridLayout(4, 2));
        
        pack();

    }

    public JTextField getItemNameTF() {
        return itemNameTF;
    }

    public JTextField getCountTF() {
        return countTF;
    }

    public JTextField getItemPriceTF() {
        return itemPriceTF;
    }
}
