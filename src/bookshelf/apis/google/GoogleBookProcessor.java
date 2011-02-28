package bookshelf.apis.google;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import bookshelf.AbstractBookProcessor;
import bookshelf.exceptions.BookshelfUnavailableException;

public class GoogleBookProcessor extends AbstractBookProcessor {
	private static final long serialVersionUID = 1L;
	protected final ArrayList<GoogleBook> books = new ArrayList<GoogleBook>();
	
	public GoogleBookProcessor(String feed) {
		super(feed);
	}
	
	public ArrayList<GoogleBook> getBooks() {
		return new ArrayList<GoogleBook>(this.books);
	}
	
	public GoogleBook getLastBook() {
		if (this.books.size() > 0)
			return this.books.get(this.books.size()-1);
		else
			return null;
	}
	
	@Override
	public void run() {
		Source source = null;
		try {
			URL feedUrl = new URL(getFeed());
		
			HttpURLConnection feedCon = (HttpURLConnection) feedUrl.openConnection(); 
			feedCon.addRequestProperty("User-Agent", "Safari/5.0");
			
			source=new Source(feedCon);
		} catch (IOException e) {
			throw new RuntimeException(new BookshelfUnavailableException());
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
				GoogleBook book = new GoogleBook("http://books.google.com/books?id=" + id);
				books.add(book);
				this.notifyObservers(book);
			} catch (IOException e) {
				continue;
			}
		}
	}
}
