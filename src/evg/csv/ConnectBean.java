package evg.csv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectBean {
    private Connection con;
    private static ConnectBean instance;
    private ConnectBean() throws Exception {
        try {           
            String driver = "org.sqlite.JDBC";
            Class.forName(driver).newInstance();
            String url = "jdbc:sqlite:C:\\Projects\\JAVA\\jsp_app\\database.sqlite3";
            con = DriverManager.getConnection(url);
        } 
        catch (ClassNotFoundException e) {
            throw new Exception(e);
        }
        catch (SQLException e) {
            throw new Exception(e);
        }
    }
    public static synchronized  ConnectBean getInstance () throws Exception {
        if (instance == null) {
            instance = new ConnectBean();
        }
        return instance;         
    }
    public Connection getConnection() {
        return con;
    }
}