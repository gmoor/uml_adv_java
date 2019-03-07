package edu.garyMooradian.advancedJava.app.stockQuote;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import edu.garyMooradian.advancedJava.app.stockQuote.StockQuoteApplication;


public class StockQuoteApplicationTest {
	
	/* IMPORTANT <<-------
	 * Both test cases are no longer useful (for assignment 5) and are being
	 * disabled. We'll require a new set of test cases for the methods in
	 * StockApplicationClass
	 */
	
	/*
	 * This test should not cause an Exception.
	 * If it does, the appropriate message will be generated
	 */
	//@Test
	public void testMainPositive() {
		String[] args = {"OLED", "1/20/2019", "1/30/2019"};
		try {
			StockQuoteApplication.main(args);	
		} catch (Exception e) {
			e.printStackTrace();
			/*
			 * We don't care what the Exception is, we are simply checking whether
			 * an Exception occurred. If an Exception does occur, the following assert
			 * will always be true and the message "Received an unexpected Exception"
			 * will be generated
			 */
			assertTrue("Received an unexpected Exception",true);
		}
	}

	
	/*
	 * This test should cause a NullPointerException
	 */
	//@Test //Test disabled: This test is no longer valid; i.e. main method has changed since assignment4
	public void testMainNegative() {
		System.out.println("RUNNING testMainNegative");
		try {
			StockQuoteApplication.main(null);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertEquals("Did not receive the expected NullPointerException", null, e.getMessage());
		}
		
	}
	
	
	

}
