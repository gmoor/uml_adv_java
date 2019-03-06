package edu.garyMooradian.advancedJava.services;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.garyMooradian.advancedJava.services.StockService;
import edu.garyMooradian.advancedJava.services.StockServiceFactory;
import junit.framework.Assert;

public class StockServiceFactoryTest {

	/**
	 * There's only one StockService in StockServiceFactory
	 * Testing that it (i.e. StockServiceFactory.getStockService())
	 * does not return NULL
	 */
	@Test
	public void testGetStockService() {
		//Call the method statically
		StockService stockService = StockServiceFactory.getStockService();
		assertNotNull("The object reference value is NULL", stockService);
	}

}
