package edu.garyMooradian.advancedJava.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonStocksTest {

	@Test
	public void testSettersAndGetters() {
		/*
		 * Create these objects for assigning to 
		 * corresponding variables in PersonStocks object
		 */
		Person person = new Person();
		StockSymbol stockSymbol = new StockSymbol();
		
		PersonStocks personStocks = new PersonStocks();
		
		//assign an id value
		personStocks.setId(1);
		
		//Now assign the Person and StockSymbol objects
		personStocks.setPerson(person);
		personStocks.setStockSymbol(stockSymbol);
		
		assertEquals("id variable value is not as expected", 1, personStocks.getId());
		
		/*
		 * person (Person object) was assigned to person variable of PersonStocks object
		 * via setPerson method (see above).
		 * We now test that getPerson method in PersonStocks returns the same Person object
		 * that was assigned to it via setPerson method above
		 */
		assertTrue("person variable is not equal to the Person object assigned to it",
				person.equals(personStocks.getPerson()));
		
		/*
		 * stockSymbol (StockSymbol object) was assigned to stockSymbol variable of
		 * PersonStocks object via setStockSymbol method (see above).
		 * We now test that getStockPerson method in PersonStocks returns the same
		 * StockSymbol object that was assigned to it via setStockSymbol method above
		 */
		assertTrue("stockSymbol variable is not equal to the StockSymbol object assigned to it",
				stockSymbol.equals(personStocks.getStockSymbol()));

	}
	
	/*
	 * Testing the overriding equal method
	 * Testing whether the same instantiation is equal to itself
	 * Testing whether different instantiations (with the same variable values)
	 * are equal to each other
	 */
	@Test
	public void testEqualsPositive() {
		
		/*
		 * Create these objects for assigning to 
		 * corresponding variables in PersonStocks object
		 */
		Person person = new Person();
		StockSymbol stockSymbol = new StockSymbol();
		
		PersonStocks personStocks1 = new PersonStocks();
		personStocks1.setId(1);
		personStocks1.setPerson(person);
		personStocks1.setStockSymbol(stockSymbol);
		
		PersonStocks personStocks2 = new PersonStocks();
		personStocks2.setId(1);
		personStocks2.setPerson(person);
		personStocks2.setStockSymbol(stockSymbol);

		
		//Seeing if object is equal to itself
		assertTrue("The objects should be equal but are not", personStocks1.equals(personStocks1));
		
		/*
		 * different instantiations but values (id and symbol) are the same,
		 * so they should be considered equal
		 */
		assertTrue("The objects should be equal but are not", personStocks1.equals(personStocks2));
		
	}
	
	/*
	 * Testing the overriding equal method
	 * Testing that instantiations with different id variable values are not equal
	 */
	@Test
	public void testEqualsNegative1() {
		
		PersonStocks personStocks1 = new PersonStocks();
		personStocks1.setId(1);
		PersonStocks personStocks2 = new PersonStocks();
		personStocks2.setId(2);
		
		//Two instantiations of PersonStocks that contain different id values
		assertFalse("The objects should not be equal but are", personStocks1.equals(personStocks2));
		
	}
	
	/*
	 * Testing the overriding equal method
	 * Testing that if object being passed to equal method is null,
	 * it returns false
	 */
	@Test
	public void testEqualsNegative2() {
		
		
		PersonStocks personStocks1 = new PersonStocks();
		PersonStocks personStocks2 = null;
		
		assertFalse("The object passed to equal method is null so method shuld return false but is returning true",
				personStocks1.equals(personStocks2));
		
	}
	
	/*
	 * Testing the overriding equal method
	 * Testing that if object we are comparing with PersonStock object,
	 * is a different object type (i.e. instantiation of a different class)
	 * we return false from the equal method
	 */
	@Test
	public void testEqualsNegative3() {
		
		PersonStocks personStocks = new PersonStocks();
		Person person = new Person();
		
		//Two instantiations of PersonStocks that contain different id values
		assertFalse("The objects should not be equal but are", personStocks.equals(person));
		
	}

	
	
	/*
	 * this overriding hashCode method generates a hash code based on
	 * the id and symbol values. If we call hashCode on the same StockSymbol
	 * object, we expect the same hash code value to be returned.
	 * 
	 * To test this method, we create the object, establishing a value 
	 * for id and symbol. We then call hashCode twice and compare their
	 * return values; The hash code values are irrelevant. What we check for
	 * is if they are the same.
	 * This satisfies the contract regarding hashCode method, which is as follows:
	 * 
	 * "Whenever hashCode is invoked on the same object more than once during
	 * an execution of a Java application, the hashCode method must consistently
	 * return the same integer, provided no information used in equals comparisons
	 * on the object is modified"
	 */
	@Test
	public void testHashCodePositive() {

		PersonStocks personStocks = new PersonStocks();
		
		/*
		 * The hashCode method uses the class's id,
		 * StockQuote object, and Person object
		 * to generate a hashCode value.
		 * Therefore, we'll need to set those values.
		 */
		personStocks.setId(1);
		personStocks.setPerson(new Person());
		personStocks.setStockSymbol(new StockSymbol());
		
		assertEquals("The hash value for the same object is not the same but should be",
				personStocks.hashCode(), personStocks.hashCode());
				
	}
	

	@Test
	public void testoString() {
		
		PersonStocks personStocks = new PersonStocks();
		
		personStocks.setId(1);
		int id = personStocks.getId();
		
		personStocks.setPerson(new Person());
		Person person = personStocks.getPerson();
		
		personStocks.setStockSymbol(new StockSymbol());
		StockSymbol stockSymbol = personStocks.getStockSymbol();
			
		/*
		 * Note that the variable values for Person and StockSymbol objects
		 * are their default values since we didn't provide values for them
		 * after instantiating them above. It was unnecessary for this test case
		 */
		String expectedString = "PersonStocks{id=1, person=Person{id=0, name='null'}, stockSymbol=StockSymbol{id=0, symbol='null'}}";
				
		assertEquals("Did not return the expected string", expectedString, personStocks.toString());
	}
	
}
