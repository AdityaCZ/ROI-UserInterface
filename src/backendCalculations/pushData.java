package backendCalculations;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class pushData {
	public static void main(String[] args) throws SQLException{
		Connection conn;
		String con="jdbc:hsqldb:hsql://localhost/"+"testdb";


		try {
     	conn = DriverManager.getConnection(con,"sa","");    

		}
		catch(Exception e) {
			System.out.println("sdfsdf");
		}
		conn = DriverManager.getConnection(con,"sa",""); 
		//Table Creation
   	 Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       	    	     
       /**
        * to commit all changes when data is inserted into all tables
        */
        conn.setAutoCommit(false);

        stmt = conn.createStatement();
        int result = stmt.executeUpdate("CREATE TABLE IF NOT EXISTS appln_details (transactionid varchar(30),app_name varchar(70) not null,app_id varchar(20) not null,appln_category varchar(80),technology varchar(40),automation_tool varchar(25),b_criticality varchar(40),cases_wells_fargo int,cases_breakdown int,percentage NUMERIC(5,2),current_onshore NUMERIC(5,2),current_offshore NUMERIC(5,2),smoke_valid NUMERIC(5,2),smoke_simple NUMERIC(5,2),smoke_medium NUMERIC(5,2),smoke_complex NUMERIC(5,2),smoke_exe int,smoke_env_os int,regress_valid NUMERIC(5,2),regress_simple NUMERIC(5,2),regress_medium NUMERIC(5,2),regress_complex NUMERIC(5,2),regress_exe int,regress_env_os int,life_valid NUMERIC(5,2),life_simple NUMERIC(5,2),life_medium NUMERIC(5,2),life_complex NUMERIC(5,2),life_exe int,life_env_os int,design_start date,design_end date not null,appln_life_factory int,maintenance_percent NUMERIC(5,2),entry_date date,entry_time time,primary key (transactionid));");
        System.out.println(result);
    	String appln_name = "1";
		String appln_id="2";
    	String technology = "3";
		String b_criticality ="4";
		String auto_tool="5";
		String appln_category="6";
						
		/**
		 * storing the details for "complexity_breakdown"
		 */
		
		Object[] wells_cases= new Object[]{1,2,3,4,5,6,7,8,9,20};
		Array arr = conn.createArrayOf("NUMERIC", wells_cases);
		int break_cases=8;
		double feasible=9;
		double onshore=10;
		double offshore=11;
										 
		/**
		 * storing the details for "release_details"
		 */
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		java.util.Date d = new java.util.Date();
        java.sql.Date design_start = new java.sql.Date(d.getTime());
        d = new java.util.Date();
        java.sql.Date design_end = new java.sql.Date(d.getTime());	       
        int appln_life_factory=12;
		double maintenance_percent=13;
		
		/**
		 * storing the details for "test_suites.smoke_suit"
		 */
		
		double valid_s=20;
		double simple_s=21;
		double medium_s=22;
		double complex_s=23;
		int on_s=24;
		int off_s=25;
		
		/**
		 * storing the details for "test_suites.regression_suit"
		 */
		
		double valid_r=26;
		double simple_r=27;
		double medium_r=28;
		double complex_r=29;
		int on_r=30;
		int off_r=31;
		
		/**
		 * storing the details for "test_suites.lifecycle_suit"
		 */
		
		double valid_l=32;
		double simple_l=33;
		double medium_l=34;
		double complex_l=35;
		int on_l=36;
		int off_l=37;
		
		
		/**
		 * entry date and time
		 */
       LocalDate localDate = LocalDate.now();
       java.sql.Date x=java.sql.Date.valueOf(localDate);
       
   
       String y1 = DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now());
       LocalTime y = LocalTime.parse(y1, DateTimeFormatter.ofPattern("hh:mm a"));
		
       /**
        * Creating unique Identifier
        */
       Random rand=new Random();
       int idno=rand.nextInt();
       if(idno==0)
    	   idno=rand.nextInt();
       if(idno<0)
    	   idno=idno*-1;
       String id=""+appln_name;
       if(id.length()>4)
    	   id=id.substring(0, 3);
       id=id+idno;
       if(id.length()>7)
    	   id=id.substring(0, 6);
       String date=""+localDate.getDayOfMonth();	//current date(timestamp)
       date=date+y.getHour()+y.getMinute()+y.getSecond(); 	//current
       id=id.concat(date);
		
       /**
        * inserting details into table "appln_details"
        */
        
		String sql="INSERT INTO appln_details (transactionid,app_name,app_id,appln_category,technology,automation_tool,b_criticality,cases_wells_fargo,cases_breakdown,percentage,current_onshore,current_offshore,smoke_valid,smoke_simple,smoke_medium,smoke_complex,smoke_exe,smoke_env_os,regress_valid,regress_simple,regress_medium,regress_complex,regress_exe,regress_env_os,life_valid,life_simple,life_medium,life_complex,life_exe,life_env_os,design_start,design_end,appln_life_factory,maintenance_percent,entry_date,entry_time) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1,id);			
		pst.setString(2,appln_name);
		pst.setString(3,appln_id);
		pst.setString(4,appln_category);
		pst.setString(5,technology);			
		pst.setString(6, auto_tool);
		pst.setString(7,b_criticality);
		pst.setArray(8,arr);
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
		
		conn.commit();
	}
}
