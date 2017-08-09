package ua.peresvit.sn.domain.entity;

public enum EnumResourceGroupType {
	
	EVENT(0),
	BASE_TECHNIQUE(1),
	BASE_TECH_COMPLEX(2),
	PAIRED_WORK(3),
	SPECIAL_PHYSICAL_TRAININGS(4),
	GENERAL_PHYSICAL_TRAININGS(5),
	ANOTHER_SUBJECTS(6),
	COMPETITION(7);
	
	private final int value;

	EnumResourceGroupType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}