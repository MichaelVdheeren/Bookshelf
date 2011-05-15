package bookshelf.apis.libis;

import java.io.Serializable;

import bookshelf.Barcode;


public class LibisBarcode extends Barcode implements Serializable {
	private static final long serialVersionUID = 1L;

	public LibisBarcode(String value) {
		super(value);
	}
	
	public boolean hasValidLength() {
		return isValidLength(this.getValue());
	}
	
	public static boolean isValidLength(String value) {
		value = value.replaceAll("-", "");
		value = value.trim();
		return value.length() == 9;
	}
}