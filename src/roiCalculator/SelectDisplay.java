package roiCalculator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import backendCalculations.DBconnect;

public class SelectDisplay 
{
	public static void main(String args[])
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		try
		{
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			DBconnect.startConnection();
			 con = DBconnect.getConnection();
			stmt = con.createStatement();
			
			System.out.println("\t\t\t***NAMES***\n");
			ResultSet result2=stmt.executeQuery("SELECT DISTINCT app_name FROM appln_details");
			
			ArrayList<String> a=new ArrayList<String>();
			
			while(result2.next())
			{
				System.out.println(result2.getString("app_name"));
				a.add(result2.getString("app_name"));
			}
			
			System.out.println("");
			ArrayList<String> id=new ArrayList<String>();
			
			for(int i=0;i<a.size();i++)
			{
				String name=a.get(i);
				String sql="SELECT transactionid FROM appln_details where app_name='"+name+"'";
				ResultSet result1=stmt.executeQuery(sql);
				System.out.println("\t\t\t================="+name+"=================");
				while(result1.next())
				{
					//System.out.println(result1.getString("transactionid"));
					
					result = stmt.executeQuery("SELECT * FROM appln_details where transactionid='"+result1.getString("transactionid")+"'");		
					
					while(result.next())
					{
						System.out.println("transactionid	:"+result.getString("transactionid"));
						System.out.println("Application Name	:"+result.getString("app_name"));
						System.out.println("Application ID	:"+result.getString("app_id"));
						System.out.println("Category	:"+result.getString("appln_category"));
						System.out.println("Technology	:"+result.getString("technology"));
						System.out.println("Automation Tool	:"+result.getString("automation_tool"));
						System.out.println("Business Criticality	:"+result.getString("b_criticality"));
						System.out.println("Number of Test Cases provided by Wells fargo	:"+result.getInt("cases_wells_fargo"));
						System.out.println("Number of Test Cases after  breakdown according to complexity definition*	:"+result.getInt("cases_breakdown"));
						System.out.println("Percentage of Feasible Test Cases*	:"+result.getDouble("percentage"));
						System.out.println("Current Onshore Percentage	:"+result.getDouble("current_onshore"));
						System.out.println("Current Offshore Percentage	:"+result.getDouble("current_offshore"));
						System.out.println("Smoke Suite Size (in %)*	:"+result.getDouble("smoke_valid"));
						System.out.println("Simple Smoke Suit Test cases (in %)	:"+result.getDouble("smoke_simple"));
						System.out.println("Medium Smoke Suit Test cases (in %)	:"+result.getDouble("smoke_medium"));
						System.out.println("Complex Smoke Suit Test Cases (in %)	:"+result.getDouble("smoke_complex"));
						System.out.println("Smoke Executions/ Year*	:"+result.getInt("smoke_exe"));
						System.out.println("No. of Environments/ Browser/ OS Combination in Smoke Suit	:"+result.getInt("smoke_env_os"));
						System.out.println("Regression Suite Size (in %)*	:"+result.getDouble("regress_valid"));
						System.out.println("Simple Regression Suit Test cases (in %)	:"+result.getDouble("regress_simple"));
						System.out.println("Medium Regression Suit Test cases (in %)	:"+result.getDouble("regress_medium"));
						System.out.println("Complex Regression Suit Test Cases (in %)	:"+result.getDouble("regress_complex"));
						System.out.println("Regression Executions/ Cycle*	:"+result.getInt("regress_exe"));
						System.out.println("No. of Environments/ Browser/ OS Combination in Regression Suit	:"+result.getInt("regress_env_os"));
						System.out.println("Life Critical Suite Size (in %)*	:"+result.getDouble("life_valid"));
						System.out.println("Simple Life Critical Suit Test cases (in %)	:"+result.getDouble("life_simple"));
						System.out.println("Medium Life Critical Suit Test cases (in %)	:"+result.getDouble("life_medium"));
						System.out.println("Complex Life Critical Suit Test Cases (in %)	:"+result.getDouble("life_complex"));
						System.out.println("Life Critical Executions / Cycle*	:"+result.getInt("life_exe"));
						System.out.println("No. of Environments/ Browser/ OS Combination in Life Critical Suit	:"+result.getInt("life_env_os"));
						System.out.println("Test Design Start Date*	:"+result.getDate("design_start"));
						System.out.println("Test Design End Date*	:"+result.getDate("design_end"));
						System.out.println("Application Life in Factory (in Months)	:"+result.getInt("appln_life_factory"));
						System.out.println("Maintenance Percentage (Per Month)	:"+result.getInt("maintenance_percent"));
						System.out.println("Timestamp	:"+result.getDate("entry_date")+" "+result.getTime("entry_time"));
						System.out.println("\n____________________________________________________________________________\n");
					} 
					
				}
				
				System.out.println("");
			}			
		} 
		catch (Exception e) 
		{
			e.printStackTrace(System.out);
		}
	}
}
