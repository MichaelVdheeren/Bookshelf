package bookshelf;

import java.util.ArrayList;
import java.util.Observable;

public abstract class AbstractBookProcessor extends Observable implements Runnable {
	private static final long serialVersionUID = 1L;
	private final String feed;
	private int limit = -1;
	private boolean finished = false;
	
	public AbstractBookProcessor(String feed) {
		this.feed = feed;
	}
	
	public String getFeed() {
		return this.feed;
	}
	
	public boolean isLimited() {
		return (limit>=0);
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public int getLimit() {
		return this.limit;
	}
	
	public void clearLimit() {
		setLimit(-1);
	}
	
	protected void setFinish() {
		this.finished = true;
	}
	
	public boolean isFinished() {
		return this.finished;
	}
	
	public abstract ArrayList<? extends AbstractBook> getBooks();
	public abstract AbstractBook getLastBook();
}
