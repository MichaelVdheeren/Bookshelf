package com.google.books.unofficial.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Config;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.LoggerProvider;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

public class Collection {
	private final String feed = "http://books.google.com/books?rview=1&hl=en";
	private final Language language;
	
	public Collection() {
		this(Language.ENGLISH);
	}
	
	public Collection(Language language) {
		this.language = language;
	}
	
	public ArrayList<Book> getBooks(String query) throws IOException {
		query = query.replaceAll(" ", "+");
		ArrayList<Book> result = new ArrayList<Book>();
		
		URL feedUrl = new URL(feed + "&q=" + query);
		Config.LoggerProvider=LoggerProvider.DISABLED;
		HttpURLConnection feedCon = (HttpURLConnection) feedUrl.openConnection(); 
		feedCon.addRequestProperty("User-Agent", "Safari/5.0");
		
		Source source=new Source(feedCon);
		
		List<Element> elements = source.getAllElementsByClass("annotated_thumbnail");
		
		for (Element e : elements) {
			Segment content = e.getContent();
			List<Element> urls = content.getAllElements(HTMLElementName.A);
			
			if (urls.size() > 0) {
				String url = urls.get(0).getAttributeValue("href");
				result.add(new Book(new URL(url.substring(0,url.indexOf("&")))));
			}
		}
		
		return result;
	}
	
	public ArrayList<Book> getRelatedBooks(Book book) throws IOException {
		return getBooks("related:ISBN"+book.getISBN());
	}
	
	public Language getLanguage() {
		return this.language;
	}
}
