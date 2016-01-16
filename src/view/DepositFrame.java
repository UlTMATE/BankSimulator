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

public class DepositFrame extends JInternalFrame implements ActionListener {
    
    static JPasswordField passPf;
    static JTextField accNumTf, nameTf, balanceTf, amtTf;
    static JButton updateBut, resetBut, closeBut;
    static ResultSet rst;
    static JLabel msgLab;
    
    public DepositFrame(String title){
        super(title, false, true, false, true);
        setSize(350,260);
        setLocation(10,280);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        JPanel centPan = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        msgLab = new JLabel("Enter Account No. and Password then Press Enter");
        msgLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        JLabel accNumLab = new JLabel("Account No. :");
        accNumTf = new JTextField("",15);
        accNumTf.requestFocus();
        accNumTf.addActionListener(this);
        JLabel passLab = new JLabel("Password :");
        passPf = new JPasswordField("",15);
        passPf.addActionListener(this);
        JLabel nameLab = new JLabel("Name :");
        nameTf = new JTextField("",15);
        nameTf.setEnabled(false);
        JLabel balanceLab = new JLabel("Balance :");
        balanceTf = new JTextField("",15);
        balanceTf.setEnabled(false);
        JLabel amtLab = new JLabel("Amount :");
        amtTf = new JTextField("",15);
        amtTf.setEnabled(false);
        
        JPanel topPan = new JPanel();
        topPan.add(msgLab);
        gbc.insets=new Insets(2,2,2,2);
        gbc.gridx=0; gbc.gridy=0;
        gbc.gridx=0; gbc.gridy=1;
        centPan.add(accNumLab, gbc); gbc.gridx=1; centPan.add(accNumTf, gbc);
        gbc.gridx=0; gbc.gridy=2;
        centPan.add(passLab, gbc); gbc.gridx=1; centPan.add(passPf, gbc);
        gbc.gridx=0; gbc.gridy=3;
        centPan.add(nameLab, gbc); gbc.gridx=1; centPan.add(nameTf, gbc);
        gbc.gridx=0; gbc.gridy=4;
        centPan.add(balanceLab, gbc); gbc.gridx=1; centPan.add(balanceTf, gbc);
        gbc.gridx=0; gbc.gridy=5;
        centPan.add(amtLab, gbc); gbc.gridx=1; centPan.add(amtTf, gbc);
        
        JPanel botPan = new JPanel();
        updateBut = new JButton("Deposit");
        updateBut.setEnabled(false);
        updateBut.addActionListener(this);
        resetBut = new JButton("Reset");
        resetBut.addActionListener(this);
        closeBut = new JButton("Close");
        closeBut.addActionListener(this);
        botPan.add(updateBut);
        botPan.add(resetBut);
        botPan.add(closeBut);
        
        add(topPan, "North");
        add(centPan, "Center");
        add(botPan, "South");
        setVisible(true);
    }
    
    public static String getAccNum(){
//        int num=0;
//        try {
//            if()
//            num = Integer.parseInt(accNumTf.getText());
//        } catch (NumberFormatException nfExc) {
//            JOptionPane.showMessageDialog(null, "Invalid Account Number", "ERROR", JOptionPane.ERROR_MESSAGE);
//        }
        return accNumTf.getText();
    }
    
    public void resetData() {
        accNumTf.setText("");
        accNumTf.requestFocus();
        passPf.setText("");
        nameTf.setText("");
        balanceTf.setText("");
        amtTf.setText("");
        updateBut.setEnabled(false);
        accNumTf.setEnabled(true);
        passPf.setEnabled(true);
        amtTf.setEnabled(false);
        msgLab.setText("Enter Account No. and Press Enter");
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
                amtTf.setEnabled(true);
                amtTf.requestFocus();
                accNumTf.setEnabled(false);
                updateBut.setEnabled(true);
                msgLab.setText("Click Reset Button to reset data");
            } else {
                msgLab.setText("Invalid AccNo. or Password");
            }
        } catch (SQLException sqlExc) {
            sqlExc.printStackTrace();
        } catch (NumberFormatException nfExc){
                        JOptionPane.showMessageDialog(null, "Invalid Account Number", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
    }
    
    @Override
    public void actionPerformed(ActionEvent axnEve){
        Object obj = axnEve.getSource();
        if (obj==passPf){
            setData();
        } else if(obj==accNumTf){
            passPf.requestFocus();
        } else if(obj == updateBut){
            try {
                rst.refreshRow();
                int bal = rst.getInt("balance");
                int amt = Integer.parseInt(amtTf.getText());
                setData();
                int option = JOptionPane.showConfirmDialog(null, "Deposit In This Account", "CONFIRM", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        rst.updateInt("balance", bal + amt);
                        rst.updateRow();
                    } catch (SQLException sqlExc) {
                        sqlExc.printStackTrace();
                    }
                    resetData();
                    if(Home.withdrawFrame.isShowing()){
                        WithdrawFrame.setData();
                    }
                }
            } catch (SQLException sqlExc) {
                sqlExc.printStackTrace();
            } catch (NumberFormatException nfExc) {
                JOptionPane.showMessageDialog(null, "Please Provide Integer Amount", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else if(obj == resetBut){
            resetData();
        } else if(obj == closeBut){
            Home.depositFrame.setVisible(false);
            Home.depositFrame.dispose();
        }
    }
}
