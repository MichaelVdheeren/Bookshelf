package bookshelf.apis.google.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import bookshelf.AbstractBook;
import bookshelf.ISBN;
import bookshelf.apis.google.GoogleBookshelf;


public class GoogleBookTest {
	
	GoogleBookshelf collection = new GoogleBookshelf();
	AbstractBook book;

	@Before
	public void setUp() throws Exception {
		book = collection.getBook(new ISBN("9781591396192"));
	}
	
	@Test
	public void retrieveBookMetadata() {
		ArrayList<String> authors = new ArrayList<String>();
		authors.add("W. Chan Kim");
		authors.add("Renée Mauborgne");
		
		assertTrue(book.getAuthors().containsAll(authors));
		assertEquals(authors.size(), book.getAuthors().size());
		
		String publisher = "Harvard Business Press";
		assertTrue(book.getPublisher().equals(publisher));
		
		int year = 2005;
		assertEquals(book.getPublishingYear(), year);
		
		ISBN isbn = new ISBN("1591396190");
		assertEquals(isbn, book.getISBN());
	}
}
