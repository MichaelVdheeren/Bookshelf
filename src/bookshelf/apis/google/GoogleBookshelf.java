package bookshelf.apis.google;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import bookshelf.AbstractBook;
import bookshelf.AbstractBookshelf;
import bookshelf.BookshelfCache;
import bookshelf.ISBN;
import bookshelf.apis.google.parameters.GoogleLanguage;
import bookshelf.exceptions.BookNotFoundException;
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
	
	public ArrayList<AbstractBook> getBooks(String query) throws BookshelfUnavailableException {
		query = query.replaceAll(" ", "+");
		ArrayList<AbstractBook> result = new ArrayList<AbstractBook>();
		
		String feed;
		feed = getFeed();
		feed += "?rview=1";
		feed += "&" + GoogleLanguage.English;
		feed += "&q=" + query;

		Source source = null;
		try {
			URL feedUrl = new URL(feed);
		
			HttpURLConnection feedCon = (HttpURLConnection) feedUrl.openConnection(); 
			feedCon.addRequestProperty("User-Agent", "Safari/5.0");
			
			source=new Source(feedCon);
		} catch (IOException e) {
			throw new BookshelfUnavailableException();
		}
		
		List<Element> urls = source.getElementById("main_content").getAllElements(HTMLElementName.A);
		
		for (int i=1; i<urls.size(); i=i+2) {			
			String url = urls.get(i).getAttributeValue("href");
			int beginId = url.indexOf("id=")+3;
			int endId = beginId+url.substring(beginId).indexOf("&");
			
			if (endId < 0)
				endId = url.length()-1;
			
			String id = url.substring(beginId,endId);
			
			try {
				AbstractBook book;
				if (getCache().containsKey(id))
					book = getCache().get(id);
				else {
					book = new GoogleBook("http://books.google.com/books?id=" + id);
					getCache().put(book.getISBN(), book);
				}
			
				result.add(book);
			} catch (IOException e) {
				continue;
			}
		}
		
		return result;
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
	public ArrayList<AbstractBook> getBooks(String query, int sYear, int eYear) throws BookshelfUnavailableException {
		return getBooks(query+"+date:"+sYear+"-"+eYear);
	}
	
	/**
	 * 
	 * @param book
	 * @return
	 * @throws IOException
	 * @throws BookshelfUnavailableException 
	 */
	public ArrayList<AbstractBook> getRelatedBooks(AbstractBook book) throws BookshelfUnavailableException {
		return getBooks("related:ISBN"+book.getISBN());
	}
	
	/**
	 * 
	 * @param isbn
	 * @return
	 * @throws BookNotFoundException 
	 * @throws IOException
	 */
	public AbstractBook getBook(ISBN isbn) throws BookNotFoundException {
		try {
			return new GoogleBook(isbn);
		} catch (IOException e) {
			throw new BookNotFoundException();
		}
	}
	
	public String getFeed() {
		return feed;
	}
}
