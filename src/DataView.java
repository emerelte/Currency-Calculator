import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.TreeMap;
import java.util.Vector;

public class DataView {
    public static void createAndShowGUI() throws SQLException, ClassNotFoundException {
        JFrame jf = new JFrame("Data");
        AppModel model = new AppModel();
        TreeMap<String, Double> buyMap = model.getBuyMap();
        TreeMap<String, Double> sellMap = model.getSellMap();

        JTextField date = new JTextField();
        date.setEditable(false); date.setText("Last refreshment: " + model.getDate());
        jf.getContentPane().add(date, BorderLayout.NORTH);

        JPanel jp = new JPanel();
        jf.getContentPane().add(jp, BorderLayout.CENTER);
        jp.setLayout(new GridLayout(buyMap.size()+1, 3));
        Vector<String> keyStrings = model.getCurrencies();
        JTextField keys[] = new JTextField[buyMap.size()+1];
        JTextField buyValues[] = new JTextField[buyMap.size()+1];
        JTextField sellValues[] = new JTextField[sellMap.size()+1];
        keys[0]=new JTextField();keys[0].setText("Waluta");keys[0].setEditable(false);jp.add(keys[0]);
        buyValues[0]=new JTextField();buyValues[0].setText("Buy");buyValues[0].setEditable(false);jp.add(buyValues[0]);
        sellValues[0]=new JTextField();sellValues[0].setText("Waluta");sellValues[0].setEditable(false);jp.add(sellValues[0]);
        for (int i=1;i<buyMap.size()+1;++i){
            keys[i] = new JTextField();
            keys[i].setText(keyStrings.get(i-1));
            jp.add(keys[i]);
            keys[i].setEditable(false);
            buyValues[i] = new JTextField();
            buyValues[i].setText(Double.toString(buyMap.get(keyStrings.get(i-1))));
            jp.add(buyValues[i]);
            buyValues[i].setEditable(false);
            sellValues[i] = new JTextField();
            sellValues[i].setText(Double.toString(sellMap.get(keyStrings.get(i-1))));
            jp.add(sellValues[i]);
            sellValues[i].setEditable(false);
        }
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    public DataView() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
