package currcalc;

import currcalc.exception.NotAllowedCalculations;

import java.sql.SQLException;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Class performing funcionality of the programm
 */
public class AppModel {
    DataBaseDownloader dataBaseConnector;
    TreeMap<String, Double> buyMap;
    TreeMap<String, Double> sellMap;

    /**
     * Constructor - creates DataBaseDownloader object. Loading data from the database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public AppModel() throws SQLException, ClassNotFoundException {
        dataBaseConnector = new DataBaseDownloader("jdbc:mysql://mysql.agh.edu.pl:3306/mtobiasz","mtobiasz", "csjH6UN5BPS7VvYY");
        String buyTableName = "exchange_buy";
        String sellTableName = "exchange_sell";
        buyMap = dataBaseConnector.getKeysAndVals(buyTableName);
        sellMap = dataBaseConnector.getKeysAndVals(sellTableName);
    }

    /**
     * This method should be called before destruction of the object
     * @throws SQLException
     */
    public void close() throws SQLException {
        dataBaseConnector.close();
    }

    /**
     * Method performing calculations, how much does the particular currency cost
     * @param m_currency name of the currency
     * @param m_buyOrSell type of operation (buying or selling)
     * @param m_quantity how much money there is to buy/sell
     * @return String representing the cost
     * @exception NotAllowedCalculations
     */
    public String calculate(String m_currency, String m_buyOrSell, String m_quantity) throws NotAllowedCalculations, NumberFormatException {
        if (Double.parseDouble(m_quantity) < 0)
            throw new NotAllowedCalculations("Use positive numbers");
        double result;
        if (m_buyOrSell == "BUY") {
            result = buyMap.get(m_currency)*Double.parseDouble(m_quantity);
        }
        else{
            result = sellMap.get(m_currency)*Double.parseDouble(m_quantity);
        }
        return Double.toString(result);
    }
    /**
     * Getter #1
     * @return Vector<String> names of currencies
     */
    public Vector<String> getCurrencies(){
        Vector<String> currenciesVec = new Vector<String>(buyMap.keySet());
        return currenciesVec;
    }

    /**
     * Getter #2
     * @return TreeMap with names and prices (buy) of currencies
     */
    public TreeMap<String,Double> getBuyMap(){
        return buyMap;
    }

    /**
     * Getter #3
     * @return TreeMap with names and prices (sell) of currencies
     */
    public TreeMap<String,Double> getSellMap(){
        return sellMap;
    }

    /**
     * Getter #4
     * @return Latest date of refreshment of the table
     */
    public String getDate(String tableName) throws SQLException {
        return dataBaseConnector.getDate(tableName).toString();
    }
}
