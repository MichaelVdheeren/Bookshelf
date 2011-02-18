package bookshelf;

import java.util.ArrayList;

import net.htmlparser.jericho.Config;
import net.htmlparser.jericho.LoggerProvider;
import bookshelf.exceptions.BookNotFoundException;
import bookshelf.exceptions.BookshelfUnavailableException;


public abstract class AbstractBookshelf {
	private BookshelfCache cache;
	
	/**
	 * Create a new instance of the Collection.
	 * @post	The cache of the collection is set to a new instance of the
	 * 			CollectionCache class with it's default cache limit.
	 */
	public AbstractBookshelf() {
		this(new BookshelfCache());
	}
	
	/**
	 * Create a new instance of the Collection.
	 * @param 	cacheLimit
	 * 			The amount of books to keep in the cache of the collection
	 * 			before discarding the oldest retrieval.
	 * @post	The cache of the collection is set to a new instance of the
	 * 			CollectionCache class with the given <cacheLimit> value as
	 * 			it's cache limit.
	 */
	public AbstractBookshelf(BookshelfCache cache) {
		this.cache = cache;
		Config.LoggerProvider=LoggerProvider.DISABLED;
	}

	public BookshelfCache getCache() {
		return cache;
	}

	protected void setCache(BookshelfCache cache) {
		this.cache = cache;
	}
	
	public abstract ArrayList<AbstractBook> getBooks(String query) throws BookshelfUnavailableException;
	public abstract AbstractBook getBook(ISBN isbn) throws BookNotFoundException, BookshelfUnavailableException;
}
