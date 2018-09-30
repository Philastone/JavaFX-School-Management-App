package Teacher;

import javafx.beans.property.SimpleStringProperty;

public class assessmentClass {
    private final SimpleStringProperty ASSESSMENT_ID;
    private final SimpleStringProperty ASSESSMENT_NAME;
    private final SimpleStringProperty ASSESSMENT_WEIGHTING;
    private final SimpleStringProperty ASSESSMENT_TOTAL;
    private final SimpleStringProperty SUBJECT_NAME;

    public assessmentClass(String assessment_id, String assessment_name,String assessment_weighting, String assessment_total, String subject_name) {
        this.ASSESSMENT_ID = new SimpleStringProperty(assessment_id) ;
        this.ASSESSMENT_NAME = new SimpleStringProperty(assessment_name);
        this.ASSESSMENT_WEIGHTING = new SimpleStringProperty(assessment_weighting);
        this.ASSESSMENT_TOTAL = new SimpleStringProperty(assessment_total);
        this.SUBJECT_NAME = new SimpleStringProperty(subject_name);
    }
   public String getASSESSMENT_ID() {
        return ASSESSMENT_ID.get();
    }

    public String getASSESSMENT_NAME() {
        return ASSESSMENT_NAME.get();
    }

    public String getASSESSMENT_WEIGHTING() {
        return ASSESSMENT_WEIGHTING.get();
    }

    public String getASSESSMENT_TOTAL() {
        return ASSESSMENT_TOTAL.get();
    }

    public String getSUBJECT_NAME() {
        return SUBJECT_NAME.get();
    }
}
