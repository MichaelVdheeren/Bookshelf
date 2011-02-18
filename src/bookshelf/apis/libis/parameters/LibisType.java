package bookshelf.apis.libis.parameters;

import bookshelf.IParameter;

public enum LibisType implements IParameter {
	All (""),
	Boeken ("(BK OR books) NOT (Theses OR Articles OR Chapters)"),
	Tijdschriften ("(SE OR journals)"),
	Audio_visueel_materiaal ("(VM OR MX OR Audio)"),
	Congressen ("Meetings"),
	Eindwerken ("Theses"),
	E_bronnen ("Online");
	
    private final String value;
    
    LibisType(String value) {
        this.value = value;
    }

    public String getName() { return "request_filter_4"; }
    public String getValue()  { return this.value; }
    public String toString() { return getName()+"="+getValue(); }
}
