package edu.garyMooradian.advancedJava.model;


import org.apache.http.annotation.Immutable;

import edu.garyMooradian.advancedJava.app.stockQuote.IntervalEnum;

import javax.validation.constraints.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is used to a single query to stock service.
 */
@Immutable
public class StockQuery extends StockData {

    private String symbol;
    private Calendar from;
    private Calendar until;
    private IntervalEnum interval;

    /** CONSTRUCTOR
     * Create a new instance from string data. This constructor will convert
     * dates described as a String, into Date objects.
     *
     * @param symbol the stock symbol
     * @param from   the start date as a string in the form of yyyy/MM/dd
     * @param until  the end date as a string in the form of yyyy/MM/dd
     * @param interval time interval between stock quotes (hourly, daily, etc..)
     * @throws ParseException if the format of the date String is incorrect. If this happens
     *                        the only recourse is to try again with a correctly formatted String.
     */
    public StockQuery(@NotNull String symbol, @NotNull String from, @NotNull String until, @NotNull String interval) throws ParseException {
        /*
         * We call the abstract class (StockData) constructor. Note that an abstract class can not
         * be instantiated, therefore the abstract class's constructor does not actually
         * instantiate the abstract class; Instead, it's constructor is used to set
         * a value (or values) in the abstract class. Currently, the constructor of the abstract
         * class, creates a SimpleFormatData object and assigns it to a class reference variable
         * called simpleDateFormat.
         * Our StockQuery class, has access to that reference variable via inheritance.
         */
    	super();
    	   	
        this.symbol = symbol;
        this.from = Calendar.getInstance();
        this.until = Calendar.getInstance();

        //add the Date/time objects to their corresponding Calendar objects
        this.from.setTime(simpleDateFormat.parse(from));
        this.until.setTime(simpleDateFormat.parse(until));
        
        /*
         * Probably not the professional way to do it but I'm not a professional; yet
         * 
         * Note: Verifying the user's input string for interval
         * should be done by the client, not here; i.e. we should 
         * expect the right value to be passed to this constructor,
         * therefore, we do not provide an else condition
         */
        if (interval.equals("daily")) {
        	this.interval = IntervalEnum.DAILY;//interval variable assigned the enum constant, DAILY
        } else if (interval.equals("twice_daily")) {
        	this.interval = IntervalEnum.TWICE_DAILY;
        } else if (interval.equals("hourly")) {
        	this.interval = IntervalEnum.HOURLY;
        }
    }

    /**
     * @return get the stock symbol associated with this query
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @return get the start Calendar associated with this query
     */
    public Calendar getFrom() {
        return from;
    }

    /**
     * @return get the end Calendar associated with this query
     */
    public Calendar getUntil() {
        return until;
    }
    
    /**
     * @return  get the interval associated with this query
     */
    public IntervalEnum getInterval() {
    	return interval;
    }
}
