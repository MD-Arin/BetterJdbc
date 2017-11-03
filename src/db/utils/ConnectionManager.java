package db.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

//Singleton Connection Manager
public class ConnectionManager {

    private static ConnectionManager instance = null;

    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String CONN_URL = "jdbc:mysql://localhost/explorecalifornia";
    private static final Logger CONNECTIONLOG = Logger.getLogger(ConnectionManager.class.getName());

    private DBType dbType = DBType.MYSQL;

    private Connection conn = null;

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public DBType getDbType() {
        return dbType;
    }

    public void setDbType(DBType dbType) {
        this.dbType = dbType;
    }

    private boolean openConnection() {
        try {
            switch (dbType) {
                case MYSQL:
                    conn = DriverManager.getConnection(CONN_URL, USERNAME, PASSWORD);
                    return true;
                default:
                    return false;
            }
        } catch (SQLException e) {
            CONNECTIONLOG.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }
    
    public Connection getConnection(){
        if(conn == null){
            if(openConnection()){
                CONNECTIONLOG.log(Level.INFO, "Connection Opened!");
                return conn;
            }else{
                return null;
            }
        }
        return conn;
    }
    
    public void close(){
        try{
            CONNECTIONLOG.log(Level.INFO, "Closing Connection!");
            conn.close();
            conn = null;
        }catch(SQLException e){
            CONNECTIONLOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
}
