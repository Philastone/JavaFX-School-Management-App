package LoginApp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DbUtil.dbConnection;
import javafx.scene.control.Alert;
public class LoginModel {
    Connection connection;
    public LoginModel(){
        try{
            this.connection = dbConnection.getConnection();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        if(this.connection==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error on Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please check internet, Application Not connected to Database");
            alert.showAndWait();
        }
    }
    public boolean isDatabaseConnected(){
        return this.connection!=null;
    }
    public boolean isLogin(String user, String pass, String opt ) throws Exception {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM login where username = ? and password = ? and role = ?";
        try{
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, user);
            pr.setString(2, pass);
            pr.setString(3, opt);
            rs = pr.executeQuery();
            boolean boll;
            if(rs.next()){
                boll = true;
                return boll;
            }
            return false;
        }
        catch (SQLException ex)
        {
            return false;
        }
        finally {
            pr.close();
            rs.close();
        }
    }
    public String getUserInfo(String employeeID){
        String name = null, surname = null;
        try{
            String qry = "Select * from employee where employee_id=?";
            PreparedStatement pr = connection.prepareStatement(qry);
            pr.setString(1,employeeID);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                name = rs.getString("Fname");
                surname = rs.getString("Lname");
            }
            pr.close();
            rs.close();
            return name +" "+ surname;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name +" "+surname;
    }
    public String getUserGrade(String employeeID){
        String grade = null;
        try{
            String qry = "Select * from class where teacher_id=?";
            PreparedStatement pr = connection.prepareStatement(qry);
            pr.setString(1,employeeID);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                grade = rs.getString("id_grade");

            }
            pr.close();
            rs.close();
            return grade;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grade;
    }

}
