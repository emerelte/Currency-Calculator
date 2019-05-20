package currcalc;

import java.sql.*;
import java.util.TreeMap;

/**
 * Class performing connection to database - read only
 */
public class DataBaseDownloader {
    private Connection conn;

    /**
     * Constructor. Connects to database
     * @param myUrl URL of database
     * @param user the user name
     * @param password user's password
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public DataBaseDownloader(String myUrl, String user, String password) throws ClassNotFoundException, SQLException {
        String myDriver = "com.mysql.jdbc.Driver";
        Class.forName(myDriver);
        conn = DriverManager.getConnection(myUrl, user, password);
    }
    /**
     * Method that should be called before destruction of an object
     */
    public void close() throws SQLException {
        conn.close();
    }

    /**
     * Getter that returns names of currencies and prices (buy or sell, depending on the name of table)
     * @param tableName name of table
     * @return TreeMap containing names of currencies and its prices
     * @throws SQLException
     */
    public TreeMap<String, Double> getKeysAndVals(String tableName) throws SQLException {
        TreeMap<String, Double> data = new TreeMap<String,Double>();
        String query = "SELECT * FROM " + tableName;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        ResultSetMetaData metaData = rs.getMetaData();
        int count = metaData.getColumnCount(); //number of columns
        String columnName[] = new String[count-1];
        while(rs.next());
        rs.previous();
        for (int i = 2; i <= count; i++)
        {
            columnName[i-2] = metaData.getColumnLabel(i);
            data.put(columnName[i-2],rs.getDouble(columnName[i-2]));
        }
        return data;
    }

    /**
     * Method returning the latest date from table
     * @param tableName table name
     * @return Timestamp containing the latest date
     * @throws SQLException
     */
    public Timestamp getDate(String tableName) throws SQLException {
        String query = "SELECT date FROM " + tableName;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next());
        rs.previous();
        return rs.getTimestamp("Date");
    }
}
