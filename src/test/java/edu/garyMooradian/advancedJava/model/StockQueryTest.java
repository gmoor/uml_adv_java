package edu.garyMooradian.advancedJava.model;

import java.text.ParseException;
import java.util.Calendar;
import static org.junit.Assert.*;
import javax.validation.constraints.NotNull;

import org.junit.Test;

import edu.garyMooradian.advancedJava.app.stockQuote.IntervalEnum;

/*
 * Inherit from StockData abstract class
 */
public class StockQueryTest extends StockData {
	
	public StockQueryTest() {
		/*
		 * provides us (via inheritance) with the simpleDateFormat object
		 * from StockData abstract class
		 */
		super();
	}
	
	/*
	 * StockQuery is a container class. It has a constructor that assigns
	 * some class variables and getter methods to access them.
	 * We test that the getter methods return what they should
	 */
	@Test
	public void testStockQuery() throws ParseException {
		
		String symbol = "GOOG";
		
		/*
		 * Create Calendar objects from the startDate and endDate String dates
		 * That's what the corresponding StockQuote getter methods will be returning
		 * and we will need to compare for verification
		 */
		String startDate = "2019/01/01";
		Calendar from = Calendar.getInstance();
		from.setTime(simpleDateFormat.parse(startDate));
		
		String endDate = "2019/01/20";
		Calendar until = Calendar.getInstance();
		until.setTime(simpleDateFormat.parse(endDate));
		
		String intervalDaily = "daily";
		String intervalTwiceDaily = "twice_daily";
		String intervalHourly = "hourly";
		StockQuery stockQuery;
		
		/*
		 * Create a StockQuery object with the following String values
		 * This object will be used to test symbol, startDate, and endDate.
		 * 
		 * interval will not be tested via this object so we pass any interval
		 */
		stockQuery = new StockQuery(symbol, startDate, endDate, intervalHourly);
		
		
		/*
		 * Now we check that the getter methods of StockQuery return what's expected.
		 */
		
		//testing getSymbol
		assertEquals("The value returned by getSymbol method is not 'GOOG'",
																"GOOG", stockQuery.getSymbol());
		//testing getFrom
		assertEquals("The value returned by getFrom method is not as expected",
																from, stockQuery.getFrom());
		//testing getUntil
		assertEquals("The value returned by getUntil method is not as expected",
				until, stockQuery.getUntil());
		
		
		/*
		 * Now we need to test getInterval. It should return an IntervalEnum type
		 * constant (i.e. DAILY, TWICE_DAILY, or HOURLY) depending on what interval string we pass
		 * We create a new object for each of the three interval tests
		 */
		
		stockQuery = new StockQuery(symbol, startDate, endDate, intervalDaily);	
		//testing getInterval for DAILY
		assertEquals("The value returned by getInterval method is not 'DAILY'",
				IntervalEnum.DAILY, stockQuery.getInterval());
		
		
		stockQuery = new StockQuery(symbol, startDate, endDate, intervalTwiceDaily);		 
		//testing getInterval for TWICE_DAILY
		assertEquals("The value returned by getInterval method is not 'TWICE_DAILY'",
				IntervalEnum.TWICE_DAILY, stockQuery.getInterval());
		
		
		stockQuery = new StockQuery(symbol, startDate, endDate, intervalHourly);		 
		//testing getInterval for HOURLY
		assertEquals("The value returned by getInterval method is not 'HOURLY'",
				IntervalEnum.HOURLY, stockQuery.getInterval());

		
	}

}
