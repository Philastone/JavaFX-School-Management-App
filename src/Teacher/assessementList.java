package Teacher;
import javafx.beans.property.SimpleStringProperty;
public class assessementList {
    private final SimpleStringProperty STUDENT_NO;
    private final SimpleStringProperty S_NAME;
    private final SimpleStringProperty S_lASTNAME;
    private final SimpleStringProperty SUB_NAME;
    private final SimpleStringProperty ASSESS_NAME;
    private final SimpleStringProperty RESULT;
    private final SimpleStringProperty OVERALL_MARK;
    public assessementList(String student_no, String s_name, String s_lASTNAME, String subject_name, String assessment_name, String result, String overall_mark) {
        STUDENT_NO = new SimpleStringProperty(student_no);
        S_NAME = new SimpleStringProperty(s_name) ;
        S_lASTNAME = new SimpleStringProperty(s_lASTNAME) ;
        SUB_NAME = new SimpleStringProperty(subject_name) ;
        ASSESS_NAME = new SimpleStringProperty(assessment_name) ;
        RESULT = new SimpleStringProperty(result);
        OVERALL_MARK = new SimpleStringProperty(overall_mark) ;
    }
    public String getSTUDENT_NO() {
        return STUDENT_NO.get();
    }
    public SimpleStringProperty STUDENT_NOProperty() {
        return STUDENT_NO;
    }
    public void setSTUDENT_NO(String STUDENT_NO) {
        this.STUDENT_NO.set(STUDENT_NO);
    }
    public String getS_NAME() {
        return S_NAME.get();
    }
    public SimpleStringProperty s_NAMEProperty() {
        return S_NAME;
    }
    public void setS_NAME(String s_NAME) {
        this.S_NAME.set(s_NAME);
    }
    public String getS_lASTNAME() {
        return S_lASTNAME.get();
    }
    public SimpleStringProperty s_lASTNAMEProperty() {
        return S_lASTNAME;
    }

    public void setS_lASTNAME(String s_lASTNAME) {
        this.S_lASTNAME.set(s_lASTNAME);
    }

    public String getSUBJ_NAME() {
        return SUB_NAME.get();
    }

    public SimpleStringProperty SUB_NAMEProperty() {
        return SUB_NAME;
    }

    public void setSUBJ_NAME(String SUB_NAME) {
        this.SUB_NAME.set(SUB_NAME);
    }

    public String getASSESS_NAME() {
        return ASSESS_NAME.get();
    }

    public SimpleStringProperty ASSESS_NAMEProperty() {
        return ASSESS_NAME;
    }

    public void setASSESS_NAME(String ASSESS_NAME) {
        this.ASSESS_NAME.set(ASSESS_NAME);
    }

    public String getRESULT() {
        return RESULT.get();
    }

    public SimpleStringProperty RESULTProperty() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT.set(RESULT);
    }

    public String getOVERALL_MARK() {
        return OVERALL_MARK.get();
    }

    public SimpleStringProperty OVERALL_MARKProperty() {
        return OVERALL_MARK;
    }

    public void setOVERALL_MARK(String OVERALL_MARK) {
        this.OVERALL_MARK.set(OVERALL_MARK);
    }
}
