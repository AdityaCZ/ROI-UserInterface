package backendCalculations;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

public class globalParameters {
	
	static Logger logger = Logger.getLogger(globalParameters.class);
	
	private final int simpleManualProductivity = 24;
	private final int mediumManualProductivity = 12;
	private final int complexManualProductivity = 8;
	private final  double yearOnePriceReductionFraction = 0;
	private final double yearTwoPriceReductionFraction = 0.05;
	private final double yearThreePriceReductionFraction = 0.08;
	private final double devEffortFactorPerEnvironment = 0.25;
	private LocalDate factoryStartDate;
	private double maintenancePercentInFirstTwoYears;
	private double maintenancePercentInThirdYear;
	private final double wellsGovernance = 0.05;
	private final double cognizantGovernance = 0.05;
	private final double blendedRate = 50; //(dollar/hr)
	private double[][] unitPrice;
	
	/**
	 * Function to choose the category,technology,complexity of the GlobalParameters
	 * 
	 * returns a String array of category,technology,complexity of the GlobalParameters
	 */
	private String[] getValues(int i, int j) {
		logger.trace("Entered getValues method with i = " + Integer.toString(i) + ", j = " + Integer.toString(j));
		String category;
		String technology;
		String complexity;
		String activity;
		
		//technology
		if(i%5 == 0) {
			technology = "Web";
		}else {
			if(i%5 == 1) {
				technology = "Thick Client/ Mobile";
			}else {
				if(i%5 == 2) {
					technology = "IVR";
				}
				else {
					if(i%5 == 3) {
						technology = "Web Services";
					}
					else {
						technology = "Mainframe";
					}
				}				
			}			
		}
		
		//category
		if(j < 3) {
			category = "Never Automated";
		}else {
			if(j < 6) {
				category = "Tool Migration";
			}else {
				if(j < 9) {
					category = "Framework Migration";
				}
				else {
					if(j < 12) {
						category = "Optimization";
					}
					else {
						category = "Componentization";
					}
				}				
			}			
		}					
					
		//complexity
		if(j%3 == 0) {
			complexity = "Simple";
		}else {
			if(j%3 == 1) {
				complexity = "Medium";
			}else {
				complexity = "Complex";
			}
			
		}
		
		//activity
		if(i<5) {
			activity = "Development";
		}
		else {
			if(i<10) {
				activity = "Execution";
			}
			else {
				activity = "Maintenance";
			}
		}
		String[] str = {category, technology, complexity, activity};
		
		logger.trace("Exiting getValues method");
		return str;
	}
	
	/**
	 * Constructor that initializes the values and inserts the values into database by using unitPrice object
	 * @param maintenance
	 * @throws IOException 
	 */
	
	public globalParameters(double maintenance) throws IOException, SQLException {
		logger.trace("Entering globalParameters class constructor with maintenance = " + Double.toString(maintenance));
		
		//factory start date is 1st April
		this.factoryStartDate = new LocalDate(2018,4,1);
		
		//fill maintenance percent values
		this.maintenancePercentInFirstTwoYears = maintenance;
		this.maintenancePercentInThirdYear = maintenance * 1.3;
		
		//default values of unitPrice to be filled
		/*	
	 	this.unitPrice = new double[][] 
		{
			{101.82, 140, 224, 101.82, 140, 224, 67.88, 93.33, 149.33, 10.18, 14.93, 22.4, 80, 101.82, 112},
			{112, 149.33, 224, 112, 149.33, 224, 86.15, 114.87, 172.31, 10.18, 14.93, 22.4, 80, 101.82, 112},
			{64, 74.67, 124.44, 64, 74.67, 124.44, 49.23, 57.44, 95.73, 10.18, 14.93, 22.4, 80, 101.82, 112},
			{28, 44.8, 112, 28, 44.8, 112, 28, 44.8, 112, 10.18, 14.93, 22.4, 80, 101.82, 112},
			{112, 149.33, 224, 112, 149.33, 224, 86.5, 114.87, 172.31, 10.18, 14.93, 22.4, 80, 101.82, 112},
			{1.72, 2.04, 2.8, 1.72, 2.04, 2.8, 1.72, 2.04, 2.8, 1.72, 2.04, 2.8, 1.72, 2.04, 2.8},
			{3.2, 4.07, 5.6, 3.2, 4.07, 5.6, 3.2, 4.07, 5.6, 3.2, 4.07, 5.6, 3.2, 4.07, 5.6},
			{1.6, 1.87, 2.8, 1.6, 1.87, 2.8, 1.6, 1.87, 2.8, 1.6, 1.87, 2.8, 1.6, 1.87, 2.8},
			{2.24, 2.8, 3.73, 1.83, 1.25, 1.75, 5.73, 1.25, 1.75, 2.24, 2.8, 3.73, 2.24, 2.8, 3.73},
			{2.8, 3.45, 4.98, 2.8, 3.45, 4.98, 2.8, 3.45, 4.98, 2.8, 3.45, 4.98, 2.8, 3.45, 4.98},
			{64, 81, 128, 64, 81, 128, 64, 81, 128, 64, 81, 128, 64, 81, 128 },
			{66, 90, 132, 66, 90, 132, 66, 90, 132, 66, 90, 132, 66, 90, 132},
			{64, 75, 124, 64, 75, 124, 64, 75, 124, 64, 75, 124, 64, 75, 124},
			{16, 22, 56, 16, 22, 56, 16, 22, 56, 16, 22, 56, 16, 22, 56},
			{66, 90, 128, 66, 90, 128, 66, 90, 128, 66, 90, 128, 66, 90, 128}
		};
		*/	
		
		/*
		 *Development, execution and maintenance prices can be specified here and insertParam function calls below can be uncommented to 
		 *insert these values in to DB
		 */
		unitPrice=new double[15][15];
		double[][] userDevelopment={{101.818182, 140, 224, 101.818182, 140, 224, 67.878788, 93.333333, 149.333333, 10.181818, 14.933333, 22.4, 80, 101.818182, 112},
				{112, 149.333333, 224, 112, 149.333333, 224, 86.153846, 114.871795, 172.307692, 10.181818, 14.933333, 22.4, 80, 101.818182, 112},
				{64, 74.666667, 124.444444, 64, 74.666667, 124.444444, 49.230769, 57.435897, 95.726496, 10.181818, 14.933333, 22.4, 80, 101.818182, 112},
				{28, 44.8, 112, 28, 44.8, 112, 28, 44.8, 112, 10.181818, 14.933333, 22.4, 80, 101.818182, 112},
				{112, 149.333333, 224, 112, 149.333333, 224, 86.153846, 114.871795, 172.307692, 10.18, 14.93, 22.4, 80, 101.818182, 112},};
		double[][] userExecution={{1.723077, 2.036364, 2.8},{3.2, 4.072727, 5.6},{1.6, 1.866667, 2.8},{2.24, 2.8, 3.733333},{2.8, 3.446154, 4.977778}};
		double[][] userMaintenance={{64, 81.454545, 128},{65.882353, 89.6, 131.764706},{64, 74.666667, 124.444444},{16, 22.4, 56},{65.882353, 89.6, 128}};		
		
		//Passing the above arrays to ArrayCreator to create a 15x15 array 
		unitPrice=ArrayCreator(userDevelopment,userDevelopment.length,userDevelopment[0].length,userExecution,
					userExecution.length,userExecution[0].length,userMaintenance,userMaintenance.length,userMaintenance[0].length);	
				
		double[][] unitPrice1=new double[15][15];
		String category;
		String technology;
		String complexity;
		
		//creation of unitPrice object 
		unitPrice price = new unitPrice();

		//values to denote the table where data should be entered
		int executionunit=0,maintenanceunit=1,developmentunit=2;
		try 
		{
			//Development Unit Price
			for(int i=0;i<5;i++)
			{
				for(int j=0;j<unitPrice[0].length;j++)
				{
					String[] str = getValues(i, j);
					category = str[0];
					technology = str[1];
					complexity = str[2];	
					
					//insert or update function for DevelopmentUnit table and called by unitPrice object 
					price.insertOrUpdateParam(category, technology, complexity, unitPrice[i][j],developmentunit);					
					try 
					{
						unitPrice1[i][j] = price.SelectParam(category, technology, complexity,developmentunit);
					}
					catch(Exception e) {
						throw new IOException("Unable to fetch development unit prices", e);
					}
				}
			}
			logger.debug("Development unit prices inserted into DB");
			
			//Execution Unit Price
			for(int i=5;i<10;i++)
			{
				for(int j=0;j<unitPrice[0].length;j++)
				{
					String[] str = getValues(i, j);
					category = str[0];
					technology = str[1];
					complexity = str[2];
					
					//insert or update function for ExecutionUnit table and called by unitPrice object
					if(j<3) {
						price.insertOrUpdateParam(category, technology, complexity, unitPrice[i][j],executionunit);					
					}
					try 
					{
						if(j>=3) {
							unitPrice1[i][j] = unitPrice1[i][j-3];
						}
						else {
							unitPrice1[i][j] = price.SelectParam(category, technology, complexity,executionunit);
						}						
					}
					catch(Exception e) {
						throw new IOException("Unable to fetch execution unit prices", e);
					}
				}			
			}
			logger.debug("Execution unit prices inserted into DB");
			
			
			//Maintenance Unit Price
			for(int i=10;i<unitPrice.length;i++)
			{
				for(int j=0;j<unitPrice[0].length;j++)
				{
					String[] str = getValues(i, j);
					category = str[0];
					technology = str[1];
					complexity = str[2];					
					
					//insert or update function for MaintenanceUnit table and called by unitPrice object					
					if(j<3) {
						price.insertOrUpdateParam(category, technology, complexity, unitPrice[i][j],maintenanceunit);					
					}				
					try 
					{
						if(j>=3) {
							unitPrice1[i][j] = unitPrice1[i][j-3];
						}
						else {
							unitPrice1[i][j] = price.SelectParam(category, technology, complexity,maintenanceunit);
						}		
					}
					catch(Exception e) {
						throw new IOException("Unable to fetch maintenance unit prices", e);
					}				
				}
			}
			logger.debug("Maintenance unit prices inserted into DB");
			
			//store the retrieved values into the original array
			for (int i=0;i<unitPrice.length;i++) 
			{
				for(int j=0;j<unitPrice[0].length;j++)
				{
					unitPrice[i][j] = unitPrice1[i][j];		
				}
			}
		}
		catch(IOException e)
		{
			throw new IOException("Unable to retrieve unit prices",e);
		}
		
		logger.trace("Exiting globalParameters class constructor");		
	}
	
	/**
	 * function to create a 15x15 array
	 * @param dev - development array 	 
	 * @param exe - execution array	 
	 * @param main- maintenance array	
	 * @return 15x15 array
	 * @throws IOException 
	 */
	public static double[][] ArrayCreator(double[][] dev,int dev_row,int dev_col,double[][] exe,int exe_row,int exe_col,double[][] main,int main_row,int main_col) throws IOException
	{
		logger.trace("Entering ArrayCreator method");
		double[][] result=new double[dev_row+exe_row+main_row][dev_col];
		
		try
		{
			if(dev_col == (exe_col*5) && dev_col == main_col*5)
			{
				
					//development unit Price table
					for(int i=0;i<dev_row;i++)
						for(int j=0;j<dev_col;j++)
							result[i][j]=dev[i][j];
					
					//Execution unit Price table
					for(int k=dev_row;k<dev_row+exe_row;k++)
					{	
						for(int i=0;i<5;i++)
						{
							for(int j=0;j<exe_col;j++)
								result[k][i*exe_col+j]=exe[k-dev_row][j];	
						}
					}
						
					//Maintenance unit Price table
					for(int k=dev_row+exe_row;k<dev_row+exe_row+main_row;k++)
					{	
						for(int i=0;i<5;i++)
						{
							for(int j=0;j<main_col;j++)
								result[k][i*main_col+j]=main[k-dev_row-exe_row][j];		
						}
					}				
			}
			else
			{
				throw new IOException("The input data is not of correct format");
			}
		}
		
		catch(Exception e)
		{
			throw new IOException("Unable to create user defined unitPrice array", e);
		}
		
		logger.trace("Exiting ArrayCreator method");
		
		
		return result;
	}
	
	
	/**
	 * Getters and setters for the variables
	 */
	
	public LocalDate getFactoryStartDate() {
		return factoryStartDate;
	}
	
	public void setMaintenancePercentInFirstTwoYears(double maintenancePercentInFirstTwoYears) {
		this.maintenancePercentInFirstTwoYears = maintenancePercentInFirstTwoYears;
	}

	public void setMaintenancePercentInThirdYear(double maintenancePercentInThirdYear) {
		this.maintenancePercentInThirdYear = maintenancePercentInThirdYear;
	}

	public int getSimpleManualProductivity() {
		return simpleManualProductivity;
	}

	public int getMediumManualProductivity() {
		return mediumManualProductivity;
	}

	public int getComplexManualProductivity() {
		return complexManualProductivity;
	}

	public double getYearOnePriceReductionFraction() {
		return yearOnePriceReductionFraction;
	}

	public double getYearTwoPriceReductionFraction() {
		return yearTwoPriceReductionFraction;
	}

	public double getYearThreePriceReductionFraction() {
		return yearThreePriceReductionFraction;
	}

	public double getDevEffortFactorPerEnvironment() {
		return devEffortFactorPerEnvironment;
	}

	public double getMaintenancePercentInFirstTwoYears() {
		return maintenancePercentInFirstTwoYears;
	}

	public double getMaintenancePercentInThirdYear() {
		return maintenancePercentInThirdYear;
	}

	public double getWellsGovernance() {
		return wellsGovernance;
	}

	public double getCognizantGovernance() {
		return cognizantGovernance;
	}

	public double getBlendedRate() {
		return blendedRate;
	}

	public double[][] getUnitPrice() {
		return unitPrice;
	}	
}
