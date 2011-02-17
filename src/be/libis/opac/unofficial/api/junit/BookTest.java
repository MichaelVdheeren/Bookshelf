package be.libis.opac.unofficial.api.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import be.libis.opac.unofficial.api.Collection;
import be.libis.opac.unofficial.api.exeptions.InvalidISBNException;
import bookshelf.api.AbstractBook;
import bookshelf.api.ISBN;


public class BookTest {
	
	Collection collection = new Collection();
	AbstractBook book;

	@Before
	public void setUp() throws Exception {
		book = collection.getBook("009906485");
	}
	
	@Test
	public void retrieveBookMetadata() throws InvalidISBNException {
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
