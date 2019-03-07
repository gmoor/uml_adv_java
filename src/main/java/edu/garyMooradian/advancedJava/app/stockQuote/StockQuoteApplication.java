package edu.garyMooradian.advancedJava.app.stockQuote;

import edu.garyMooradian.advancedJava.model.StockQuery;
import edu.garyMooradian.advancedJava.model.StockQuote;
import edu.garyMooradian.advancedJava.services.DatabaseStockService;
import edu.garyMooradian.advancedJava.services.StockService;
import edu.garyMooradian.advancedJava.services.StockServiceException;
import edu.garyMooradian.advancedJava.services.StockServiceFactory;
import edu.garyMooradian.advancedJava.utilities.DatabaseConnectionException;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * A simple application that shows the StockService in action.
 * Note that in this assignment, the application uses the StockService
 * known as DatabaseStockService. All references to StockService are
 * the DatabaseStockService
 */
public class StockQuoteApplication {

    private StockService stockService;
    

    // an example of how to use enum - not part of assignment 3 but useful for assignment 4

    /**  ENUM
     * An enumeration that indicates how the program terminates (ends)
     */
    private enum ProgramTerminationStatusEnum {
        // for now, we just have normal or abnormal but could more specific ones as needed.
        NORMAL(0),
        ABNORMAL(-1);
    	
    	// when the program exits, this value will be reported to underlying OS
        private int statusCode;
           	
    	/** Enum CONSTRUCTOR
         * Create a new  ProgramTerminationStatusEnum
         *
         * @param statusCodeValue the value to return to the OS. A value of 0
         *                        indicates success or normal termination.
         *                        non 0 numbers indicate abnormal termination.
         */
        private ProgramTerminationStatusEnum(int statusCodeValue) {
            this.statusCode = statusCodeValue;
        }
               
        /**
         * @return The statusCode (NORMAL or ABNORMAL) sent to OS when the program ends.
         * getter method for ProgramTerminationStatusEnum statusCode
         */
        private int getStatusCode() {
            return statusCode;
        }
    }//end of enum
    
    

    /** CONSTRUCTOR
     * Create a new Application.
     *
     * @param stockService the StockService this application instance should use for
     *                     stock queries.
     *                     <p/>
     *                     NOTE: this is a example of Dependency Injection in action.
     */
    public StockQuoteApplication(StockService stockService) {
        this.stockService = stockService;
    }
    
    

    /**
     * Given a <CODE>stockQuery</CODE> get back the info about the stock to display to the user.
     *
     * @param stockQuery   object that contains the 4 parameters for querying the database.
     * @return a String with the stock data in it.
     * @throws SQLException 
     * @throws DatabaseConnectionException 
     * @throws StockServiceExceptionTest If data about the stock can't be retrieved. This is a
     *                               fatal error.
     */
    public String displayStockQuotes(StockQuery stockQuery) throws StockServiceException, DatabaseConnectionException, SQLException {

    	StringBuilder stringBuilder = new StringBuilder();

        /*
         * The 4 args we pass originate from the user, and are assigned to a StockQuery object;
         * A container object.
         * So the StockQuery object established here is the user's representation of their requested
         * stock quote; i.e. the stock symbol, start and end dates, and interval, that they are
         * querying for.
         * StockQuery converts the user's String entries into the appropriate types for
         * passing to getQuoteHistory; e.g. The "from" and "until" String dates provided by
         * the user, are converted to Calendar objects in StockQuote. 
         * 
         * We reference the StockQuery object getter methods, to obtain those 4 values and pass
         * them as arguments to the stockService.getQuoteHistory method call below.
         * 
         * The return from that call is an ArrayList of StockQuote objects which we assign here to
         * another ArrayList of the same type. We then create a string of those stock quotes returned
         * from the stock service (i.e. from DatabaseStockService)
         * We then return that string to the client (i.e. main method) for display
         */
        List<StockQuote> stockQuotes =
        		stockService.getQuoteHistory(stockQuery.getSymbol(), stockQuery.getFrom(), 
        				stockQuery.getUntil(), stockQuery.getInterval());
        

        stringBuilder.append("Stock quotes for: " + stockQuery.getSymbol() + "\n");
        /*
         * Get each StockQuote object from the ArrayList and append it's string representation
         * to the above string.
         * The string representation of the StockQuote object, specifies the price, date, symbol and interval;
         * (See toString method in StockQuote class) 
         */
        for (StockQuote stockQuote : stockQuotes) {
            stringBuilder.append(stockQuote.toString());
        }
        
        /*
         * Note that stringBuilder is an object that contains the above string we just created.
         * We need to return the object in String form, therefore we call it's toString method
         */
        return stringBuilder.toString();//returns to main method
    }
    
    

    /**
     * Terminate the application.
     *
     * @param statusCode        an enum value that indicates if the program terminated ok or not.
     * @param diagnosticMessage A message to display to the user when the program ends.
     *                          This should be an error message in the case of abnormal termination
     *                          <p/>
     *                          NOTE: This is an example of DRY in action.
     *                          A program should only have one exit point. This makes it easy to do any clean up
     *                          operations before a program quits from just one place in the code.
     *                          It also makes for a consistent user experience.
     */
    private static void exit(ProgramTerminationStatusEnum statusCode, String diagnosticMessage) {
        if (statusCode == ProgramTerminationStatusEnum.NORMAL) {
            System.out.println(diagnosticMessage);
        } else if (statusCode == ProgramTerminationStatusEnum.ABNORMAL) {
            System.err.println(diagnosticMessage);
        } else {
        	//Only NORMAL or ABNORMAL allowed
            throw new IllegalStateException("Unknown ProgramTerminationStatusEnum.");
        }
        /*
         * statusCode is a reference variable to a ProgramTerminationStatusEnum object
         * We use it to call the getStatusCode method of that enum object.
         * Here, we end the application and provide that status code 
         */        
        System.exit(statusCode.getStatusCode());
    }
    
    

    /**
     * Run the StockQuote application.
     * <p/>
     * When invoking the program supply one or more stock symbols.
     *
     * @param args one or more stock symbols
     */
    public static void main(String[] args) {

        // be optimistic; initialize to positive values
    	//Instantiate an enum and assign enum object to exitStatus reference variable
    	//The object will contain the enum constant, NORMAL
        ProgramTerminationStatusEnum exitStatus = ProgramTerminationStatusEnum.NORMAL;
        String programTerminationMessage = "Normal program termination.";
        
        //This cannot generate an Exception so no try/catch required
        //Since we are hard coding the input right now, we'll comment out the following for now
        if (args.length != 4) {
            exit(ProgramTerminationStatusEnum.ABNORMAL,
                    "Please supply 4 arguments a stock symbol, a start date (MM/DD/YYYY) and end date (MM/DD/YYYY), and an interval (DAILY, TWICE_DAILY, or HOURLY)");
        }
        
        try {			
        	/*
        	 * The user provides 4 inputs. The symbol, the from date and the until date
        	 * and the interval
        	 * We pass these values to the constructor of the StockQuery class, where the
        	 * 4 values are assigned to class variables. The class also has the getters
        	 * to obtain these values.
        	 * StockQuery class is being used as a container; i.e Easier to pass
        	 * user input values around as an object
        	 *
        	 */
			StockQuery stockQuery = new StockQuery(args[0], args[1], args[2], args[3]);
			
            /*
             * Get the StockService (i.e. DatabaseStockService) from the StockServiceFactory
             */
            StockService stockService = StockServiceFactory.getStockService();

            /*
             * Instantiate this class here (i.e. StockQuoteApplication), passing it the
             * StockService object.
             * It will be assigned to a class variable of course, because other methods
             * in this class (i.e. local methods) will need to reference it.
             */
            StockQuoteApplication stockQuoteApplication = new StockQuoteApplication(stockService);           
 
            /*
             * displayStockQuotes will call the stockService (DatabaseStockService)
             * and return a string of stock quotes that are returned. We print out
             * that string here
             */
            System.out.println(stockQuoteApplication.displayStockQuotes(stockQuery));

        } catch (ParseException e) {
            exitStatus = ProgramTerminationStatusEnum.ABNORMAL;
            programTerminationMessage = "Invalid date data: " + e.getMessage();
        } catch (StockServiceException e) {
            exitStatus = ProgramTerminationStatusEnum.ABNORMAL;
            programTerminationMessage = "StockService failed: " + e.getMessage();
        } catch (SQLException e) {
        	exitStatus = ProgramTerminationStatusEnum.ABNORMAL;
            programTerminationMessage = "database or JDBC driver failed: " + e.getMessage();
        } catch (DatabaseConnectionException e) {
        	exitStatus = ProgramTerminationStatusEnum.ABNORMAL;
            programTerminationMessage = "database connection failed: " + e.getMessage();
        }

        exit(exitStatus, programTerminationMessage);
        System.out.println("Oops could not parse a date");
    }
    
}
