package bookshelf.api;

import java.util.ArrayList;

import com.google.books.unofficial.api.Book;

public abstract class AbstractBook {

	protected ISBN isbn;
	protected String publisher;
	protected int publishingYear;
	protected String title;
	protected ArrayList<String> authors = new ArrayList<String>();

	public abstract int getPublishingYear();

	public abstract String getPublisher();

	public abstract String getTitle();

	public abstract ArrayList<String> getAuthors();

	public abstract ISBN getISBN();

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Book))
			return false;
		
		AbstractBook b = (AbstractBook) o;
		return getISBN().equals(b.getISBN());
	}

	protected void setTitle(String title) {
		this.title = title;
	}

	protected void setISBN(ISBN isbn) {
		this.isbn = isbn;
	}

	protected void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	protected void setPublishingYear(int year) {
		this.publishingYear = year;
	}

	public boolean hasTitle() {
		return (getTitle() != null);
	}

	public boolean hasISBN() {
		return (getISBN() != null);
	}

	public boolean hasAuthors() {
		return (getAuthors().size() > 0);
	}

	public boolean hasPublisher() {
		return (getPublisher() != null);
	}

	protected void addAuthor(String author) {
		this.authors.add(author);
	}

}
