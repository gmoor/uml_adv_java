package edu.garyMooradian.advancedJava.utilities;

import com.ibatis.common.jdbc.ScriptRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class that contains database related utility methods.
 */
public class DatabaseUtils {

	public static final String initializationFile = "./src/main/sql/stocks_db_initialization.sql" ;
    
	/*
     * In a real program these values would be a configurable property and not hard coded.
     * JDBC driver name and database URL
     */
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";//deprecated -> "com.mysql.jdbc.Driver";
    
    /*
     * The name of the database (stocks)
     * Note that the stocks database was manually created
     */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/stocks";

    /*
     * Database credentials
     * Although hard coding credentials is unacceptable in a real app,
     * for this assignment we allow it
     */
    private static final String USER = "monty";      //"root";
    private static final String PASS = "some_pass";  //"Cas9Edit7!";
    

    public static Connection getConnection() throws DatabaseConnectionException{
        Connection connection = null;
        try {
        	/*
        	 * forName is a static method in the class called Class
        	 * We use it to load/register the Driver.
        	 * Can generate a ClassNotFoundException
        	 */
        	Class.forName(JDBC_DRIVER);
        	
        	/*
        	 * Create the connection (Connection object) with this database and
        	 * credentials; i.e. We are connected
        	 * Can generate an SQLException
        	 */
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            
        } catch (ClassNotFoundException | SQLException e)  {//either Exception is possible
        	/*
        	 * Either Exception affects connecting to the database, therefore, we
        	 * throw the home grown DatabaseConnectionException
        	 */
        	throw new DatabaseConnectionException("Could not connect to database." + e.getMessage(), e);
        }
        /*
         * return Connection object; it will be used to send SQL commands via
         * the Connection object's createStatement method; i.e.
         * createStatement method will create a Statement object for sending SQL statements
         * to the database.
         */
        return connection;//return the Connection object
    }
    
    

    /**
     * A utility method that runs a db initialize script.
     * @param initializationScript - full path to the script to run, to create the schema
     * @throws DatabaseInitializationExceptionTest
     * 
     */
    public static void initializeDatabase(String initializationScript) throws DatabaseInitializationException{

        Connection connection = null;
        try {
        	/*
        	 * Call the getConnection method in this class, to obtain a Connection object
        	 */
            connection = getConnection();
            
            //Passing the connection
            ScriptRunner runner = new ScriptRunner(connection, false, false);
            
            //I assume that the script is used to create the database if it was
            //not created beforehand? NOTE: initializationScript is the variable pointing to the script file
            //The script file was passes into our method
            InputStream inputStream = new  FileInputStream(initializationScript);
            InputStreamReader reader = new InputStreamReader(inputStream);

            runner.runScript(reader);
            reader.close();
            connection.commit();
            connection.close();

        } catch (DatabaseConnectionException | SQLException |IOException e) {
           throw new DatabaseInitializationException("Could not initialize db because of:"
                   + e.getMessage(),e);
        }
    }

}
