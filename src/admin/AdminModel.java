package admin;
import DbUtil.dbConnection;
import java.sql.*;
public class AdminModel {
    Connection connection;
    public AdminModel() {
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (this.connection == null) {
            System.exit(1);
        }
    }
    public boolean DoesParentExist(String name, String lastname, String occupation) throws Exception {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM parent where Fname = ? and Lname = ? and occupation = ?";
        try {
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, name);
            pr.setString(2, lastname);
            pr.setString(3, occupation);
            rs = pr.executeQuery();
            boolean boll;
            if (rs.next()) {
                boll = true;
                return boll;
            }
            return false;
        } catch (SQLException ex) {
            return false;
        } finally {
            pr.close();
            rs.close();
        }
    }
    public boolean DoesClassExist(String class_id) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sql = "select * from class where class_id=?";

        try {
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, class_id);


            rs = pr.executeQuery();

            boolean boll;
            if (rs.next()) {
                boll = true;
                return boll;
            }
            return false;
        } catch (SQLException ex) {
            return false;
        } finally {
            pr.close();
            rs.close();
        }
    }
    public boolean DoesStudentNumberExist(String studentNumber) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sql = "select * from student where student_id=?";
        try {
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, studentNumber);
            rs = pr.executeQuery();
            boolean boll;
            if (rs.next()) {
                boll = true;
                return boll;
            }
            return false;
        } catch (SQLException ex) {
            return false;
        } finally {
            pr.close();
            rs.close();
        }
    }
    public boolean DoesEmployeeIDExist(String employeeNumber) throws  SQLException{
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sql = "select * from employee where employee_id=?";
        try {
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, employeeNumber);
            rs = pr.executeQuery();
            boolean boll;
            if (rs.next()) {
                boll = true;
                return boll;
            }
            return false;
        } catch (SQLException ex) {
            return false;
        } finally {
            pr.close();
            rs.close();
        }
    }
    public String GetParentId(String name, String lastname, String occupation) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String parent_id;
        String sql = "SELECT * FROM parent where Fname = ? and Lname = ? and occupation = ?";
        try {
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, name);
            pr.setString(2, lastname);
            pr.setString(3, occupation);
            rs = pr.executeQuery();
            while (rs.next()) {
                parent_id = rs.getString("parent_id");
                return parent_id;
            }
            pr.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public void AddStudent(String studentNumber, String GenderRadio, String name, String surname, String homeAddress, String dob, String parentname, String phone,
                           String parentlastname, String parentOccupation, String gradeID,String classID, String year,String division) {
        try {
            String query = "INSERT INTO student (student_id,Name,Surname,HomeAddress,Gender,DOB,parent_id) " +
                    "VALUES (?,?,?,?,?,?,?)";
            String qry = "INSERT INTO parent (Fname,Lname,occupation,Phone) values (?,?,?,?)";
            String queryClass = "INSERT INTO class (class_id,year,id_grade,Division) VALUES (?,?,?,?);";
            String queryClassStudent ="insert into class_student (student_id,class_id) values (?,?)";

            PreparedStatement pst = connection.prepareStatement(query);
            PreparedStatement preparedStatement = connection.prepareStatement(qry);
            PreparedStatement prepClass = connection.prepareStatement(queryClass);
            PreparedStatement preClassStudent = connection.prepareStatement(queryClassStudent);

            boolean doesParentExist = DoesParentExist(parentname, parentlastname, parentOccupation);
            if (doesParentExist) {
                String parentID = GetParentId(parentname, parentlastname, parentOccupation);
                //entering student data to student table on db if parent already exist

                pst = connection.prepareStatement(query);

                pst.setString(1, studentNumber);
                pst.setString(2, name);
                pst.setString(3, surname);
                pst.setString(4, homeAddress);
                pst.setString(5, GenderRadio);
                pst.setString(6, dob);
                pst.setString(7, parentID);

                pst.execute();
                //entering data to class table on db
                if (!DoesClassExist(classID)){
                        prepClass.setString(1, classID);
                        prepClass.setString(2, year);
                        prepClass.setString(3, gradeID);
                        prepClass.setString(4, division);
                        prepClass.execute();
                    }
                //entering data to class_student table on database
                preClassStudent.setString(1,studentNumber);
                preClassStudent.setString(2,classID);
                preClassStudent.execute();
                pst.close();
                prepClass.close();
                preClassStudent.close();
            } else {

                // prepared statement for parent table
                preparedStatement.setString(1, parentname);
                preparedStatement.setString(2, parentlastname);
                preparedStatement.setString(3, parentOccupation);
                preparedStatement.setString(4,phone);
                preparedStatement.execute();

                //prepared statement for student table
                String parentID = GetParentId(parentname, parentlastname, parentOccupation);
                pst.setString(1, studentNumber);
                pst.setString(2, name);
                pst.setString(3, surname);
                pst.setString(4, homeAddress);
                pst.setString(5, GenderRadio);
                pst.setString(6, dob);
                pst.setString(7, parentID);


                pst.execute();
                //entering data to class table on db
                if (!DoesClassExist(classID)) {
                    prepClass.setString(1, classID);
                    prepClass.setString(2, year);
                    prepClass.setString(3, gradeID);
                    prepClass.setString(4, division);
                    prepClass.execute();
                }
                //entering data to class_student table on database
                preClassStudent.setString(1,studentNumber);
                preClassStudent.setString(2,classID);
                preClassStudent.execute();

                preparedStatement.close();
                pst.close();
                prepClass.close();
                preClassStudent.close();

            }
        } catch (SQLIntegrityConstraintViolationException sql) {
            sql.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void AddTeacher(String employeeID,String Fname,String Lname,String DateOfBirth, String Gender,String HomeAddress,
                           String Phone, String UserRole, String Password){
        try{
            //creating employee onto database
            String addEmployeeQuery = "INSERT INTO employee (employee_id,Fname,Lname,DOB,Gender,Address,Phone)" +
                    "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement prs = connection.prepareStatement(addEmployeeQuery);
            prs.setString(1,employeeID);
            prs.setString(2,Fname);
            prs.setString(3,Lname);
            prs.setString(4,DateOfBirth);
            prs.setString(5,Gender);
            prs.setString(6,HomeAddress);
            prs.setString(7,Phone);
            prs.execute();
            prs.close();
            //create login for employee
            String AddLoginQuery = "insert into login (username,password,role) values (?,?,?)";
            PreparedStatement pst = connection.prepareStatement(AddLoginQuery);
            pst.setString(1,employeeID);
            pst.setString(2,Password);
            pst.setString(3,UserRole);

            pst.execute();
            pst.close();

            if(UserRole.equals("Teacher")){
                String teacherClass = "INSERT INTO teacher (idTeacher) VALUES (?)";
                PreparedStatement pr = connection.prepareStatement(teacherClass);

                pr.setString(1,employeeID);
                pr.execute();
                pr.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void EditStudentData(String idStudent, String idName,String idSurname, String idAddress,String parentName,String parentSurname,
                                String occupation, String Phone, String parent_id ){
        try {

         String qry = "UPDATE student SET Name=?, Surname=?, HomeAddress=? WHERE student_id=?";
         String query = "UPDATE parent SET `Fname`=?, `Lname`=?, `occupation`=?, `Phone`=? WHERE `parent_id`=?";

         PreparedStatement preparedStatement = connection.prepareStatement(qry);
         PreparedStatement pr = connection.prepareStatement(query);

            preparedStatement.setString(1,idName);
            preparedStatement.setString(2,idSurname);
            preparedStatement.setString(3,idAddress);
            preparedStatement.setString(4,idStudent);

            pr.setString(1,parentName);
            pr.setString(2,parentSurname);
            pr.setString(3,occupation);
            pr.setString(4,Phone);
            pr.setString(5,parent_id);


            preparedStatement.execute();
            preparedStatement.close();
            pr.execute();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void EditTeacherData(String employeeID, String name,String surname, String homeAddress, String telephone){
        try {

            String qry = "UPDATE employee SET `Fname`=?, `Lname`='?, `Address`=?, `Phone`=? WHERE `employee_id`=?";
            String query = "";

            PreparedStatement preparedStatement = connection.prepareStatement(qry);

            preparedStatement.setString(1,name);
            preparedStatement.setString(2,surname);
            preparedStatement.setString(3,homeAddress);
            preparedStatement.setString(4,telephone);
            preparedStatement.setString(5,employeeID);

            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void UpdatePass(String employeeID, String password) throws SQLException {
        PreparedStatement pr = null;

        String sql = "UPDATE login SET `password`=? WHERE `username`=?";

        try {
            pr = this.connection.prepareStatement(sql);
            pr.setString(1,password);
            pr.setString(2,employeeID);
            pr.execute();
            pr.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
