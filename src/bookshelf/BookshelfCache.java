package bookshelf;

import java.util.LinkedHashMap;
import java.util.Map;

public class BookshelfCache<E extends AbstractBook> extends LinkedHashMap<ISBN, E> {
	private static final long serialVersionUID = 1L;
	private final int maxEntries;

	public BookshelfCache() {
		this(1000);
	}
	
	public BookshelfCache(int maxEntries) {
		this(maxEntries, 16);
	}
	
	public BookshelfCache(int maxEntries, int initialCapacity) {
		this(maxEntries, initialCapacity, 0.75f);
	}
	
	public BookshelfCache(int maxEntries, int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		this.maxEntries = maxEntries;
	}
	
	public BookshelfCache(int maxEntries, int initialCapacity, float loadFactor, boolean accessOrder) {
		super(initialCapacity, loadFactor, accessOrder);
		this.maxEntries = maxEntries;
	}
	
	
	@Override
    protected boolean removeEldestEntry(Map.Entry<ISBN,E> eldest) {
    	return size() > maxEntries;
    }
}
