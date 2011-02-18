package bookshelf.apis.libis.junit;


import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import bookshelf.AbstractBook;
import bookshelf.apis.libis.LibisBookshelf;
import bookshelf.exceptions.BookshelfUnavailableException;


public class LibisBookshelfTest {
	
	LibisBookshelf collection = new LibisBookshelf("SG4JHVPDF9CHC5V9D2XQE4Y6278V1KFUCFDRDV6UGY7J9R76MA-25544");

	@Before
	public void setUp() throws Exception {
	}
	
	
	@Test
	public void retrieveBookByBarcode() throws BookshelfUnavailableException {
		ArrayList<AbstractBook> books = collection.getBooks("009906485");
	}
	
	@Test
	public void retrieveBookByISBN() {
		//978-0-470-39880-7
	}
}
