package assignment6;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

public class JFrameL extends MyFrame {
    /** Creates a new instance of JFrameL */
    public JFrameL(CheckingAccount checkingAccount, DecimalFormat df) {
    super(checkingAccount, df);
        FrameListener listener = new FrameListener();
        addWindowListener(listener);
    }
    private class FrameListener extends WindowAdapter
    {

        public void windowClosing(WindowEvent e) {
            int confirm;
//            if (!MyElements4.saved)
//            {
//                String  message = "The data in the application is not saved.\n"+
//                        "Would you like to save it before exiting the application?";
//                confirm = JOptionPane.showConfirmDialog (null, message);
//                if (confirm == JOptionPane.YES_OPTION)
//                    MyElements4.chooseFile(2);
//            }
            System.exit(0);
        }
    }
}