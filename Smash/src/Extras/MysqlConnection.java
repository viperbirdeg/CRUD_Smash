package Extras;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {

    //Driver mysql
    public String driver = "com.mysql.cj.jdbc.Driver";

    //Database constants
    public final String HOST = System.getenv("DATABASE_HOST");
    public final String PORT = System.getenv("DATABASE_PORT");
    public final String NAME = System.getenv("DATABASE_NAME");
    public final String PASSWORD = System.getenv("DATABASE_PASSWORD");
    public final String USER = System.getenv("DATABASE_USERNAME");

    //Database connection url
    public final String URL = "jdbc:mysql://"+ HOST + ":" + PORT + "/" + NAME + "?autoReconnect=true&useSSL=false";

    //Method to getConnection
    public Connection getDatabaseConnection(){
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //method to Close connection
    public void closeDatabaseConnection(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

}
