package backendCalculations;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class unitPrice
{
	static Logger logger = Logger.getLogger(unitPrice.class);
	
	static Connection con=null;
	static Statement stmt=null;

	/**
	 * Insert the UnitPrice data into the table(development,Execution,Maintenance) depending on the code
	 * @param code [0-Execution,1-Maintenance,2-Development]
	 * @throws IOException 
	 * @throws SQLException
	 */
	public int InsertParam(String category,String tech,String complex, double unit,int code) throws SQLException 
	{	
		logger.trace("Entered InsertParam method with parameters- category = " + category + ", technology = " + tech + ", complexity = " + ", mode = " + Integer.toString(code) + ", unitPrice = " + Double.toString(unit));
		int res=0;String sql="";
		
		try
		{	
			if(code==0)
				sql="INSERT INTO ExecutionUnitPrice(Technology,Complexity,price) VALUES(?,?,?);";
			else if(code==1)
				sql="INSERT INTO MaintenanceUnitPrice(Technology,Complexity,price) VALUES(?,?,?);";
			else
				sql="INSERT INTO DevelopmentUnitPrice(Category,Technology,Complexity,price) VALUES(?,?,?,?);";
			
			
			PreparedStatement pst=con.prepareStatement(sql);
			if(code==2)
			{
				pst.setString(1,category);			
				pst.setString(2,tech);
				pst.setString(3,complex);		
				pst.setDouble(4,unit );
			}
			else
			{
				pst.setString(1,tech);
				pst.setString(2,complex);		
				pst.setDouble(3,unit );
			}

			res = pst.executeUpdate();
			DBconnect.DbCommit();
		}
		catch(SQLException e)
		{
			throw new SQLException("Unable to insert into the Database table.", e);			
		}
		
		logger.trace("Exited InsertParam method");
		return res;
	}
	
	/**
	 * Update the UnitPrice data into the table(development,Execution,Maintenance) depending on the code
	 * If the record does not exist,It will be inserted into the table 
	 * @param code [0-Execution,1-Maintenance,2-Development]
	 * @throws SQLException
	 * @throws IOException 
	 */
	public void  insertOrUpdateParam(String category,String tech,String complex,double unit,int code) throws SQLException, IOException
	{
		logger.trace("Entered insertOrUpdateParam method with parameters- category = " + category + ", technology = " + tech + ", complexity = " + ", mode = " + Integer.toString(code) + ", unitPrice = " + Double.toString(unit));
		try {
			SelectParam(category, tech, complex, code);
			//if control reaches here, an entry already exists so update is required
			String sql="";
			if(code==0)
				sql="UPDATE ExecutionUnitPrice SET price="+unit+" where Technology = '" + tech + "' and Complexity = '" + complex + "'";
			else if(code==1)
				sql="UPDATE MaintenanceUnitPrice SET price="+unit+" where Technology = '" + tech + "' and Complexity = '" + complex + "'";			
			else
				sql="UPDATE DevelopmentUnitPrice SET price="+unit+" where Technology = '" + tech + "' and Complexity = '" + complex + "' and Category = '" + category +"'";
			
				
			stmt.executeUpdate(sql);
			DBconnect.DbCommit();
			
		} 
		catch(Exception e) {
			//if control reaches here, you have to insert record
			InsertParam(category, tech, complex, unit,code);
		}
		finally {
			logger.trace("Exited insertOrUpdateParam method");
		}
	}
	
	/**
	 * Retrieves the UnitPrice data into the table(development,Execution,Maintenance) depending on the code
	 * @param code [0-Execution,1-Maintenance,2-Development]
	 * @throws SQLException
	 */
	public double  SelectParam(String category,String tech,String complex,int code) throws SQLException, IOException
	{	
		logger.trace("Entered SelectParam method with parameters- category = " + category + ", technology = " + tech + ", complexity = " + ", mode = " + Integer.toString(code));
		double unit=0;
		ResultSet result=null; 
		
		if(code==0)
			result=stmt.executeQuery("SELECT price from ExecutionUnitPrice WHERE Technology = '" + tech + "' and Complexity = '" + complex + "'");
		else if(code==1)
			result=stmt.executeQuery("SELECT price from MaintenanceUnitPrice WHERE Technology = '" + tech + "' and Complexity = '" + complex + "'");
		else
			result=stmt.executeQuery("SELECT price from DevelopmentUnitPrice WHERE Technology = '" + tech + "' and Complexity = '" + complex + "' and Category = '"  + category + "'");
		if(result.next())
		{
			unit=result.getDouble("price");
		}
		else
		{
			throw new IOException("Data for Category " +category + ", Technology  " + tech + ", Complexity  " + complex + " not present in DB");
		}
		logger.trace("Exited SelectParam method");
		return unit;
	}
	
	/**
	 * class constructor
	 * @throws IOException  
	 */
	public unitPrice() throws IOException 
	{		
		logger.trace("Entered constructor of unitPrice class");
        try 
        {
		
        	DBconnect.startConnection();
			con = DBconnect.getConnection();

	        stmt = con.createStatement();
	       
	        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ExecutionUnitPrice(Technology varchar(60),Complexity varchar(50),price NUMERIC(11,6),primary key(Technology,Complexity))");
	        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS MaintenanceUnitPrice(Technology varchar(60),Complexity varchar(50),price NUMERIC(11,6),primary key(Technology,Complexity))");
	        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS DevelopmentUnitPrice(Category varchar(40),Technology varchar(60),Complexity varchar(50),price NUMERIC(11,6),primary key(Category,Technology,Complexity))");	        
		} 
        catch (Exception e)
        {		
        	throw new IOException("Unable to create unit price tables in the Database table.", e);			
		}
        logger.trace("Exiting constructor of unitPrice class");
	}	

}

