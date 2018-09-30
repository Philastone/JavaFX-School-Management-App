package admin;

import javafx.beans.property.SimpleStringProperty;

public class Student {
    private final SimpleStringProperty StudentNo;
    private final SimpleStringProperty Name;
    private final SimpleStringProperty Surname;
    private final SimpleStringProperty Address;
    private final SimpleStringProperty Gender;
    private final SimpleStringProperty DOB;
    private final SimpleStringProperty Phone;
    private final SimpleStringProperty ParentName;
    private final SimpleStringProperty ParentSurname;
    private final SimpleStringProperty ParentOccupation;
    public Student(String StudentNo, String Name, String Surname,String address, String gender, String dob , String phone, String parentName, String parentSurname, String parentOccupation) {
        this.StudentNo = new SimpleStringProperty(StudentNo);
        this.Name = new SimpleStringProperty(Name);
        this.Surname = new SimpleStringProperty(Surname);
        this.Address = new SimpleStringProperty(address);
        this.Gender = new SimpleStringProperty(gender);
        this.DOB = new SimpleStringProperty(dob);
        this.Phone = new SimpleStringProperty(phone);
        this.ParentName = new SimpleStringProperty(parentName);
        this.ParentSurname = new SimpleStringProperty(parentSurname);
        this.ParentOccupation = new SimpleStringProperty(parentOccupation);

    }
    public String getStudentNo() {
        return StudentNo.get();
    }
    public String getName() {
        return Name.get();
    }
    public String getSurname() {
        return Surname.get();
    }
    public String getGender() {
        return Gender.get();
    }
    public String getDOB() {
        return DOB.get();
    }
    public String getAddress() {
        return Address.get();
    }
    public String getPhone() {
        return Phone.get();
    }
    public String getParentName() {
        return ParentName.get();
    }
    public String getParentSurname() {
        return ParentSurname.get();
    }
    public String getParentOccupation() {
        return ParentOccupation.get();
    }

}


