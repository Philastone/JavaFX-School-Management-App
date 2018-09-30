package admin;

public enum Classes {
    All,grade8, grade9, grade10, grade11, grade12;
    private Classes(){}

    public String value(){return name();}
    public static Classes fromvalue (String v){return valueOf(v);}
}



