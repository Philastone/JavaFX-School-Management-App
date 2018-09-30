package admin;

import javafx.beans.property.SimpleStringProperty;

public class Teacher {
    private final SimpleStringProperty TeacherID;
    private final SimpleStringProperty Name_t;
    private final SimpleStringProperty Surname_t;
    private final SimpleStringProperty HomeAddress_t;
    private final SimpleStringProperty Contact_t;
    private final SimpleStringProperty Grade_t;
    public Teacher(String teacherID, String name_t, String surname_t, String homeAddress_t, String contact_t, String grade_t) {
        TeacherID = new SimpleStringProperty (teacherID);
        Name_t = new SimpleStringProperty (name_t);
        Surname_t = new SimpleStringProperty (surname_t);
        HomeAddress_t = new SimpleStringProperty (homeAddress_t);
        Contact_t = new SimpleStringProperty (contact_t);
        Grade_t = new SimpleStringProperty (grade_t);
    }
    public String getTeacherID() {
        return TeacherID.get();
    }
    public String getName_t() {
        return Name_t.get();
    }
    public String getSurname_t() {
        return Surname_t.get();
    }
    public String getHomeAddress_t() {
        return HomeAddress_t.get();
    }
    public String getContact_t() {
        return Contact_t.get();
    }

    public String getGrade_t() {
        return Grade_t.get();
    }
}
