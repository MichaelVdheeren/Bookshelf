package com.google.books.unofficial.api;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Config;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.LoggerProvider;
import net.htmlparser.jericho.Source;
import bookshelf.api.AbstractBook;
import bookshelf.api.ISBN;


public class Book extends AbstractBook {
	private final Source source;
	
	private Image cover;
	private URL coverUrl;
	private String summary;
	private float rating = -1f;
	private ArrayList<String> subtitles = new ArrayList<String>();
	private ArrayList<String> words = new ArrayList<String>();
	private boolean cachedCover;
	private boolean cachedCoverUrl;
	private boolean cachedSummary;
	private boolean cachedRating;
	private boolean cachedMetadata;
	private boolean cachedTitles;
	private boolean cachedSubtitles;
	private boolean cachedWords;
	
	public Book(String url) throws IOException {
		Config.LoggerProvider=LoggerProvider.DISABLED;
		
		HttpURLConnection bookCon = (HttpURLConnection)
				(new URL(url)).openConnection();
		bookCon.addRequestProperty("User-Agent", "Mozilla/5.0");
		this.source=new Source(bookCon);	
	}
	
	public Book(ISBN isbn) throws IOException {
		Config.LoggerProvider=LoggerProvider.DISABLED;
		
		HttpURLConnection bookCon = (HttpURLConnection)
				(new URL("http://books.google.com/books?isbn=" + isbn)).openConnection();
		bookCon.addRequestProperty("User-Agent", "Mozilla/5.0");
		this.source=new Source(bookCon);
	}
	
	private void addSubtitle(String subtitle) {
		this.subtitles.add(subtitle);
	}
	
	private void addWords(String word) {
		this.words.add(word);
	}
	
	public Image getCover() throws IOException {
		if(!hasCachedCover()) {
		    setCover(Toolkit.getDefaultToolkit().getImage(getCoverUrl()));
			setCachedCover(true);
		}

		return this.cover;
	}
	
	public URL getCoverUrl() throws IOException {
		if(!hasCachedCoverUrl()) {
			Element element = source.getElementById("summary-frontcover");
			setCoverUrl(new URL(element.getAttributeValue("href")));
			setCachedCoverUrl(true);
		}

		return this.coverUrl;
	}
	
	public String getSummary() {
		if (!hasCachedSummary()) {
			Element element = source.getElementById("synopsistext");
			
			if (element != null) {
				setSummary(element.getTextExtractor().setIncludeAttributes(false).toString());
			}
			
			setCachedSummary(true);
		}
		
		return this.summary;
	}

	public String getTitle() {
		if (!hasCachedTitles()) {
			List<Element> elements = source.getAllElementsByClass("booktitle");
			for (Element e : elements) {
				e = e.getFirstElement();
				setTitle(e.getTextExtractor().setIncludeAttributes(false).toString());
			}
			
			setCachedTitles(true);
		}
		
		return this.title;
	}

	public ArrayList<String> getSubtitles() {
		if (!hasCachedSubtitles()) {
			List<Element> elements = source.getAllElementsByClass("subtitle");
			for (Element e : elements)
				addSubtitle(e.getTextExtractor().setIncludeAttributes(false).toString());
			
			setCachedSubtitles(true);
		}
		
		return new ArrayList<String>(this.subtitles);
	}

	public ArrayList<String> getWords() {
		if (!hasCachedWords()) {
		
			Element element = source.getFirstElementByClass("cloud");
			if (element != null) {
				List<Element> elements = element.getAllElements(HTMLElementName.A);
				for (Element e : elements) {
					addWords(e.getTextExtractor().setIncludeAttributes(false).toString());
				}
			}
			
			setCachedWords(true);
		}
		
		return this.words;
	}
	
	public float getRating() {
		if (!hasCachedRating()) {
			List<Element> starOn = source.getAllElementsByClass("gb-star-on");
			List<Element> starHalf = source.getAllElementsByClass("gb-star-half");
			
			if (starOn.size() > 0 || starHalf.size() > 0)
				setRating(1f*starOn.size() + 0.5f*starHalf.size());
			
			setCachedRating(true);
		}
		
		return this.rating;
	}
	
	private void cacheMetadata() {
		List<Element> metalabels = source.getAllElementsByClass("metadata_label");
		List<Element> metavalues = source.getAllElementsByClass("metadata_value");
		
		for (int i=0; i<metalabels.size(); i++) {
			String label = metalabels.get(i).getTextExtractor().setIncludeAttributes(false).toString();
			
			if (label.equals("ISBN")) {
				String[] isbns = metavalues.get(i).getTextExtractor().setIncludeAttributes(false).toString().split(", ");
				setISBN(new ISBN(isbns[0]));
			} else if (label.equals("Author") || label.equals("Authors")) {
				List<Element> elements = metavalues.get(i).getAllElements(HTMLElementName.A);
				for (Element e : elements)
					addAuthor(e.getTextExtractor().setIncludeAttributes(false).toString());
			} else if (label.equals("Publisher")) {
				String[] values = metavalues.get(i).getTextExtractor().setIncludeAttributes(false).toString().split(", ");
				
				if (values.length >= 1)
					setPublisher(values[0]);
				
				if (values.length >= 2)
					setPublishingYear(Integer.parseInt(values[values.length-1]));
			}
				
		}
	}
	
	public ISBN getISBN() {
		if (!hasCachedMetadata()) {
			cacheMetadata();
			setCachedMetadata(true);
		}
			
		return this.isbn;
	}
	
	public ArrayList<String> getAuthors() {
		if (!hasCachedMetadata()) {
			cacheMetadata();
			setCachedMetadata(true);
		}
		
		return new ArrayList<String>(this.authors);
	}
	
	public String getPublisher() {
		if (!hasCachedMetadata()) {
			cacheMetadata();
			setCachedMetadata(true);
		}
		
		return this.publisher;
	}
	
	public int getPublishingYear() {
		if (!hasCachedMetadata()) {
			cacheMetadata();
			setCachedMetadata(true);
		}
		
		return this.publishingYear;
	}
	
	private void setCachedCover(boolean b) {
		this.cachedCover = b;
	}
	
	private void setCachedCoverUrl(boolean b) {
		this.cachedCoverUrl = b;
	}
	
	private void setCachedTitles(boolean b) {
		this.cachedTitles = b;
	}
	
	private void setCachedSubtitles(boolean b) {
		this.cachedSubtitles = b;
	}
	
	private void setCachedSummary(boolean b) {
		this.cachedSummary = b;
	}
	
	private void setCachedRating(boolean b) {
		this.cachedRating = b;
	}
	
	private void setCachedWords(boolean b) {
		this.cachedWords = b;
	}
	
	private void setCachedMetadata(boolean b) {
		this.cachedMetadata = b;
	}
	
	private void setCover(Image cover) {
		this.cover = cover;
	}
	
	private void setCoverUrl(URL coverUrl) {
		this.coverUrl = coverUrl;
	}
	
	private void setSummary(String summary) {
		this.summary = summary;
	}
	
	private void setRating(float rating) {
		this.rating = rating;
	}
	
	public boolean hasCover() throws IOException {
		return (getCover() != null);
	}
	
	public boolean hasSubtitles() {
		return (getSubtitles().size() > 0);
	}
	
	public boolean hasRating() {
		return (getRating() != -1f);
	}
	
	public boolean hasSummary() {
		return (getSummary() != null);
	}
	
	public boolean hasWords() {
		return (getWords().size() > 0);
	}
	
	private boolean hasCachedCover() {
		return cachedCover;
	}
	
	private boolean hasCachedCoverUrl() {
		return cachedCoverUrl;
	}
	
	private boolean hasCachedTitles() {
		return cachedTitles;
	}
	
	private boolean hasCachedSubtitles() {
		return cachedSubtitles;
	}
	
	private boolean hasCachedRating() {
		return cachedRating;
	}
	
	private boolean hasCachedSummary() {
		return cachedSummary;
	}
	
	private boolean hasCachedWords() {
		return cachedWords;
	}
	
	private boolean hasCachedMetadata() {
		return cachedMetadata;
	}
	
	public void resetCache() {
		setCachedCover(false);
		setCachedCoverUrl(false);
		setCachedRating(false);
		setCachedSubtitles(false);
		setCachedSummary(false);
		setCachedTitles(false);
		setCachedWords(false);
		setCachedMetadata(false);
	}
}
