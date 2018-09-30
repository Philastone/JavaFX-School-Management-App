package admin;

import javafx.beans.property.SimpleStringProperty;

public class ClassList {
    private SimpleStringProperty TEACHER_ID;
    private final SimpleStringProperty TEACHER_NAME;
    private final SimpleStringProperty TEACHER_lASTNAME;
    private final SimpleStringProperty STUDENT_NO;
    private final SimpleStringProperty S_NAME;
    private final SimpleStringProperty S_lASTNAME;
    private final SimpleStringProperty GRADE_ID;
    private final SimpleStringProperty GRADE_DIV;
    private final SimpleStringProperty CLASS_YEAR;


    public ClassList(String teacher_id, String teacher_name, String teacher_lASTNAME, String student_no, String s_name, String s_lASTNAME, String grade_id, String grade_div, String class_year) {
        TEACHER_ID = new SimpleStringProperty(teacher_id) ;
        TEACHER_NAME = new SimpleStringProperty(teacher_name);
        TEACHER_lASTNAME = new SimpleStringProperty(teacher_lASTNAME);
        STUDENT_NO =new SimpleStringProperty(student_no) ;
        S_NAME =new SimpleStringProperty(s_name) ;
        S_lASTNAME =new SimpleStringProperty(s_lASTNAME) ;
        GRADE_ID =new SimpleStringProperty(grade_id) ;
        GRADE_DIV =new SimpleStringProperty(grade_div) ;
        CLASS_YEAR =new SimpleStringProperty(class_year) ;
    }

    public void setTEACHER_ID(String teacher_id){
        TEACHER_ID = new SimpleStringProperty(teacher_id);
    }
    public String getTEACHER_ID() {
        return TEACHER_ID.get();
    }

    public String getTEACHER_NAME() {
        return TEACHER_NAME.get();
    }

    public String getTEACHER_lASTNAME() {
        return TEACHER_lASTNAME.get();
    }

    public String getSTUDENT_NO() {
        return STUDENT_NO.get();
    }

    public String getS_NAME() {
        return S_NAME.get();
    }

    public String getS_lASTNAME() {
        return S_lASTNAME.get();
    }

    public String getGRADE_ID() {
        return GRADE_ID.get();
    }

    public String getGRADE_DIV() {
        return GRADE_DIV.get();
    }

    public String getCLASS_YEAR() {
        return CLASS_YEAR.get();
    }
}
