package roiCalculator;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.hsqldb.jdbc.JDBCDriver;

import backendCalculations.DBconnect;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@SuppressWarnings("unused")
@Path("/DisplayData")
public class UIDisplay 
{
	public static Connection con = null;
	public static Statement stmt = null;
	public static ResultSet result = null;

	@POST
	@Path("/getTeansId")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_PLAIN })	
	public Response Transid() throws SQLException, ClassNotFoundException, UnsupportedEncodingException
	{
		Class.forName("org.hsqldb.jdbc.JDBCDriver");   
		DBconnect.startConnection();
		 con = DBconnect.getConnection();
		 stmt = con.createStatement();

		String sql="SELECT transactionid FROM appln_details";
		ResultSet result1=stmt.executeQuery(sql);
		String ids = "";
		result1.next();
			ids = result1.getString("transactionid");
			System.out.println(ids);
		return Response.status(201).entity(ids).build();
	}
	@POST
	@Path("/exportData")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_PLAIN })
	public Response Details(String id) throws SQLException, JSONException, ClassNotFoundException, UnsupportedEncodingException
	{
		Class.forName("org.hsqldb.jdbc.JDBCDriver");   
		DBconnect.startConnection();
		 con = DBconnect.getConnection();
		 stmt = con.createStatement();

			JSONObject json = new JSONObject(); 
		//System.out.println(id.split("\"")[3]);
		String id1 = id.split("\"")[3];
		//id1 = "Test92191720";
		//System.out.println("SELECT * FROM PUBLIC.APPLN_DETAILS where TRANSACTIONID = '" + id1 +"'");
		ResultSet result = stmt.executeQuery("SELECT * FROM PUBLIC.APPLN_DETAILS where TRANSACTIONID = '" + id1 +"'");		
		System.out.println(result);
		while(result.next())
		{
			json.append("app_name", result.getString("app_name"));
			json.append("app_id", result.getString("app_id"));
			json.append("appln_category", result.getString("appln_category"));
			json.append("technology", result.getString("technology"));
			json.append("automation_tool", result.getString("automation_tool"));
			json.append("b_criticality", result.getString("b_criticality"));
			
			json.append("cases_wells_fargo", result.getInt("cases_wells_fargo"));
			json.append("cases_breakdown", result.getInt("cases_breakdown"));
			json.append("percentage", result.getInt("percentage"));
			json.append("current_onshore", result.getInt("current_onshore"));
			json.append("current_offshore", result.getInt("current_offshore"));
			
			json.append("smoke_valid", result.getInt("smoke_valid"));
			json.append("smoke_simple", result.getInt("smoke_simple"));
			json.append("smoke_medium", result.getInt("smoke_medium"));
			json.append("smoke_complex", result.getInt("smoke_complex"));
			json.append("smoke_exe", result.getInt("smoke_exe"));
			json.append("smoke_env_os", result.getInt("smoke_env_os"));
			
			json.append("regress_valid", result.getInt("regress_valid"));
			json.append("regress_simple", result.getInt("regress_simple"));
			json.append("regress_medium", result.getInt("regress_medium"));
			json.append("regress_complex", result.getInt("regress_complex"));
			json.append("regress_exe", result.getInt("regress_exe"));
			json.append("regress_env_os", result.getInt("regress_env_os"));
			
			json.append("life_valid", result.getInt("life_valid"));
			json.append("life_simple", result.getInt("life_simple"));
			json.append("life_medium", result.getInt("life_medium"));
			json.append("life_complex", result.getInt("life_complex"));
			json.append("life_exe", result.getInt("life_exe"));
			json.append("life_env_os", result.getInt("life_env_os"));
			
			json.append("design_start", result.getString("design_start"));
			json.append("design_end", result.getString("design_end"));
			json.append("appln_life_factory", result.getInt("appln_life_factory"));
			json.append("maintenance_percent", result.getInt("maintenance_percent"));
			json.append("entry_time", result.getString("entry_time"));
			
			}
		
		
		
		
		return Response.status(201).entity(json.toString()).build();			
	}
}
