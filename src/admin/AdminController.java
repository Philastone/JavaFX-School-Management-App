package admin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import DbUtil.dbConnection;
import LoginApp.options;
import javafx.stage.StageStyle;
import javax.print.DocFlavor;
import javax.print.attribute.standard.MediaSize;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class AdminController implements Initializable {
    Connection connection;
    AdminModel adminModel = new AdminModel();
    @FXML private ComboBox<grade>  gradeCombo = new ComboBox<>();
    @FXML private TextField stdName,stdSurname,StdHA,Contact,parentname,parentlastname,parentOccupation;
    @FXML private DatePicker stdDatePick;
    @FXML private RadioButton radioFemale, radioMale;
    @FXML private TableView<Student> tableView;
    @FXML private TableView<Employee> employeeTableView;
    @FXML private TableView<ClassList> classListTableView;
    @FXML private TextField gradeDivision;
    @FXML private Label lblName,lblSurname,lblDatePick,lblAddress,lblNkName,lblContact;
    @FXML private ComboBox<Classes> filterGrade = new ComboBox<>();
    // new add and edit
    @FXML private Label labelStudentNumber;
    @FXML private Label lblEmployeeID;
    //Teacher
    @FXML private TextField FirstName,LastName,HomeAddress,ContactNumber;
    @FXML private PasswordField passWord,rePassWord;
    @FXML private DatePicker employeeDob;
    @FXML private ComboBox<options> roleCombo = new ComboBox<>();
    @FXML private RadioButton employeeMale, employeeFemale;
    @FXML private Label labelName,LabelSurname,labelAddress,labelNumber,LabelPass,LabelRole,lblLabel;
    @FXML public TextField txtName,txtSurname;
    @FXML private ComboBox<String> ClassPicker = new ComboBox<>();
    @FXML private ComboBox<String> TeachersId = new ComboBox<>();
    @FXML private TableColumn<Student,String> StudentNo_s;
    @FXML private TableColumn<Student,String> Name_s;
    @FXML private TableColumn<Student,String> Surname_s;
    @FXML private TableColumn<Student,String> Address_s;
    @FXML private TableColumn<Student,String> Gender_s;
    @FXML private TableColumn<Student,String> DOB_s;
    @FXML private TableColumn<Student,String> Phone_s;
    @FXML private TableColumn<Student,String> ParentName_s;
    @FXML private TableColumn<Student,String> ParentSurname_s;
    @FXML private TableColumn<Student,String> ParentOccupation_s;
    //Teacher Table
    @FXML private TableColumn<Employee,String> EmployeeID;
    @FXML private TableColumn<Employee,String> FNAME;
    @FXML private TableColumn<Employee,String> LNAME;
    @FXML private TableColumn<Employee,String> DateOfBirth;
    @FXML private TableColumn<Employee,String> GENDER;
    @FXML private TableColumn<Employee,String> ADDRESS;
    @FXML private TableColumn<Employee,String> PHONE;
    @FXML private TableColumn<Employee,String> ROLE;

    @FXML private TableColumn<ClassList,String> TEACHER_ID;
    @FXML private TableColumn<ClassList,String> TEACHER_NAME;
    @FXML private TableColumn<ClassList,String> TEACHER_lASTNAME;
    @FXML private TableColumn<ClassList,String> STUDENT_NO;
    @FXML private TableColumn<ClassList,String> S_NAME;
    @FXML private TableColumn<ClassList,String> S_lASTNAME;
    @FXML private TableColumn<ClassList,String> GRADE_ID;
    @FXML private TableColumn<ClassList,String> GRADE_DIV;
    @FXML private TableColumn<ClassList,String> CLASS_YEAR;
    ObservableList<String> Ids = FXCollections.observableArrayList();
    ObservableList<String> classIds = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<grade> grades = FXCollections.observableArrayList(grade.values());
        this.gradeCombo.setItems(grades);
        this.roleCombo.setItems(FXCollections.observableArrayList(options.values()));
        this.filterGrade.setItems(FXCollections.observableArrayList(Classes.values()));
        this.filterGrade.getSelectionModel().selectFirst();
        FillComboBox();
    }
    public void Logout(javafx.event.ActionEvent event) {
        try {
            ((Node)event.getSource()).getScene().getWindow().hide();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("/LoginApp/login2.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("School Data Management System");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void AssignTeacherCombobox(ActionEvent actionEvent){
        try{
            String name = "",surname="";
            String qry = "select employee.Fname,employee.Lname from employee where employee_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(qry);
            preparedStatement.setString(1,TeachersId.getSelectionModel().getSelectedItem());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                name =rs.getString("Fname");
                surname = rs.getString("Lname");
            }
            this.txtName.setText(name);
            this.txtSurname.setText(surname);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void AssignTeacher(){
        try {

            Stage AddStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("assignTeacher.fxml").openStream());

            Scene scene = new Scene(root);
            AddStage.setScene(scene);
            AddStage.setTitle("Assign Teacher To Class");
            AddStage.setResizable(false);
            AddStage.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void LoadingTeacherData(){
        ObservableList<Employee> employees =FXCollections.observableArrayList();
        try{
            this.connection = dbConnection.getConnection();
            String qry ="select employee.*, login.role from employee, login where employee.employee_id=username ";

            PreparedStatement pst = connection.prepareStatement(qry);
            ResultSet rs = pst.executeQuery();
           while (rs.next()){
                employees.add(
                        new Employee(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8)
                        ));
        }
    } catch (SQLException e) {
            e.printStackTrace();
        }
        EmployeeID.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));
        FNAME.setCellValueFactory(new PropertyValueFactory<>("FNAME"));
        LNAME.setCellValueFactory(new PropertyValueFactory<>("LNAME"));
        DateOfBirth.setCellValueFactory(new PropertyValueFactory<>("DOB_e"));
        GENDER.setCellValueFactory(new PropertyValueFactory<>("GENDER"));
        ADDRESS.setCellValueFactory(new PropertyValueFactory<>("ADDRESS"));
        PHONE.setCellValueFactory(new PropertyValueFactory<>("PHONE"));
        ROLE.setCellValueFactory(new PropertyValueFactory<>("ROLE"));
        this.employeeTableView.setItems(employees);
        employeeTableView.setEditable(true);
    }
    public void LoadingDatabase() {
        ObservableList<Student> data =FXCollections.observableArrayList();
        try{
            this.connection = dbConnection.getConnection();
            String selectQuery = "SELECT student.student_id , student.Name, student.Surname, student.HomeAddress, student.Gender, student.DOB, parent.Phone," +
                    "parent.Fname, parent.Lname,parent.occupation" +
                    "    FROM `parent`, `student`" +
                    "    WHERE `parent`.`parent_id` = `student`.`parent_id`";
            PreparedStatement pst = connection.prepareStatement(selectQuery);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                data.add(
                        new Student(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getString(9),
                                rs.getString(10))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StudentNo_s.setCellValueFactory(new PropertyValueFactory<>("StudentNo"));
        Name_s.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Surname_s.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        Address_s.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Gender_s.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        DOB_s.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        Phone_s.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        ParentName_s.setCellValueFactory(new PropertyValueFactory<>("ParentName"));
        ParentSurname_s.setCellValueFactory(new PropertyValueFactory<>("ParentSurname"));
        ParentOccupation_s.setCellValueFactory(new PropertyValueFactory<>("ParentOccupation"));

       // Grade.setCellValueFactory(new PropertyValueFactory<>("Grade"));




        this.tableView.setItems(data);
        tableView.setEditable(true);

    }
    public String CheckStudentNo() throws SQLException {
        String Name = stdName.getText();
        String Surname = stdSurname.getText();
        String noStudent;
        do {
            noStudent = NumberGenerator(Name,Surname);
        } while (adminModel.DoesStudentNumberExist(noStudent));

        return noStudent;
    }
    public String CheckEmployeeNo() throws SQLException {
       String Fname = FirstName.getText();
       String Lname = LastName.getText();
        String emNumber;
        do {
            emNumber = NumberGenerator(Fname,Lname);
        } while (adminModel.DoesEmployeeIDExist(emNumber));

        return emNumber;
    }
    public void StudentAdd(javafx.event.ActionEvent actionEvent) throws SQLException {
        String Name = stdName.getText();
        String Surname = stdSurname.getText();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (labelStudentNumber.getText() == null || labelStudentNumber.getText().equals("")) {
            this.lblName.setText("");
            lblSurname.setText("");
            lblDatePick.setText("");
            lblAddress.setText("");
            lblNkName.setText("");
            lblContact.setText("");
            if (!stdName.getText().equals("") && !stdSurname.getText().equals("") && stdDatePick.getValue() != null && !StdHA.getText().equals("")
                    && !Contact.getText().equals("")/* && !nkName.getText().equals("") && gradeCombo.getSelectionModel() != null*/) {
                {
                    String studentNumber = CheckStudentNo();
                    boolean rMale = radioMale.isSelected();
                    boolean rFemale= radioFemale.isSelected();
                    String GenderRadio = radioGender(rMale,rFemale);
                    String gradeID = gradeCombo.getValue().toString();
                    LocalDate ld = stdDatePick.getValue();
                    String dob = ld.toString();
                    String HomeAddress = StdHA.getText();
                    String Phone = Contact.getText();
                    String pName = parentname.getText();
                    String pSurname = parentlastname.getText();
                    String pOccupation = parentOccupation.getText();
                    String Division = gradeDivision.getText();
                    String classID = gradeID.toLowerCase()+"_"+Division.toLowerCase();
                    String year1 = String.valueOf(year);
                    adminModel.AddStudent(studentNumber, GenderRadio, Name, Surname, HomeAddress, dob, pName,
                            Phone, pSurname, pOccupation,gradeID,classID,year1,Division.toUpperCase());
                    AlertBox("Student has been Created Successfully!");
                    ClearFields();
                    LoadingDatabase();
                }
            }else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error on Fields");
                    alert.setHeaderText(null);
                    alert.setContentText("Please Fill in All Fields");
                    alert.showAndWait();
                if (stdName.getText().equals("")){
                    //label = Name field is required
                    this.lblName.setText("* Name is Required");
                }
                if (stdSurname.getText().equals("")){
                    //label = Name field is required
                    this.lblSurname.setText("* Surname is Required");
                }
                if (stdDatePick.getValue() == null){
                    //label = Name field is required
                    this.lblDatePick.setText("* D.O.B is Required");
                }
                if (Contact.getText().equals("")){
                    //label = Name field is required
                    this.lblContact.setText("* Contact is Required");
                }
                if (StdHA.getText().equals("")){
                    //label = Name field is required
                    this.lblAddress.setText("* Home Address is Required");
                }
            }
        }
        else{
            AlertBox("Student Entry already exist, You can't add");
        }
    }
    public void EditStudentInfo() throws SQLException {
        String Name,Surname,Address,StudentNumber,telephone;
        Student student = tableView.getSelectionModel().getSelectedItem();
        if ( student.getStudentNo()!= null){
            StudentNumber = (student.getStudentNo());
            Name = stdName.getText();
            Surname = stdSurname.getText();
            Address = StdHA.getText();
            String parentName = parentname.getText();
            String parentSurname = parentlastname.getText();
            String occupation = parentOccupation.getText();
            telephone = Contact.getText();
            String parent_id = adminModel.GetParentId(student.getParentName(),student.getParentSurname(),student.getParentOccupation());
            adminModel.EditStudentData(StudentNumber,Name,Surname,Address,parentName,parentSurname,occupation,telephone,parent_id);
            ClearFields();
        }
    }
    public void DeleteEntry(){
       Student student = tableView.getSelectionModel().getSelectedItem();
       String Stu_number = student.getStudentNo();
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Confirm Delete");
       alert.setHeaderText(null);
       alert.setContentText("Are you sure you want to delete "+student.getName()+" "
       +student.getSurname()+"?");
        Optional <ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            try {
                String Delete = "DELETE FROM student WHERE student_id=?";
                PreparedStatement pr = connection.prepareStatement(Delete);
                pr.setString(1, Stu_number);
                pr.execute();
                ClearFields();
                AlertBox("Delete was successful!");
                LoadingDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void DeleteTeacherEntry(){
        Employee employee = employeeTableView.getSelectionModel().getSelectedItem();
        String Stu_number = employee.getEmployeeID();
        try{
            String Delete = "DELETE FROM schooldb.employee WHERE employee_id=?";
            PreparedStatement pr = connection.prepareStatement(Delete);
            pr.setString(1,Stu_number);
            pr.execute();
            LoadingTeacherData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private String radioGender(boolean Male, boolean Female) {
        String GenderRadio = "Male";
        if (Male){
            GenderRadio ="Male";
            return GenderRadio;
        }
        else if (Female){
            GenderRadio = "Female";
            return GenderRadio;
        }
        return GenderRadio;
    }
    private String NumberGenerator(String NameID, String SurnameID){
        Random random = new Random();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String year1 = String.valueOf(year);
        String DateYear = year1.substring(0,1) + year1.substring(2,3) + year1.substring(3);
        int i = random.nextInt(10)+1;
        int n = random.nextInt(30)+i;
        String rand ;
        Integer firstNum,secondNum,thirdNum,firstNum1,secondNum1,thirdNum1,Sum,Sum1;
        firstNum = Switch(NameID.charAt(0));
        secondNum = Switch(NameID.charAt(1));
        thirdNum = Switch(NameID.charAt(2));
        firstNum1 = Switch(SurnameID.charAt(0));
        secondNum1 = Switch(SurnameID.charAt(1));
        thirdNum1 = Switch(SurnameID.charAt(2));
        Sum = firstNum+secondNum+thirdNum+n;
        Sum1 = firstNum1+secondNum1+thirdNum1+n;
        if(n<=9){
            rand ="0"+n;
            return (DateYear+Sum.toString()+rand+Sum1.toString());
        }
        else {
            return (DateYear+Sum.toString()+n+Sum1.toString());
        }
    }
    private Integer Switch(Character character){
        Integer number = 0;
        switch (character){
            case 'a':
                number =1;
                break;
            case 'b':
                number =2;
                break;
            case 'c':
                number =3;
                break;
            case 'd':
                number =4;
                break;
            case 'e':
                number =5;
                break;
            case 'f':
                number =6;
                break;
            case 'g':
                number =7;
                break;
            case 'h':
                number =8;
                break;
            case 'i':
                number =9;
                break;
            case 'j':
                number =10;
                break;
            case 'k':
                number =11;
                break;
            case 'l':
                number =12;
                break;
            case 'm':
                number =13;
                break;
            case 'n':
                number =14;
                break;
            case 'o':
                number =15;
                break;
            case 'p':
                number =16;
                break;
            case 'q':
                number =17;
                break;
            case 'r':
                number =18;
                break;
            case 's':
                number =19;
                break;
            case 't':
                number =20;
                break;
            case 'u':
                number =21;
                break;
            case 'v':
                number =22;
                break;
            case 'w':
                number =23;
                break;
            case 'x':
                number =24;
                break;
            case 'y':
                number =25;
                break;
            case 'z':
                number =26;
                break;
        }
        return number;
    }
    private String RoleSwitch(){
        String Role = null;
        switch (this.roleCombo.getValue().toString()){
            case "Admin":
                Role = "Admin";
                break;
            case "Teacher":
                Role = "Teacher";
                break;
        }
        return Role;
    }
    public void ClearFields(){
        labelStudentNumber.setText("");
        labelStudentNumber.setVisible(false);
        labelStudentNumber.setDisable(true);
        stdName.setText("");
        stdSurname.setText("");
        StdHA.setText("");
        radioMale.fire();
        stdDatePick.setValue(null);
        gradeDivision.setText("");
        gradeDivision.setDisable(false);
        gradeCombo.getSelectionModel().clearSelection();
        gradeCombo.setDisable(false);
        parentname.setText("");
        parentlastname.setText("");
        parentOccupation.setText("");
        Contact.setText("");

    }
    public void ClearTeacherFields(){
        lblEmployeeID.setVisible(false);
        lblLabel.setVisible(false);
        FirstName.clear();
        LastName.clear();
        employeeMale.fire();
        HomeAddress.clear();
        ContactNumber.clear();
        passWord.clear();
        rePassWord.clear();
        employeeDob.setValue(null);
        this.roleCombo.getSelectionModel().clearSelection();
    }
    public void MouseClickTeacherTable(MouseEvent mouseEvent){
        if (!employeeTableView.getSelectionModel().isEmpty()) {
            String Name,Surname,Address,EmployeeNumber,gender,telephone,role;
            Employee employee = employeeTableView.getSelectionModel().getSelectedItem();
            EmployeeNumber = (employee.getEmployeeID());
            Name = employee.getFNAME();
            Surname = employee.getLNAME();
            Address = employee.getADDRESS();
            gender = employee.getGENDER();
            LocalDate ld = LocalDate.parse(employee.getDOB_e());
            telephone = employee.getPHONE();
            role = employee.getROLE();
            lblEmployeeID.setText(EmployeeNumber);
            lblEmployeeID.setVisible(true);
            lblLabel.setVisible(true);
            FirstName.setText(Name);
            LastName.setText(Surname);
            HomeAddress.setText(Address);
            if (gender.equals("Male")){
                employeeMale.fire();
            }
            else{
                employeeFemale.fire();
            }
            employeeDob.setValue(ld);
            ContactNumber.setText(telephone);
            if (role.equals("Admin")){
                roleCombo.getSelectionModel().selectFirst();
            }
            else{
                roleCombo.getSelectionModel().selectLast();
            }
            employeeTableView.getSelectionModel().clearSelection();
        }


    }
    public void MouseDoubleClickOnStudentTable(MouseEvent mouseEvent){
        if (!tableView.getSelectionModel().isEmpty()) {
            String Name,Surname,Address,StudentNumber,gender,div,grade,telephone,parentN,parentS,parentO;
            Student student = tableView.getSelectionModel().getSelectedItem();
            StudentNumber = (student.getStudentNo());
            Name = student.getName();
            Surname = student.getSurname();
            Address = student.getAddress();
            gender = student.getGender();
            LocalDate ld = LocalDate.parse(student.getDOB());
            telephone = student.getPhone();
            parentN = student.getParentName();
            parentS = student.getParentSurname();
            parentO = student.getParentOccupation();
            labelStudentNumber.setText(StudentNumber);
            labelStudentNumber.setVisible(true);
            labelStudentNumber.setDisable(false);
            stdName.setText(Name);
            stdSurname.setText(Surname);
            StdHA.setText(Address);
            if (gender.equals("Male")){
                radioMale.fire();
            }
            else{
                radioFemale.fire();
            }
            stdDatePick.setValue(ld);
            parentname.setText(parentN);
            parentlastname.setText(parentS);
            parentOccupation.setText(parentO);
            Contact.setText(telephone);
            gradeCombo.setDisable(true);
            gradeDivision.setDisable(true);
        }


    }
    public void EmployeeAdd(javafx.event.ActionEvent actionEvent) throws SQLException {
        String Fname = "";
        String Lname = "";
        String EmployeeNumber = "";
        String Gender = "";
        String Address = "";
        String Phone = "";
        String UserRole = "";
        String Password = "";
        String RePassword = "";
        labelName.setText("");
        LabelSurname.setText("");
        labelAddress.setText("");
        labelNumber.setText("");
        LabelPass.setText("");
        LabelRole.setText("");
        Fname = FirstName.getText();
        Lname = LastName.getText();
        boolean rMale=employeeMale.isSelected();
        boolean rFemale=employeeFemale.isSelected();
        Gender = radioGender(rMale,rFemale);
        Address = HomeAddress.getText();
        Phone = ContactNumber.getText();
        Password = passWord.getText();
        RePassword = rePassWord.getText();
        if ( !Fname.equals("") && !Lname.equals("") && !Address.equals("") && !Password.equals("")
                && Password.equals(RePassword) && !roleCombo.getSelectionModel().isEmpty()){
            EmployeeNumber = CheckEmployeeNo(); //returns unique employee ID
            LocalDate localDate = employeeDob.getValue();
            String DateOfBirth = localDate.toString();
            UserRole = RoleSwitch();
            adminModel.AddTeacher(EmployeeNumber,Fname,Lname,DateOfBirth,Gender,Address,Phone,UserRole,Password);
            ClearTeacherFields();
        }
        else{
            if (Fname.equals("")){
                labelName.setText("Name is Required");
            }
            if (Lname.equals("")){
                LabelSurname.setText("Surname is Required");
            }
            if (Address.equals("")){
                labelAddress.setText("Address is Required");
            }
            if (Phone.equals("")){
                labelNumber.setText("Number is Required");
            }
            if (Password.equals("")){
                LabelPass.setText("Password Required");
            }
            if (!Password.equals(RePassword)){
                LabelPass.setText("Passwords Don't Match");
            }
            if (this.roleCombo.getSelectionModel().isEmpty()){
                LabelRole.setText("Choose Role");
            }
        }
    }
    public void EditEmployeeInfo() throws SQLException {
        Employee employee = employeeTableView.getSelectionModel().getSelectedItem();
        String employeeId = employee.getEmployeeID();
        String name = FirstName.getText();
        String lName = LastName.getText();
        String homeAddress = HomeAddress.getText();
        String telephone = ContactNumber.getText();
        String passwrd = passWord.getText();
        String rPasswrd = rePassWord.getText();
        adminModel.EditTeacherData(employeeId,name,lName,
                homeAddress,telephone);
        if (passwrd.equals("") && rPasswrd.equals("") && passwrd.equals(rPasswrd)){
            UpdatePassword();
        }
    }
    public void loadingClassTable(){
        ObservableList<ClassList> classList = FXCollections.observableArrayList();
      try{
            this.connection = dbConnection.getConnection();
            String query = "select class.teacher_id,employee.Fname,employee.Lname,student.student_id," +
                    "                    student.Name,student.Surname,class.id_grade,class.Division,class.year" +
                    "                    from student,class,class_student,employee" +
                    "                    where class.teacher_id=employee.employee_id and student.student_id=class_student.student_id" +
                    "                    and class_student.class_id=class.class_id ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs= preparedStatement.executeQuery();
            while(rs.next()){
                classList.add(
                        new ClassList(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getString(9))
                    );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TEACHER_ID.setCellValueFactory(new PropertyValueFactory<>("TEACHER_ID"));
        TEACHER_NAME.setCellValueFactory(new PropertyValueFactory<>("TEACHER_NAME"));
        TEACHER_lASTNAME.setCellValueFactory(new PropertyValueFactory<>("TEACHER_lASTNAME"));
        STUDENT_NO.setCellValueFactory(new PropertyValueFactory<>("STUDENT_NO"));
        S_NAME.setCellValueFactory(new PropertyValueFactory<>("S_NAME"));
        S_lASTNAME.setCellValueFactory(new PropertyValueFactory<>("S_lASTNAME"));
        GRADE_ID.setCellValueFactory(new PropertyValueFactory<>("GRADE_ID"));
        GRADE_DIV.setCellValueFactory(new PropertyValueFactory<>("GRADE_DIV"));
        CLASS_YEAR.setCellValueFactory(new PropertyValueFactory<>("CLASS_YEAR"));
        this.classListTableView.setItems(classList);
        classListTableView.setEditable(true);
    }
    public void LoadTableOnComboxBoxClick(){
        boolean isComboBoxEmpty = (filterGrade.getValue() == null);
        if(isComboBoxEmpty || filterGrade.getSelectionModel().getSelectedItem().toString().equals("All")){
            ObservableList<ClassList> classList = FXCollections.observableArrayList();
            try{
                this.connection = dbConnection.getConnection();
                String query = "select class.teacher_id,employee.Fname,employee.Lname,student.student_id," +
                        "                    student.Name,student.Surname,class.id_grade,class.Division,class.year" +
                        "                    from student,class,class_student,employee" +
                        "                    where class.teacher_id=employee.employee_id and student.student_id=class_student.student_id" +
                        "                    and class_student.class_id=class.class_id ";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet rs= preparedStatement.executeQuery();
                while(rs.next()){
                    classList.add(
                            new ClassList(
                                    rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7),
                                    rs.getString(8),
                                    rs.getString(9))
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            TEACHER_ID.setCellValueFactory(new PropertyValueFactory<>("TEACHER_ID"));
            TEACHER_NAME.setCellValueFactory(new PropertyValueFactory<>("TEACHER_NAME"));
            TEACHER_lASTNAME.setCellValueFactory(new PropertyValueFactory<>("TEACHER_lASTNAME"));
            STUDENT_NO.setCellValueFactory(new PropertyValueFactory<>("STUDENT_NO"));
            S_NAME.setCellValueFactory(new PropertyValueFactory<>("S_NAME"));
            S_lASTNAME.setCellValueFactory(new PropertyValueFactory<>("S_lASTNAME"));
            GRADE_ID.setCellValueFactory(new PropertyValueFactory<>("GRADE_ID"));
            GRADE_DIV.setCellValueFactory(new PropertyValueFactory<>("GRADE_DIV"));
            CLASS_YEAR.setCellValueFactory(new PropertyValueFactory<>("CLASS_YEAR"));
            this.classListTableView.setItems(classList);
            classListTableView.setEditable(true);
        }
        else {
            String gradeF = this.filterGrade.getSelectionModel().getSelectedItem().toString();

            ObservableList<ClassList> classList = FXCollections.observableArrayList();
            try{
                this.connection = dbConnection.getConnection();
                String query = "select class.teacher_id,employee.Fname,employee.Lname,student.student_id," +
                        "                    student.Name,student.Surname,class.id_grade,class.Division,class.year" +
                        "                    from student,class,class_student,employee" +
                        "                    where class.teacher_id=employee.employee_id and student.student_id=class_student.student_id" +
                        "                    and class_student.class_id=class.class_id "+
                        "                    and id_grade=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,gradeF);
                ResultSet rs= preparedStatement.executeQuery();
                while(rs.next()){
                    classList.add(
                            new ClassList(
                                    rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7),
                                    rs.getString(8),
                                    rs.getString(9))
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            TEACHER_ID.setCellValueFactory(new PropertyValueFactory<>("TEACHER_ID"));
            TEACHER_NAME.setCellValueFactory(new PropertyValueFactory<>("TEACHER_NAME"));
            TEACHER_lASTNAME.setCellValueFactory(new PropertyValueFactory<>("TEACHER_lASTNAME"));
            STUDENT_NO.setCellValueFactory(new PropertyValueFactory<>("STUDENT_NO"));
            S_NAME.setCellValueFactory(new PropertyValueFactory<>("S_NAME"));
            S_lASTNAME.setCellValueFactory(new PropertyValueFactory<>("S_lASTNAME"));
            GRADE_ID.setCellValueFactory(new PropertyValueFactory<>("GRADE_ID"));
            GRADE_DIV.setCellValueFactory(new PropertyValueFactory<>("GRADE_DIV"));
            CLASS_YEAR.setCellValueFactory(new PropertyValueFactory<>("CLASS_YEAR"));
            this.classListTableView.setItems(classList);
            classListTableView.setEditable(true);
        }
    }
    public void AssignTeacherToClass(Event event){

        try {
            String teacher_id = TeachersId.getSelectionModel().getSelectedItem();
            String class_id = ClassPicker.getSelectionModel().getSelectedItem();
            String Query = "UPDATE class SET `teacher_id`=? WHERE `class_id`=?;";
            String TeachQuery = "UPDATE teacher SET `idClass`=? WHERE `idTeacher`=?";
            PreparedStatement pr = connection.prepareStatement(Query);
            PreparedStatement prs = connection.prepareStatement(TeachQuery);
            pr.setString(1,teacher_id);
            pr.setString(2,class_id);

            prs.setString(1,class_id);
            prs.setString(2,teacher_id);


            prs.execute();
            prs.close();
            pr.execute();
            pr.close();
            ((Node)event.getSource()).getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void FillComboBox(){
        try {
            this.connection = dbConnection.getConnection();
            ResultSet rs;
            ResultSet resultSet;
            Ids.clear();
            String QryClassId = "select class.class_id from class where class.teacher_id is null";
            String QryTeacherID = "SELECT idTeacher from teacher where teacher.idClass is null";
            PreparedStatement prClassID = connection.prepareStatement(QryClassId);
            PreparedStatement prteacherID = connection.prepareStatement(QryTeacherID);
            rs = prClassID.executeQuery();
            resultSet = prteacherID.executeQuery();
            while (rs.next()) {
                //class id
                classIds.add(rs.getString("class_id"));
                ClassPicker.setItems(classIds);
            }
            while (resultSet.next()) {
                //teacher id
                Ids.add(resultSet.getString("idTeacher"));
                TeachersId.setItems(Ids);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void UpdatePassword() throws SQLException {
        String pass,rePass,employeeID;
        pass = passWord.getText();
        rePass = rePassWord.getText();
        employeeID = lblEmployeeID.getText();
        if (pass.equals(rePass)){
            adminModel.UpdatePass(employeeID,pass);
        }
        else{
            if (pass.equals("")){
                LabelPass.setText("Password Required");
            }
            if (!pass.equals(rePass)){
                LabelPass.setText("Passwords Don't Match");
            }
        }
    }
    public void AlertBox(String textMessage){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!!!");
        alert.setHeaderText(null);
        alert.setContentText(textMessage);
        alert.showAndWait();
    }
}
