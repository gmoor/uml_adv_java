package edu.garyMooradian.advancedJava.services;

import edu.garyMooradian.advancedJava.app.stockQuote.IntervalEnum;
import edu.garyMooradian.advancedJava.model.StockQuote;
import edu.garyMooradian.advancedJava.utilities.DatabaseConnectionException;
import edu.garyMooradian.advancedJava.utilities.DatabaseUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * An implementation of the StockService interface that gets
 * stock data from a database.
 */
public class DatabaseStockService implements StockService {
	
    /**
     * Returns a stock quote for the provided symbol and date
     *
     * @param symbol The stock symbol of the company you want a quote for.
     *               												e.g. APPL for APPLE
     * @param date
     * @return a  <CODE>BigDecimal</CODE> instance
     * @throws DatabaseConnectionException (generated here only)
     * @throws SQLException (generated here only)
     * @throws StockServiceExceptionTest if using the service generates an exception.
     *                               If this happens, trying the service may work,
     *                               depending on the actual cause of the error.
     */
    @Override
    public StockQuote getQuote(String symbol, Calendar date) throws StockServiceException, DatabaseConnectionException, SQLException {
        
    	try {
        	
        	String TIME_ZONE = "UTC";
        	
        	/*
        	 * Establish a connection to the database
        	 * Note: getConnection method could throw DatabaseConnectionException
        	 */
            Connection connection = DatabaseUtils.getConnection();

            String queryString = "select * from quotes WHERE DATE(time) = ? AND symbol = ? AND Time(time) = '16:00:01'";

            PreparedStatement preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setObject(1, date.toInstant().atZone(ZoneId.of(TIME_ZONE)).toLocalDate());
            preparedStatement.setString(2, symbol);
            
            /*
             * Execute the query
             */
            ResultSet resultSet = preparedStatement.executeQuery();
            
            resultSet.last(); //set pointer to end of resultSet
            if(resultSet.getRow() == 0) {
            	throw new StockServiceException
                	("There is no stock data for: " + symbol + " for the date " + date.getTime());
            }
            
            /*
             * The resultSet contains the record that was returned. The record has 3 columns,
             * price, time, and symbol. We obtain the 3 values from the resultSet and pass
             * them to the StockQuote constructor. The StockQuote constructor is a container
             * It contains the 3 values for the record and has getter methods to access the
             * data. Note that the data received from the time column of the record is a
             * Date type (i.e. DATETIME)
             */
            return new StockQuote(resultSet.getBigDecimal("price"), resultSet.getDate("time"), 
            															resultSet.getString("symbol"));
            
        } catch (DatabaseConnectionException e) {
        	throw new DatabaseConnectionException(e.getMessage(), e);
        } catch (SQLException e) {
        	throw new SQLException(e.getMessage(), e);
        } catch (StockServiceException e) {
        	throw new StockServiceException(e.getMessage(), e);
        }

    }

    
    /**************************************************************************/  
    

    /**
     * Returns an historical list of stock quotes for the provided symbol and date range
     *
     * @param symbol the stock symbol to search for
     * @param from   the date of the first stock quote
     * @param until  the date of the last stock quote
     * @return a list of StockQuote instances
     * @throws DatabaseConnectionException (received from prepareQueryStatement method)
     * @throws SQLException (generated here or from prepareQueryStatement)
     * @throws StockServiceExceptionTest (generated here only)
     * 				If this happens, trying the service may work,
     * 				depending on the actual cause of the error.
     * 
     * The database stock quotes are recorded hourly from 9:00:01 - 16:00:01; i.e.
     * There are 8 records for each day in the database
     * This method returns stock quote once a day by acquiring the
     * quote on just the last hour (16:00:01) of each day. Therefore, the query
     * will need to specify the hour 16:00...
     */
    public ArrayList<StockQuote> getQuoteHistory(String symbol, Calendar from, Calendar until) throws StockServiceException, DatabaseConnectionException, SQLException {
    	try {
 
            String queryString = "select * from quotes WHERE DATE(time) BETWEEN ? AND ? AND symbol = ? AND Time(time) = '16:00:01'";

        	PreparedStatement preparedStatement = prepareQueryStatement(queryString, symbol, from, until);
            
            /*
             * Execute the query
             */
            ResultSet resultSet = preparedStatement.executeQuery();
            
            resultSet.last(); //set pointer to end of resultSet
            if(resultSet.getRow() == 0) {
            	throw new StockServiceException
                	("There is no stock data for: " + symbol + " for the date range of " + from.getTime().toString() + " through " + until.getTime().toString());
            }
            
            /*
             * Create an ArrayList of StockQuote type set to the size of the resultSet.
             */
            ArrayList<StockQuote> stockQuotes = new ArrayList<>(resultSet.getRow());
            resultSet.beforeFirst();//set pointer back to beginning of resultSet
            while(resultSet.next()) {
            	stockQuotes.add(new StockQuote(resultSet.getBigDecimal("price"), 
            				resultSet.getDate("time"), resultSet.getString("symbol")));
            }
			return stockQuotes;
			
    	} catch (StockServiceException e) {
            throw new StockServiceException(e.getMessage(), e);
        } catch (SQLException e) {
        	throw new SQLException(e.getMessage(), e);
        }
    	
    }
    
 
   /**************************************************************************/ 
    
    /**  
     * Returns an historical list of stock quotes for the provide symbol, date range, and interval
     *
     * @param symbol the stock symbol to search for
     * @param from   the date of the first stock quote we are obtaining from the database
     * @param until  the date of the last stock quote we are obtaining from the database
     * @param interval  an enum that establishes the frequency for obtaining a stock quote,
     *                  within the date range specified by "from" and "until" parameters
     * @return a list of StockQuote instances
     * 
     * @throws DatabaseConnectionException (received from prepareQueryStatement method)
     * @throws StockServiceExceptionTest (generated here only)
     * @throws SQLException (generated here or from prepareQueryStatement)
     * 				If this happens, trying the service may work, depending on the actual cause of the error.
     */
    @Override
    public ArrayList<StockQuote> getQuoteHistory(String symbol, Calendar from, Calendar until, IntervalEnum interval) throws StockServiceException, DatabaseConnectionException, SQLException  {
    	//String TIME_ZONE = "UTC";
    	
    	try {
    		
    		String TIME_ZONE = "UTC";
        	String queryString;
        	PreparedStatement preparedStatement = null;
        	
        	/*
        	 * NOTE: Verifying the user's input string for interval
        	 * should be done by the client, not here; i.e. we should expect the right
        	 * value to be passed to this method, therefore, we do not provide an else condition
        	 */
            if(interval == IntervalEnum.DAILY) {
            	queryString = "select * from quotes WHERE DATE(time) BETWEEN ? AND ? AND symbol = ? AND Time(time) = '16:00:01'";
            	preparedStatement = prepareQueryStatement(queryString, symbol, from, until);
			} else if (interval == IntervalEnum.TWICE_DAILY) {
				queryString = "select * from quotes WHERE DATE(time) BETWEEN ? AND ? AND symbol = ? AND (Time(time) = '11:00:01' OR Time(time) = '14:00:01')";
				preparedStatement = prepareQueryStatement(queryString, symbol, from, until);
			} else if (interval == IntervalEnum.HOURLY) {
				queryString = "select * from quotes WHERE DATE(time) BETWEEN ? AND ? AND symbol = ?";
				preparedStatement = prepareQueryStatement(queryString, symbol, from, until);
			}
            
            /*
             * Execute the query
             */
            ResultSet resultSet = preparedStatement.executeQuery();
            
            /*
             * set pointer to last row/record of resultSet.
             * getRow() provides the row number, and in this case, the last row number
             * Therefore, it will represent the row count.
             * If the row count is 0, we didn't get anything from the database
             * so we throw an Exception 
             */
            resultSet.last();
            if(resultSet.getRow() == 0) {
            	throw new StockServiceException
                	("There is no stock data for: " + symbol + " for the date range of " + from.getTime().toString() + " through " + until.getTime().toString());
            }
            
            /*
             * Create an ArrayList of StockQuote type, set to the size of the resultSet.
             */
            ArrayList<StockQuote> stockQuotes = new ArrayList<>(resultSet.getRow());
            resultSet.beforeFirst();//set pointer back to beginning of resultSet
            while(resultSet.next()) {
            	stockQuotes.add(new StockQuote(resultSet.getBigDecimal("price"),
            				resultSet.getDate("time"), resultSet.getString("symbol")));
            }
			return stockQuotes;
			
    	} catch (StockServiceException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        } catch (SQLException e) {
        	throw new SQLException(e.getMessage(), e);
        }
    	
    }

    
    
    /**
     * The following method (prepareQueryStatement) will support (be called by) the following
     * two local methods (i.e. the clients):
     * getQuoteHistory(String symbol, Calendar from, Calendar until)
     * getQuoteHistory(String symbol, Calendar from, Calendar until, IntervalEnum interval)
     * 
     * prepareQueryStatement will condition the queries (for the two clients)
     * for the parameters "from", "until", and "symbol"
     * Any additional parameters that are unique to the clients will be handled by the clients
     * 
     * @param queryString  The sql query
     * @param symbol       The stock symbol
     * @param from		   The starting date
     * @param until		   The ending date
     * 
     * @throws SQLException
     * @throws DatabaseConnectionException
     */
    public PreparedStatement prepareQueryStatement(String queryString, String symbol, Calendar from, Calendar until) throws DatabaseConnectionException, SQLException {
    	
    	try {
    		String TIME_ZONE = "UTC";
    		Connection connection = DatabaseUtils.getConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(queryString);
    		preparedStatement.setObject(1, from.toInstant().atZone(ZoneId.of(TIME_ZONE)).toLocalDate());
    		preparedStatement.setObject(2, until.toInstant().atZone(ZoneId.of(TIME_ZONE)).toLocalDate());
    		preparedStatement.setString(3, symbol);
    		return preparedStatement;
    	} catch (DatabaseConnectionException e) {
    		throw new DatabaseConnectionException(e.getMessage(), e);
        } catch (SQLException e) {
        	throw new SQLException(e.getMessage(), e);
        }
        
    }
    
    
}
