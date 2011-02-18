package bookshelf.apis.libis.junit;


import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import bookshelf.AbstractBook;
import bookshelf.apis.libis.LibisBookshelf;


public class CollectionTest {
	
	LibisBookshelf collection = new LibisBookshelf("SG4JHVPDF9CHC5V9D2XQE4Y6278V1KFUCFDRDV6UGY7J9R76MA-25544");

	@Before
	public void setUp() throws Exception {
	}
	
	
	@Test
	public void retrieveBookByBarcode() throws IOException {
		ArrayList<AbstractBook> books = collection.getBooks("009906485");
	}
}
