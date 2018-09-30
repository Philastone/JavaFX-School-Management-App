package Teacher;

import javafx.beans.property.SimpleStringProperty;

public class GenAssessment {

    private final SimpleStringProperty SubjectName;
    private final SimpleStringProperty AsessName;
    private final SimpleStringProperty StudentMark;

    public GenAssessment(String subjectName, String asessName, String studentMark) {
        this.SubjectName = new SimpleStringProperty(subjectName);
        this.AsessName = new SimpleStringProperty(asessName);
        this.StudentMark = new SimpleStringProperty(studentMark);
    }

    public String getSubjectName() {
        return SubjectName.get();
    }

    public SimpleStringProperty subjectNameProperty() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        this.SubjectName.set(subjectName);
    }

    public String getAsessName() {
        return AsessName.get();
    }

    public SimpleStringProperty asessNameProperty() {
        return AsessName;
    }

    public void setAsessName(String asessName) {
        this.AsessName.set(asessName);
    }

    public String getStudentMark() {
        return StudentMark.get();
    }

    public SimpleStringProperty studentMarkProperty() {
        return StudentMark;
    }

    public void setStudentMark(String studentMark) {
        this.StudentMark.set(studentMark);
    }
}
