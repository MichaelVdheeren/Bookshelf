package bookshelf.apis.google.parameters;

import bookshelf.IParameter;

public enum Language implements IParameter {
	Nederlands ("nl"),
    English   ("en");

    private final String value;
    
    Language(String value) {
        this.value = value;
    }
    
    public String getName()   { return "hl"; }
    public String getValue()   { return value; }
    public String toString() { return getName()+"="+getValue(); }
}
