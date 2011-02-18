package bookshelf.apis.libis;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import net.htmlparser.jericho.Config;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.LoggerProvider;
import net.htmlparser.jericho.Source;
import bookshelf.AbstractBook;
import bookshelf.ISBN;

public class LibisBook extends AbstractBook {
	private final Source source;
	private String id;
	
	public LibisBook(String url) throws IOException {
		Config.LoggerProvider=LoggerProvider.DISABLED;
		
		HttpURLConnection bookCon = (HttpURLConnection)
				(new URL(url)).openConnection();
		bookCon.addRequestProperty("User-Agent", "Mozilla/5.0");
		this.source=new Source(bookCon);
		
		List<Element> keys = source.getAllElementsByClass("label");
		List<Element> values = source.getAllElementsByClass("content");
		String key, value;
		
		for (int i=0; i<keys.size(); i++) {
			key = keys.get(i).getTextExtractor().setIncludeAttributes(false).toString();
			value = values.get(i).getTextExtractor().setIncludeAttributes(false).toString();
			value = value.replaceAll("\\.$", "");
			value = value.replaceAll(",$", "");
			
			if (key.equals("Titel")) {
				setTitle(value);
			} else if (key.equals("ISBN")) {
				setISBN(new ISBN(value.replace("\\(*.\\)","")));
			} else if (key.equals("Publicatiejaar")) {
				int year = Integer.parseInt(value);
				setPublishingYear(year);
			} else if (key.equals("Auteurs")) {
				String[] authors = value.split(" / ");
				for (String author : authors)
					addAuthor(author);
			} else if (key.equals("Uitgever")) {
				setPublisher(value.replaceAll("^.* : ", ""));
			}
		}
	}
	
	public String getId() {
		return this.id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof LibisBook))
			return false;
		
		LibisBook b = (LibisBook) o;
		return getId().equals(b.getId());
	}
}
