package be.libis.opac.unofficial.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import net.htmlparser.jericho.Config;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.LoggerProvider;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import bookshelf.api.AbstractBook;

/**
 * Class which represents the Libis Book collection.
 */
public class Collection {
	private final String key = "SG4JHVPDF9CHC5V9D2XQE4Y6278V1KFUCFDRDV6UGY7J9R76MA-25544";
	private final String feed = "http://opac.libis.be:80/F/";
	private CollectionCache<String,Book> cache;
	
	/**
	 * Create a new instance of the Collection.
	 * @post	The cache of the collection is set to a new instance of the
	 * 			CollectionCache class with it's default cache limit.
	 */
	public Collection() {
		this.cache = new CollectionCache<String,Book>();
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
	public Collection(int cacheLimit) {
		this.cache = new CollectionCache<String,Book>(cacheLimit);
	}
	
	public AbstractBook getBook(String barcode) throws IOException {
		// TODO: change local_base to ENUM Library.
		URL feedUrl = new URL(feed + key + "?func=find-c&local_base=OPAC01&ccl_term=BAR=" + barcode);
		Config.LoggerProvider=LoggerProvider.DISABLED;
		HttpURLConnection feedCon = (HttpURLConnection) feedUrl.openConnection(); 
		feedCon.addRequestProperty("User-Agent", "Safari/5.0");
		
		Source source=new Source(feedCon);
		
		List<Element> elements = source.getAllElementsByClass("lbs_td1");
		Iterator<Element> iterator = elements.iterator();
		boolean found = false;
		
		while (iterator.hasNext() && !found) {
			Segment content = iterator.next().getContent();
			Element a = content.getFirstElement(HTMLElementName.A);
			
			if (a == null)
				continue;
			
			String url = a.getAttributeValue("href");
			
			if (url.startsWith("http") && url.indexOf("set_entry") >= 0) {
				return new Book(url);
			}
		}
		
		return null;
	}
}
