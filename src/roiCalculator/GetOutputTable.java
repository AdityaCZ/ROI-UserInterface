package roiCalculator;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;

import org.hsqldb.jdbc.JDBCDriver;



import backendCalculations.DBconnect;
import backendCalculations.outputTable;


@SuppressWarnings("unused")
@Path("/outputTable")
public class GetOutputTable 
{
	public static Connection con = null;
	public static Statement stmt = null;
	public static ResultSet result = null;

	@POST
	@Path("/getOutputTable")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_PLAIN })
	public Response Details(String id) throws SQLException, ClassNotFoundException, UnsupportedEncodingException
	{
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		DBconnect.startConnection();
		 con = DBconnect.getConnection();
		 stmt = con.createStatement();
 
		String data = "";
		String id1 = id.split("\"")[3];
		System.out.println(id1);
		
		PreparedStatement pstmt = con.prepareStatement("SELECT output FROM output_table WHERE transactionid = '"+ id1 +"'");

		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Object deSerializedObject = rs.getObject(1); 
		JSONObject json = (JSONObject) deSerializedObject;

		ResultSet resultInputTable = stmt.executeQuery("SELECT * FROM PUBLIC.APPLN_DETAILS where TRANSACTIONID = '" + id1 +"'");		
		resultInputTable.next();

		json.put("app_name", resultInputTable.getString("app_name"));
		json.put("app_id", resultInputTable.getString("app_id"));
		json.put("appln_category", resultInputTable.getString("appln_category"));
			
		System.out.println(json.toString()); 
		data = json.toString();		
		return Response.status(201).entity(data).build();			
	}
}
