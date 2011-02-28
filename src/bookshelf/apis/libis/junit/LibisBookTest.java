package bookshelf.apis.libis.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import bookshelf.ISBN;
import bookshelf.apis.libis.LibisBarcode;
import bookshelf.apis.libis.LibisBook;
import bookshelf.apis.libis.LibisBookProcessor;
import bookshelf.apis.libis.LibisBookshelf;


public class LibisBookTest {
	String key = "96UNFINK44R9MEHP3HD5NYBU61QV63JSPDUYXIFTY56TYQ9MUX-00871";
	LibisBookshelf collection = new LibisBookshelf(key);
	LibisBook book;

	@Before
	public void setUp() throws Exception {
		LibisBookProcessor processor = collection.getBook(new LibisBarcode("009906485"));
		processor.run();
		book = processor.getLastBook();
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
