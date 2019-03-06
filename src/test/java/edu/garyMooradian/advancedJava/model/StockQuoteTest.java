package edu.garyMooradian.advancedJava.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import edu.garyMooradian.advancedJava.model.StockQuote;

public class StockQuoteTest {

	/*
	 * Testing the constructor and the three getter methods
	 * getRecordedDate(), getStockPrice(), getStockSymbol() 
	 */
	@Test
	public void testStockQuote() throws ParseException {

		Date date = new Date(2018/01/01);
		BigDecimal stockPrice = new BigDecimal(4.11);
		String stockSymbol = "OLED";
		
		//Call StockQuote constructor, passing it the test values
		StockQuote stockQuote = new StockQuote(stockPrice,date,stockSymbol);
		
		/*
		 * Verify that the dateRecorded value passed to the constructor was assigned to
		 * the corresponding class variable
		 */
		assertEquals("The class variable dateRecorded, does not equal it's assigned value",
						date,stockQuote.getDate());
		
		/*
		 * Verify that the stockPrice value passed to the constructor was assigned to
		 * the corresponding class variable
		 */
		assertEquals("The class variable stockPrice, does not equal it's assigned value",
						stockPrice,stockQuote.getPrice());
		
		
		/*
		 * Verify that the stockSymbol value passed to the constructor was assigned to
		 * the corresponding class variable
		 */
		assertEquals("The class variable stockSymbol, does not equal it's assigned value",
						stockSymbol,stockQuote.getSymbol());		
	}
	
	
	@Test
    public void testToString() {
        
		/*
		 * Create Date object
		 */
		Date date = new Date("01/01/2019");
		
		/*
		 * Create SimpleDateFormat object with a format of yyyy/MM/dd
		 */
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		
		/*
		 * Pass the Date object to the format method.
		 * It will return a date (in string form) in the established established format
		 */
		String dateString = simpleDateFormat.format(date);

		BigDecimal price = new BigDecimal(5.00);
        String symbol = "GOOG";
        
        String expectedStockQuote = "StockQuote{price=5, date=2019/01/01, symbol='GOOG'}";
        String actualStockQuote = "StockQuote{" + "price=" + price + ", date=" + dateString + ", symbol='" + symbol + '\'' + '}';

        assertEquals("The stock quote string does not match the expected stock quote string",
																		expectedStockQuote, actualStockQuote);        
    }

}
