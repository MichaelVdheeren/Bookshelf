package bookshelf.apis.libis.junit;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import bookshelf.ISBN;
import bookshelf.apis.libis.LibisBarcode;
import bookshelf.apis.libis.LibisBook;
import bookshelf.apis.libis.LibisBookProcessor;
import bookshelf.apis.libis.LibisBookshelf;
import bookshelf.exceptions.BookNotFoundException;
import bookshelf.exceptions.BookshelfUnavailableException;


public class LibisBookshelfTest {
	String key = "96UNFINK44R9MEHP3HD5NYBU61QV63JSPDUYXIFTY56TYQ9MUX-00871";
	LibisBookshelf collection = new LibisBookshelf(key);

	@Before
	public void setUp() throws Exception {
	}
	
	
	@Test
	public void retrieveBookByBarcode() throws BookshelfUnavailableException, BookNotFoundException {
		LibisBookProcessor processor = collection.getBook(new LibisBarcode("009906485"));
		processor.run();
		LibisBook foundBook = processor.getLastBook();
		assertEquals(new ISBN("0072518847"), foundBook.getISBN());
	}
	
	@Test
	public void retrieveBookByISBN() throws BookNotFoundException, BookshelfUnavailableException {
		LibisBookProcessor processor = collection.getBook(new ISBN("978-0-470-39880-7"));
		processor.run();
		LibisBook foundBook = processor.getLastBook();
		assertEquals(new ISBN("978-0-470-39880-7"), foundBook.getISBN());
	}
}
