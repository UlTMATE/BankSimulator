package controller;
import view.*;
/**
 *
 * @author UlTMATE
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuItemListener implements ActionListener{
    
    @Override
    public void actionPerformed(ActionEvent axnEve){
        String obj = axnEve.getActionCommand();
        if (obj.equals("New")){
            Home.showNewAccountFrame();
        } else if (obj.equals("Close")){
            Home.showCloseAccountFrame();
        } else if (obj.equals("Exit")){
            Home.homeFrame.setVisible(false);
            Home.homeFrame.dispose();
        } else if (obj.equals("Deposit")){
            Home.showDepositFrame();
        } else if (obj.equals("Withdraw")){
            Home.showWithdrawFrame();
        }
    }
}
