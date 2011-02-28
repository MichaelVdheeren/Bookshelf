package bookshelf.apis.google;

import java.io.IOException;

import bookshelf.AbstractBook;
import bookshelf.AbstractBookshelf;
import bookshelf.BookshelfCache;
import bookshelf.ISBN;
import bookshelf.apis.google.parameters.GoogleLanguage;
import bookshelf.exceptions.BookshelfUnavailableException;

/**
 * Class which represents the Google Book collection.
 */
public class GoogleBookshelf extends AbstractBookshelf {
	private final String feed = "http://books.google.com/books?rview=1&hl=en";
	
	/**
	 * Create a new instance of the Collection.
	 * @post	The cache of the collection is set to a new instance of the
	 * 			CollectionCache class with it's default cache limit.
	 */
	public GoogleBookshelf() {
		super();
	}
	
	/**
	 * Create a new instance of the Collection.
	 * @param 	cache
	 * 			The cache of the collection.
	 * @post	The cache of the collection is set to the given <cache>.
	 */
	public GoogleBookshelf(String key, BookshelfCache cache) {
		super(cache);
	}
	
	public GoogleBookProcessor getBooks(String query) throws BookshelfUnavailableException {
		query = query.replaceAll(" ", "+");
		
		String feedQuery;
		feedQuery = getFeed();
		feedQuery += "?rview=1";
		feedQuery += "&" + GoogleLanguage.English;
		feedQuery += "&q=" + query;
		
		return new GoogleBookProcessor(feedQuery);
	}
	
	/**
	 * 
	 * @param query
	 * @param sYear
	 * @param eYear
	 * @return
	 * @throws IOException
	 * @throws BookshelfUnavailableException 
	 */
	public GoogleBookProcessor getBooks(String query, int sYear, int eYear) throws BookshelfUnavailableException {
		return getBooks(query+"+date:"+sYear+"-"+eYear);
	}
	
	/**
	 * 
	 * @param book
	 * @return
	 * @throws IOException
	 * @throws BookshelfUnavailableException 
	 */
	public GoogleBookProcessor getRelatedBooks(AbstractBook book) throws BookshelfUnavailableException {
		return getBooks("related:ISBN"+book.getISBN());
	}
	
	public GoogleBookProcessor getBook(ISBN isbn) {
		return new SingleGoogleBookProcessor(isbn);
	}
	
	public String getFeed() {
		return feed;
	}
}
