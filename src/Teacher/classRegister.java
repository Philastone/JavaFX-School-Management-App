package Teacher;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class classRegister {
    private final SimpleIntegerProperty STUDENT_ID;
    private final SimpleStringProperty STUDENT_NAME;
    private final SimpleStringProperty STUDENT_SURNAME;
    private final SimpleStringProperty ATTENDANCE;


    public classRegister(Integer student_id, String student_name, String student_surname, String attendance) {
        this.STUDENT_ID = new SimpleIntegerProperty(student_id);
        this.STUDENT_NAME = new SimpleStringProperty(student_name);
        this.STUDENT_SURNAME = new SimpleStringProperty(student_surname);
        this.ATTENDANCE = new SimpleStringProperty(attendance);
    }

    public int getSTUDENT_ID() {
        return STUDENT_ID.get();
    }

    public SimpleIntegerProperty STUDENT_IDProperty() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(int STUDENT_ID) {
        this.STUDENT_ID.set(STUDENT_ID);
    }

    public String getSTUDENT_NAME() {
        return STUDENT_NAME.get();
    }

    public SimpleStringProperty STUDENT_NAMEProperty() {
        return STUDENT_NAME;
    }

    public void setSTUDENT_NAME(String STUDENT_NAME) {
        this.STUDENT_NAME.set(STUDENT_NAME);
    }

    public String getSTUDENT_SURNAME() {
        return STUDENT_SURNAME.get();
    }

    public SimpleStringProperty STUDENT_SURNAMEProperty() {
        return STUDENT_SURNAME;
    }

    public void setSTUDENT_SURNAME(String STUDENT_SURNAME) {
        this.STUDENT_SURNAME.set(STUDENT_SURNAME);
    }

    public String getATTENDANCE() {
        return ATTENDANCE.get();
    }

    public SimpleStringProperty ATTENDANCEProperty() {
        return ATTENDANCE;
    }

    public void setATTENDANCE(String ATTENDANCE) {
        this.ATTENDANCE.set(ATTENDANCE);
    }
}
