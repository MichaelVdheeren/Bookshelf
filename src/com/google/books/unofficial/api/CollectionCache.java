package com.google.books.unofficial.api;

import java.util.LinkedHashMap;
import java.util.Map;

public class CollectionCache<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = 1L;
	private final int maxEntries;

	public CollectionCache() {
		this(1000);
	}
	
	public CollectionCache(int maxEntries) {
		this(maxEntries, 16);
	}
	
	public CollectionCache(int maxEntries, int initialCapacity) {
		this(maxEntries, initialCapacity, 0.75f);
	}
	
	public CollectionCache(int maxEntries, int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		this.maxEntries = maxEntries;
	}
	
	public CollectionCache(int maxEntries, int initialCapacity, float loadFactor, boolean accessOrder) {
		super(initialCapacity, loadFactor, accessOrder);
		this.maxEntries = maxEntries;
	}
	
    protected boolean removeEldestEntry(Map.Entry eldest) {
    	return size() > maxEntries;
    }
}
