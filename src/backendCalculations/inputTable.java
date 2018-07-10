package backendCalculations;

import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

public class inputTable {
	static Logger logger = Logger.getLogger(inputTable.class);
	
	//input table fields
	private String transactionID;
	private String appName;
	private String appId;
	private String appCategory;
	private String technology;
	private String automationTool;
	private String bussinessCriticality;
	private long testCases;
	private long testCasesAfterBreakdown;
	private double percentFeasible;
	private double onshore;
	private double offshore;
	private double smokePercent;
	private double smokeSimplePercent;
	private double smokeMediumPercent;
	private double smokeComplexPercent;
	private int smokeNoOfExecutions;
	private int smokeNoOfEnvironment;
	private double regressionPercent;
	private double regressionSimplePercent;
	private double regressionMediumPercent;
	private double regressionComplexPercent;
	private int regressionNoOfExecutions;
	private int regressionNoOfEnvironment;
	private double lifeCriticalPercent;
	private double lifeCriticalSimplePercent;
	private double lifeCriticalMediumPercent;
	private double lifeCriticalComplexPercent;
	private int lifeCriticalNoOfExecutions;
	private int lifeCriticalNoOfEnvironment;	
	private LocalDate designStartDate;
	private LocalDate designEndDate;
	private int applicationLife;
	private double maintenancePercent;
	private LocalDate entryDate;
	private Time entryTime;	

	//connection variable for connecting to DB
	private static Connection con = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	
	//constructor which triggers the readTable function
	public inputTable (String transactionID, String address) throws ClassNotFoundException, SQLException, IOException {
		logger.trace("entered input table constructor");
		readTable(transactionID, address);
		logger.trace("exited input table constructor");
	}
	
	//getters and setters for member variables
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppCategory() {
		return appCategory;
	}
	public void setAppCategory(String appCategory) {
		this.appCategory = appCategory;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getAutomationTool() {
		return automationTool;
	}
	public void setAutomationTool(String automationTool) {
		this.automationTool = automationTool;
	}
	public String getBussinessCriticality() {
		return bussinessCriticality;
	}
	public void setBussinessCriticality(String bussinessCriticality) {
		this.bussinessCriticality = bussinessCriticality;
	}
	public long getTestCases() {
		return testCases;
	}
	public void setTestCases(long testCases) {
		this.testCases = testCases;
	}
	public long getTestCasesAfterBreakdown() {
		return testCasesAfterBreakdown;
	}
	public void setTestCasesAfterBreakdown(long testCasesAfterBreakdown) {
		this.testCasesAfterBreakdown = testCasesAfterBreakdown;
	}
	public double getPercentFeasible() {
		return percentFeasible;
	}
	public void setPercentFeasible(double percentFeasible) {
		this.percentFeasible = percentFeasible;
	}
	public double getOnshore() {
		return onshore;
	}
	public void setOnshore(double onshore) {
		this.onshore = onshore;
	}
	public double getOffshore() {
		return offshore;
	}
	public void setOffshore(double offshore) {
		this.offshore = offshore;
	}
	public double getSmokePercent() {
		return smokePercent;
	}
	public void setSmokePercent(double smokePercent) {
		this.smokePercent = smokePercent;
	}
	public double getSmokeSimplePercent() {
		return smokeSimplePercent;
	}
	public void setSmokeSimplePercent(double smokeSimplePercent) {
		this.smokeSimplePercent = smokeSimplePercent;
	}
	public double getSmokeMediumPercent() {
		return smokeMediumPercent;
	}
	public void setSmokeMedium(double smokeMediumPercent) {
		this.smokeMediumPercent = smokeMediumPercent;
	}
	public double getSmokeComplexPercent() {
		return smokeComplexPercent;
	}
	public void setSmokeComplexPercent(double smokeComplexPercent) {
		this.smokeComplexPercent = smokeComplexPercent;
	}
	public int getSmokeNoOfExecutions() {
		return smokeNoOfExecutions;
	}
	public void setSmokeNoOfExecutions(int smokeNoOfExecutions) {
		this.smokeNoOfExecutions = smokeNoOfExecutions;
	}
	public int getSmokeNoOfEnvironment() {
		return smokeNoOfEnvironment;
	}
	public void setSmokeNoOfEnvironment(int smokeNoOfEnvironment) {
		this.smokeNoOfEnvironment = smokeNoOfEnvironment;
	}
	public double getRegressionPercent() {
		return regressionPercent;
	}
	public void setRegressionPercent(double regressionPercent) {
		this.regressionPercent = regressionPercent;
	}
	public double getRegressionSimplePercent() {
		return regressionSimplePercent;
	}
	public void setRegressionSimplePercent(double regressionSimplePercent) {
		this.regressionSimplePercent = regressionSimplePercent;
	}
	public double getRegressionMediumPercent() {
		return regressionMediumPercent;
	}
	public void setRegressionMediumPercent(double regressionMediumPercent) {
		this.regressionMediumPercent = regressionMediumPercent;
	}
	public double getRegressionComplexPercent() {
		return regressionComplexPercent;
	}
	public void setRegressionComplexPercent(double regressionComplexPercent) {
		this.regressionComplexPercent = regressionComplexPercent;
	}
	public int getRegressionNoOfExecutions() {
		return regressionNoOfExecutions;
	}
	public void setRegressionNoOfExecutions(int regressionNoOfExecutions) {
		this.regressionNoOfExecutions = regressionNoOfExecutions;
	}
	public int getRegressionNoOfEnvironment() {
		return regressionNoOfEnvironment;
	}
	public void setRegressionNoOfEnvironment(int regressionNoOfEnvironment) {
		this.regressionNoOfEnvironment = regressionNoOfEnvironment;
	}
	public double getLifeCriticalPercent() {
		return lifeCriticalPercent;
	}
	public void setLifeCriticalPercent(double lifeCriticalPercent) {
		this.lifeCriticalPercent = lifeCriticalPercent;
	}
	public double getLifeCriticalSimplePercent() {
		return lifeCriticalSimplePercent;
	}
	public void setLifeCriticalSimplPercente(double lifeCriticalSimplePercent) {
		this.lifeCriticalSimplePercent = lifeCriticalSimplePercent;
	}
	public double getLifeCriticalMediumPercent() {
		return lifeCriticalMediumPercent;
	}
	public void setLifeCriticalMediumPercent(double lifeCriticalMediumPercent) {
		this.lifeCriticalMediumPercent = lifeCriticalMediumPercent;
	}
	public double getLifeCriticalComplexPercent() {
		return lifeCriticalComplexPercent;
	}
	public void setLifeCriticalComplexPercent(double lifeCriticalComplexPercent) {
		this.lifeCriticalComplexPercent = lifeCriticalComplexPercent;
	}
	public int getLifeCriticalNoOfExecutions() {
		return lifeCriticalNoOfExecutions;
	}
	public void setLifeCriticalNoOfExecutions(int lifeCriticalNoOfExecutions) {
		this.lifeCriticalNoOfExecutions = lifeCriticalNoOfExecutions;
	}
	public int getLifeCriticalNoOfEnvironment() {
		return lifeCriticalNoOfEnvironment;
	}
	public void setLifeCriticalNoOfEnvironment(int lifeCriticalNoOfEnvironment) {
		this.lifeCriticalNoOfEnvironment = lifeCriticalNoOfEnvironment;
	}
	public LocalDate getDesignStartDate() {
		return designStartDate;
	}
	public void setDesignStartDate(LocalDate designStartDate) {
		this.designStartDate = designStartDate;
	}
	public LocalDate getDesignEndDate() {
		return designEndDate;
	}
	public void setDesignEndDate(LocalDate designEndDate) {
		this.designEndDate = designEndDate;
	}
	public int getApplicationLife() {
		return applicationLife;
	}
	public void setApplicationLife(int applicationLife) {
		this.applicationLife = applicationLife;
	}
	public LocalDate getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}
	public Time getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Time entryTime) {
		this.entryTime = entryTime;
	}
	public double getMaintenancePercent() {
		return maintenancePercent;
	}	
	public void setMaintenancePercent(double maintenancePercent) {
		this.maintenancePercent = maintenancePercent;
	}
	public static Connection getCon() {
		return con;
	}
	public static Statement getStmt() {
		return stmt;
	}
	public static ResultSet getResult() {
		return result;
	}
	
	/*
	 * Method readTable to read contents of input table stored in database and store it into 
	 * class member variables
	 */
	public void readTable(String id, String address) throws ClassNotFoundException, SQLException, IOException{
		logger.trace("Entered readTable function, with id = " + id + ", address = " + address);
		logger.info("Transaction ID = " + id);
		logger.info("DB address = " + address);
//		try {
//			Class.forName("org.hsqldb.jdbc.JDBCDriver");
//		}
//		catch(ClassNotFoundException cfe) {
//			throw new ClassNotFoundException("Unable to find class org.hsqldb.jdbc.JDBCDriver", cfe);
//		}
		
		try {
			//establish connection
			DBconnect.startConnection();
			Connection con = DBconnect.getConnection();
			stmt = con.createStatement();
			
			logger.trace("Extracting input table values from DB");
			
			ResultSet result = stmt.executeQuery("SELECT * FROM PUBLIC.APPLN_DETAILS where TRANSACTIONID = '" + id +"'");
			
			//return exception if transactionID is not unique
			if (result.getFetchSize() > 1) {
				throw new IOException("Transaction ID is not unique");
			} 

			result.next();
			//store input table data into class member variables
			this.appName = result.getString("app_name");
			this.appId = result.getString("app_id");
			this.appCategory = result.getString("appln_category");
			this.technology = result.getString("technology");
			this.automationTool = result.getString("automation_tool");
			this.bussinessCriticality = result.getString("b_criticality");
			
			this.testCases = result.getInt("cases_wells_fargo");
			this.testCasesAfterBreakdown = result.getInt("cases_breakdown");
			this.percentFeasible = result.getDouble("percentage");
			this.onshore = result.getDouble("current_onshore");
			this.offshore = result.getDouble("current_offshore");
			
			this.smokePercent = result.getDouble("smoke_valid");
			this.smokeSimplePercent = result.getDouble("smoke_simple");
			this.smokeMediumPercent = result.getDouble("smoke_medium");
			this.smokeComplexPercent = result.getDouble("smoke_complex");
			this.smokeNoOfExecutions = result.getInt("smoke_exe");
			this.smokeNoOfEnvironment = result.getInt("smoke_env_os");
			
			this.regressionPercent = result.getDouble("regress_valid");
			this.regressionSimplePercent = result.getDouble("regress_simple");
			this.regressionMediumPercent = result.getDouble("regress_medium");
			this.regressionComplexPercent = result.getDouble("regress_complex");
			this.regressionNoOfExecutions = result.getInt("regress_exe");
			this.regressionNoOfEnvironment = result.getInt("regress_env_os");
			
			this.lifeCriticalPercent = result.getDouble("life_valid");
			this.lifeCriticalSimplePercent = result.getDouble("life_simple");
			this.lifeCriticalMediumPercent = result.getDouble("life_medium");
			this.lifeCriticalComplexPercent = result.getDouble("life_complex");
			this.lifeCriticalNoOfExecutions = result.getInt("life_exe");
			this.lifeCriticalNoOfEnvironment = result.getInt("life_env_os");

			this.designStartDate = new LocalDate(result.getDate("design_start"));
			this.designEndDate = new LocalDate(result.getDate("design_end"));
			this.applicationLife = result.getInt("appln_life_factory");
			this.maintenancePercent = result.getDouble("maintenance_percent");
			this.entryTime = result.getTime("entry_time");
			this.entryDate = new LocalDate(result.getDate("entry_date"));
		}
		catch(SQLException sql) {			
			throw new SQLException("Unable to retrieve data from HSQLDB", sql);			
		}
		logger.trace("Exiting readTable function");
	}
}
