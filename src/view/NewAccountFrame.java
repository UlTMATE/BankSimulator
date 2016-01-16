/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.*;
/**
 *
 * @author UlTMATE
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class NewAccountFrame extends JInternalFrame implements ActionListener {

    JPasswordField newPassPf, confirmPassPf;
    JTextField accNumTf, nameTf, amtTf;
    JButton saveBut, resetBut, closeBut;
    ResultSet rst;

    public NewAccountFrame(String title) {
        super(title, false, true, false, true);
        setSize(350, 250);

        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        JPanel centPan = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel accNumLab = new JLabel("Account No. :");
        accNumTf = new JTextField("", 15);
        accNumTf.setEnabled(false);
        JLabel newPassLab = new JLabel("New Password :");
        newPassPf = new JPasswordField("", 15);
        JLabel confirmPassLab = new JLabel("Confirm Password :");
        confirmPassPf = new JPasswordField("", 15);
        JLabel nameLab = new JLabel("Name :");
        nameTf = new JTextField("", 15);
        JLabel amtLab = new JLabel("Amount :");
        amtTf = new JTextField("", 15);

        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centPan.add(accNumLab, gbc);
        gbc.gridx = 1;
        centPan.add(accNumTf, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        centPan.add(newPassLab, gbc);
        gbc.gridx = 1;
        centPan.add(newPassPf, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        centPan.add(confirmPassLab, gbc);
        gbc.gridx = 1;
        centPan.add(confirmPassPf, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        centPan.add(nameLab, gbc);
        gbc.gridx = 1;
        centPan.add(nameTf, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        centPan.add(amtLab, gbc);
        gbc.gridx = 1;
        centPan.add(amtTf, gbc);

        JPanel botPan = new JPanel();
        saveBut = new JButton("Save");
        saveBut.addActionListener(this);
        resetBut = new JButton("Reset");
        resetBut.addActionListener(this);
        closeBut = new JButton("Close");
        closeBut.addActionListener(this);
        botPan.add(saveBut);
        botPan.add(resetBut);
        botPan.add(closeBut);

        add(centPan, "Center");
        add(botPan, "South");
        setVisible(true);

        setUpFrame();
    }

    public void setUpFrame() {
        newPassPf.setText("");
        confirmPassPf.setText("");
        nameTf.setText("");
        amtTf.setText("");
        rst = new Database().getData("SELECT * FROM account");
        try {
            if (!rst.next()) {
                accNumTf.setText("1");
            } else {
                accNumTf.setText("Create account to get this");
//                rst.last();
//                accNumTf.setText(rst.getInt("accNum") + 1 + "");
            }
        } catch (SQLException sqlExc) {
            sqlExc.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent axnEve) {
        Object obj = axnEve.getSource();
        if (obj == saveBut) {
            try {
                if (!newPassPf.getText().equals(confirmPassPf.getText())) {
                    JOptionPane.showMessageDialog(null, "Passwords Do Not Match", "WARNING", JOptionPane.ERROR_MESSAGE);
                } else if (nameTf.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Name Field Can Not Be Empty", "Provide Name", JOptionPane.ERROR_MESSAGE);
                } else if (amtTf.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Amount Field Can Not Be Empty", "Provide Amount", JOptionPane.ERROR_MESSAGE);
                } else if (Integer.parseInt(amtTf.getText()) < 0) {
                    JOptionPane.showMessageDialog(null, "Amount Can Not Be Negative", "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                } else {
                    int option = JOptionPane.showConfirmDialog(null, "Create Account ? : ", "CONFIRM", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (option == JOptionPane.YES_OPTION) {

                        rst.moveToInsertRow();
                        rst.updateString("password", confirmPassPf.getText());
                        rst.updateString("name", nameTf.getText());
                        rst.updateInt("balance", Integer.parseInt(amtTf.getText()));
                        rst.insertRow();
                        rst.last();
                        JOptionPane.showMessageDialog(null, "Amount Created with Account No. : " + rst.getString("accNum"), "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                        setUpFrame();
                    }
                }
            } catch (SQLException sqlExc) {
                sqlExc.printStackTrace();
            } catch (NumberFormatException nfExc) {
                JOptionPane.showMessageDialog(null, "Invalid Amount ", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } else if (obj == resetBut) {
            newPassPf.setText("");
            confirmPassPf.setText("");
            nameTf.setText("");
            amtTf.setText("");
        } else if (obj == closeBut) {
            Home.newAccFrame.setVisible(false);
            Home.newAccFrame.dispose();
        }
    }
}
