package bookshelf.apis.google.parameters;

import bookshelf.parameters.IParameter;

public enum GoogleLanguage implements IParameter {
	Nederlands ("nl"),
    English   ("en");

    private final String value;
    
    GoogleLanguage(String value) {
        this.value = value;
    }
    
    public String getName()   { return "hl"; }
    public String getValue()   { return value; }
    public String toString() { return getName()+"="+getValue(); }
}
