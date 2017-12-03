package ua.peresvit.sn.security;

public enum RoleEnum {

    ADMIN("ROLE_ADMIN"), USER("ROLE_USER"),
    LEVEL_1("LEVEL_1"), LEVEL_2("LEVEL_2"), LEVEL_3("LEVEL_3");

    private String code;
    RoleEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
