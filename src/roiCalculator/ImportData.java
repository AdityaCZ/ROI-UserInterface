
package roiCalculator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.hsqldb.jdbc.JDBCDriver;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl.AclFormatException;

import backendCalculations.DBconnect;
import backendCalculations.fetchData;

import org.hsqldb.Server;
	
	import javax.ws.rs.Consumes;
	import javax.ws.rs.POST;
	import javax.ws.rs.Path;
	import javax.ws.rs.Produces;
	import javax.ws.rs.core.MediaType;
	import javax.ws.rs.core.Response;
	
	import org.codehaus.jettison.json.JSONArray;
	import org.codehaus.jettison.json.JSONException;
	import org.codehaus.jettison.json.JSONObject;
	
	@SuppressWarnings("unused")
	@Path("/ImportData")
	public class ImportData 
	{
		public static String strResult = "yuos";
		static Server dbserver;
		static int count=0;
		
		@POST
		@Path("/ImportMethod")
		@Consumes({ MediaType.APPLICATION_JSON })
		@Produces({ MediaType.TEXT_PLAIN })
		public Response ImportMethod(String strUploadDtls) throws JSONException 
		{
			System.out.println(strUploadDtls);
			JSONObject json = new JSONObject(strUploadDtls); 
			InsertData(json,1);		
			return Response.status(201).entity(strResult).build();
		}
		
		@POST
		@Path("/ImportMethodMultiple")
		@Consumes({ MediaType.APPLICATION_JSON })
		@Produces({ MediaType.TEXT_PLAIN })
		public Response ImportMethodMultiple(String strUploadDtls) throws JSONException 
		{
			System.out.println(strUploadDtls);
			String jsonStringArray = "{     \"dataArray\"  :" +  strUploadDtls + "}" ;
	
			System.out.println(jsonStringArray);
			JSONObject json = new JSONObject(jsonStringArray); 
			JSONArray jsonArray = json.getJSONArray("dataArray");
			  for (int i = 0 ; i < jsonArray.length(); i++) 
			  {
			        JSONObject obj = jsonArray.getJSONObject(i);
					InsertData(obj,jsonArray.length());		
			  }									
			return Response.status(201).entity(strResult).build();
		}
			 
		public static void InsertData(JSONObject jo,int len)
		{
			try
			{
				/**
				 * code to establish connectivity with the HSqlDB
				 */
				//StartDb();
				
				 DBconnect.startConnection();
							
				 Connection con=DBconnect.getConnection();
				
		         Statement stmt = DBconnect.getStatement();
		         int result = stmt.executeUpdate("CREATE TABLE IF NOT EXISTS appln_details (transactionid varchar(50),app_name varchar(70) not null,app_id varchar(20) not null,appln_category varchar(80),technology varchar(40),automation_tool varchar(25),b_criticality varchar(40),cases_wells_fargo int,cases_breakdown int,percentage NUMERIC(5,2),current_onshore NUMERIC(5,2),current_offshore NUMERIC(5,2),smoke_valid NUMERIC(5,2),smoke_simple NUMERIC(5,2),smoke_medium NUMERIC(5,2),smoke_complex NUMERIC(5,2),smoke_exe int,smoke_env_os int,regress_valid NUMERIC(5,2),regress_simple NUMERIC(5,2),regress_medium NUMERIC(5,2),regress_complex NUMERIC(5,2),regress_exe int,regress_env_os int,life_valid NUMERIC(5,2),life_simple NUMERIC(5,2),life_medium NUMERIC(5,2),life_complex NUMERIC(5,2),life_exe int,life_env_os int,design_start date,design_end date not null,appln_life_factory int,maintenance_percent NUMERIC(5,2),entry_date date,entry_time time,primary key (transactionid));");
	
			    /**
		    	 * storing the details for "appln_details"
		    	 */	        
		        JSONObject app_det = jo.getJSONObject("Application_Details");
		         
		    	String appln_name = app_det.getString("Application_Name");
				String appln_id = app_det.getString("Application_ID");
		    	String technology = app_det.getString("Technology");
				String b_criticality = app_det.getString("Business_Criticality");
				String auto_tool= app_det.getString("Automation_Tool");
				String appln_category = app_det.getString("Application_Category");
				
				/**
				 * storing the details for "complexity_breakdown"
				 */
				
				JSONObject test = jo.getJSONObject("Test_Case_Counts");
										
				int wells_cases=test.getInt("Cases_by_Wells_Fargo");
				int break_cases=test.getInt("Cases_after_breakdown");
				double feasible=test.getDouble("Percentage_of_feasible_cases");
				double onshore =test.getDouble("Current_Onshore_Percentage");
				double offshore=test.getDouble("Current_Offshore_Percentage");
												 
				/**
				 * storing the details for "release_details"
				 */
				
				JSONObject release =jo.getJSONObject("Release_Details");
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
				java.util.Date d = sdf.parse((String) release.get("Test_Design_Start_Date"));
		        java.sql.Date design_start = new java.sql.Date(d.getTime());
		        d = sdf.parse((String) release.get("Test_Design_End_Date"));
		        java.sql.Date design_end = new java.sql.Date(d.getTime());	       
		        int appln_life_factory=release.getInt("Application_Life_in_Factory");
				double maintenance_percent=release.getDouble("Maintenance_Percentage");
				
				JSONObject suite = (JSONObject) jo.getJSONObject("Test_Suites");
				JSONObject smoke = (JSONObject) suite.getJSONObject("Smoke_Suite");
				
				/**
				 * storing the details for "test_suites.smoke_suit"
				 */
				
				double valid_s=smoke.getDouble("Size");
				double simple_s=smoke.getDouble("Simple_Percentage");
				double medium_s=smoke.getDouble("Medium_Percentage");
				double complex_s=smoke.getDouble("Complex_Percentage");
				int on_s=smoke.getInt("Executions_per_Year");
				int off_s=smoke.getInt("Environment/OS/Browser");
				
				/**
				 * storing the details for "test_suites.regression_suit"
				 */
				JSONObject regress = suite.getJSONObject("Regeression_Suite");
				
				double valid_r=regress.getDouble("Size");
				double simple_r=regress.getDouble("Simple_Percentage");
				double medium_r=regress.getDouble("Medium_Percentage");
				double complex_r=regress.getDouble("Complex_Percentage");
				int on_r=regress.getInt("Executions_per_Year");
				int off_r=regress.getInt("Environment/OS/Browser");
				
				/**
				 * storing the details for "test_suites.lifecycle_suit"
				 */
				
				JSONObject life =  suite.getJSONObject("Life_Critical_Suite");
				
				double valid_l=life.getDouble("Size");
				double simple_l=life.getDouble("Simple_Percentage");
				double medium_l=life.getDouble("Medium_Percentage");
				double complex_l=life.getDouble("Complex_Percentage");
				int on_l=life.getInt("Executions_per_Year");
				int off_l=life.getInt("Environment/OS/Browser");
				
				
				/**
				 * entry date and time
				 */
	           LocalDate localDate = LocalDate.now();
	           java.sql.Date x=java.sql.Date.valueOf(localDate);
	           
	       
	           String y1 = DateTimeFormatter.ofPattern("hh:mm:ss.SSS a").format(LocalTime.now());
	           LocalTime y = LocalTime.parse(y1, DateTimeFormatter.ofPattern("hh:mm:ss.SSS a"));
	          			
	           /**
	            * Creating unique Identifier
	            * idno.length()=36
	            */   
	           String idno=UUID.randomUUID().toString();   		  
	           String id=""+appln_name;
	           if(id.length()>4)
	        	   id=id.substring(0, 3);           
	           String date=""+localDate.getDayOfMonth();
	           date=date+y.getHour()+y.getMinute()+String.valueOf(y.getNano()).substring(0, 4);
	           id=id.concat(date);          
	           id=id+"-"+idno.substring(0,8);
				
	           /**
	            * inserting details into table "appln_details"
	            */
	    		String sql="INSERT INTO appln_details (transactionid,app_name,app_id,appln_category,technology,automation_tool,b_criticality,cases_wells_fargo,cases_breakdown,percentage,current_onshore,current_offshore,smoke_valid,smoke_simple,smoke_medium,smoke_complex,smoke_exe,smoke_env_os,regress_valid,regress_simple,regress_medium,regress_complex,regress_exe,regress_env_os,life_valid,life_simple,life_medium,life_complex,life_exe,life_env_os,design_start,design_end,appln_life_factory,maintenance_percent,entry_date,entry_time) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement pst=con.prepareStatement(sql);
				pst.setString(1,id);			
				pst.setString(2,appln_name);
				pst.setString(3,appln_id);
				pst.setString(4,appln_category);
				pst.setString(5,technology);			
				pst.setString(6, auto_tool);
				pst.setString(7,b_criticality);
				pst.setInt(8,wells_cases);
				pst.setInt(9,break_cases);
				pst.setDouble(10,feasible);
				pst.setDouble(11,onshore);
				pst.setDouble(12,offshore);			
				pst.setDouble(13,valid_s);
				pst.setDouble(14,simple_s);
				pst.setDouble(15,medium_s);
				pst.setDouble(16,complex_s);
				pst.setInt(17,on_s);
				pst.setInt(18,off_s);
				pst.setDouble(19,valid_r);
				pst.setDouble(20,simple_r);
				pst.setDouble(21,medium_r);
				pst.setDouble(22,complex_r);
				pst.setInt(23,on_r);
				pst.setInt(24,off_r);
				pst.setDouble(25,valid_l);
				pst.setDouble(26,simple_l);
				pst.setDouble(27,medium_l);
				pst.setDouble(28,complex_l);
				pst.setInt(29,on_l);
				pst.setInt(30,off_l);
				pst.setDate(31,design_start);
				pst.setDate(32,design_end);
				pst.setInt(33,appln_life_factory);
				pst.setDouble(34,maintenance_percent); 
				pst.setDate(35, x);
				pst.setTime(36,java.sql.Time.valueOf(y));
			
				int i = pst.executeUpdate();
				
				DBconnect.DbCommit();
				
				fetchData calculateOutput = new fetchData();	
				
				calculateOutput.calculate(id, "jdbc:hsqldb:hsql://localhost/testdb");
				if(i>0)
					count=count+1;
				
//				if(count==len)
//					DBconnect.shutdown();
//				
//				if(!DBconnect.openConnection())
//					System.out.println("Closed");
				
				
				System.out.println("6");
			}
			catch(Exception e)
			{
				e.printStackTrace(System.out);
			}			
		}			 
	}
	


//package roiCalculator;
//import backendCalculations.fetchData;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Random;
//
//import org.hsqldb.jdbc.JDBCDriver;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import org.codehaus.jettison.json.JSONArray;
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;
//
//
////3. DASHBOARD - IMPORT DATA
//@SuppressWarnings("unused")
//@Path("/ImportData")
//public class ImportData 
//{
//	public static String strResult = "yuos";
//	//public static String strHost = "LocalHost";
//	
//	@POST
//	@Path("/ImportMethod")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.TEXT_PLAIN })
//	public Response ImportMethod(String strUploadDtls) throws JSONException 
//	{
//		//String noJson[] = strUploadDtls.split("\"");
//		System.out.println(strUploadDtls);
//		JSONObject json = new JSONObject(strUploadDtls); 
//		InsertData(json);		
//		return Response.status(201).entity(strResult).build();
//	}
//	
//	@POST
//	@Path("/ImportMethodMultiple")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.TEXT_PLAIN })
//	public Response ImportMethodMultiple(String strUploadDtls) throws JSONException 
//	{
//		//String noJson[] = strUploadDtls.split("\"");
//		System.out.println(strUploadDtls);
//		String jsonStringArray = "{     \"dataArray\"  :" +  strUploadDtls + "}" ;
//
//		System.out.println(jsonStringArray);
//		JSONObject json = new JSONObject(jsonStringArray); 
//		JSONArray jsonArray = json.getJSONArray("dataArray");
//		  for (int i = 0 ; i < jsonArray.length(); i++) {
//		        JSONObject obj = jsonArray.getJSONObject(i);
//				InsertData(obj);		
//		    }		
//		return Response.status(201).entity(strResult).build();
//	}
//
//	
//	public static void InsertData(JSONObject jo)
//	{
//		try
//		{
//			/**
//			 * code to establish connectivity with the HSqlDB
//			 */
//			
//			Connection con=null;
//		
//	        Class.forName("org.hsqldb.jdbc.JDBCDriver");   
//	        
//	        //URL="jdbc:hsqldb:hsql://localhost/testdb", User=SA, Password="", 
//	        
//	        con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", ""); 
//	        
//			//Table Creation
//	    	 Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
//	        	     	     
//	        /**
//	         * to commit all changes when data is inserted into all tables
//	         */
//	         con.setAutoCommit(false);
//
//	         stmt = con.createStatement();
//	         int result = stmt.executeUpdate("CREATE TABLE IF NOT EXISTS appln_details (transactionid varchar(30),app_name varchar(70) not null,app_id varchar(20) not null,appln_category varchar(80),technology varchar(40),automation_tool varchar(25),b_criticality varchar(40),cases_wells_fargo int,cases_breakdown int,percentage NUMERIC(5,2),current_onshore NUMERIC(5,2),current_offshore NUMERIC(5,2),smoke_valid NUMERIC(5,2),smoke_simple NUMERIC(5,2),smoke_medium NUMERIC(5,2),smoke_complex NUMERIC(5,2),smoke_exe int,smoke_env_os int,regress_valid NUMERIC(5,2),regress_simple NUMERIC(5,2),regress_medium NUMERIC(5,2),regress_complex NUMERIC(5,2),regress_exe int,regress_env_os int,life_valid NUMERIC(5,2),life_simple NUMERIC(5,2),life_medium NUMERIC(5,2),life_complex NUMERIC(5,2),life_exe int,life_env_os int,design_start date,design_end date not null,appln_life_factory int,maintenance_percent NUMERIC(5,2),entry_date date,entry_time time,primary key (transactionid));");
//
//	       	
//	        //quality_cases varchar(9),need varchar(9),test_availability varchar(9),env_availability varchar(9),SME_support varchar(9),appln_change varchar(9),defect_mgmt varchar(9),
//	      		                 
//	    	/**
//	    	 * storing the details for "appln_details"
//	    	 */
//	        
//	         JSONObject app_det = jo.getJSONObject("Application_Details");
//	         
//	    	String appln_name = app_det.getString("Application_Name");
//			String appln_id=app_det.getString("Application_ID");
//	    	String technology = app_det.getString("Technology");
//			String b_criticality =app_det.getString("Business_Criticality");
//			String auto_tool=app_det.getString("Automation_Tool");
//			String appln_category=app_det.getString("Application_Category");
//			
//			//storing the details for "factory_acceptance"
//		/*	 JSONObject factory =  jo.getJSONObject("Factory_Acceptance_Criteria");
//			
//			String need=factory.getString("Environment_Configuration");
//			String test_availability=factory.getString("Test_Data_Availability");
//			String env_availability=factory.getString("Environment_Availability");
//			String SME_support=factory.getString("SME_Support");
//			String appln_change=factory.getString("Application_Changes");
//			String quality_cases=factory.getString("Quality_of_Manual_Test_Cases");
//			String defect_mgmt=factory.getString("Defect_management");
//		*/							
//			/**
//			 * storing the details for "complexity_breakdown"
//			 */
//			
//			JSONObject test = jo.getJSONObject("Test_Case_Counts");
//									
//			int wells_cases=test.getInt("Cases_by_Wells_Fargo");
//			int break_cases=test.getInt("Cases_after_breakdown");
//			double feasible=test.getDouble("Percentage_of_feasible_cases");
//			double onshore=test.getDouble("Current_Onshore_Percentage");
//			double offshore=test.getDouble("Current_Offshore_Percentage");
//											 
//			/**
//			 * storing the details for "release_details"
//			 */
//			
//			JSONObject release =jo.getJSONObject("Release_Details");
//			
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
//			java.util.Date d = sdf.parse((String) release.get("Test_Design_Start_Date"));
//	        java.sql.Date design_start = new java.sql.Date(d.getTime());
//	        d = sdf.parse((String) release.get("Test_Design_End_Date"));
//	        java.sql.Date design_end = new java.sql.Date(d.getTime());	       
//	        int appln_life_factory=release.getInt("Application_Life_in_Factory");
//			double maintenance_percent=release.getDouble("Maintenance_Percentage");
//			
//			JSONObject suite = (JSONObject) jo.getJSONObject("Test_Suites");
//			JSONObject smoke = (JSONObject) suite.getJSONObject("Smoke_Suit");
//			
//			/**
//			 * storing the details for "test_suites.smoke_suit"
//			 */
//			
//			double valid_s=smoke.getDouble("Size");
//			double simple_s=smoke.getDouble("Simple_Percentage");
//			double medium_s=smoke.getDouble("Medium_Percentage");
//			double complex_s=smoke.getDouble("Complex_Percentage");
//			int on_s=smoke.getInt("Executions_per_Year");
//			int off_s=smoke.getInt("Environment/OS/Browser");
//			
//			/**
//			 * storing the details for "test_suites.regression_suit"
//			 */
//			JSONObject regress = suite.getJSONObject("Regeression_Suit");
//			
//			double valid_r=regress.getDouble("Size");
//			double simple_r=regress.getDouble("Simple_Percentage");
//			double medium_r=regress.getDouble("Medium_Percentage");
//			double complex_r=regress.getDouble("Complex_Percentage");
//			int on_r=regress.getInt("Executions_per_Year");
//			int off_r=regress.getInt("Environment/OS/Browser");
//			
//			/**
//			 * storing the details for "test_suites.lifecycle_suit"
//			 */
//			
//			JSONObject life =  suite.getJSONObject("Life_Critical_Suit");
//			
//			double valid_l=life.getDouble("Size");
//			double simple_l=life.getDouble("Simple_Percentage");
//			double medium_l=life.getDouble("Medium_Percentage");
//			double complex_l=life.getDouble("Complex_Percentage");
//			int on_l=life.getInt("Executions_per_Year");
//			int off_l=life.getInt("Environment/OS/Browser");
//			
//			
//			/**
//			 * entry date and time
//			 */
//           LocalDate localDate = LocalDate.now();
//           java.sql.Date x=java.sql.Date.valueOf(localDate);
//           
//       
//           String y1 = DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now());
//           LocalTime y = LocalTime.parse(y1, DateTimeFormatter.ofPattern("hh:mm a"));
//			
//           /**
//            * Creating unique Identifier
//            */
//           Random rand=new Random();
//           int idno=rand.nextInt();
//           if(idno==0) 
//        	   idno=rand.nextInt();
//           if(idno<0)
//        	   idno=idno*-1;
//           String id=""+appln_name;
//           if(id.length()>4)
//        	   id=id.substring(0, 3);
//           id=id+idno;
//           if(id.length()>7)
//        	   id=id.substring(0, 6);
//           String date=""+localDate.getDayOfMonth();
//           date=date+y.getHour()+y.getMinute()+y.getSecond();
//           id=id.concat(date);
//			
//           /**
//            * inserting details into table "appln_details"
//            */
//           
//           //quality_cases,need,test_availability,env_availability,sme_support,appln_change,defect_mgmt,
//           
//			String sql="INSERT INTO appln_details (transactionid,app_name,app_id,appln_category,technology,automation_tool,b_criticality,cases_wells_fargo,cases_breakdown,percentage,current_onshore,current_offshore,smoke_valid,smoke_simple,smoke_medium,smoke_complex,smoke_exe,smoke_env_os,regress_valid,regress_simple,regress_medium,regress_complex,regress_exe,regress_env_os,life_valid,life_simple,life_medium,life_complex,life_exe,life_env_os,design_start,design_end,appln_life_factory,maintenance_percent,entry_date,entry_time) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//			PreparedStatement pst=con.prepareStatement(sql);
//			pst.setString(1,id);			
//			pst.setString(2,appln_name);
//			pst.setString(3,appln_id);
//			pst.setString(4,appln_category);
//			pst.setString(5,technology);			
//			pst.setString(6, auto_tool);
//			pst.setString(7,b_criticality);
//		/*	pst.setString(7,quality_cases);
//			pst.setString(8,need);
//			pst.setString(9,test_availability);
//			pst.setString(10,env_availability);
//			pst.setString(11,SME_support);
//			pst.setString(12,appln_change);
//			pst.setString(13,defect_mgmt);		*/
//			pst.setInt(8,wells_cases);
//			pst.setInt(9,break_cases);
//			pst.setDouble(10,feasible);
//			pst.setDouble(11,onshore);
//			pst.setDouble(12,offshore);			
//			pst.setDouble(13,valid_s);
//			pst.setDouble(14,simple_s);
//			pst.setDouble(15,medium_s);
//			pst.setDouble(16,complex_s);
//			pst.setInt(17,on_s);
//			pst.setInt(18,off_s);
//			pst.setDouble(19,valid_r);
//			pst.setDouble(20,simple_r);
//			pst.setDouble(21,medium_r);
//			pst.setDouble(22,complex_r);
//			pst.setInt(23,on_r);
//			pst.setInt(24,off_r);
//			pst.setDouble(25,valid_l);
//			pst.setDouble(26,simple_l);
//			pst.setDouble(27,medium_l);
//			pst.setDouble(28,complex_l);
//			pst.setInt(29,on_l);
//			pst.setInt(30,off_l);
//			pst.setDate(31,design_start);
//			pst.setDate(32,design_end);
//			//pst.setDate(32,exe_end);
//			pst.setInt(33,appln_life_factory);
//			pst.setDouble(34,maintenance_percent); 
//			pst.setDate(35, x);
//			pst.setTime(36,java.sql.Time.valueOf(y));
//		
//			int i = pst.executeUpdate();
//			
//			
//			//System.out.println(id);
//			
//			con.commit();
//			//System.out.println(id);
//			fetchData calculateOutput = new fetchData();	
//			
//			calculateOutput.calculate(id, "jdbc:hsqldb:hsql://localhost/testdb");
//			
//			//if(i>0)
//			{
//				System.out.println(appln_id);			
//			}		
//			//return Response.status(201).entity(strResult).build();
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace(System.out);
//		}
//		
//	}
//}
//
