package bookshelf.apis.libis;

import bookshelf.AbstractBookshelf;
import bookshelf.BookshelfCache;
import bookshelf.ISBN;
import bookshelf.apis.libis.parameters.LibisCatalogue;
import bookshelf.apis.libis.parameters.LibisLibrary;
import bookshelf.apis.libis.parameters.LibisSearchfield;
import bookshelf.apis.libis.parameters.LibisType;
import bookshelf.exceptions.BookNotFoundException;
import bookshelf.exceptions.BookshelfUnavailableException;

/**
 * Class which represents the Libis Book collection.
 */
public class LibisBookshelf extends AbstractBookshelf {
	private final String key;
	private final String feed = "http://opac.libis.be:80/F/";
	private LibisCatalogue catalogue = LibisCatalogue.All;
	private LibisLibrary library = LibisLibrary.All;
	private LibisType type = LibisType.All;
	private LibisSearchfield searchfield = LibisSearchfield.All;
	
	/**
	 * Create a new instance of the Collection.
	 * @param	key
	 * 			The key needed to search through the Libis database.
	 * @post	The key needed to search through the Libis database is set
	 * 			to the given <key>.
	 * @post	The cache of the collection is set to a new instance of the
	 * 			CollectionCache class with it's default cache limit.
	 */
	public LibisBookshelf(String key) {
		super();
		this.key = key;
	}
	
	/**
	 * Create a new instance of the Collection.
	 * @param	key
	 * 			The key needed to search through the Libis database.
	 * @param 	cache
	 * 			The cache of the collection.
	 * @post	The key needed to search through the Libis database is set
	 * 			to the given <key>.
	 * @post	The cache of the collection is set to the given <cache>.
	 */
	public LibisBookshelf(String key, BookshelfCache cache) {
		super(cache);
		this.key = key;
	}
	
	public LibisBookProcessor getBook(LibisBarcode barcode) throws BookshelfUnavailableException, BookNotFoundException {	
		String feedQuery;
		feedQuery = getFeed() + getKey();
		feedQuery += "?func=find-c";
		feedQuery += "&" + getCatalogue().toString();
		feedQuery += "&" + getLibrary().toString();
		feedQuery += "&" + getType().toString();
		feedQuery += "&ccl_term=BAR=" + barcode;
		
		return new LibisBookProcessor(feedQuery);
	}

	@Override
	public LibisBookProcessor getBooks(String query) throws BookshelfUnavailableException {
		String feedQuery;
		feedQuery = getFeed() + getKey();
		feedQuery += "?func=find-b";
		feedQuery += "&" + getSearchfield().toString();
		feedQuery += "&" + getCatalogue().toString();
		feedQuery += "&" + getLibrary().toString();
		feedQuery += "&" + getType().toString();
		feedQuery += "&request=" + query;
		
		return new LibisBookProcessor(feedQuery);
	}
	
	public LibisBookProcessor getBook(ISBN isbn) throws BookNotFoundException, BookshelfUnavailableException {
		LibisSearchfield searchfield = getSearchfield();
		setSearchfield(LibisSearchfield.ISBN);
		LibisBookProcessor result = getBooks(isbn.toString());
		setSearchfield(searchfield);
		return result;
	}

	public LibisCatalogue getCatalogue() {
		return this.catalogue;
	}
	
	public void setCatalogue(LibisCatalogue catalogue) {
		this.catalogue = catalogue;
	}

	public LibisLibrary getLibrary() {
		return library;
	}

	public void setLibrary(LibisLibrary library) {
		this.library = library;
	}
	
	public LibisSearchfield getSearchfield() {
		return searchfield;
	}
	
	public void setSearchfield(LibisSearchfield searchfield) {
		this.searchfield = searchfield;
	}

	public LibisType getType() {
		return type;
	}

	public void setType(LibisType type) {
		this.type = type;
	}

	private String getKey() {
		return key;
	}

	public String getFeed() {
		return feed;
	}
}
