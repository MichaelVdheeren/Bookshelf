package bookshelf;

import net.htmlparser.jericho.Config;
import net.htmlparser.jericho.LoggerProvider;
import bookshelf.exceptions.BookshelfUnavailableException;


public abstract class AbstractBookshelf {

	public AbstractBookshelf() {
		Config.LoggerProvider=LoggerProvider.DISABLED;
	}

	public abstract BookshelfCache<? extends AbstractBook> getCache();
	
	public abstract AbstractBookProcessor getBooks(String query) throws BookshelfUnavailableException;
	public abstract AbstractBookProcessor getBook(ISBN isbn) throws BookshelfUnavailableException;
}
