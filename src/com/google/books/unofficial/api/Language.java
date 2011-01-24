package com.google.books.unofficial.api;

public enum Language {
	NEDERLANDS ("nl"),
    ENGLISH   ("en");

    private final String value;
    
    Language(String value) {
        this.value = value;
    }
    public String getAttributeValue()   { return value; }
}
