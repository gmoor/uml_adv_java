package edu.garyMooradian.advancedJava.services;

import edu.garyMooradian.advancedJava.app.stockQuote.IntervalEnum;
import edu.garyMooradian.advancedJava.model.StockQuote;
import edu.garyMooradian.advancedJava.utilities.DatabaseConnectionException;

import java.util.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * This API describes how to get stock data from an external resource.
 */
public interface StockService {


    /**
     * Return the current price for a share of stock  for the given symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @return a  <CODE>BigDecimal</CODE> instance
     * @throws StockServiceExceptionTest if using the service generates an exception.
     *                               If this happens, trying the service may work, depending on the actual cause of the
     *                               error.
     */
    StockQuote getQuote(String symbol, Calendar date) throws StockServiceException, DatabaseConnectionException, SQLException;
    

    /**
     * Get a historical list of stock quotes for the provided symbol
     *
     * @param symbol the stock symbol to search for
     * @param from   the date of the first stock quote
     * @param until  the date of the last stock quote
     * @return a list of StockQuote instances
     * @throws   StockServiceExceptionTest if using the service generates an exception.
     * If this happens, trying the service may work, depending on the actual cause of the
     * error.
     */
    List<StockQuote> getQuoteHistory(String symbol, Calendar from, Calendar until) throws StockServiceException, DatabaseConnectionException, SQLException;
    
  
    
    /**
     * Get a historical list of stock quotes for the provided symbol
     *
     * @param symbol the stock symbol to search for
     * @param from   the date of the first stock quote
     * @param until  the date of the last stock quote
     * @param interval  an enum that establishes the frequency for obtaining a stock quote
     *                  within the date range specified by "from" and "until" parameters
     * @return a list of StockQuote instances
     * @throws DatabaseConnectionException 
     * @throws   StockServiceExceptionTest if using the service generates an exception.
     * If this happens, trying the service may work, depending on the actual cause of the
     * error.
     */
    List<StockQuote> getQuoteHistory(String symbol, Calendar from, Calendar until, IntervalEnum interval) throws StockServiceException, DatabaseConnectionException, SQLException;

}

