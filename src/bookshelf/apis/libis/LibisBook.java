package bookshelf.apis.libis;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import bookshelf.AbstractBook;
import bookshelf.ISBN;
import bookshelf.apis.libis.parameters.LibisLibrary;
import bookshelf.exceptions.LocationUnavailableException;

public class LibisBook extends AbstractBook {
	private static final long serialVersionUID = 1L;
	private String location;

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
			} else if (key.equals("DISP_ITM1")) {
				location = values.get(i).getFirstElement(HTMLElementName.A).getAttributeValue("href");
			}
		}
	}
	
	public URL getCoverUrl() throws IOException {
		return new URL("http://books.google.com/books?vid=ISBN"+getISBN()+"&printsec=frontcover&img=1&zoom=1");
	}
	
	public ArrayList<LibisLocation> getLocations() {
		return getLocations(LibisLibrary.All);
	}
	
	public ArrayList<LibisLocation> getLocations(LibisLibrary libcode) {
		ArrayList<LibisLocation> result = new ArrayList<LibisLocation>();
		
		if (location == null)
			return result;
		
		Source source = null;
		
		try {
			URL feedUrl = new URL(location+"&sub_library="+libcode.getValue());
			HttpURLConnection feedCon = (HttpURLConnection) feedUrl.openConnection(); 
			feedCon.addRequestProperty("User-Agent", "Safari/5.0");
			source = new Source(feedCon);
		} catch (IOException e) {
			throw new RuntimeException (new LocationUnavailableException());
		}
		
		List<Element> keys = source.getAllElementsByClass("label");
		List<Element> values = source.getAllElementsByClass("content");
		
		for (int i=0; i<keys.size(); i++) {
			String key = keys.get(i).getTextExtractor().setIncludeAttributes(false).toString();
			String value = values.get(i).getTextExtractor().setIncludeAttributes(false).toString();
			value = value.replaceAll("\\.$", "");
			value = value.replaceAll(",$", "");
			
			if (key.equals("Locatie")) {
				int dashIndex = value.indexOf(" - ");
				int pointIndex = value.indexOf(":");
				int commaIndex = value.indexOf(" ; ");
				
				if (dashIndex < 0
					|| commaIndex < 0
					|| pointIndex < 0
					|| pointIndex < dashIndex
					|| commaIndex < dashIndex)
					continue;
				
				String library = value.substring(0, dashIndex);
				String collection = value.substring(dashIndex+3,pointIndex);
				String shelf = value.substring(commaIndex+3,value.length()-1);
				result.add(new LibisLocation(library, collection, shelf));
			}
		}
		
		return result;
	}
}
