package bookshelf.apis.libis;


public class LibisBarcode {
	public final String value;

	public LibisBarcode(String value) {
		// Remove all dashes
		value = value.replaceAll("-", "");
		value = value.trim();
		
		this.value = value;
	}

	@Override
	public String toString() {
		return getValue();
	}

	public String getValue() {
		return this.value;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof LibisBarcode))
			return false;
		
		LibisBarcode code = (LibisBarcode) o;
		return getValue().equals(code.getValue());
	}
}