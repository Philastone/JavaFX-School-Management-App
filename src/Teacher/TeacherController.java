package Teacher;
import DbUtil.dbConnection;
import admin.AdminController;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeacherController implements Initializable {
    Connection connection;
    TeacherModel teacherModel = new TeacherModel();
    @FXML private Label userLoginLabal;
    @FXML private TextField AssName,AssWeight,AssTotal;
    //Class Table
    @FXML private TableView<assessementList> tableClassList;
    @FXML private TableColumn<assessementList,String> STUDENT_NO;
    @FXML private TableColumn<assessementList,String> S_NAME;
    @FXML private TableColumn<assessementList,String> S_lASTNAME;
    @FXML private TableColumn<assessementList,String> SUB_NAME;
    @FXML private TableColumn<assessementList,String> ASSESS_NAME;
    @FXML private TableColumn<assessementList,String> RESULT;
    @FXML private TableColumn<assessementList,String> OVERALL_MARK;
    @FXML private ListView<String> listView = new ListView<>();
    @FXML private ListView<String> GenerateStudentList = new ListView<>();
    @FXML private ComboBox<String> SubComboBox = new ComboBox<>();
    @FXML private ComboBox<String> filterbySub = new ComboBox<>();
    private String GradeID;
    private String TeacherID;
    @FXML private TableView<assessmentClass> assessmentTableView;
    @FXML private TableColumn<assessmentClass,String> ASSESSMENT_ID;
    @FXML private TableColumn<assessmentClass,String> ASSESSMENT_NAME;
    @FXML private TableColumn<assessmentClass,String> ASSESSMENT_WEIGHTING;
    @FXML private TableColumn<assessmentClass,String> ASSESSMENT_TOTAL;
    @FXML private TableColumn<assessmentClass,String> SUBJECT_NAME;
    @FXML private ComboBox<String> subjectComboBox = new ComboBox<>();
    @FXML private ComboBox<String> assessmentNameComboBox = new ComboBox<>();
    @FXML private TextField studentMarksTxt;
    @FXML private ListView<String> studentListView = new ListView<>();
    //Class Register table
    @FXML private TableView<classRegister> classRegisterTableView;
    @FXML private TableColumn<classRegister,Number> STUDENT_ID;
    @FXML private TableColumn<classRegister,String> STUDENT_NAME;
    @FXML private TableColumn<classRegister,String> STUDENT_SURNAME;
    @FXML private TableColumn<classRegister,String> ATTENDANCE;
    @FXML private Button updateMark;
    @FXML private TableView<GenAssessment> genAssessmentTableView;
    @FXML private TableColumn<GenAssessment,String> subjectName;
    @FXML private TableColumn<GenAssessment,String> asessName;
    @FXML private TableColumn<GenAssessment,String> studentResult;
    @FXML private Label lblEnglish,lblMaths,lblLo,lblAfrikaans,lblSub1;
    @FXML private TextArea teacherComment;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public void setGradeID(String gradeID){
        this.GradeID = gradeID;
    }
    public String getGradeID(){
        return this.GradeID;
    }
    public String getTeacherID() {
        return this.TeacherID;
    }
    public void setTeacherID(String teacherID) {
        this.TeacherID = teacherID;
    }
    public void LogoutButton (ActionEvent event){
        try {
            ((Node)event.getSource()).getScene().getWindow().hide();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("/LoginApp/login2.fxml").openStream());

            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.setTitle("School Data Management System");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void DisplayLoginDetails(String name){
        this.userLoginLabal.setText(name);
    }
    public void LoadingClassList(){
        ObservableList<assessementList> assessementLists = FXCollections.observableArrayList();
        String teacherID = getTeacherID();
        String subject = filterbySub.getSelectionModel().getSelectedItem();
        String subID = teacherModel.getSubjectID(subject,getGradeID());
        if (filterbySub.getSelectionModel().isEmpty() || filterbySub.getSelectionModel().getSelectedItem().equals("Show All"))
        {
            try {
                connection = dbConnection.getConnection();
                String query = "select student.student_id,student.Name,student.Surname,subject.name,assessment.name,assessment_result.result,assessment_result.overallMark " +
                        "from student,subject,assessment,assessment_result " +
                        "where student.student_id=assessment_result.id_student and subject.subject_id=assessment_result.id_subject and assessment.assessment_id=assessment_result.assessment_id " +
                        " and assessment_result.id_class=?";

                PreparedStatement pr = connection.prepareStatement(query);
                pr.setString(1,getClassID());
                ResultSet rs = pr.executeQuery();

                while (rs.next()){
                    assessementLists.add(
                            new assessementList(
                                    rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7))

                    );

                }
                STUDENT_NO.setCellValueFactory(new PropertyValueFactory<>("STUDENT_NO"));
                S_NAME.setCellValueFactory(new PropertyValueFactory<>("S_NAME"));
                S_lASTNAME.setCellValueFactory(new PropertyValueFactory<>("S_lASTNAME"));
                SUB_NAME.setCellValueFactory(new PropertyValueFactory<>("SUB_NAME"));
                ASSESS_NAME.setCellValueFactory(new PropertyValueFactory<>("ASSESS_NAME"));
                RESULT.setCellValueFactory(new PropertyValueFactory<>("RESULT"));
                OVERALL_MARK.setCellValueFactory(new PropertyValueFactory<>("OVERALL_MARK"));


                this.tableClassList.setItems(assessementLists);
                tableClassList.setEditable(true);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                connection = dbConnection.getConnection();
                String query = "select student.student_id,student.Name,student.Surname,subject.name,assessment.name,assessment_result.result,assessment_result.overallMark " +
                        "from student,subject,assessment,assessment_result " +
                        "where student.student_id=assessment_result.id_student and subject.subject_id=assessment_result.id_subject and assessment.assessment_id=assessment_result.assessment_id " +
                        " and id_subject=? and id_class=?";
                PreparedStatement pr = connection.prepareStatement(query);
                pr.setString(1,subID);
                pr.setString(2,getClassID());
                ResultSet rs = pr.executeQuery();
                while (rs.next()){
                    assessementLists.add(
                            new assessementList(
                                    rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7))

                    );

                }
                STUDENT_NO.setCellValueFactory(new PropertyValueFactory<>("STUDENT_NO"));
                S_NAME.setCellValueFactory(new PropertyValueFactory<>("S_NAME"));
                S_lASTNAME.setCellValueFactory(new PropertyValueFactory<>("S_lASTNAME"));
                SUB_NAME.setCellValueFactory(new PropertyValueFactory<>("SUB_NAME"));
                ASSESS_NAME.setCellValueFactory(new PropertyValueFactory<>("ASSESS_NAME"));
                RESULT.setCellValueFactory(new PropertyValueFactory<>("RESULT"));
                OVERALL_MARK.setCellValueFactory(new PropertyValueFactory<>("OVERALL_MARK"));


                this.tableClassList.setItems(assessementLists);
                tableClassList.setEditable(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void LoadSubjects() { // loads all subject for the grade

        try{
            ObservableList<String> subs = FXCollections.observableArrayList();
            ObservableList<String> subsComboFilter = FXCollections.observableArrayList();
            subsComboFilter.add("Show All");
            String grade = getGradeID();
            connection = dbConnection.getConnection();
            String qry = "select * from schooldb.subject where grade_id=?";
            PreparedStatement pr = connection.prepareStatement(qry);
            pr.setString(1,grade);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                subs.add(rs.getString("name"));
                subsComboFilter.add(rs.getString("name"));
                listView.setItems(subs);
                SubComboBox.setItems(subs);
                subjectComboBox.setItems(subs);
                filterbySub.setItems(subsComboFilter);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void CreateAssessment() {
        // assessmentClass id will be subject code + assessmentClass name = subjectID_assessementName
        // find a way to get grade id
        String subjectCode = "";

        String grade = getGradeID();
        String assessmentName = AssName.getText();
        if (stringValidator(assessmentName)){
            AlertBox("Valid string");
        }
        else{
            AlertBox("Invalid string");

        }
        if (Validation(AssTotal.getText()) && Validation(AssWeight.getText()) && !assessmentName.equals("")
                && !SubComboBox.getSelectionModel().isEmpty()) {
            double toatlMarks = Double.valueOf(AssTotal.getText());
            float Weight = Float.valueOf(AssWeight.getText());
            String subject = SubComboBox.getSelectionModel().getSelectedItem();

            if (Weight <=100 ) {
                if (IsWeightingTotalLessThanHundred(teacherModel.getSubjectID(subject,getGradeID()),Weight)) {
                    try {
                        String sql = "select * from subject where grade_id=? and name=?";
                        PreparedStatement prs = this.connection.prepareStatement(sql);
                        String qry = "INSERT INTO `schooldb`.`assessment` (`assessment_id`, `name`, `weighting`, `total`,subjectID) VALUES (?,?,?,?,?)";
                        PreparedStatement pr = this.connection.prepareStatement(qry);
                        prs.setString(1, grade);
                        prs.setString(2, subject);

                        ResultSet rs;
                        rs = prs.executeQuery();
                        while (rs.next()) {
                            subjectCode = rs.getString("subject_id");
                            String AssessmentID = subjectCode + "_" + assessmentName.toLowerCase();
                            //teacherModel.CreateAssessment(AssessmentID, assessmentName, toatlMarks, Weight);
                            pr.setString(1,AssessmentID);
                            pr.setString(2,assessmentName);
                            pr.setString(3, String.valueOf(Weight));
                            pr.setString(4, String.valueOf(toatlMarks));
                            pr.setString(5,subjectCode);
                            pr.execute();
                            pr.close();

                        }
                        ClearAssessmentFields();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (Weight>100){
                    AlertBox("Please enter assessment weighting that is less or equal to 100 ");
                }
            }
        }
        else{
            if (assessmentName.equals("")){
                AlertBox("Please enter Assessment Name");
            }
            if (!Validation(AssTotal.getText())) {
                AlertBox("Invalid Value on Assessment Total");
            }
            if (!Validation(AssWeight.getText())){
                AlertBox("Invalid Value on Assessment Weighting");
            }
            if(SubComboBox.getSelectionModel().isEmpty()){
                AlertBox("PLease select Subject");
            }
        }
    }
    public void onAssessmentTableClick(){
        if (!assessmentTableView.getSelectionModel().isEmpty()) {
            String AssNam,AssTot,AssWeig,Subject;
            assessmentClass assessmentClass = assessmentTableView.getSelectionModel().getSelectedItem();
            AssNam = assessmentClass.getASSESSMENT_NAME();
            AssTot = assessmentClass.getASSESSMENT_TOTAL();
            AssWeig = assessmentClass.getASSESSMENT_WEIGHTING();
            Subject = assessmentClass.getSUBJECT_NAME();

            AssName.setText(AssNam);
            AssWeight.setText(AssWeig);
            AssTotal.setText(AssTot);
            SubComboBox.getSelectionModel().select(Subject);
            SubComboBox.setDisable(true);
        }

    }
    public void ClearAssessmentFields(){
        AssName.setText("");
        AssWeight.setText("");
        AssTotal.setText("");
        SubComboBox.getSelectionModel().clearSelection();
        SubComboBox.setDisable(false);
    }
    public void ClearStudentAssessment(){
        subjectComboBox.getSelectionModel().clearSelection();
        assessmentNameComboBox.getSelectionModel().clearSelection();
        studentMarksTxt.setText("");
        studentListView.getSelectionModel().clearSelection();
        tableClassList.getSelectionModel().clearSelection();
        updateMark.setDisable(true);
    }
    public void DeleteStudentAssessment() {
        assessementList assessementList = tableClassList.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete Assessment: \n"+assessementList.getSTUDENT_NO()+" "+assessementList.getSUBJ_NAME()+" " +
                ""+assessementList.getASSESS_NAME()+" ?");
        Optional <ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            teacherModel.deletelStudentAssessment(assessementList.getSTUDENT_NO());
        }
    }
    public void DeleteAssessment() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete Assessment: \n"+SubComboBox.getSelectionModel().getSelectedItem() +" "+AssName.getText()+" ?");
        Optional <ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            String assessmentName = teacherModel.getAssessmentID(teacherModel.getSubjectID(SubComboBox.getSelectionModel().getSelectedItem(),getGradeID()),AssName.getText());
            teacherModel.deleteAssessment(assessmentName);
        }

    }
    public void LoadAssessments(){
        ObservableList<assessmentClass> assessmentClassList = FXCollections.observableArrayList();
        String SubName = listView.getSelectionModel().getSelectedItem();
        String subjectID = teacherModel.getSubjectID(SubName,getGradeID());
        if (listView.getSelectionModel().isEmpty()) {
            try {
                connection = dbConnection.getConnection();
                String query = "select assessment.assessment_id,assessment.name,assessment.weighting,assessment.total,subject.name from schooldb.assessment,schooldb.subject" +
                        " where assessment.subjectID=subject.subject_id ";
                PreparedStatement pr = connection.prepareStatement(query);
                //   pr.setString(1,subjectID);
                ResultSet rs = pr.executeQuery();
                while (rs.next()){
                    assessmentClassList.add(
                            new assessmentClass(
                                    rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5))
                    );
                }
                ASSESSMENT_ID.setCellValueFactory(new PropertyValueFactory<>("ASSESSMENT_ID"));
                ASSESSMENT_NAME.setCellValueFactory(new PropertyValueFactory<>("ASSESSMENT_NAME"));
                ASSESSMENT_WEIGHTING.setCellValueFactory(new PropertyValueFactory<>("ASSESSMENT_WEIGHTING"));
                ASSESSMENT_TOTAL.setCellValueFactory(new PropertyValueFactory<>("ASSESSMENT_TOTAL"));
                SUBJECT_NAME.setCellValueFactory(new PropertyValueFactory<>("SUBJECT_NAME"));

                this.assessmentTableView.setItems(assessmentClassList);
                assessmentTableView.setEditable(true);

                pr.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                connection = dbConnection.getConnection();
                String query = "select assessment.assessment_id,assessment.name,assessment.weighting,assessment.total,subject.name from schooldb.assessment,schooldb.subject" +
                        " where assessment.subjectID=subject.subject_id and assessment.subjectID=?";
                PreparedStatement pr = connection.prepareStatement(query);
                pr.setString(1,subjectID);
                ResultSet rs = pr.executeQuery();
                while (rs.next()){
                    assessmentClassList.add(
                            new assessmentClass(
                                    rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5))
                    );
                }
                ASSESSMENT_ID.setCellValueFactory(new PropertyValueFactory<>("ASSESSMENT_ID"));
                ASSESSMENT_NAME.setCellValueFactory(new PropertyValueFactory<>("ASSESSMENT_NAME"));
                ASSESSMENT_WEIGHTING.setCellValueFactory(new PropertyValueFactory<>("ASSESSMENT_WEIGHTING"));
                ASSESSMENT_TOTAL.setCellValueFactory(new PropertyValueFactory<>("ASSESSMENT_TOTAL"));
                SUBJECT_NAME.setCellValueFactory(new PropertyValueFactory<>("SUBJECT_NAME"));

                this.assessmentTableView.setItems(assessmentClassList);
                assessmentTableView.setEditable(true);
                pr.close();
                rs.close();
                listView.getSelectionModel().clearSelection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public String getClassID(){
        String classID ="";
        try{
            connection = dbConnection.getConnection();
            String getClassID = "select * from class where teacher_id=?";
            PreparedStatement prp = connection.prepareStatement(getClassID);
            prp.setString(1,getTeacherID());

            ResultSet rs = prp.executeQuery();
            if (rs.next()){
                classID = rs.getString("class_id");
                return  classID;
            }
            prp.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classID;
    }
    public void loadStudentListView(){
/*        ObservableList<String> studentList = FXCollections.observableArrayList();
        try{
            connection = dbConnection.getConnection();

            String qry = "select concat(student.student_id,\"   \", student.Name,\" \",student.Surname) as STUDENT_NAME from student,class_student\n" +
                    "where student.student_id=class_student.student_id and class_student.class_id=?";
            PreparedStatement prp = connection.prepareStatement(qry);
            prp.setString(1,getClassID());
            ResultSet rs = prp.executeQuery();
            while (rs.next()){
                studentList.add(rs.getString("STUDENT_NAME"));
                studentListView.setItems(studentList);
                studentListView.setCellFactory(CheckBoxListCell.forListView());
                GenerateStudentList.setItems(studentList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
    public void LoadClassRegister(){
        ObservableList<classRegister> classRegisters = FXCollections.observableArrayList();
        try{
            connection = dbConnection.getConnection();
            String qry = "select student.student_id,student.Name,student.Surname " +
                    "from student,class,class_student,employee " +
                    "where class.teacher_id=employee.employee_id and student.student_id=class_student.student_id and " +
                    "class_student.class_id=class.class_id and class.teacher_id=?";
            PreparedStatement pr = connection.prepareStatement(qry);
            pr.setString(1,getTeacherID());
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                classRegisters.add(
                        new classRegister(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                "Absent/Present")

                );
            }
            STUDENT_ID.setCellValueFactory(new PropertyValueFactory<>("STUDENT_ID"));
            STUDENT_NAME.setCellValueFactory(new PropertyValueFactory<>("STUDENT_NAME"));
            STUDENT_SURNAME.setCellValueFactory(new PropertyValueFactory<>("STUDENT_SURNAME"));
            ATTENDANCE.setCellValueFactory(new PropertyValueFactory<>("ATTENDANCE"));
            ObservableList<String> mylist = FXCollections.observableArrayList();
            mylist.add("Absent");
            mylist.add("Present");
            ATTENDANCE.setCellFactory(ChoiceBoxTableCell.forTableColumn(mylist));


            classRegisterTableView.setEditable(true);
            classRegisterTableView.setItems(classRegisters);
            ATTENDANCE.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<classRegister, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<classRegister, String> event) {
                            (event.getTableView().getItems().get(event.getTablePosition().getRow())).setATTENDANCE(event.getNewValue());

                            classRegister classRegister = classRegisterTableView.getSelectionModel().getSelectedItem();
                            String attend = classRegister.getATTENDANCE();
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                            Integer StudentID = classRegister.getSTUDENT_ID();
                            LocalDate ld = LocalDate.now();
                            String date = dtf.format(ld);
                            String RegID = StudentID+""+date;
                            Boolean doesRegStudentExist = teacherModel.DoesStudentRegEntryExist(RegID);
                            if (!doesRegStudentExist){
                                try{

                                    connection = dbConnection.getConnection();
                                    String RegQuery = "INSERT INTO `schooldb`.`class_register` (`id_class`, `id_student`, `attendance`, `date`,`reg_ID`) " +
                                            "VALUES (?,?,?,?,?);";
                                    PreparedStatement pr = connection.prepareStatement(RegQuery);

                                    pr.setString(1,getClassID());
                                    pr.setString(2, String.valueOf(StudentID));
                                    pr.setString(3,attend);
                                    pr.setString(4,date);
                                    pr.setString(5,RegID);
                                    pr.execute();
                                    pr.close();

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            else{

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirm Register Entry");
                                alert.setHeaderText(null);
                                alert.setContentText("Register entry for Student: "+ StudentID+" on "+date+" already exist. \nDo you want to change entry?");
                                Optional<ButtonType> action = alert.showAndWait();

                                if (action.get() == ButtonType.OK) {
                                    try{
                                        connection = dbConnection.getConnection();
                                        String RegQuery = "UPDATE `schooldb`.`class_register` SET `attendance`=? WHERE `reg_ID`=?;";
                                        PreparedStatement pr = connection.prepareStatement(RegQuery);

                                        pr.setString(1,attend);
                                        pr.setString(2, RegID);

                                        pr.execute();
                                        pr.close();

                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }

                        }
                    }
            );


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void LoadGenerateAssessmentList(){
        ObservableList<GenAssessment> stdAssessment = FXCollections.observableArrayList();
        if (!GenerateStudentList.getSelectionModel().isEmpty()) {
            try{
                String student = GenerateStudentList.getSelectionModel().getSelectedItem();
                String studentID = student.substring(0,9);
                connection = dbConnection.getConnection();
                String query = "select subject.name,assessment.name,assessment_result.result from subject,assessment_result,assessment" +
                        " where assessment_result.assessment_id = assessment.assessment_id " +
                        "and subject.subject_id=assessment_result.id_subject and id_student=?";
                PreparedStatement pr = connection.prepareStatement(query);
                pr.setString(1,studentID);
                ResultSet rs = pr.executeQuery();
                while (rs.next()){
                    stdAssessment.add(
                            new GenAssessment(
                                    rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3)
                            )
                    );
                    subjectName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
                    asessName.setCellValueFactory(new PropertyValueFactory<>("asessName"));
                    studentResult.setCellValueFactory(new PropertyValueFactory<>("StudentMark"));


                    genAssessmentTableView.setEditable(true);
                    genAssessmentTableView.setItems(stdAssessment);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public String GetSubjectTotal(String studentID,String subjectID){
        try{
            int SubjectTotal=0, mark=0;

            connection = dbConnection.getConnection();
            String query = "select subject.name,assessment.name,assessment_result.overallMark from subject,assessment_result,assessment " +
                    " where assessment_result.assessment_id = assessment.assessment_id " +
                    " and subject.subject_id=assessment_result.id_subject and id_student=? and id_subject=?";
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString(1,studentID);
            prs.setString(2,subjectID);
            ResultSet rs = prs.executeQuery();
            while(rs.next()){
                mark = rs.getInt("overallMark");
                SubjectTotal= SubjectTotal + mark;
            }
            return String.valueOf(SubjectTotal);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void CalculateSubjectTotal(){
        String student = GenerateStudentList.getSelectionModel().getSelectedItem();
        String studentID = student.substring(0,9);
        String Maths, Afri,Eng,LO,Psy;
        String mathsMark,afriMark,engMark,loMark,psyMark;
        Maths ="Mathematics";
        Afri = "Afrikaans";
        Eng = "English";
        LO = "Life Orientation";
        Psy = "Physical Science";

        mathsMark = GetSubjectTotal(studentID,teacherModel.getSubjectID(Maths,getGradeID()));
        afriMark = GetSubjectTotal(studentID,teacherModel.getSubjectID(Afri,getGradeID()));
        engMark = GetSubjectTotal(studentID,teacherModel.getSubjectID(Eng,getGradeID()));
        loMark = GetSubjectTotal(studentID, teacherModel.getSubjectID(LO,getGradeID()));
        psyMark = GetSubjectTotal(studentID,teacherModel.getSubjectID(Psy,getGradeID()));

        lblMaths.setText(mathsMark);
        lblAfrikaans.setText(afriMark);
        lblEnglish.setText(engMark);
        lblLo.setText(loMark);
        lblSub1.setText(psyMark);


    }
    public String getWeighting(String assessmentID){
        String weighting = "";
        try {
            connection = dbConnection.getConnection();
            String getAssName ="select * from assessment where assessment_id=?";
            PreparedStatement pr = connection.prepareStatement(getAssName);
            pr.setString(1,assessmentID);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                weighting = resultSet.getString("weighting");
                return weighting;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weighting;
    }
    public String getTotal(String assessmentID){
        String total = "";
        try {
            connection = dbConnection.getConnection();
            String getAssName ="select * from assessment where assessment_id=?";
            PreparedStatement pr = connection.prepareStatement(getAssName);
            pr.setString(1,assessmentID);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                total = resultSet.getString("total");
                return total;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
    public boolean IsWeightingTotalLessThanHundred(String subjectID, float weight){

        try {
            int weighting = 0;
            int totalWeight =0;
            connection = dbConnection.getConnection();
            String getAssName ="select * from assessment where subjectID=? ";
            PreparedStatement pr = connection.prepareStatement(getAssName);
            pr.setString(1,subjectID);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                weighting = Integer.parseInt(resultSet.getString("weighting"));
                totalWeight = totalWeight+weighting;
            }
            if (totalWeight < 100){
                return true;
            }
            else{
                AlertBox("Assessment weighting is : "+totalWeight + "and after adding new Assessment will be"+ (totalWeight+weight));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void EnterStudentMarks(){
        String student,studentID,subjectID,assessmentID,result, assessName;

        result = studentMarksTxt.getText();

        String studentMark ="";

        if (Validation(result)) {
            if (!subjectComboBox.getSelectionModel().isEmpty() && !assessmentNameComboBox.getSelectionModel().isEmpty()) {
                subjectID = teacherModel.getSubjectID(subjectComboBox.getSelectionModel().getSelectedItem(),getGradeID());
                assessName = assessmentNameComboBox.getSelectionModel().getSelectedItem();
                assessmentID = teacherModel.getAssessmentID(subjectID,assessName);

                float weighting= Float.parseFloat(getWeighting(assessmentID));
                double total= Double.parseDouble(getTotal(assessmentID));

                if (!studentListView.getSelectionModel().isEmpty() && !result.equals("") ) {
                    if (Integer.parseInt(result) > 0) {
                        if (Float.parseFloat(result)<=total)
                        {
                            Double studentMarks = ((Integer.parseInt(result) / total) * weighting);
                            String overallMark = String.valueOf(Math.round(studentMarks*100.0)/100.0);
                            student = studentListView.getSelectionModel().getSelectedItem();
                            studentID = student.substring(0,9);
                            boolean DoesMarkExist = teacherModel.DoesStudentAssessmentMarkExist(studentID,assessmentID);
                            if (DoesMarkExist == false) {
                                try {
                                    connection = dbConnection.getConnection();
                                    String qry = "INSERT INTO `schooldb`.`assessment_result` (`result`, `id_student`, `id_subject`, `assessment_id`, `overallMark`,`id_class`)" +
                                            " VALUES (?,?,?,?,?,?);";
                                    PreparedStatement pr = connection.prepareStatement(qry);
                                    pr.setString(1,result);
                                    pr.setString(2,studentID);
                                    pr.setString(3,subjectID);
                                    pr.setString(4,assessmentID);
                                    pr.setString(5,overallMark);
                                    pr.setString(6,getClassID());
                                    pr.execute();
                                    pr.close();
                                    AlertBox("Student Mark entered Successfully");
                                    LoadingClassList();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                AlertBox("Student Mark already entered!!!");
                            }
                        }else{
                            if (Float.parseFloat(result) > total){
                                AlertBox("Please enter Student mark not greater than "+total);
                            }
                        }
                    }else{
                        if (Integer.parseInt(result) < 0){
                            AlertBox("Please enter a valid mark");
                        }
                    }
                }
                else{
                    if (studentListView.getSelectionModel().isEmpty()){
                        AlertBox("Please select a student from the list");
                    }
                    if (result.equals("") || studentMark.equals("")){
                        AlertBox("Please enter a value for the Student Mark");
                    }
                }
            }
            else{
                if (subjectComboBox.getSelectionModel().isEmpty()){
                    AlertBox("Please select a Subject");
                }
                if (assessmentNameComboBox.getSelectionModel().isEmpty()){
                    AlertBox("Please select Asssessment Name");
                }
            }
        }else{
            AlertBox("Please enter a valid student mark!");
        }

    }
    public void UpdateStudentMark(){

        assessementList assess = tableClassList.getSelectionModel().getSelectedItem();
        String StudentID = assess.getSTUDENT_NO();
        String SubName = assess.getSUBJ_NAME();
        String AssessName = assess.getASSESS_NAME();
        String assessID = teacherModel.getAssessmentID(teacherModel.getSubjectID(SubName,getGradeID()),AssessName);
        String result = studentMarksTxt.getText();
        float weighting= Float.parseFloat(getWeighting(assessID));
        double total= Double.parseDouble(getTotal(assessID));

        if (Validation(result)) {
            if (!result.equals("") && Float.parseFloat(result)<=total) {
                try{
                    Double studentMark = (((Integer.parseInt(result) / total) * weighting));
                    String overallMark = String.valueOf(Math.round(studentMark*100.0)/100.0);
                    connection = dbConnection.getConnection();
                    String UpdateQry = "UPDATE `schooldb`.`assessment_result` SET `result`=?, `overallMark`=? WHERE `id_student`=? and assessment_id=?";
                    PreparedStatement pr = connection.prepareStatement(UpdateQry);
                    pr.setString(1,result);
                    pr.setString(2,overallMark);
                    pr.setString(3,StudentID);
                    pr.setString(4,assessID);

                    pr.execute();
                    pr.close();
                    AlertBox("Student mark updated successfully");
                    studentMarksTxt.clear();
                    LoadingClassList();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else{
                if (result.equals("")){
                    AlertBox("Please enter a value for student mark!!!");
                }
                if (Float.parseFloat(result) > total){
                    AlertBox("Please enter Student mark not greater than "+total);
                }
            }
        }
    }
    public void OnTableStudentAssessClick(){
        if (!tableClassList.getSelectionModel().isEmpty()) {
            assessementList assess = tableClassList.getSelectionModel().getSelectedItem();
            String SubName = assess.getSUBJ_NAME();
            String AssessName = assess.getASSESS_NAME();
            String studentMark = assess.getRESULT();

            subjectComboBox.setValue(SubName);
            assessmentNameComboBox.setValue(AssessName);
            updateMark.setDisable(false);
        }
    }
    public void LoadComboBoxAssessments(){
        String subject = subjectComboBox.getSelectionModel().getSelectedItem();
        String subjectID = teacherModel.getSubjectID(subject,getGradeID());
        ObservableList<String> assessName = FXCollections.observableArrayList();
        try {
            connection = dbConnection.getConnection();
            String getAssName ="select * from assessment where subjectID=?";
            PreparedStatement pr = connection.prepareStatement(getAssName);
            pr.setString(1,subjectID);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                assessName.add(resultSet.getString("Name"));
                assessmentNameComboBox.setItems(assessName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void CreatePdf() throws DocumentException {
        try {
            String student = GenerateStudentList.getSelectionModel().getSelectedItem();
            String studentID = student.substring(0,9);
            String studentName = student.substring(11);
            String Maths, Afri,Eng,LO,Psy;
            Maths ="Mathematics";
            Afri = "Afrikaans";
            Eng = "English";
            LO = "Life Orientation";
            Psy = "Physical Science";
            Document myDocument = new Document();

            PdfWriter.getInstance(myDocument,new FileOutputStream(studentID+"_Final_Report.pdf"));
            myDocument.open();

            Image image = Image.getInstance("reportImg.png");
            image.setDpi(50,50);
            myDocument.add(image);
            myDocument.add(new Paragraph("Student Number: "+studentID+"\nName & Surname: "+studentName+"\n",
                    com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.TIMES_BOLD,15)));
            myDocument.add(new Paragraph("_____________________________________________________________________________\n\n\n\n"));
            PdfPTable table = new PdfPTable(2);
            PdfPCell cell = new PdfPCell(new Paragraph("My Title"));
            cell.setColspan(4);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.GRAY);
            //table.addCell(cell);
            table.addCell("Subject");
            table.addCell("Student Marks");
            table.addCell(Eng);
            table.addCell(lblEnglish.getText());
            table.addCell(Afri);
            table.addCell(lblAfrikaans.getText());
            table.addCell(Maths);
            table.addCell(lblMaths.getText());
            table.addCell(LO);
            table.addCell(lblLo.getText());
            table.addCell(Psy);
            table.addCell(lblSub1.getText());
            myDocument.add(table);
            myDocument.add(new Paragraph("\n\n_____________________________________________________________________________\n\n\n"));
            myDocument.add(new Paragraph("Student Conduct and Behaviour\n\n",FontFactory.getFont(FontFactory.TIMES_BOLD,12)));
            myDocument.add(new Paragraph(teacherComment.getText(),FontFactory.getFont(FontFactory.TIMES,10)));
            myDocument.add(new Paragraph("\nNumber of days absent: \n"+ getNumberOfDaysAbsent(studentID)+" ",FontFactory.getFont(FontFactory.TIMES_BOLD,12)));
            myDocument.add(new Paragraph("\nStudent Result",FontFactory.getFont(FontFactory.TIMES_BOLD,12)));
            myDocument.add(new Paragraph("Passed\n",FontFactory.getFont(FontFactory.TIMES,10)));

/*            List list = new List(true,10);
            list.add("1ST Item");
            list.add("2nd Item");
            list.add("3rd Item");
            list.add("4th Item");
            myDocument.add(list);*/
            myDocument.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void AlertBox(String textMessage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(textMessage);
        alert.showAndWait();
    }
    public boolean Validation(String number) {

        Pattern pattern = Pattern.compile("[0-9]+('\'.){0,1}[0-9]*");
        //  Pattern pattern = Pattern.compile("(0-9)+");
        Matcher matcher = pattern.matcher(number);
        if (matcher.find() && matcher.group().equals(number)) {
            return true;
        }
        return false;
    }
    public boolean stringValidator(String string){
        Pattern pattern = Pattern.compile("[A-Za-z]");

        Matcher matcher = pattern.matcher(string);
        if (matcher.find() && matcher.group().equals(string)) {
            return true;
        }
        return false;
    }
    public int getNumberOfDaysAbsent(String studentID){
        int count = 0;
        try{

            connection = dbConnection.getConnection();
            String query= "select * from class_register where id_student=? and class_register.attendance='Present'";
            PreparedStatement prp = connection.prepareStatement(query);
            prp.setString(1,studentID);
            ResultSet rs = prp.executeQuery();
            while (rs.next())
            {
                count++;
            }
            prp.close();
            rs.close();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
