package edu.garyMooradian.advancedJava.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/*
 * This class represents a Person. It maps to the person table
 * in the stocks database in mysql; i.e. the person table has
 * an id column and user_name column, and this class has an id variable
 * and userName variable. 
 * Instantiating this class creates an object for one person in
 * the person table; i.e. a record/row in the table
 * Essentially, a person table record maps to an instantiation
 * of a Person. For example, if we had 5 records in the person table we would have
 * 5 Person objects representing them
 */

@Entity //This annotation means that this class is mapped to a table
@Table(name="person") //name attribute specifies the exact name of the table in the database; i.e. person
public class Person {
	
	private int id;
	private String userName;
	
	/**
	 * 
	 * @return  Primary Key - Unique ID for a particular row in the person table.
	 */
	@Id //Tells Hibernate this column is the primary key
	@Column(name = "id", nullable = false, insertable = true, updatable = true) //name attribute is the exact name of the column
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 *
     * @return the user_name column as a String
     *
	 * Use @Basic as long as the data types are basic, e.g. String, int etc, and can be
	 * easily mapped to the SQL data types. Using @Basic makes the conversion automatic.
	 */
	@Basic 
	@Column(name = "user_name", unique = true, nullable = false, insertable = true, updatable = true, length = 256)
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	@Override
    public boolean equals(Object o) {

        if (this == o) return true; //They are the same object so of course they are equal

        if (o == null || getClass() != o.getClass()) return false; //they are different types of objects so they're not equal

        Person person = (Person) o; //cast the incoming Object type to a Person type

        //different instantiations of the same type. Is it the same record?
        if (id != person.id) return false;
        if (userName != null ? !userName.equals(person.userName) :
        												person.userName != null) return false;
        
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", name='" + userName + '\'' + '}';
    }

}
