package ua.peresvit.sn.domain.entity;

public enum EnumUserInfo {

	ADITTIONAL(1L);
//	CLUB(2L),
//	MENTOR(3L),
//	ABOUT(4L);
	
	private final Long value;
	
    EnumUserInfo(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }	
}
