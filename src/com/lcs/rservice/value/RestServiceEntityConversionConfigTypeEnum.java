package com.lcs.rservice.value;

public enum RestServiceEntityConversionConfigTypeEnum {

	INCLUDE(1), EXCLUDE(2);
	
	private int value;
	
	RestServiceEntityConversionConfigTypeEnum(int value) {
		this.value = value;
	}
	public int getValue() {
		return this.value;
	}
}
