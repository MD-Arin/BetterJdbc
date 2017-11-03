/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import com.sun.istack.internal.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 *
 * @author arin
 */
public class DBUtil {
    
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String CONN_URL = "jdbc:mysql://localhost/explorecalifornia";
    private static final Logger DBUTILLOG = Logger.getLogger(DBUtil.class);
    
    public static Connection getConnection(DBType dbType) throws SQLException{
        switch(dbType){
            case MYSQL: 
                DBUTILLOG.log(Level.INFO, "Connected to MYSQL");
                return DriverManager.getConnection(CONN_URL, USERNAME, PASSWORD);
            default:
                DBUTILLOG.log(Level.SEVERE, "No Connection Specified, Connection Failed!");
                return null;
        }
    }
    
}
