package edu.garyMooradian.advancedJava.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {

	@Test
	public void testSettersAndGetters() {
		
		Person person = new Person();
		
		person.setId(1);
		person.setUserName("John");;
		
		assertEquals("id variable value is not as expected", 1, person.getId());
		
		assertEquals("userName variable value is not as expected", "John", person.getUserName());

	}
	
	/*
	 * Testing the overriding equal method
	 * Testing whether the same instantiation is equal to itself
	 * Testing whether different instantiations with the same variable values
	 * are equal to each other
	 */
	@Test
	public void testEqualsPositive() {
		
		Person person1 = new Person();
		person1.setId(1);
		person1.setUserName("John");
		
		Person person2 = new Person();
		person2.setId(1);
		person2.setUserName("John");
		
		//Seeing if object is equal to itself
		assertTrue("The objects should be equal but are not", person1.equals(person1));
		
		//different instantiations but values (id and symbol) are the same, so they
		//should be considered equal
		assertTrue("The objects should be equal but are not", person1.equals(person2));
		
	}
	
	/*
	 * Testing the overriding equal method
	 * Testing that instantiations with different variable values are not equal
	 */
	@Test
	public void testEqualsNegative() {
		
		Person person1 = new Person();
		person1.setId(1);
		person1.setUserName("John");
		
		Person person2 = new Person();
		person2.setId(1);
		person2.setUserName("Mike");
		
		//Two instantiations with same id values but different userName values
		assertFalse("The objects should not be equal but are", person1.equals(person2));
		
		
		person2.setId(2);
		person2.setUserName("John");
		//Two instantiations with same userName values but different id values
		assertFalse("The objects should not be equal but are", person1.equals(person2));
		
		/*
		 * Wasn't clear on this shorthand line in Person code:
		 * if (userName != null ? !userName.equals(person.userName) :
         *									person.userName != null) return false;
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

		Person person = new Person();
		
		/*
		 * The hashCode method uses the class's id and symbol values
		 * to generate a hashCode value.
		 * Therefore, we'll need to set them to some values.
		 */
		person.setId(1);
		person.setUserName("John"); //non null value
		
		assertEquals("The hash value for the same object is not the same but should be",
				person.hashCode(), person.hashCode());
		
	}
	
	
	/*
	 * We test that hashCode returns 31 when the stock symbol is null
	 */
	@Test
	public void testHashCodeNegative() {
		
		Person person = new Person();
		
		person.setId(1);
		person.setUserName(null);
				
		assertEquals("The hash code value should be 31 but is not", person.hashCode(), 31 );
	}
	

	@Test
	public void testoString() {
		
		Person person = new Person();
		
		person.setId(1);
		person.setUserName("John");
		
		String expectedString = "Person{id=1, name='John'}";
				
		assertEquals("Did not return the expected string", expectedString, person.toString());
	}


}
