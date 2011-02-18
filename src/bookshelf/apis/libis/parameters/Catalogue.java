package bookshelf.apis.libis.parameters;

import bookshelf.IParameter;

public enum Catalogue implements IParameter {
	All ("OPAC01"),
	ACV ("ACV"),
	Academia_Belgica ("AB"),
	Belgisch_Parlement ("BPB"),
	Boerenbond ("BB"),
	De_Nayer_Instituut ("DNA"),
	FARO ("VCV"),
	FOD_FIN ("FINBI"),
	FOD_PO ("OFO"),
	HUBrussel ("HUB"),
	IMEC ("IMEC"),
	KULeuven ("KUL"),
	KADOC ("KADOC"),
	KATHO ("KATHO"),
	KBC ("KBC"),
	KBIN ("RBINS"),
	KHBO ("KHBO"),
	KHK ("KHK"),
	KHLim ("KHLIM"),
	KHMechelen ("KHM"),
	KMKG ("KMKG"),
	KMMA ("RMCA"),
	KaHo_SL ("KAHO"),
	Lessius ("LESS"),
	Liberaal_Archief ("LIBAR"),
	NBB ("NBB"),
	Religieuze_Inst ("RELI"),
	SERV ("SERV"),
	St_Lukas ("LUKAS"),
	VDIC ("WIV"),
	Vlaams_Parlement ("VLP"),
	WenK ("WENK");

    private final String value;
    
    Catalogue(String value) {
        this.value = value;
    }

    public String getName() { return "local_base"; }
    public String getValue()  { return this.value; }
    public String toString() { return getName()+"="+getValue(); }
}
