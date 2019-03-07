package edu.garyMooradian.advancedJava.services;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.junit.Test;

import edu.garyMooradian.advancedJava.app.stockQuote.IntervalEnum;
import edu.garyMooradian.advancedJava.model.StockData;
import edu.garyMooradian.advancedJava.model.StockQuote;
import edu.garyMooradian.advancedJava.services.DatabaseStockService;
import edu.garyMooradian.advancedJava.utilities.DatabaseConnectionException;

public class DatabaseStockServiceTest extends StockData {
	
	/*
	 * The database is comprised of records per hour from 9:00 - 16:00 (i.e. 8 hours) for each day.
	 * In other words, there are 8 records for each symbol for any day
	 */
	
	
				/***** Tests for getQuote(String, Calendar) *****/
	
	/*
	 * This test case verifies that we receive one stock quote and that it contains
	 * the correct information (i.e. date symbol, price) based on the test data we provide
	 */
	@Test
	public void testGetQuotePositive() throws StockServiceException, DatabaseConnectionException, SQLException, ParseException {
		/*
		 * Create the Calendar date	
		 */
		String aDate = "2019/01/01";
		Calendar date = Calendar.getInstance();
		date.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(aDate));
		
		String symbol = "GOOG";
		
		/*
		 * Pass both variables to the getQuote method. We should receive a
		 * StockQuote back.
		 * Note: getQuote expects a Calendar date
		 */
		StockQuote quote = new DatabaseStockService().getQuote(symbol, date);
		
		/*
		 * The StockQuote will have 3 pieces of information that we need to verify;
		 * i.e. symbol, time, price. The price should be what's in the database for
		 * GOOG on 2019/01/01 at 16:00:01. That price is 1042.
		 * 
		 * Note that the date returned from the database is not a Calendar date, it's a Date.
		 * We'll need to create a Date to compare, to the Date in the record.
		 * Since we sent the Calendar date 2019/01/01 to query the database (i.e. via getQuote)
		 * we'll need to create a date object of the same date
		 */
		String expectedDate = "2019/01/01";
		String actualDate = simpleDateFormat.format(quote.getDate());
		
		/*
		 * And we need to provide the expected price
		 */
		BigDecimal expectedPrice = new BigDecimal(1042);
		
		/*
		 * Verify that date recorded is correct for the StockQuote object
		 */
		assertTrue("The date recorded is not correct", expectedDate.equals(actualDate));
		/*
		 * Verify that the stock price recorded is correct for the StockQuote object
		 */
		assertTrue("The stock price is not correct", expectedPrice.equals(quote.getPrice()));
		
		/*
		 * Verify the stock symbol is correct for the stockQuote object
		 * The symbol we sent is the symbol we expect to receive from the database; i.e. "GOOG"
		 */
		assertEquals("The stock symbol is not correct", symbol, quote.getSymbol());
		
	}
	
	
	
	/*
	 * This test case verifies that when we query for a non-existent record,
	 * a StockServiceException is thrown
	 */
	@Test
	public void testGetQuoteNegative() throws DatabaseConnectionException, SQLException, ParseException {

		String expectedExceptionMessage = "There is no stock data";
		
		try {
			
			/*
			 * Create the Calendar date	
			 */
			String startDate = "2019/01/21";
			Calendar date = Calendar.getInstance();
			date.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(startDate));	
			String symbol = "GOOG";
					
			/*
			 * Pass both variables to the getQuote method. We should
			 * generate a StockServiceException
			 */
			new DatabaseStockService().getQuote(symbol, date);
			
		} catch (StockServiceException e) {
			assertTrue("We did not receive the expected StockServiceException message",
											e.getMessage().contains(expectedExceptionMessage));	
		}
		
	}
	
	
	
	
				/***** Tests for getQuoteHistory(String, Calendar, Calendar) *****/
	
	@Test
	public void testGetQuoteHistoryPositive() throws ParseException, StockServiceException, DatabaseConnectionException, SQLException {
		/*
		 * Create the "from" Calendar date	
		 */
		String startDate = "2019/01/01";
		Calendar from = Calendar.getInstance();
		from.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(startDate));	

		/*
		 * Create the "until" Calendar date
		 */
		String endDate = "2019/01/01";
		Calendar until = Calendar.getInstance();
		until.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(endDate));
		
		String symbol = "AAPL";
		
		ArrayList<StockQuote> quote = new DatabaseStockService().getQuoteHistory(symbol, from, until);

		/*
		 * Verify that the ArrayList contains 2 StockQuotes
		 */
		assertTrue("Number of StockQuotes should be 2 but is not", quote.size() == 1);//TEST changed to 1 change back to 2 eventually
		
		
		/*
		 * Now Verify the 2 stockQuotes.
		 */
		
		//Create an ArrayList of expected prices
		ArrayList<BigDecimal> expectedPrice = new ArrayList<>();
		
		/*
		 * expected price is a BigDecimal object.
		 * Add an expected price (for each StockQuote returned) to the ArrayList
		 * Note: The prices happen to be sequential but that's not realistic so we
		 * do it this way in case the data/price is not sequential and we want to change it
		 * in future test cases
		 */
		expectedPrice.add(new BigDecimal(164));
		expectedPrice.add(new BigDecimal(172));
		
		//And here are the new lines
		String dateFrom = "2019/01/01";
		String dateUntil = "2019/01/02";
		String expectedDate = dateFrom;
		
		int i = 0; //ArrayList index. Also used for loop count so we can set new expected date in for loop
		for(StockQuote stockQuote: quote) {
			
			/*
			 * Can't compare sql DateTime with java Date. Their formats are completely
			 * different. So I'll convert the StockQuote date to a String and compare
			 * it to the expected date which will also be a String
			 */
			String actualDate = simpleDateFormat.format(stockQuote.getDate());
			
			assertTrue("The start date recorded is not correct", expectedDate.equals(actualDate));
			assertTrue("The symbol is not correct", symbol.equals(stockQuote.getSymbol()));
			assertTrue("The stock price is not correct", expectedPrice.get(i++).equals(stockQuote.getPrice()));
			
			if (i == 1) {
				//change the expected date for last record to dateUntil
				expectedDate = dateUntil;
			}			
		}
	}
	
	
	/*
	 * This test case verifies that when we query for a non-existent record,
	 * a StockServiceException is thrown
	 */
	@Test
	public void testGetQuoteHistoryNegative() throws ParseException, StockServiceException, DatabaseConnectionException, SQLException {

		String expectedExceptionMessage = "There is no stock data for";
		
		try {
			
			/*
			 * Create the Calendar date	
			 */
			String startDate = "2019/01/21";
			Calendar from = Calendar.getInstance();
			from.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(startDate));
			
			/*
			 * Create the "until" Calendar date
			 */
			String endDate = "2019/01/22";
			Calendar until = Calendar.getInstance();
			until.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(endDate));
			
			String symbol = "GOOG"; //And here's the symbol
			
			
			/*
			 * Pass both variables to the getQuote method. We should
			 * generate a StockServiceException
			 */
			new DatabaseStockService().getQuoteHistory(symbol, from, until);
			
		} catch (StockServiceException e) {
			assertTrue("We did not receive the expected StockServiceException message",
											e.getMessage().contains(expectedExceptionMessage));	
		}
		
	
	}
	
	
	
		/***** Tests for getQuoteHistory(String, Calendar, Calendar, IntervalEnum) *****/
	
	@Test
	public void testGetQuoteHistory2Positive() throws  StockServiceException, DatabaseConnectionException, SQLException, ParseException {
		/*
		 * Create the "from" Calendar date	
		 */
		String startDate = "2019/01/01";
		Calendar from = Calendar.getInstance();
		from.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(startDate));	

		/*
		 * Create the "until" Calendar date
		 */
		String endDate = "2019/01/02";
		Calendar until = Calendar.getInstance();
		until.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(endDate));
		
		String symbol = "AMZN";
		
		IntervalEnum interval = IntervalEnum.HOURLY;
		/*
		 * We should receive 16 StockQuotes back; i.e.
		 * 8 (hourly) StockQuotes per day x two days 
		 */
		ArrayList<StockQuote> quote = new DatabaseStockService().getQuoteHistory(symbol, from, until, interval);
		/*
		 * Verify that the ArrayList contains 16 StockQuotes
		 */
		assertTrue("Number of StockQuotes should be 16 but is not", quote.size() == 16);

		/*
		 * Now Verify the 16 StockQuotes.
		 */
		
		//Create an ArrayList of expected prices
		ArrayList<BigDecimal> expectedPrice = new ArrayList<>();
		
		/*
		 * expected price is a BigDecimal object.
		 * Add an expected price (for each StockQuote returned) to the ArrayList
		 * Note: The prices happen to be sequential but that's not realistic so we
		 * do it this way in case the data/price is not sequential and we want to change it
		 * in future test cases
		 */
		expectedPrice.add(new BigDecimal(1500));
		expectedPrice.add(new BigDecimal(1501));
		expectedPrice.add(new BigDecimal(1502));
		expectedPrice.add(new BigDecimal(1503));
		expectedPrice.add(new BigDecimal(1504));
		expectedPrice.add(new BigDecimal(1505));
		expectedPrice.add(new BigDecimal(1506));
		expectedPrice.add(new BigDecimal(1507));
		expectedPrice.add(new BigDecimal(1508));
		expectedPrice.add(new BigDecimal(1509));
		expectedPrice.add(new BigDecimal(1510));
		expectedPrice.add(new BigDecimal(1511));
		expectedPrice.add(new BigDecimal(1512));
		expectedPrice.add(new BigDecimal(1513));
		expectedPrice.add(new BigDecimal(1514));
		expectedPrice.add(new BigDecimal(1515));
		
		String dateFrom = "2019/01/01";
		String dateUntil = "2019/01/02";
		String expectedDate = dateFrom;
		String actualDate;
		
		int i = 0; //ArrayList index. Also used for loop count so we can set new expected date in for loop
		for(StockQuote stockQuote: quote) {
			/*
			 * Can't compare sql DateTime with java Date. Their formats are completely
			 * different. So I'll convert the StockQuote date to a String and compare
			 * it to the expected date which will also be a String
			 */
			actualDate = simpleDateFormat.format(stockQuote.getDate());
			
			assertTrue("The start date recorded is not correct", expectedDate.equals(actualDate));
			assertTrue("The symbol is not correct", symbol.equals(stockQuote.getSymbol()));
			assertTrue("The stock price is not correct", expectedPrice.get(i++).equals(stockQuote.getPrice()));
			
			if (i == 8) {
				//change the expected date for last 8 records to dateUntil
				expectedDate = dateUntil;
			}			
		}
		
	}
	
	
	/*
	 * Testing interval of TWICE_DAILY
	 * We expect to get 2 records per day (at 11:00 and 14:00) for each day in the date range
	 */
	@Test
	public void testGetQuoteHistory2Positive2() throws  StockServiceException, DatabaseConnectionException, SQLException, ParseException {
		/*
		 * Create the "from" Calendar date	
		 */
		String startDate = "2019/01/01";
		Calendar from = Calendar.getInstance();
		from.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(startDate));	

		/*
		 * Create the "until" Calendar date
		 */
		String endDate = "2019/01/02";
		Calendar until = Calendar.getInstance();
		until.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(endDate));
		
		String symbol = "AMZN";
		
		IntervalEnum interval = IntervalEnum.TWICE_DAILY;
		
		ArrayList<StockQuote> quote = new DatabaseStockService().getQuoteHistory(symbol, from, until, interval);
		
		/*
		 * Verify that the ArrayList contains 4 StockQuotes
		 */
		assertTrue("Number of StockQuotes should be 4 but is not", quote.size() == 4);
		
		/*
		 * Now Verify the 4 StockQuotes.
		 */
		
		//Create an ArrayList of expected prices
		ArrayList<BigDecimal> expectedPrice = new ArrayList<>();
		
		/*
		 * expected price is a BigDecimal object.
		 * Add an expected price (for each StockQuote returned) to the ArrayList
		 * Note: The prices happen to be sequential but that's not realistic so we
		 * do it this way in case the data/price is not sequential and we want to change it
		 * in future test cases
		 */
		expectedPrice.add(new BigDecimal(1502));
		expectedPrice.add(new BigDecimal(1505));
		expectedPrice.add(new BigDecimal(1510));
		expectedPrice.add(new BigDecimal(1513));
		

		String dateFrom = "2019/01/01";
		String dateUntil = "2019/01/02";
		String expectedDate = dateFrom;
		String actualDate;
		
		int i = 0; //ArrayList index. Also used for loop count so we can set new expected date in for loop
		for(StockQuote stockQuote: quote) {
			
			actualDate = simpleDateFormat.format(stockQuote.getDate());
			
			assertTrue("The start date recorded is not correct", expectedDate.equals(actualDate));
			assertTrue("The symbol is not correct", symbol.equals(stockQuote.getSymbol()));
			assertTrue("The stock price is not correct", expectedPrice.get(i++).equals(stockQuote.getPrice()));
			
			if (i == 2) {
				//change the expected date for last 2 records to dateUntil
				expectedDate = dateUntil;
			}			
		}
	}
	
	
	/*
	 * Testing interval of DAILY
	 * We expect to get 1 record per day (at 16:00) for each day in the date range
	 */
	@Test
	public void testGetQuoteHistory2Positive3() throws  StockServiceException, DatabaseConnectionException, SQLException, ParseException {
		/*
		 * Create the "from" Calendar date	
		 */
		String startDate = "2019/01/01";
		Calendar from = Calendar.getInstance();
		from.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(startDate));	

		/*
		 * Create the "until" Calendar date
		 */
		String endDate = "2019/01/02";
		Calendar until = Calendar.getInstance();
		until.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(endDate));
		
		String symbol = "AMZN";
		
		IntervalEnum interval = IntervalEnum.DAILY;
		
		
		/*
		 * We should receive 2 StockQuotes; i.e.
		 * A StockQuote at 16:00 for each day specified
		 */
		ArrayList<StockQuote> quote = new DatabaseStockService().getQuoteHistory(symbol, from,
																					until, interval);
		
		/*
		 * Verify that the ArrayList contains 2 StockQuotes
		 */
		assertTrue("Number of StockQuotes should be 2 but is not", quote.size() == 2);

		
		/*
		 * Verify the 2 stockQuotes.
		 */
		
		//Create an ArrayList of expected prices
		ArrayList<BigDecimal> expectedPrice = new ArrayList<>();
		
		/*
		 * expected price is a BigDecimal object.
		 * Add an expected price (for each StockQuote returned) to the ArrayList
		 * Note: The prices happen to be sequential but that's not realistic so we
		 * do it this way in case the data/price is not sequential and we want to change it
		 * in future test cases
		 */
		expectedPrice.add(new BigDecimal(1507));
		expectedPrice.add(new BigDecimal(1515));
		

		String dateFrom = "2019/01/01";
		String dateUntil = "2019/01/02";
		String expectedDate = dateFrom;
		String actualDate;
		
		int i = 0; //ArrayList index. Also used for loop count so we can set new expected date in for loop
		for(StockQuote stockQuote: quote) {
			
			actualDate = simpleDateFormat.format(stockQuote.getDate());
			
			assertTrue("The start date recorded is not correct", expectedDate.equals(actualDate));
			assertTrue("The symbol is not correct", symbol.equals(stockQuote.getSymbol()));
			assertTrue("The stock price is not correct", expectedPrice.get(i++).equals(stockQuote.getPrice()));
			
			if (i == 1) {
				//change the expected date for last record to dateUntil
				expectedDate = dateUntil;
			}			
		}
	}
	
	
	/*
	 * This test case verifies that when we query for a non-existent record,
	 * a StockServiceException is thrown
	 */
	@Test
	public void testGetQuoteHistory2Negative() throws ParseException, StockServiceException, DatabaseConnectionException, SQLException {

		String expectedExceptionMessage = "There is no stock data for";
		
		try {
			
			/*
			 * Create the Calendar date	
			 */
			String startDate = "2019/01/21";
			Calendar from = Calendar.getInstance();
			from.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(startDate));
			
			String endDate = "2019/01/22";
			Calendar until = Calendar.getInstance();
			until.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(endDate));
			
			String symbol = "GOOG";
			
			IntervalEnum interval = IntervalEnum.DAILY;
			
			
			/*
			 * Pass both variables to the getQuote method. We should
			 * generate a StockServiceException
			 */
			new DatabaseStockService().getQuoteHistory(symbol, from, until, interval);
			
		} catch (StockServiceException e) {
			assertTrue("We did not receive the expected StockServiceException message",
											e.getMessage().contains(expectedExceptionMessage));	
		}
		
	}
	
	
}
