package edu.garyMooradian.advancedJava.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import edu.garyMooradian.advancedJava.model.Person;
import edu.garyMooradian.advancedJava.model.PersonStocks;
import edu.garyMooradian.advancedJava.model.StockSymbol;

public class DatabasePersonServiceTest {

	@Test
	public void testGetPerson() throws StockServiceException {
		
		DatabasePersonService databasePersonService = new DatabasePersonService();
		
		String[] persons = new String[] {"sam", "john"};
		int i = 0;
		for(Person person: databasePersonService.getPerson()) {
			assertEquals("Expected name to be sam but was not", persons[i++], person.getUserName());			
		}
	}
	
	
	@Test
	public void testGetPersonStockSymbol() throws StockServiceException {
		
		DatabasePersonService databasePersonService = new DatabasePersonService();
		
		/*
		 * Person class maps to person table.
		 * getPerson returns all the records from the person table.
		 * Each record maps to a Person object. Therefore, getPerson returns
		 * a List of Person objects.
		 * 
		 * The second record in the person table is "john"
		 * Our test will be to see if we get the right symbols for john
		 * Based on the current tables, john's symbols are APPL and GOOG
		 */
		
		//First we get all the persons/records in the person table
		List<Person> persons = databasePersonService.getPerson();

		/*
		 * john is the second record (i.e. get(1)), so we access that record
		 * and pass it to our method uder test to get the symbols for john
		 */
		List<StockSymbol> stockSymbols = databasePersonService.getPersonStockSymbol(persons.get(1));
		
		//We know john has two stock symbols; i.e. AAPL and GOOG.
		if(stockSymbols.size() == 2) {

			assertEquals("We did not get the expected symbol for john", "AAPL",
													stockSymbols.get(0).getSymbol());
			
			assertEquals("We did not get the expected symbol for john", "GOOG",
													stockSymbols.get(1).getSymbol());
		} else if (stockSymbols.size() < 2) {
			assertTrue("john has less stock symbols than expected",false);
		} else {
			assertTrue("john has more stock symbols than expected",false);
		}
		
	}

}
