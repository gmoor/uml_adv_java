package edu.garyMooradian.advancedJava.model;

import java.text.SimpleDateFormat;

/**
 * Abstract Base class for classes that hold Stock data.
 * Provides common code for such classes including date formatting.
 */
public abstract class StockData {

    /**
     * Provide a single SimpleDateFormat for consistency (i.e. we want the same type of
     * format throughout the app), and to avoid duplicated code (i.e. the simpleDateFormat object
     * is established here and all subclasses reference it)
     */
    protected SimpleDateFormat simpleDateFormat;

    /** CONSTRUCTOR
     * Base constructor for StockData classes.
     * Initialize member data that is shared with sub classes.
     */
    public StockData() {
        this.simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    }

}
