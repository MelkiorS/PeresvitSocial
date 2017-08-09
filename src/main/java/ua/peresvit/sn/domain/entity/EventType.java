package ua.peresvit.sn.domain.entity;

public enum EventType {

    COMPETITION("Змагання"),
    CELEBRATION("Святкування"),
    DEMONSTARTION("Показові виступи"),
    APPRAISAL("Атестації"),
    WORKSHOP("Семінари"),
    TEASCHOOL("Чайні школи"),
    HIKECAMPMOVIE("Походи, табори, перегляд кінофільмів");

    private final String pres;

    EventType(String pres) {
        this.pres = pres;
    }

    public String getPres() {
        return pres;
    }

    public static EventType defaultType() {
        return COMPETITION;
    }
}
