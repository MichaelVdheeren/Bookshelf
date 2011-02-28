package bookshelf.apis.libis;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import bookshelf.AbstractBook;
import bookshelf.ISBN;

public class LibisBook extends AbstractBook {
	private static final long serialVersionUID = 1L;

	public LibisBook(String feed) throws IOException {
		HttpURLConnection bookCon = (HttpURLConnection)
				(new URL(feed)).openConnection();
		bookCon.addRequestProperty("User-Agent", "Mozilla/5.0");
		setSource(new Source(bookCon));
		
		List<Element> keys = getSource().getAllElementsByClass("label");
		List<Element> values = getSource().getAllElementsByClass("content");
		String key, value;
		
		for (int i=0; i<keys.size(); i++) {
			key = keys.get(i).getTextExtractor().setIncludeAttributes(false).toString();
			value = values.get(i).getTextExtractor().setIncludeAttributes(false).toString();
			value = value.replaceAll("\\.$", "");
			value = value.replaceAll(",$", "");
			
			if (key.equals("Titel")) {
				setTitle(value);
			} else if (key.equals("ISBN")) {
				setISBN(new ISBN(value.replaceAll("\\((.*)\\)","")));
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
}
