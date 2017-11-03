/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package betterjdbc;

import db.beans.Admin;
import db.tables.AdminManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BetterJdbc {

    private static Logger MainLogger = Logger.getLogger(BetterJdbc.class.getName());
    
    public static void main(String[] args) throws SQLException{
        
//      Display All Rows
        AdminManager.displayAllRows();

//      Get a row of data        
        System.out.println("Select a row : ");
        int adminId = (new Scanner(System.in)).nextInt();
        
        Admin admin = AdminManager.getRow(adminId);
        
        if(admin != null){
            System.out.println("Admin Id: " + admin.getAdminId());
            System.out.println("Admin Username: " + admin.getUserName());
            System.out.println("Admin Password: " + admin.getPassword());
            
        }else{
            MainLogger.log(Level.WARNING, "No Rows Were Found");
        }
        
//      Insert A Row Of Data
        Admin admin1 = new Admin();
        
        System.out.println("Please Enter a Username: ");
        admin1.setUserName((new Scanner(System.in)).nextLine());
        
        System.out.println("Please Enter a Password: ");
        admin1.setPassword((new Scanner(System.in)).nextLine());
        
        boolean result = AdminManager.insert(admin1);
        if(result){
            MainLogger.log(Level.INFO, "New row with primary key " + admin1.getAdminId() + " Was Inserted");
        } else {
            MainLogger.log(Level.WARNING, "No Row Was Inserted");
        }
        
//        Update a row of data
        System.out.println("Select a row to update: ");
        int admin2Id = (new Scanner(System.in).nextInt());
        Admin admin2 = AdminManager.getRow(admin2Id);
        
        if(admin2 == null){
            MainLogger.log(Level.WARNING, "Row Not Found");
            return;
        }
        
        System.out.println("Enter a new Password: ");
        String password = (new Scanner(System.in).nextLine());
        admin2.setPassword(password);
        
        if(AdminManager.update(admin2)){
           MainLogger.log(Level.INFO, "1 Row updated");
        } else {
           MainLogger.log(Level.SEVERE, "No Row Was Updated");
        }
        
//      Delete a row of data
        System.out.println("Enter a row id to delete: ");
        int admin3Id = (new Scanner(System.in).nextInt());
        
        if(AdminManager.delete(admin3Id)){
            MainLogger.log(Level.INFO, "1 Row Deleted!");
        }else{
            MainLogger.log(Level.SEVERE, "Nothing to Delete!");
        }
        
    }
    
}
