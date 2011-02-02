package com.google.books.unofficial.api.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.google.books.unofficial.api.Book;
import com.google.books.unofficial.api.Collection;
import com.google.books.unofficial.api.ISBN;
import com.google.books.unofficial.api.exceptions.InvalidISBNException;

public class BookTest {
	
	Collection collection = new Collection();
	Book book;

	@Before
	public void setUp() throws Exception {
		try {
			book = collection.getBook(new ISBN("9781591396192"));
		} catch (InvalidISBNException e) {
			System.out.println(e.getMessage());
		}
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
		
		try {
			ISBN isbn = new ISBN("1591396190");
			assertEquals(isbn, book.getISBN());
		} catch (InvalidISBNException e) {
			System.out.println(e.getMessage());
		}
	}
}
