package admin;
import javafx.beans.property.SimpleStringProperty;
public class Employee {
    private final SimpleStringProperty EmployeeID;
    private final SimpleStringProperty FNAME;
    private final SimpleStringProperty LNAME;
    private final SimpleStringProperty DOB_e;
    private final SimpleStringProperty GENDER;
    private final SimpleStringProperty ADDRESS;
    private final SimpleStringProperty PHONE;
    private final SimpleStringProperty ROLE;
    public Employee(String employeeID, String fname, String lname, String dob, String gender, String address, String phone, String role) {
        this.EmployeeID = new SimpleStringProperty(employeeID);
        this.FNAME = new SimpleStringProperty(fname);
        this.LNAME = new SimpleStringProperty(lname);
        this.DOB_e =  new SimpleStringProperty(dob);
        this.GENDER = new SimpleStringProperty(gender);
        this.ADDRESS = new SimpleStringProperty(address);
        this.PHONE = new SimpleStringProperty(phone);
        this.ROLE = new SimpleStringProperty(role);
    }
    public String getEmployeeID() {
        return EmployeeID.get();
    }
    public String getFNAME() {
        return FNAME.get();
    }
    public String getLNAME() {
        return LNAME.get();
    }
    public String getDOB_e() {
        return DOB_e.get();
    }
    public String getGENDER() {
        return GENDER.get();
    }
    public String getADDRESS() {
        return ADDRESS.get();
    }
    public String getPHONE() {
        return PHONE.get();
    }
    public String getROLE() {
        return ROLE.get();
    }

}
