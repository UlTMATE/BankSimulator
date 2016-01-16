package model;
/**
 *
 * @author UlTMATE
 */
import java.sql.*;
public class Database {
    
    ResultSet rst;
    Connection conn;
    
    public Database(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/?","root","root");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS bankSimulator");
            stmt.close();
            conn.close();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankSimulator","root","root");
            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS account(accNum INT NOT NULL AUTO_INCREMENT, password varchar(50) not null, name varchar(30) not null, balance int default 0, PRIMARY KEY (accNum))");
            stmt.close();
        } catch (ClassNotFoundException|SQLException exc){
            exc.printStackTrace();
        }
    }
    
    public ResultSet getData(String query){
        try{
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rst = stmt.executeQuery(query);
        } catch(SQLException sqlExc){
            sqlExc.printStackTrace();
        }
        return rst;
    }
}
