package bookshelf;

import java.util.ArrayList;

public interface BookFilter<E extends AbstractBook> {
	public ArrayList<E> applyTo(ArrayList<E> books);
	public boolean applyTo(E book);
}
