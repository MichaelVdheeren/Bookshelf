package bookshelf;

import java.util.ArrayList;

/**
 * This generic interface can be used to filter books
 * @param 	<E>
 * 			A class extending the AbstractBook class
 */
public interface AbstractBookFilter<E extends AbstractBook> {
	public ArrayList<E> applyTo(ArrayList<E> books);
	public boolean applyTo(E book);
}
