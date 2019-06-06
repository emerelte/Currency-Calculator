package currcalc;

import dataload.MainClass;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
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
    static Logger log = Logger.getLogger(AppView.class.getName());
    public static void createAndShowGUI() throws SQLException, ClassNotFoundException, IOException {
        log.info("GUI created.");
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
                log.info("App closed");
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
                    if (e.getActionCommand().isEmpty()) {
                        log.info("Empty string as argument.");
                        result.setText("");
                        return;
                    }
                    char currentVal = e.getActionCommand().charAt(0);
                    switch (currentVal){
                        case 'R':
                            log.info("Refreshing data");
                            MainClass.main(new String[0]);
                            break;
                        case 'S':
                            log.info("Showing data");
                            DataView dw = new DataView();
                            break;
                        case 'C':
                        default:
                            if (quantityText.isEmpty())
                                return;
                            try {
                                result.setText(model.calculate(currencyString, buyOrSellString, quantityText));
                                log.info("Calculations performed");
                            //} catch (NumberFormatException nfe){
                            //    log.warn("Completely wrong String");
                            //    result.setText("Illegal String");
                            } catch (IllegalArgumentException notAllCalc){
                                log.warn("Bad arguments entered");
                                result.setText(notAllCalc.getMessage());
                            }
                            break;
                    }
                } catch (Exception exc) {
                    log.error("Exception caught: " + exc.toString());
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
        jf.setResizable(false);
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
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    log.error("Exception in createAndShowGui");
                    e.printStackTrace();
                }
            }
        });
    }
}