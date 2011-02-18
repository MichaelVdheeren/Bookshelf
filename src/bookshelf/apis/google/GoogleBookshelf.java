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
	
	/**
	 * Retrieve books from the collection.
	 * @param 	query
	 * 			The query which is used to find books in the collection.
	 * @return	The books resulting from the execution of the query.
	 * @throws 	IOException
	 * @TODO	Remove IOException and add InvalidQueryException
	 */
	public ArrayList<AbstractBook> getBooks(String query) throws IOException {
		query = query.replaceAll(" ", "+");
		ArrayList<AbstractBook> result = new ArrayList<AbstractBook>();
		
		URL feedUrl = new URL(feed + "&q=" + query);
		HttpURLConnection feedCon = (HttpURLConnection) feedUrl.openConnection(); 
		feedCon.addRequestProperty("User-Agent", "Safari/5.0");
		
		Source source=new Source(feedCon);
		
		List<Element> urls = source.getElementById("main_content").getAllElements(HTMLElementName.A);
		
		for (int i=1; i<urls.size(); i=i+2) {			
			String url = urls.get(i).getAttributeValue("href");
			int beginId = url.indexOf("id=")+3;
			int endId = beginId+url.substring(beginId).indexOf("&");
			
			if (endId < 0)
				endId = url.length()-1;
			
			String id = url.substring(beginId,endId);
			AbstractBook book;
			
			if (getCache().containsKey(id))
				book = getCache().get(id);
			else {
				book = new GoogleBook("http://books.google.com/books?id=" + id);
				getCache().put(book.getISBN(), book);
			}
			
			result.add(book);
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
	 */
	public ArrayList<AbstractBook> getBooks(String query, int sYear, int eYear) throws IOException {
		return getBooks(query+"+date:"+sYear+"-"+eYear);
	}
	
	/**
	 * 
	 * @param book
	 * @return
	 * @throws IOException
	 */
	public ArrayList<AbstractBook> getRelatedBooks(AbstractBook book) throws IOException {
		return getBooks("related:ISBN"+book.getISBN());
	}
	
	/**
	 * 
	 * @param isbn
	 * @return
	 * @throws IOException
	 */
	public AbstractBook getBook(ISBN isbn) throws IOException {
		return new GoogleBook(isbn);
	}
}
