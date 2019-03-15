package edu.garyMooradian.advancedJava.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="stock_symbol")
public class StockSymbol {
	
	private int id;
	private String symbol;
	
	
	
	@Id
	@Column(name = "id", nullable = false, insertable = true, updatable = true)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	@Basic
	@Column(name = "symbol", nullable = false, insertable = true, updatable = true, length = 4)
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	
	
	@Override
    public boolean equals(Object o) {

        if (this == o) return true; //They are the same object so of course they are equal

        if (o == null || getClass() != o.getClass()) return false; //they are different types of objects so they're not equal

        StockSymbol stockSymbol = (StockSymbol) o; //cast the incoming Object type to a StockSymbol type

        //different instantiations of the same type. Is it the same record?
        if (id != stockSymbol.id) return false;
        if (symbol != null ? !symbol.equals(stockSymbol.symbol) :
        												stockSymbol.symbol != null) return false;
        
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StockSymbol{" + "id=" + id + ", symbol='" + symbol + '\'' + '}';
    }


}
