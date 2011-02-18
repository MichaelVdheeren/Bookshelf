package bookshelf.apis.libis.parameters;

import bookshelf.parameters.IParameter;

public enum LibisSearchfield implements IParameter {
	All ("WRD"),
	Auteurs ("WAU"),
	Titels ("WTI"),
	Exacte_titels ("TITH"),
	Reekstitels ("WSE"),
	Alle_onderwerpen ("WSU"),
	Uitgevers ("WPU"),
	ISBN ("STIDN"),
	ISSN ("STIDN"),
	EAN ("STIDN");
	
    private final String value;
    
    LibisSearchfield(String value) {
        this.value = value;
    }

    public String getName() { return "find_code"; }
    public String getValue()  { return this.value; }
    public String toString() { return getName()+"="+getValue(); }
}
