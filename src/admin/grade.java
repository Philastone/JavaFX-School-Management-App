package admin;

public enum grade {
    grade8, grade9, grade10, grade11, grade12;

    private grade(){}

    public String value(){
        return name();
    }
    public static grade fromvalue (String v){
        return valueOf(v);
    }
}

