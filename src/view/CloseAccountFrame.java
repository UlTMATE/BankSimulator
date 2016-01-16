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

public class CloseAccountFrame extends JInternalFrame implements ActionListener {

    static JPasswordField passPf;
    static JTextField accNumTf, nameTf, balanceTf;
    static JButton deleteBut, resetBut, closeBut;
    static ResultSet rst;
    static JLabel msgLab;

    public CloseAccountFrame(String title) {
        super(title, false, true, false, true);
        setSize(350, 240);
        setLocation(370, 10);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        JPanel centPan = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel accNumLab = new JLabel("Account No. :");
        accNumTf = new JTextField("", 15);
        JLabel passLab = new JLabel("Password :");
        passPf = new JPasswordField("", 15);
        passPf.addActionListener(this);
        JLabel nameLab = new JLabel("Name :");
        nameTf = new JTextField("", 15);
        nameTf.setEnabled(false);
        JLabel balanceLab = new JLabel("Balance :");
        balanceTf = new JTextField("", 15);
        balanceTf.setEnabled(false);
        msgLab = new JLabel("");

        JPanel topPan = new JPanel();
        topPan.add(msgLab);
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centPan.add(accNumLab, gbc);
        gbc.gridx = 1;
        centPan.add(accNumTf, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        centPan.add(passLab, gbc);
        gbc.gridx = 1;
        centPan.add(passPf, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        centPan.add(nameLab, gbc);
        gbc.gridx = 1;
        centPan.add(nameTf, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        centPan.add(balanceLab, gbc);
        gbc.gridx = 1;
        centPan.add(balanceTf, gbc);

        JPanel botPan = new JPanel();
        deleteBut = new JButton("Delete");
        deleteBut.addActionListener(this);
        resetBut = new JButton("Reset");
        resetBut.addActionListener(this);
        closeBut = new JButton("Close");
        closeBut.addActionListener(this);
        botPan.add(deleteBut);
        botPan.add(resetBut);
        botPan.add(closeBut);

        add(topPan, "North");
        add(centPan, "Center");
        add(botPan, "South");
        setVisible(true);
    }

    public void resetFrame() {
        accNumTf.setText("");
        accNumTf.setEnabled(true);
        accNumTf.requestFocus();
        passPf.setText("");
        passPf.setEnabled(true);
        nameTf.setText("");
        balanceTf.setText("");
        msgLab.setText("");
    }

    public static void setData() {
        try {
            int accNum = Integer.parseInt(accNumTf.getText());
            rst = new Database().getData("SELECT * FROM account WHERE accNum=" + accNum + "");
            if (rst.next()) {
                nameTf.setText(rst.getString("name"));
                balanceTf.setText(rst.getString("balance"));
                accNumTf.setEnabled(false);
                passPf.setEnabled(false);
                deleteBut.requestFocus();
                msgLab.setText("");
            } else {
                msgLab.setText("Account not found, check AccNo. and Password");
            }
        } catch (SQLException sqlExc) {
            sqlExc.printStackTrace();
        } catch (NumberFormatException nfExc) {
            JOptionPane.showMessageDialog(null, "Invalid Account Number", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent axnEve) {
        Object obj = axnEve.getSource();
        if (obj == passPf) {
            setData();
        } else if (obj == deleteBut) {
            if (nameTf.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please Provide Account No. and Password then press Enter", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int option = JOptionPane.showConfirmDialog(null, "Delete this Account", "CONFIRM", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        rst.deleteRow();
                    } catch (SQLException sqlExc) {
                        sqlExc.printStackTrace();
                    }
                    resetFrame();
                    if (DepositFrame.getAccNum().equals(accNumTf.getText())) {
                        Home.depositFrame.dispose();
                    }
                    if (WithdrawFrame.getAccNum().equals(accNumTf.getText())) {
                        Home.withdrawFrame.dispose();
                    }
                }
            }
        } else if (obj == resetBut) {
            resetFrame();
        } else if (obj == closeBut) {
            Home.closeAccFrame.setVisible(false);
            Home.closeAccFrame.dispose();
        }
    }
}
