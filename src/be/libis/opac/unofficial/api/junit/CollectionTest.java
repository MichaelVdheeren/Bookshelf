package be.libis.opac.unofficial.api.junit;


import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import be.libis.opac.unofficial.api.Collection;
import bookshelf.api.AbstractBook;


public class CollectionTest {
	
	Collection collection = new Collection();

	@Before
	public void setUp() throws Exception {
	}
	
	
	@Test
	public void retrieveBookByBarcode() throws IOException {
		AbstractBook book = collection.getBook("009906485");
	}

}
