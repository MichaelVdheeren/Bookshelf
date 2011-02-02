package com.google.books.unofficial.api.junit;


import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.google.books.unofficial.api.Book;
import com.google.books.unofficial.api.Collection;



public class QueryCollectionTest {
	
	Collection collection = new Collection();;
	ArrayList<Book> books;
	
	@Before
	public void setUp() throws Exception {
		try {
			books = collection.getBooks("Blue Ocean Strategy");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void queryBooks() {
		for (Book b : books)
			System.out.println(b);
	}
	
	@Test
	public void titleBooks() {
		for (Book b : books)
			System.out.println(b.getTitles());
	}
	
	@Test
	public void summaryBooks() {
		for (Book b : books)
			System.out.println(b.getSummary());
	}
	
	@Test
	public void wordBooks() {
		for (Book b : books)
			System.out.println(b.getWords());
	}
	
	@Test
	public void isbnBooks() {
		for (Book b : books)
			System.out.println(b.getISBN());
	}
	
	@Test
	public void relatedBooks() {
		try {
			System.out.println(collection.getRelatedBooks(books.get(0)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
