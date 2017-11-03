package db.tables;

import db.beans.Admin;
import db.utils.DBType;
import db.utils.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminManager {

    private static final Logger ADMINMANAGERLOG = Logger.getLogger(AdminManager.class.getName());

//    Display All Rows of data in Admin Table
    public static void displayAllRows() throws SQLException {
        String sql = "SELECT adminId, userName, password FROM admin";

        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

            System.out.println("Admin Table: ");
            while (rs.next()) {
                StringBuffer bf = new StringBuffer();
                bf.append(rs.getInt("adminId") + ": ");
                bf.append(rs.getString("userName") + ", ");
                bf.append(rs.getString("password"));
                System.out.println(bf.toString());
            }
            ADMINMANAGERLOG.log(Level.INFO, "Admin Rows Retrieved");

        } catch (SQLException e) {
            ADMINMANAGERLOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

//    Get a single row of data in Admin Table
    public static Admin getRow(int adminId) throws SQLException {
        String sql = "SELECT * FROM admin WHERE adminId = ?";
        ResultSet rs = null;

        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, adminId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                ADMINMANAGERLOG.log(Level.INFO, "Rows Found!");
                Admin admin = new Admin();
                admin.setAdminId(adminId);
                admin.setUserName(rs.getString("userName"));
                admin.setPassword(rs.getString("Password"));
                return admin;
            } else {
                ADMINMANAGERLOG.log(Level.WARNING, "No Rows Found");
                return null;
            }

        } catch (SQLException e) {
            ADMINMANAGERLOG.log(Level.SEVERE, e.getMessage(), e);
            return null;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

//    Insert a row of data in Admin Table
    public static boolean insert(Admin admin) throws SQLException {
        String sql = "INSERT into admin(username, password) VALUES(?, ?);";

        ResultSet keys = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, admin.getUserName());
            stmt.setString(2, admin.getPassword());
            int affected = stmt.executeUpdate();

            if (affected == 1) {
                keys = stmt.getGeneratedKeys();
                keys.next();

                admin.setAdminId(keys.getInt(1));

                ADMINMANAGERLOG.log(Level.INFO, "1 Row Affected");
                return true;

            } else {
                ADMINMANAGERLOG.log(Level.SEVERE, "No Rows Affected");
                return false;
            }
        } catch (Exception e) {
            ADMINMANAGERLOG.log(Level.SEVERE, e.getMessage(), e);
            return false;
        } finally {
            if (keys != null) {
                keys.close();
            }
        }
    }

//    Update a row of data in Admin Table
    public static boolean update(Admin admin) throws SQLException {
        String sql = "UPDATE admin SET userName = ?, password = ? WHERE adminId = ?";
        try(
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql);
                ){
            
            stmt.setString(1, admin.getUserName());
            stmt.setString(2, admin.getPassword());
            stmt.setInt(3, admin.getAdminId());
            
            int affected = stmt.executeUpdate();
            
            if(affected == 1){
                ADMINMANAGERLOG.log(Level.INFO, "Row Updated Successfully");
                return true;
            }else{
                ADMINMANAGERLOG.log(Level.WARNING, "No Row Was Updated");
                return false;
            }
        }catch(SQLException e){
            ADMINMANAGERLOG.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }
    
//    Delete a row of data in Admin Table
    public static boolean delete(int adminId) throws SQLException{
        String sql = "DELETE FROM admin WHERE adminId = ?;";
        
        try(
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql);
                ){
            
            stmt.setInt(1, adminId);
            int affected = stmt.executeUpdate();
            if(affected == 1){
                ADMINMANAGERLOG.log(Level.INFO, "1 Row deleted");
                return true;
            }else{
                ADMINMANAGERLOG.log(Level.WARNING, "No Row Found with that id! No Row was Affected!");
                return false;
            }
        }catch(SQLException e){
            ADMINMANAGERLOG.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }
}
