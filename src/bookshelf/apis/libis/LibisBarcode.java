package bookshelf.apis.libis;

import bookshelf.Barcode;


public class LibisBarcode extends Barcode {
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