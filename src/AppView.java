import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AppView {
    public static void createAndShowGUI() {
        JFrame jf = new JFrame("Currency Calulator");
        final TextField tf = new TextField();
        JComboBox currency = new JComboBox();
        JComboBox buyOrSell = new JComboBox();
        TextField quantity = new TextField();
        ActionListener myActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                } catch (Exception exc) {
                    tf.setText(exc.toString());
                }
            }
        };

        jf.getContentPane().add(tf, BorderLayout.NORTH);

        JPanel jp = new JPanel();
        jf.getContentPane().add(jp, BorderLayout.CENTER);
        jp.setLayout(new GridLayout(3, 1));
        jp.add(currency);jp.add(buyOrSell);jp.add(quantity);
        currency.addActionListener(myActionListener);
        buyOrSell.addActionListener(myActionListener);
        quantity.addActionListener(myActionListener);
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[]) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}