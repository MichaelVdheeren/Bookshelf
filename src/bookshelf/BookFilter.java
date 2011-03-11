package bookshelf;

import java.util.ArrayList;

public interface BookFilter<E extends AbstractBook> {
	public ArrayList<E> filter(ArrayList<E> books);
}
