package edu.garyMooradian.advancedJava.services;

import java.util.List;

import edu.garyMooradian.advancedJava.model.Person;
import edu.garyMooradian.advancedJava.model.StockSymbol;


public interface PersonService {
	
	
	/**
     * Get a list of all people
     *
     * @return a list of Person instances
     * @throws StockServiceException if a service can not read the requested data
     *                                    or otherwise perform the requested operation.
     *
     */
    List<Person> getPerson() throws StockServiceException;
    
    
    /**
     * Get a list of all a person's stocks (i.e. stock symbols).
     *
     * @return a list of StockSymbol instances
     * @throws ActivitiesServiceException if a service can not read the requested data
     *                                    or otherwise perform the requested operation.
     */
    List<StockSymbol> getPersonStockSymbol(Person person) throws StockServiceException;

}
