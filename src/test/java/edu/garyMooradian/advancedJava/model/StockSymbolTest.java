package edu.garyMooradian.advancedJava.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StockSymbolTest {

	@Test
	public void testSettersAndGetters() {
		
		StockSymbol stockSymbol = new StockSymbol();
		
		stockSymbol.setId(1);
		stockSymbol.setSymbol("GOOG");
		
		assertEquals("id variable value is not as expected", 1, stockSymbol.getId());
		
		assertEquals("symbol variable value is not as expected", "GOOG", stockSymbol.getSymbol());

	}
	
	/*
	 * Testing the overriding equal method
	 * Testing whether the same instantiation is equal to itself
	 * Testing whether different instantiations with the same variable values
	 * are equal to each other
	 */
	@Test
	public void testEqualsPositive() {
		
		StockSymbol stockSymbol1 = new StockSymbol();
		stockSymbol1.setId(1);
		stockSymbol1.setSymbol("GOOG");
		
		StockSymbol stockSymbol2 = new StockSymbol();
		stockSymbol2.setId(1);
		stockSymbol2.setSymbol("GOOG");
		
		//Seeing if object is equal to itself
		assertTrue("The objects should be equal but are not", stockSymbol1.equals(stockSymbol1));
		
		//different instantiations but values (id and symbol) are the same, so they
		//should be considered equal
		assertTrue("The objects should be equal but are not", stockSymbol1.equals(stockSymbol2));
		
	}
	
	/*
	 * Testing the overriding equal method
	 * Testing that instantiations with different variable values are not equal
	 */
	@Test
	public void testEqualsNegative() {
		
		StockSymbol stockSymbol1 = new StockSymbol();
		stockSymbol1.setId(1);
		stockSymbol1.setSymbol("GOOG");
		
		StockSymbol stockSymbol2 = new StockSymbol();
		stockSymbol2.setId(1);
		stockSymbol2.setSymbol("AAPL");
		
		//Two instantiations with same id values but different symbol values
		assertFalse("The objects should not be equal but are", stockSymbol1.equals(stockSymbol2));
		
		
		stockSymbol2.setId(2);
		stockSymbol2.setSymbol("GOOG");
		//Two instantiations with same symbol values but different id values
		assertFalse("The objects should not be equal but are", stockSymbol1.equals(stockSymbol2));
		
		/*
		 * Wasn't clear on this shorthand line in StockSymbol code:
		 * if (symbol != null ? !symbol.equals(stockSymbol.symbol) :
         *									stockSymbol.symbol != null) return false;
         *
         * Looks like nested if statements; not sure. Therefore, there's probably a couple
         * of more test cases here that I'm not doing												
		 */
	}
	
	
	/*
	 * this overriding hashCode method generates a hash code based on
	 * the id and symbol values. If we call hashCode on the same StockSymbol
	 * object, we expect the same hash code value to be returned.
	 * 
	 * To test this method, we create the object, establishing a value 
	 * for id and symbol. We then call hashCode twice and compare their
	 * return values; They should be the same.
	 * This satisfies the contract regarding hashCode method, which is as follows:
	 * 
	 * "Whenever hashCode is invoked on the same object more than once during
	 * an execution of a Java application, the hashCode method must consistently
	 * return the same integer, provided no information used in equals comparisons
	 * on the object is modified"
	 */
	@Test
	public void testHashCodePositive() {

		StockSymbol stockSymbol = new StockSymbol();
		
		/*
		 * The hashCode method uses the class's id and symbol values
		 * to generate a hashCode value.
		 * Therefore, we'll need to set them to some values.
		 */
		stockSymbol.setId(1);
		stockSymbol.setSymbol("GOOG"); //non null value
		
		assertEquals("The hash value for the same object is not the same but should be",
				stockSymbol.hashCode(), stockSymbol.hashCode());
			
	}
	
	
	/*
	 * We test that hashCode returns 31 when the stock symbol is null
	 */
	@Test
	public void testHashCodeNegative() {
		
		StockSymbol stockSymbol = new StockSymbol();
		
		stockSymbol.setId(1);
		stockSymbol.setSymbol(null);
				
		assertEquals("The hash code value should be 31 but is not", stockSymbol.hashCode(), 31 );
	}
	

	@Test
	public void testoString() {
		
		StockSymbol stockSymbol = new StockSymbol();
		
		stockSymbol.setId(1);
		stockSymbol.setSymbol("GOOG");
		
		String expectedString = "StockSymbol{id=1, symbol='GOOG'}";
				
		assertEquals("Did not return the expected string", expectedString, stockSymbol.toString());
	}

}
