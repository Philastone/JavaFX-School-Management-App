package LoginApp;

public enum options {
    Admin, Teacher;

    private options(){}

    public String value(){
        return name();
    }
    public static options fromvalue (String v){
        return valueOf(v);
    }
}
