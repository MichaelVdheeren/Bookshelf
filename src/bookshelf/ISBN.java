package bookshelf;


/**
 * Class representing an International Standard Book Number
 */
public class ISBN {
	public final String value;
	
	/**
	 * Create a new instance of the ISBN class
	 * @param	value
	 * 			The string representing an ISBN.
	 */
	public ISBN(String value) {
		// Remove all dashes
		value = value.replaceAll("-", "");
		value = value.trim();
		
		this.value = value;
	}
	
	/**
	 * Returns a string representation of the ISBN.
	 * @return	The string representation of the ISBN.
	 */
	@Override
	public String toString() {
		return getValue();
	}

	public String getValue() {
		return this.value;
	}
	
	/**
	 * Check if the given value is a valid ISBN.
	 * @param	value
	 * 			The <value> to check for it's ISBN validity.
	 * @return	True if the given <value> is a valid ISBN in the ISBN-10
	 * 			or ISBN-13 format. False otherwise.
	 */
	public static boolean isValidISBN(String value) {
		if (value.length() == 10)
			return isValidISBN10(value);
		else if (value.length() == 13)
			return isValidISBN13(value);
		
		return false;
	}
	
	/**
	 * Check if the given value is a valid ISBN-10.
	 * @param 	value
	 * 			The <value> to check for it's ISBN validity.
	 * @return	True if the given <value> is a valid ISBN in the ISBN-10
	 * 			format. False otherwise.
	 */
	private static boolean isValidISBN10(String value) {
		if (value.length() != 10)
			return false;
		
		int sum = 0, n;
		String s;
		
		for (int i=10; i>0; i--) {
			s = value.substring(10-i, 10-i+1);
			
			if (s.equals("X") || s.equals("x")) {
				n = 10;
			} else {
				try {
					n = Integer.parseInt(s);
				} catch (NumberFormatException e) {
					return false;
				}
			}
			sum += i*n;
		}
		
		return (sum%11 == 0);
	}
	
	/**
	 * Check if the given value is a valid ISBN-13.
	 * @param 	value
	 * 			The <value> to check for it's ISBN validity.
	 * @return	True if the given <value> is a valid ISBN in the ISBN-13
	 * 			format. False otherwise.
	 */
	private static boolean isValidISBN13(String value) {
		int sum = 0, n, m;
		String s;
		
		for (int i=1; i<13; i++) {
			s = value.substring(i-1,i);
			try {
				n = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return false;
			}
			
			m = ((i%2 == 1) ? 1 : 3);
			sum += n*m;
		}
		
		try {
			n = Integer.parseInt(value.substring(12,13));
		} catch (NumberFormatException e) {
			return false;
		}
		
		return (((10-sum%10)%10)-n == 0);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ISBN))
			return false;
		
		ISBN isbn = (ISBN) o;
		return getValue().equals(isbn.getValue());
	}
}
