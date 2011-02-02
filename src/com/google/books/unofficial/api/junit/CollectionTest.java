package com.google.books.unofficial.api.junit;


import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.google.books.unofficial.api.Book;
import com.google.books.unofficial.api.Collection;
import com.google.books.unofficial.api.ISBN;
import com.google.books.unofficial.api.exceptions.InvalidISBNException;

public class CollectionTest {
	
	Collection collection = new Collection();

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void retrieveBooksByQuery() {
		try {
			ArrayList<Book> books = collection.getBooks("Blue Ocean Strategy");
			
			ArrayList<Book> expected = new ArrayList<Book>();
			expected.add(new Book("BmPPAjGaDuQC"));
			expected.add(new Book("8ZJbKqMsnWQC"));
			expected.add(new Book("s2UO2Apy9ikC"));
			expected.add(new Book("QQtCwQE4e-AC"));
			
			assertTrue(books.containsAll(expected));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void retrieveBookByISBN() throws IOException {
		try {
			Book book = collection.getBook(new ISBN("9781591396192"));
			assertEquals("BmPPAjGaDuQC",book.getId());
		} catch (InvalidISBNException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
