package Teacher;
import DbUtil.dbConnection;
import java.sql.*;
public class TeacherModel {
    Connection connection;
    public TeacherModel() {
        try {
            this.connection = dbConnection.getConnection();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (this.connection == null) {
            System.exit(1);
        }
    }
    public void CreateAssessment(String AssessmentID, String AssessmentName, double toatlMarks, float Weight) {
        try {
            String qry = "INSERT INTO `schooldb`.`assessmentClass` (`assessment_id`, `name`, `weighting`, `total`) VALUES (?,?,?,?)";
            PreparedStatement pr = this.connection.prepareStatement(qry);
            pr.setString(1, AssessmentID);
            pr.setString(2, AssessmentName);
            pr.setString(3, String.valueOf(toatlMarks));
            pr.setString(4, String.valueOf(Weight));
            pr.execute();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean DoesStudentAssessmentMarkExist(String studentID, String assessmentID){
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sql = "select * from assessment_result where id_student=? and assessment_id=?";

        try {
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, studentID);
            pr.setString(2,assessmentID);

            rs = pr.executeQuery();

            boolean boll;
            if (rs.next()) {
                boll = true;
                return boll;
            }
            pr.close();
            rs.close();
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }
    public boolean DoesStudentRegEntryExist(String RegID){
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sql = "select * from class_register where reg_ID=?";

        try {
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, RegID);

            rs = pr.executeQuery();

            boolean boll;
            if (rs.next()) {
                boll = true;
                return boll;
            }
            pr.close();
            rs.close();
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }

    public void deleteAssessment(String assessmentName) {
        try {

            connection = dbConnection.getConnection();
            String qry = "DELETE FROM `schooldb`.`assessment` WHERE `assessment_id`=?;";
            PreparedStatement pr = connection.prepareStatement(qry);
            pr.setString(1, assessmentName);
            pr.execute();
            pr.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deletelStudentAssessment(String studentID){
        try {
            connection = dbConnection.getConnection();
            String query = "DELETE FROM `schooldb`.`assessment_result` WHERE `id_student`=?;";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, studentID);
            pr.execute();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getAssessmentID(String subjectID,String name){
        String assessmentID = "";
        try {
            connection = dbConnection.getConnection();
            String getAssName ="select * from assessment where subjectID=? and name=?";
            PreparedStatement pr = connection.prepareStatement(getAssName);
            pr.setString(1,subjectID);
            pr.setString(2,name);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                assessmentID = resultSet.getString("assessment_id");
                return assessmentID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assessmentID;
    }
    public String getSubjectID(String SubName, String gradeID){
        PreparedStatement pr = null;
        ResultSet rs = null;
        String assessID;
        String subjectId = "";
        String sql = "select * from subject where grade_id=? and name=?";

        try {
            connection = dbConnection.getConnection();
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, gradeID);
            pr.setString(2,SubName);


            rs = pr.executeQuery();
            if (rs.next()){
                subjectId = rs.getString("subject_id");
                return subjectId;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return subjectId;
    }

}