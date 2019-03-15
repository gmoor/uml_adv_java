package edu.garyMooradian.advancedJava.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import edu.garyMooradian.advancedJava.model.Person;
import edu.garyMooradian.advancedJava.model.PersonStocks;
import edu.garyMooradian.advancedJava.model.StockSymbol;
import edu.garyMooradian.advancedJava.utilities.DatabaseUtils;

public class DatabasePersonService implements PersonService {


	public List<Person> getPerson() throws StockServiceException {
		
		Session session =  DatabaseUtils.getSessionFactory().openSession();
		Transaction transaction = null;
		List<Person> returnValue = null;
		
		try {
        	
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Person.class);

            /**
             * NOTE criteria.list(); generates unchecked warning so SuppressWarnings
             * is used - HOWEVER, this about the only @SuppressWarnings I think it is OK
             * to suppress them - in almost all other cases they should be fixed not suppressed
             * 
             */
            returnValue = criteria.list();

        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            throw new StockServiceException("Could not get Person data. " + e.getMessage(), e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }

        return returnValue;//GM-return Hibernate Criteria list
	}
	
	/*
	 * get the List of StockSymbols for a Person
	 */
	public List<StockSymbol> getPersonStockSymbol(Person person) throws StockServiceException {
		
		//A session allows Hibernate to interact with the database
		Session session =  DatabaseUtils.getSessionFactory().openSession();
		Transaction transaction = null;
		List<StockSymbol> stockSymbols = new ArrayList<>();		
        try {

            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(PersonStocks.class);
            criteria.add(Restrictions.eq("person", person));
            
            /**
             * NOTE criteria.list(); generates unchecked warning so SuppressWarnings
             * is used - HOWEVER, this about the only @SuppressWarnings I think it is OK
             * to suppress them - in almost all other cases they should be fixed not suppressed
             */
            List<PersonStocks> list = criteria.list();
            for (PersonStocks personStocks : list) {
                stockSymbols.add(personStocks.getStockSymbol());
            }
            transaction.commit();
            
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            throw new StockServiceException("Could not get Person data. " + e.getMessage(), e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
        
        return stockSymbols;

	}
}
