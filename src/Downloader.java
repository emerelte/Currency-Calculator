import java.sql.*;
import java.util.TreeMap;

public class Downloader {
    private Connection conn;
    public Downloader(String myUrl, String user, String password) throws ClassNotFoundException, SQLException {
        String myDriver = "com.mysql.jdbc.Driver";
        Class.forName(myDriver);
        conn = DriverManager.getConnection(myUrl, user, password);
    }
    public void close() throws SQLException {
        conn.close();
    }
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
    public Timestamp getDate(String tableName) throws SQLException {
        String query = "SELECT date FROM " + tableName;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next());
        rs.previous();
        return rs.getTimestamp("Date");
    }
}
