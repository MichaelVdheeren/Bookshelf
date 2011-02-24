package bookshelf.apis.libis.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import bookshelf.AbstractBook;
import bookshelf.ISBN;
import bookshelf.apis.libis.LibisBarcode;
import bookshelf.apis.libis.LibisBookshelf;


public class LibisBookTest {
	String key = "YIRIIK92UHUVH99AHUDAQXNNR1F24D6U3XD5U65RHNRULMK3GC-08666";
	LibisBookshelf collection = new LibisBookshelf(key);
	AbstractBook book;

	@Before
	public void setUp() throws Exception {
		book = collection.getBook(new LibisBarcode("009906485"));
	}
	
	@Test
	public void retrieveBookMetadata() {
		String title = "An introduction to object-oriented programming with Java";
		String author = "Wu, C. Thomas";
		String publisher = "McGraw Hill";
		int year = 2004;
		ISBN isbn = new ISBN("0072518847");
		
		assertEquals(title, book.getTitle());
		assertEquals(year, book.getPublishingYear());
		assertEquals(isbn, book.getISBN());
		assertTrue(book.getAuthors().contains(author));
		assertEquals(publisher, book.getPublisher());
	}
}
