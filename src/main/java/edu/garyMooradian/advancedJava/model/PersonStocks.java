package edu.garyMooradian.advancedJava.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/*
 * This class represents a table that joins the Person and StockSymbol tables
 */
@Entity
@Table(name = "person_stocks", catalog = "stocks")
public class PersonStocks {
	
	private int id;
	private Person person;
	private StockSymbol stockSymbol;
	
	// CONSTRUCTOR
	public PersonStocks(Person person, StockSymbol stockSymbol) {
		setPerson(person);
		setStockSymbol(stockSymbol);
	}
	
	// CONSTRUCTOR (Required parameterless constructor)
	public PersonStocks() {}
	
	
	
	
	
	/**
     * Primary Key - Unique ID for a particular row in the person_hobby table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the person_hobby table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value.
     */
    public void setId(int id) {
        this.id = id;
    }
	
	
	/*
	 * Regarding @JoinColumn; The person_id column (FK) of the stock_symbol table
	 * references the id column of the person table. 
	 */
	@ManyToOne //The relationship between the person_stocks and person tables respectively
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    public Person getPerson() {
        return person;
    }

    /**
     * Specify the Person associated with the stockSymbol.
     *
     * @param person a person instance
     */
    public void setPerson(Person person) {
        this.person = person;
    }
    
	/*
	 * Regarding @JoinColumn; The symbol_id column (FK) of the stock_symbol table
	 * references the id column of the stock_symbol table. 
	 */
    @ManyToOne //The relationship between the person_stocks and stock_symbol tables respectively
    @JoinColumn(name = "symbol_id", referencedColumnName = "id", nullable = false)
    public StockSymbol getStockSymbol() {
        return stockSymbol;
    }
    

    /**
     * Specify the Person associated with the hobby.
     *
     * @param person a person instance
     */
    public void setStockSymbol(StockSymbol stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
	
	
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonStocks that = (PersonStocks) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + person.hashCode();
        result = 31 * result + stockSymbol.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PersonStocks{" +
                "id=" + id +
                ", person=" + person +
                ", stockSymbol=" + stockSymbol +
                '}';
    }

}
