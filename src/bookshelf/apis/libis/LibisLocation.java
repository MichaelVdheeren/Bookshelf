package bookshelf.apis.libis;

import java.io.Serializable;

public class LibisLocation implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String library;
	private final String collection;
	private final String shelf;
	
	public LibisLocation(String library, String collection, String shelf) {
		this.library = library;
		this.collection = collection;
		this.shelf = shelf;
	}

	public String getLibrary() {
		return library;
	}

	public String getCollection() {
		return collection;
	}

	public String getShelf() {
		return shelf;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((collection == null) ? 0 : collection.hashCode());
		result = prime * result + ((library == null) ? 0 : library.hashCode());
		result = prime * result + ((shelf == null) ? 0 : shelf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LibisLocation other = (LibisLocation) obj;
		if (collection == null) {
			if (other.collection != null)
				return false;
		} else if (!collection.equals(other.collection))
			return false;
		if (library == null) {
			if (other.library != null)
				return false;
		} else if (!library.equals(other.library))
			return false;
		if (shelf == null) {
			if (other.shelf != null)
				return false;
		} else if (!shelf.equals(other.shelf))
			return false;
		return true;
	}

	public String toString() {
		return "("+getLibrary()+","+getCollection()+","+getShelf()+")";
	}
}
