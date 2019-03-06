package edu.garyMooradian.advancedJava.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * A container class that contains stock data.
 * Note: StockData is an abstract class
 */
public class StockQuote extends StockData {

    private BigDecimal price;
    private Date date;
    private String symbol;

    /**
     * Create a new instance of a StockQuote.
     *
     * @param price  the share price for the given date
     * @param date   the date of the share price
     * @param symbol the stock symbol.
     */
    public StockQuote(BigDecimal price, Date date, String symbol) {
    	/*
    	 * call StockData (i.e. our abstract superclass) constructor
    	 * It will establish a date format (simpleDateFormat object)
    	 * and assign it to the class variable simpleDateFormat.
    	 * We will inherit that variable here in StockQuote and use it
    	 * in the toString method. See StockData for the actual format
    	 * (e.g. "yyyy/MM/dd")
    	 */
        super();
 
        this.price = price;
        this.date = date;
        this.symbol = symbol;
    }

    /**
     * @return Get the share price for the given date.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @return The date of the share price
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return The stock symbol.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @return The stock quote in string format.
     */
    @Override
    public String toString() {
    	/*
    	 * the format method formats the given date into
    	 * a date string. A format example would be "yyyy/MM/dd"
    	 */
        String dateString = simpleDateFormat.format(date);
        return "StockQuote{" +
                "price=" + price +
                ", date=" + dateString +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
