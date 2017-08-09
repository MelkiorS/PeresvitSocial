package ua.peresvit.sn.util.helper;


public enum SexEnum {
    MALE("М",'M'),
    FEMALE("Ж",'F');

    private String display;
    private Character dBName;

    SexEnum( String display, Character dBName) {
        this.display = display;
        this.dBName = dBName;
    }


    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Character getdBName() {
        return dBName;
    }

    public void setdBName(Character dBName) {
        this.dBName = dBName;
    }
}
