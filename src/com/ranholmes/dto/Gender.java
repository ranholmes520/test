package com.ranholmes.dto;

public enum Gender{
    MALE("MALE"), FEMALE("FEMALE");
    
    private final String value;

    Gender(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
