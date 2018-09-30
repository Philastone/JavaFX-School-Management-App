package DbUtil;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "mysql";
    private static final String REMOTECONN = "jdbc:mysql://192.168.0.10:3306/schoolDB";
    private static final String CONN = "jdbc:mysql://localhost:3377/schoolDB";
    private static final String SQCONN = "jdbc:sqlite:SchoolDBM.db";
    private static final String AWSUSER = "root";
    private static final String AWSPASSWORD = "ASAnde12#";
    private static final String AWSURL= "jdbc:mysql://schooldb.czwca8zacnit.us-east-2.rds.amazonaws.com/schooldb";


    public static Connection getConnection() throws SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        Connection conn = null;

        try{
            conn = DriverManager.getConnection(AWSURL,AWSUSER,AWSPASSWORD);
            return conn;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
