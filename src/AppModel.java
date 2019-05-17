import java.sql.SQLException;
import java.util.TreeMap;
import java.util.Vector;

public class AppModel {
    Downloader dataBaseConnector;
    TreeMap<String, Double> buyMap;
    TreeMap<String, Double> sellMap;
    public AppModel() throws SQLException, ClassNotFoundException {
        dataBaseConnector = new Downloader("jdbc:mysql://mysql.agh.edu.pl:3306/mtobiasz","mtobiasz", "csjH6UN5BPS7VvYY");
        String buyTableName = "exchange_buy";
        String sellTableName = "exchange_sell";
        buyMap = dataBaseConnector.getKeysAndVals(buyTableName);
        sellMap = dataBaseConnector.getKeysAndVals(sellTableName);
    }
    public void close() throws SQLException {
        dataBaseConnector.close();
    }
    public Vector<String> getCurrencies(){
        Vector<String> currenciesVec = new Vector<String>();
        for (String currency : buyMap.keySet()){
            currenciesVec.add(currency);
        }
        return currenciesVec;
    }
    public String calculate(String m_currency, String m_buyOrSell, String m_quantity){
        double result;
        if (m_buyOrSell == "BUY") {
            result = buyMap.get(m_currency)*Double.parseDouble(m_quantity);
        }
        else{
            result = sellMap.get(m_currency)*Double.parseDouble(m_quantity);
        }
        return Double.toString(result);
    }
    public TreeMap<String,Double> getBuyMap(){
        return buyMap;
    }
    public TreeMap<String,Double> getSellMap(){
        return sellMap;
    }
    public String getDate() throws SQLException {
        return dataBaseConnector.getDate("exchange_buy").toString();
    }
}
