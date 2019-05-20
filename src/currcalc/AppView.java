package currcalc;

import dataload.MainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

/**
 * Class that creates a graphical interface of the calculator
 */
public class AppView {
    /**
     * Method responsible for graphics and events functionality.
     * It uses AppModel object to perform logical functionality of programm
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void createAndShowGUI() throws SQLException, ClassNotFoundException {
        JFrame jf = new JFrame("Currency Calulator");
        AppModel model = new AppModel();
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    model.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                System.out.println("Everything closed");
            }
        };
        jf.addWindowListener(exitListener); //in aim to close AppModel properly

        final TextField result = new TextField();
        JComboBox currency = new JComboBox(model.getCurrencies());
        JComboBox buyOrSell = new JComboBox(new String[]{"BUY", "SELL"});
        TextField quantity = new TextField();
        JButton calculate = new JButton("Calculate");
        JButton refreshData = new JButton("RefreshData");
        JButton showData = new JButton("Show data");
        ActionListener myActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String currencyString = (String) currency.getSelectedItem();
                    String buyOrSellString = (String) buyOrSell.getSelectedItem();
                    String quantityText = quantity.getText();
                    char currentVal = e.getActionCommand().charAt(0);
                    System.out.println(currentVal);
                    switch (currentVal){
                        case 'R':
                            MainClass.main(new String[0]);
                            break;
                        case 'S':
                            DataView dw = new DataView();
                            break;
                        case 'C':
                        default:
                            result.setText(model.calculate(currencyString,buyOrSellString,quantityText));
                            break;
                    }
                } catch (Exception exc) {
                    result.setText(exc.toString());
                }
            }
        };

        jf.getContentPane().add(result, BorderLayout.NORTH);

        JPanel jp = new JPanel();
        jf.getContentPane().add(jp, BorderLayout.CENTER);
        jp.setLayout(new GridLayout(1, 3));
        jp.add(currency);jp.add(buyOrSell);jp.add(quantity);

        currency.addActionListener(myActionListener);
        buyOrSell.addActionListener(myActionListener);
        quantity.addActionListener(myActionListener);
        showData.addActionListener(myActionListener);

        JPanel jp2 = new JPanel();
        jf.getContentPane().add(jp2,BorderLayout.SOUTH);
        jp2.setLayout(new GridLayout(1,3));
        jp2.add(calculate);jp2.add(refreshData);jp2.add(showData);

        calculate.addActionListener(myActionListener);
        refreshData.addActionListener(myActionListener);

        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Main function that creates a new thread running the GUI
     * @param args arguments from console, not used
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void main(String args[]) throws SQLException, ClassNotFoundException {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}