package bookshelf;

import java.util.ArrayList;

public interface BookFilter {
	public ArrayList<? extends AbstractBook> filter(ArrayList<? extends AbstractBook> books);
}
