package backendCalculations;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class DBconnect 
{
	static Logger logger = Logger.getLogger(DBconnect.class);
	
	static Connection connection=null;
	static Statement statement=null;
	
	public static void startConnection() throws SQLException, ClassNotFoundException, UnsupportedEncodingException 
	{
		System.out.println("1");
		try 
        {	
			System.out.println("2");
			if(connection == null) {
				Class.forName("org.hsqldb.jdbc.JDBCDriver");
				System.out.println("3");
		        //String port=PortNumber();
				String port="9001";
		        System.out.println(port);
		   	        
		        connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:"+port+"/testdb", "SA", "");
		        
//		        if(openConnection())
//		        	System.out.println("OPEN");
		        
		        connection.setAutoCommit(false);
			}			
		} 
        catch (SQLException e)
        {		
        	System.out.println("4");
        	throw new SQLException("Unable to access Database" , e);			
		}
		catch(ClassNotFoundException c) {
			throw new ClassNotFoundException("Can't find class org.hsqldb.jdbc.JDBCDriver", c);
		}
		/*catch(UnsupportedEncodingException u) {
			throw new UnsupportedEncodingException("Unable to read portConfig file");
		}*/
		System.out.println("5");
	}
	
	public static void shutdown() throws SQLException {

		statement = connection.createStatement();

		statement.execute("SHUTDOWN");
        
        connection.close();  
        
        if(!openConnection())
        	System.out.println("CLOSqD");
    }
	
	public static void DbCommit() throws SQLException 
	{
		 connection.commit();	
	}
	
	public static boolean openConnection() throws SQLException
	{
		if(connection.isClosed())
			return false;
		else
			return true;		
	}
	
	public static String PortNumber() throws UnsupportedEncodingException 
	 {
		    String absolutePath = DBconnect.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		 	absolutePath = URLDecoder.decode(absolutePath, "UTF-8");
			absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
			absolutePath = absolutePath.replaceAll("%20", " ");	//remove %20 from the whitespace in path
			System.out.println(absolutePath);
			absolutePath = absolutePath.substring(1, absolutePath.length());
			absolutePath = absolutePath.replaceAll("/", "\\\\");String port=null;
			for(int j=absolutePath.lastIndexOf("\\");j>absolutePath.indexOf("\\");j--)
			{
				String absolute=readPortLogFile(absolutePath);
				if(absolute!=null)						
				{
					System.out.println("Port file in: "+absolutePath);
					port=absolute;
					break;
				}
				System.out.println("\n" + absolutePath);
				absolutePath=absolutePath.substring(0, absolutePath.lastIndexOf("\\"));				
			}			
			return port;
	 }
	 
	 public static String readPortLogFile(String directoryDestPath) {

			String LastPortHsqldb = null;
		    File[] filesList = new File(directoryDestPath).listFiles();
		 	for(File f : filesList)
			{
				if(f.isDirectory())
		        {   
		          	if("resources".equals(f.getName()))
		           	{
		          		try {
	           				File fileTxtPort = new File(directoryDestPath.concat("\\resources\\Port_log.txt"));
	           			
	           				try (FileReader frPort = new FileReader(fileTxtPort)) 
	           				{
	           					Scanner reader = new Scanner(frPort);
								String line;
								String[] lineArr;
			
								while ((line = reader.nextLine()) != null) 
								{
									lineArr = line.split(",");
									if (lineArr[0].equals("HSQLDB PORT Current")) 
									{
										LastPortHsqldb = lineArr[1];
										break;
									}
								}
								reader.close();		
							}				
						} 
	           			catch (IOException e) 
	           			{
							e.printStackTrace();
						}	
		           	}
		        }
			}
			return LastPortHsqldb;
		}

	public static Connection getConnection()
	{
		return connection;
	}

	public static void setConnection(Connection connection) 
	{
		DBconnect.connection = connection;
	}

	public static Statement getStatement() throws SQLException 
	{
		statement = connection.createStatement();
		return statement;
	}

	public static void setStatement(Statement statement)
	{
		DBconnect.statement = statement;
	}	

}