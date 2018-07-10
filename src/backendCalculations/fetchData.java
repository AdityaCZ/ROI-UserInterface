package backendCalculations;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;

import org.apache.log4j.Logger;

import backendCalculations.inputTable;

public class fetchData {
	
	static Logger logger = Logger.getLogger(fetchData.class);
	
//	public static void main(String[] args) {		
//		try {
//			logger.info("Connecting to DB");
//			inputTable input = new inputTable("Tre18887140", "jdbc:hsqldb:hsql://localhost/"+"testdb");
//			logger.info("Input table data retrieved");
//			outputTable output = new outputTable(input);
//			logger.info("Output table calculation complete");
//			
//			JSONObject outputInJSON = output.getJSON();
//			logger.info(outputInJSON.toJSONString());
//			
//			pushIntoDB("Tre18887140", "jdbc:hsqldb:hsql://localhost/"+"testdb", outputInJSON, output);
//			logger.info("Completed");
//		}
//		catch(ClassNotFoundException e) {
//			logger.fatal(e.getMessage(), e);			
//		}
//		catch (SQLException e) {
//			logger.fatal(e.getMessage(), e);
//		}
//		catch (IOException e) {
//			logger.error(e.getMessage(), e);
//		}
//		catch (ArithmeticException e) {
//			logger.fatal(e.getMessage(), e);
//		}
//		catch (Exception e) {
//			logger.fatal(e.getMessage(), e);
//		}
//	}
//	
	/*
	 * Method to push output and Cost summary table into Database
	 */	
	public static void pushIntoDB(String transactionID, String address, Object outputInJSON, Object output) {
		try {
			logger.trace("Entered pushIntoDB function, with transactionId = " + transactionID + ", address = " + address);
			
			DBconnect.startConnection();
			Connection con = DBconnect.getConnection();
	        
			//Table Creation
	         Statement stmt = con.createStatement();
	         stmt.executeUpdate("CREATE TABLE IF NOT EXISTS output_table (transactionid varchar(30), output object, primary key (transactionid));");
	         
	         logger.trace("Output table schema created");
	         
	         String sql="INSERT INTO output_table (transactionid, output) VALUES (?,?)";
			 PreparedStatement pst=con.prepareStatement(sql);
			 pst.setString(1, transactionID);
			 pst.setObject(2, outputInJSON);
			 pst.executeUpdate();
			 
			 //pushing output table into DB
			 DBconnect.DbCommit();
			 logger.trace("Exited pushIntoDB function");

		}
		catch(Exception e) {
			e.printStackTrace();
			logger.fatal("Unable to store output table into DB", e);
		}
	}
	
	/*
	 * Main function of the package.
	 * Triggers output and cost summary table calculation and stores the calculated data into database.
	 */
	public void calculate(String transactionID, String address) {
		logger.trace("Entered function calculate with transactionId = " + transactionID + ", address = " + address);		
		try {
			logger.info("Connecting to DB");
			//reads input table & stores information in class member variables
			inputTable input = new inputTable(transactionID, address);
			logger.info("Input table data retrieved");
			//calculates output table parameters and stores as class member variables
			outputTable output = new outputTable(input);
			logger.info("Output table calculation complete");
			
			JSONObject outputInJSON = output.getJSON();
			logger.info(outputInJSON.toJSONString());
			
			//store output table data into database
			pushIntoDB(transactionID, address, outputInJSON, output);
			logger.info("Completed");
		}
		catch(ClassNotFoundException e) {
			logger.fatal(e.getMessage(), e);			
		}
		catch (SQLException e) {
			logger.fatal(e.getMessage(), e);
		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		catch (ArithmeticException e) {
			logger.fatal(e.getMessage(), e);
		}
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
		}
		logger.trace("Exiting calculate function");
	}
}
