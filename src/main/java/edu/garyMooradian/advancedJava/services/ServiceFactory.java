package edu.garyMooradian.advancedJava.services;

//Class originally called StockServiceFactory.
//We renamed it to ServiceFactory for Assignment 6

/**
 * A factory that returns a <CODE>StockService</CODE> instance.
 */
public class ServiceFactory {

    /**
     * Prevent instantiations; i.e. the default constructor is public
     * so we need to override it with a private constructor
     */
    private ServiceFactory() {}
       
    /*
	 * We call this method getStockService because it returns any implementation
	 * of StockService; i.e. we don't want to specify a particular implementation
	 * because there could be more than one.
	 * In this limited case we just so happen to return just one
	 * implementation; i.e. DatabaseStockService
	 * If there were multiple StockServices that could be returned, we would
	 * need if/else condition (or switch statement) to determine which to return.
	 * The client would need to pass the criteria that would determine which
	 * StockService to return. But that's not the best approach. We can take the
	 * onus off the client, by using a configuration file. This method would read
	 * the file and that would specify which StockService to return
	 */
	//public static StockService getStockService() {
	//	return new DatabaseStockService();
	//}
	//This method replaces the above commented out method; i.e. the method name has been changed
	public static StockService getStockServiceInstance() {
		return new DatabaseStockService();
	}

	
	//This is the new static method we added for this assignment
	//DatabasePersonService contains the getPerson and get getStockSymbol methods
	public static PersonService getPersonServiceInstance() {
		return new DatabasePersonService();
	}
    

}
