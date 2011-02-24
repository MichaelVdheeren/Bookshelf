package bookshelf.apis.libis.junit;


import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import bookshelf.AbstractBook;
import bookshelf.apis.libis.LibisBarcode;
import bookshelf.apis.libis.LibisBook;
import bookshelf.apis.libis.LibisBookshelf;
import bookshelf.exceptions.BookNotFoundException;
import bookshelf.exceptions.BookshelfUnavailableException;


public class LibisBookshelfTest {
	String key = "YIRIIK92UHUVH99AHUDAQXNNR1F24D6U3XD5U65RHNRULMK3GC-08666";
	LibisBookshelf collection = new LibisBookshelf(key);

	@Before
	public void setUp() throws Exception {
	}
	
	
	@Test
	public void retrieveBookByBarcode() throws BookshelfUnavailableException, BookNotFoundException {
		try {
			AbstractBook foundBook = collection.getBook(new LibisBarcode("009906485"));
			AbstractBook expectedBook = new LibisBook("http://opac.libis.be:80/F/"+key+"?func=full-set-set&set_number=074686&set_entry=000001&format");
			assertEquals(expectedBook, foundBook);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void retrieveBookByISBN() {
		//978-0-470-39880-7
	}
}
