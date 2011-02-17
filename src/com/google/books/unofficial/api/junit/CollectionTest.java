package com.google.books.unofficial.api.junit;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import bookshelf.api.AbstractBook;
import bookshelf.api.ISBN;

import com.google.books.unofficial.api.Book;
import com.google.books.unofficial.api.Collection;

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
			expected.add(new Book("http://books.google.com/books?id=BmPPAjGaDuQC"));
			expected.add(new Book("http://books.google.com/books?id=8ZJbKqMsnWQC"));
			expected.add(new Book("http://books.google.com/books?id=s2UO2Apy9ikC"));
			expected.add(new Book("http://books.google.com/books?id=QQtCwQE4e-AC"));
			
			assertTrue(books.containsAll(expected));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void retrieveBookByISBN() throws IOException {
		AbstractBook book = collection.getBook(new ISBN("9781591396192"));
		assertEquals("Blue Ocean Strategy: How to Create Uncontested Market Space and Make Competition Irrelevant",book.getTitle());
	}
}
