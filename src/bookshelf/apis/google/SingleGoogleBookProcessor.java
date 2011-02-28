package bookshelf.apis.google;

import java.io.IOException;

import bookshelf.ISBN;

public class SingleGoogleBookProcessor extends GoogleBookProcessor {
	
	public SingleGoogleBookProcessor(ISBN isbn) {
		super("http://books.google.com/books?isbn=" + isbn);
	}
	
	@Override
	public void run() {
		try {
			GoogleBook book = new GoogleBook(getFeed());
			books.add(book);
			this.notifyObservers(book);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
