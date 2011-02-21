package bookshelf;

import java.util.ArrayList;

public abstract class AbstractBook {

	protected ISBN isbn;
	protected String publisher;
	protected int publishingYear;
	protected String title;
	protected ArrayList<String> authors = new ArrayList<String>();
	
	public ISBN getISBN() {
		return this.isbn;
	}

	public ArrayList<String> getAuthors() {
		return this.authors;
	}

	public String getTitle() {
		return this.title;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public int getPublishingYear() {
		return this.publishingYear;
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
	
	public boolean equals(Object o) {
		if (!(o instanceof AbstractBook))
			return false;
		
		AbstractBook b = (AbstractBook) o;
		return getISBN().equals(b.getISBN());
	}
}
