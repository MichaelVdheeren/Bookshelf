package bookshelf.apis.libis;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import bookshelf.AbstractBook;
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
	
	public AbstractBook getBook(LibisBarcode barcode) throws BookshelfUnavailableException, BookNotFoundException {	
		String feed;
		feed = getFeed() + getKey();
		feed += "?func=find-c";
		feed += "&" + getCatalogue().toString();
		feed += "&" + getLibrary().toString();
		feed += "&" + getType().toString();
		feed += "&ccl_term=BAR=" + barcode;
		
		ArrayList<AbstractBook> result = processFeed(feed);
		
		if (result.size() <= 0)
			throw new BookNotFoundException();
		
		return result.get(0);
	}
	
	@Override
	public ArrayList<AbstractBook> getBooks(String query) throws BookshelfUnavailableException {	
		
		String feed;
		feed = getFeed() + getKey();
		feed += "?func=find-b";
		feed += "&" + getSearchfield().toString();
		feed += "&" + getCatalogue().toString();
		feed += "&" + getLibrary().toString();
		feed += "&" + getType().toString();
		feed += "&request=" + query;
		
		return processFeed(feed);
	}
	
	public AbstractBook getBook(ISBN isbn) throws BookNotFoundException, BookshelfUnavailableException {
		LibisSearchfield searchfield = getSearchfield();
		setSearchfield(LibisSearchfield.ISBN);
		ArrayList<AbstractBook> result = getBooks(isbn.toString());
		setSearchfield(searchfield);
		
		if (result.size() <= 0)
			throw new BookNotFoundException();
		
		return result.get(0);
	}
	
	private ArrayList<AbstractBook> processFeed(String feed) throws BookshelfUnavailableException {
		ArrayList<AbstractBook> result = new ArrayList<AbstractBook>();		
		Source source = null;
		try {
			URL feedUrl = new URL(feed);
		
			HttpURLConnection feedCon = (HttpURLConnection) feedUrl.openConnection(); 
			feedCon.addRequestProperty("User-Agent", "Safari/5.0");
			
			source=new Source(feedCon);
		} catch (IOException e) {
			throw new BookshelfUnavailableException();
		}
		
		Element iframe = source.getElementById("ShelfNumberShim");
		Element tbody = source.getNextElement(iframe.getEnd()).getFirstElement();
		
		List<Element> trs = tbody.getChildElements();
		Iterator<Element> iterator = trs.iterator();
		
		while (iterator.hasNext()) {
			Segment content = iterator.next().getContent();
			Element a = content.getFirstElement(HTMLElementName.A);
			
			if (a == null)
				continue;
			
			String url = a.getAttributeValue("href");
			try {
				result.add(new LibisBook(url));
			} catch (IOException e) {
				// TODO: just skip
			}
		}
		
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
