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
import bookshelf.AbstractBookProcessor;
import bookshelf.exceptions.BookshelfUnavailableException;

public class LibisBookProcessor extends AbstractBookProcessor {
	private static final long serialVersionUID = 1L;
	private final ArrayList<LibisBook> books = new ArrayList<LibisBook>();
	
	public LibisBookProcessor(String feed) {
		super(feed);
	}
	
	public ArrayList<LibisBook> getBooks() {
		return new ArrayList<LibisBook>(this.getBooks());
	}
	
	public LibisBook getLastBook() {
		if (this.books.size() > 0)
			return this.books.get(this.books.size()-1);
		else
			return null;
	}
	
	protected boolean hasReachedLimit() {
		return (books.size() >= getLimit());
	}

	@Override
	public void run() {
		Source source = null;
		
		try {
			URL feedUrl = new URL(getFeed());
			HttpURLConnection feedCon = (HttpURLConnection) feedUrl.openConnection(); 
			feedCon.addRequestProperty("User-Agent", "Safari/5.0");
			source = new Source(feedCon);
		} catch (IOException e) {
			throw new RuntimeException (new BookshelfUnavailableException());
		}
		
		Element iframe = source.getElementById("ShelfNumberShim");
		Element tbody = source.getNextElement(iframe.getEnd()).getFirstElement();
		
		List<Element> trs = tbody.getChildElements();
		Iterator<Element> iterator = trs.iterator();
		
		while (iterator.hasNext() && !(isLimited() && hasReachedLimit())) {
			Segment content = iterator.next().getContent();
			Element a = content.getFirstElement(HTMLElementName.A);
			
			if (a == null)
				continue;
			
			String url = a.getAttributeValue("href");
			try {
				LibisBook book = new LibisBook(url);
				books.add(book);
				this.setChanged();
				this.notifyObservers(book);
			} catch (IOException e) {
				continue;
			}
		}
	}
}
