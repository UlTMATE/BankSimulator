package view;
import model.*;
import controller.*;
/**
 *
 * @author UlTMATE
 */
import java.awt.*;
import javax.swing.*;

public class Home {

    public static JFrame homeFrame;
    public static JDesktopPane deskPane;
    public static JInternalFrame newAccFrame, closeAccFrame, depositFrame, withdrawFrame;
    JMenuBar jmb;
    JMenu account, transaction;
    JMenuItem newMI, closeMI, exitMI, depositMI, withdrawMI;

    public Home() {
        createGUI();
    }

    public void createGUI() {
        homeFrame = new JFrame("Bank Simulator");
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimScr = Toolkit.getDefaultToolkit().getScreenSize();
        homeFrame.setSize(dimScr.width - 50, dimScr.height - 80);
        homeFrame.setLocationRelativeTo(null);
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception exc) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception exc2) {

            }
        }

        jmb = new JMenuBar();
        account = new JMenu("Account");
        transaction = new JMenu("Transaction");
        newMI = new JMenuItem("New");
        newMI.addActionListener(new MenuItemListener());
        closeMI = new JMenuItem("Close");
        closeMI.addActionListener(new MenuItemListener());
        exitMI = new JMenuItem("Exit");
        exitMI.addActionListener(new MenuItemListener());
        depositMI = new JMenuItem("Deposit");
        depositMI.addActionListener(new MenuItemListener());
        withdrawMI = new JMenuItem("Withdraw");
        withdrawMI.addActionListener(new MenuItemListener());
        account.add(newMI);
        account.add(closeMI);
        account.add(exitMI);
        transaction.add(depositMI);
        transaction.add(withdrawMI);
        jmb.add(account);
        jmb.add(transaction);
        homeFrame.setJMenuBar(jmb);

        deskPane = new JDesktopPane();
        deskPane.setOpaque(false);
        homeFrame.add(deskPane);
        homeFrame.setVisible(true);
    }

    public static void showNewAccountFrame() {
        if (newAccFrame == null || newAccFrame.isClosed()) {
            newAccFrame = new NewAccountFrame("NEW ACCOUNT");
            deskPane.add(newAccFrame);
        }
    }

    public static void showCloseAccountFrame() {
        if (closeAccFrame == null || closeAccFrame.isClosed()) {
            closeAccFrame = new CloseAccountFrame("CLOSE ACCOUNT");
            deskPane.add(closeAccFrame);
        }
    }

    public static void showDepositFrame() {
        if (depositFrame == null || depositFrame.isClosed()) {
            depositFrame = new DepositFrame("DEPOSIT");
            deskPane.add(depositFrame);
        }
    }

    public static void showWithdrawFrame() {
        if (withdrawFrame == null || withdrawFrame.isClosed()) {
            withdrawFrame = new WithdrawFrame("WITHDRAW");
            deskPane.add(withdrawFrame);
        }
    }

    public static void main(String args[]) {
        new Home();
    }
}
