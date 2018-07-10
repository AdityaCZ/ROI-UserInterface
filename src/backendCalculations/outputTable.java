package backendCalculations;
import backendCalculations.inputTable;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

public class outputTable{
	static Logger logger = Logger.getLogger(outputTable.class);
	
	//output table variables
	private int totalMonths = 36;
	private String transactionID;
	private String appName;
	private String appId;
	
	private final int[] monthNumber = new int[totalMonths];	
	private double[] percentTD = new double[totalMonths];
	private double[] testDevelopmentTC = new double[totalMonths];
	private double[] cumulativeCount = new double[totalMonths];
	private double[] TCExecutionCountRegression = new double[totalMonths];
	private double[] TCExecutionCountSmoke = new double[totalMonths];
	private double[] TCExecutionCountLC = new double[totalMonths];
	private double[] totalCount = new double[totalMonths];
	private double[] totalDevelopmentScope = new double[totalMonths];
	private double[] totalDevelopmentCost = new double[totalMonths];
	private double[] automationExecutionCost = new double[totalMonths];
	private double[] maintenanceCost = new double[totalMonths];
	private double[] maintenanceAndExecutionCost = new double[totalMonths];
	private double[] totalBilling = new double[totalMonths];
	private double[] manualExecutionCost = new double[totalMonths];
	private double[] WfSpendWithAutomation = new double[totalMonths];
	private double[] WfSpendWithoutAutomation = new double[totalMonths];
	private double[] gainInDollar = new double[totalMonths];
	private double[] gainPercent = new double[totalMonths];
	private double[] netSavings = new double[totalMonths];
			
	//calculated parameters
	private double noOfFeasibleTC;
	private double noOfRegressionTC;
	private double simpleRegressionTC;
	private double mediumRegressionTC;
	private double complexRegressionTC;
	private double totalRegressionExecutions;
	private double noOfSmokeTC;
	private double simpleSmokeTC;
	private double mediumSmokeTC;
	private double complexSmokeTC;
	private double totalSmokeExecutions;
	private double noOfLifeCriticalTC;
	private double simpleLifeCriticalTC;
	private double mediumLifeCriticalTC;
	private double complexLifeCriticalTC;
	private double totalLifeCriticalExecutions;
	private double manualExecutionProductivity;
	
	//output summary variables
	private double totalSpendWithoutAutomation;		// also part of cost summary table
	private double totalSpendWithAutomation;		// also part of cost summary table
	private double gainPerScript;		// also part of cost summary table
	private double developmentCost;		// also part of cost summary table
	private double totalGain;
	private double totalAutomationExecutionCost;	// also part of cost summary table
	private double totalBillingToWF;
	private double totalMaintenanceCost;	// also part of cost summary table
	
	//cost summary variables
	private double developmentCostY1;
	private double developmentCostY2;
	private double developmentCostY3;
	private double totalAutomationExecutionCostY1;
	private double totalAutomationExecutionCostY2;
	private double totalAutomationExecutionCostY3;
	private double maintenanceCostY1;
	private double maintenanceCostY2;
	private double maintenanceCostY3;
	private double EplusMCost;
	private double EplusMCostY1;
	private double EplusMCostY2;
	private double EplusMCostY3;
	private double totalSpendWithoutAutomationY1;
	private double totalSpendWithoutAutomationY2;
	private double totalSpendWithoutAutomationY3;
	private double totalBillingToWFY1;
	private double totalBillingToWFY2;
	private double totalBillingToWFY3;
	private double manualExecutionCostInfeasible;
	private double manualExecutionCostInfeasibleY1;
	private double manualExecutionCostInfeasibleY2;
	private double manualExecutionCostInfeasibleY3;
	private double totalSpendWithAutomationY1;
	private double totalSpendWithAutomationY2;
	private double totalSpendWithAutomationY3;
	private double totalGainY1;
	private double totalGainY2;
	private double totalGainY3;
	private double totalGainPercent;
	
		
	//helper variables
	private int categoryIndex;
	private int technologyIndex;
	
	private JSONObject outputJSON;
	
	//constructor for initiating calculation
	public outputTable(inputTable input) throws IOException, ArithmeticException, Exception{
		logger.trace("Entered outputTable constructor");
		for(int i=0;i<totalMonths;i++) {
			monthNumber[i] = i+1;
		}
		fillTable(input);		
		logger.trace("Exiting outputTable constructor");
	}
	
	//default constructor for setting month number
	public outputTable(){
		logger.trace("Entered outputTable constructor");
		for(int i=0;i<totalMonths;i++) {
			monthNumber[i] = i+1;
		}
		logger.trace("Exiting outputTable constructor");
	}	
	
	//Method to find values of categoryIndex and technologyIndex from input data
	private void assignIndex(inputTable input) throws IOException{
		logger.trace("Entered assignIndex function");
		switch(input.getAppCategory()) {
			case "Never Automated" : 
				this.categoryIndex = 0;
				break;
			case "Tool Migration" :
				this.categoryIndex = 3;
				break;
			case "Framework Migration" :
				this.categoryIndex = 6;
				break;
			case "Optimization" :
				this.categoryIndex = 9;
				break;
			case "Componentization" :
				this.categoryIndex = 12;
				break;
			default:
				throw new IOException("App category " + input.getAppCategory() +" does not exist");				
		}
		
		switch(input.getTechnology()) {
			case "Web" : 
				this.technologyIndex = 0;
				break;
			case "Thick Client/ Mobile" :
				this.technologyIndex = 1;
				break;
			case "IVR" :
				this.technologyIndex = 2;
				break;
			case "Web Services" :
				this.technologyIndex = 3;
				break;
			case "Mainframe" :
				this.technologyIndex = 4;
				break;
			default:
				throw new IOException("Technology " + input.getTechnology() + " does not exist");
		}	
		logger.trace("Exited assignIndex function");
	}
	
	//getters and setters for output class member variables
	public double getDevelopmentCostY1() {
		return developmentCostY1;
	}
	
	public JSONObject getJSON() {
		return this.outputJSON;
	}
	
	public double getDevelopmentCostY2() {
		return developmentCostY2;
	}

	public double getDevelopmentCostY3() {
		return developmentCostY3;
	}

	public double getTotalAutomationExecutionCostY1() {
		return totalAutomationExecutionCostY1;
	}

	public double getTotalAutomationExecutionCostY2() {
		return totalAutomationExecutionCostY2;
	}

	public double getTotalAutomationExecutionCostY3() {
		return totalAutomationExecutionCostY3;
	}

	public double getmaintenanceCostY1() {
		return maintenanceCostY1;
	}

	public double getMaintenanceCostY2() {
		return maintenanceCostY2;
	}

	public double getMaintenanceCostY3() {
		return maintenanceCostY3;
	}

	public double getEplusMCost() {
		return EplusMCost;
	}

	public double getEplusMCostY1() {
		return EplusMCostY1;
	}

	public double getEplusMCostY2() {
		return EplusMCostY2;
	}

	public double getEplusMCostY3() {
		return EplusMCostY3;
	}

	public double getTotalSpendWithoutAutomationY1() {
		return totalSpendWithoutAutomationY1;
	}

	public double getTotalSpendWithoutAutomationY2() {
		return totalSpendWithoutAutomationY2;
	}

	public double getTotalSpendWithoutAutomationY3() {
		return totalSpendWithoutAutomationY3;
	}

	public double getTotalBillingToWFY1() {
		return totalBillingToWFY1;
	}

	public double getTotalBillingToWFY2() {
		return totalBillingToWFY2;
	}

	public double getTotalBillingToWFY3() {
		return totalBillingToWFY3;
	}

	public double getManualExecutionCostInfeasibleY1() {
		return manualExecutionCostInfeasibleY1;
	}

	public double getManualExecutionCostInfeasibleY2() {
		return manualExecutionCostInfeasibleY2;
	}

	public double getManualExecutionCostInfeasibleY3() {
		return manualExecutionCostInfeasibleY3;
	}

	public double getTotalSpendWithAutomationY1() {
		return totalSpendWithAutomationY1;
	}

	public double getTotalSpendWithAutomationY2() {
		return totalSpendWithAutomationY2;
	}

	public double getTotalSpendWithAutomationY3() {
		return totalSpendWithAutomationY3;
	}

	public double getTotalGainY1() {
		return totalGainY1;
	}

	public double getTotalGainY2() {
		return totalGainY2;
	}

	public double getTotalGainY3() {
		return totalGainY3;
	}

	public double getTotalGainPercent() {
		return totalGainPercent;
	}
	
	public int getTotalMonths() {
		return totalMonths;
	}
	
	public String getTransactionID() {
		return transactionID;
	}
	public String getAppName() {
		return appName;
	}
	public String getAppId() {
		return appId;
	}
	public int[] getMonthNumber() {
		return monthNumber;
	}
	public double[] getPercentTD() {
		return percentTD;
	}
	public double[] getTestDevelopmentTC() {
		return testDevelopmentTC;
	}
	public double[] getCumulativeCount() {
		return cumulativeCount;
	}
	public double[] getTCExecutionCountRegression() {
		return TCExecutionCountRegression;
	}
	public double[] getTCExecutionCountSmoke() {
		return TCExecutionCountSmoke;
	}
	public double[] getTCExecutionCountLC() {
		return TCExecutionCountLC;
	}
	public double[] getTotalCount() {
		return totalCount;
	}
	public double[] getTotalDevelopmentScope() {
		return totalDevelopmentScope;
	}
	public double[] getTotalDevelopmentCost() {
		return totalDevelopmentCost;
	}
	public double[] getAutomationExecutionCost() {
		return automationExecutionCost;
	}
	public double[] getMaintenanceCost() {
		return maintenanceCost;
	}
	public double[] getMaintenanceAndExecutionCost() {
		return maintenanceAndExecutionCost;
	}
	public double[] getTotalBilling() {
		return totalBilling;
	}
	public double[] getManualExecutionCost() {
		return manualExecutionCost;
	}
	public double[] getWfSpendWithAutomation() {
		return WfSpendWithAutomation;
	}
	public double[] getWfSpendWithoutAutomation() {
		return WfSpendWithoutAutomation;
	}
	public double[] getGainInDollar() {
		return gainInDollar;
	}
	public double[] getGainPercent() {
		return gainPercent;
	}
	public double[] getNetSavings() {
		return netSavings;
	}
	public double getTotalSpendWithoutAutomation() {
		return totalSpendWithoutAutomation;
	}
	public double getTotalSpendWithAutomation() {
		return totalSpendWithAutomation;
	}
	public double getGainPerScript() {
		return gainPerScript;
	}
	public double getDevelopmentCost() {
		return developmentCost;
	}
	public double getTotalGain() {
		return totalGain;
	}
	public double getTotalAutomationExecutionCost() {
		return totalAutomationExecutionCost;
	}
	public double getTotalBillingToWF() {
		return totalBillingToWF;
	}
	public double getTotalMaintenanceCost() {
		return totalMaintenanceCost;
	}
	public void setTotalMonths(int totalMonths) {
		this.totalMonths = totalMonths;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	/*
	 * calculate intermediate parameters useful for aiding calculation
	 * of output table fields
	 */
	private void calculateInterimParameters(inputTable input) throws Exception{
		logger.trace("Enttered calculateInterimParameters function");
		try {
			this.noOfFeasibleTC =  (input.getTestCasesAfterBreakdown() * input.getPercentFeasible()/100.0);
			this.noOfRegressionTC =  (input.getRegressionPercent()/100.0 * this.noOfFeasibleTC);
			this.simpleRegressionTC =  (input.getRegressionSimplePercent()/100.0 * this.noOfRegressionTC);
			this.mediumRegressionTC =  (input.getRegressionMediumPercent()/100.0 * this.noOfRegressionTC);
			this.complexRegressionTC =  (input.getRegressionComplexPercent()/100.0 * this.noOfRegressionTC);
			this.totalRegressionExecutions = input.getRegressionNoOfExecutions() * input.getRegressionNoOfEnvironment();
			this.noOfSmokeTC =  (input.getSmokePercent()/100.0 * this.noOfFeasibleTC);
			this.simpleSmokeTC =  (input.getSmokeSimplePercent()/100.0 * this.noOfSmokeTC);
			this.mediumSmokeTC =  (input.getSmokeMediumPercent()/100.0 * this.noOfSmokeTC);
			this.complexSmokeTC =  (input.getSmokeComplexPercent()/100.0 * this.noOfSmokeTC);
			this.totalSmokeExecutions = input.getSmokeNoOfExecutions() * input.getSmokeNoOfEnvironment();
			this.noOfLifeCriticalTC =  (input.getLifeCriticalPercent()/100.0 * this.noOfFeasibleTC);
			this.simpleLifeCriticalTC =  (input.getLifeCriticalSimplePercent()/100.0 * this.noOfLifeCriticalTC);
			this.mediumLifeCriticalTC =  (input.getLifeCriticalMediumPercent()/100.0 * this.noOfLifeCriticalTC);
			this.complexLifeCriticalTC = (input.getLifeCriticalComplexPercent()/100.0 * this.noOfLifeCriticalTC);
			this.totalLifeCriticalExecutions = input.getLifeCriticalNoOfExecutions() * input.getLifeCriticalNoOfEnvironment();
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		logger.trace("Exited calculateInterimParameters function");
	}
	
	/*
	 * Method to calculate values of output table parameters, given input table object
	 */
	public void fillTable(inputTable input) throws IOException, ArithmeticException, Exception{
		logger.trace("Entered fillTable function");
		//calculate intermediate parameters
		calculateInterimParameters(input);
		//initialize values of global parameters
		globalParameters global = new globalParameters(input.getMaintenancePercent()); 
		//find indexes of category and technology
		assignIndex(input);
		
		//calculate manualProductivity	
		logger.debug("Manual Execution Productivity calculation in progress");
		if((input.getSmokePercent()/100.0 + input.getRegressionPercent()/100.0 + input.getLifeCriticalPercent()/100.0) == 0) {
			throw new ArithmeticException("Sum of Smoke, Regression and Life Critical suite percent can't be 0.");
		}
		else {
			this.manualExecutionProductivity = ( global.getSimpleManualProductivity() * (input.getSmokePercent()/100.0 *input.getSmokeSimplePercent()/100.0 + input.getRegressionPercent()/100.0 * input.getRegressionSimplePercent()/100.0 + input.getLifeCriticalPercent()/100.0 * input.getLifeCriticalSimplePercent()/100.0) + 
					global.getMediumManualProductivity() * (input.getSmokePercent()/100.0 * input.getSmokeMediumPercent()/100.0 + input.getRegressionPercent()/100.0 * input.getRegressionMediumPercent()/100.0 + input.getLifeCriticalPercent()/100.0*input.getLifeCriticalMediumPercent()/100.0) +
					global.getComplexManualProductivity() * (input.getSmokePercent()/100.0 * input.getSmokeComplexPercent()/100.0 + input.getRegressionPercent()/100.0 * input.getRegressionComplexPercent()/100.0 + input.getLifeCriticalPercent()/100.0*input.getLifeCriticalComplexPercent()/100.0) )/
					(input.getSmokePercent()/100.0 + input.getRegressionPercent()/100.0 + input.getLifeCriticalPercent()/100.0);
		}
		
		
		//fill percentTD
		logger.debug("% TD calculation in progress");
		LocalDate start = input.getDesignStartDate();
		LocalDate end = input.getDesignEndDate();
		int months = Months.monthsBetween(start, end).getMonths()+1;

		for(int i=0;i<totalMonths;i++) {
			if(months <= 0) {
				throw new ArithmeticException("Number of months between start and end date can't be zero or negative");
			}
			else {
				if(i < months) {
					this.percentTD[i] = 100.0/(months);
				}	
				else {
					this.percentTD[i] = 0.0;
				}
			}
		}

		//fill testDevelopmentTC
		logger.debug("Test development TC calculation in progress");
		for(int i=0;i<totalMonths;i++) {
			this.testDevelopmentTC[i] =   (this.noOfFeasibleTC * this.percentTD[i]/100.0);
		}
		
		
		//fill cumulative count
		logger.debug("Cumulative count calculation in progress");
		months = Months.monthsBetween(global.getFactoryStartDate(), start).getMonths();
		for(int i=0;i<totalMonths;i++) {
			if (i ==0) {
				this.cumulativeCount[i] = this.testDevelopmentTC[i];
			}
			else {				
				if(i >= months && i <= months + input.getApplicationLife() - 1) {
					this.cumulativeCount[i] = this.cumulativeCount[i-1] + this.testDevelopmentTC[i];
				}
				else {
					this.cumulativeCount[i] = 0;
				}
			}
		}
		
		
		//fill TCExecutionCountRegression
		logger.debug("TC Execution count regression calculation in progress");
		this.TCExecutionCountRegression[0] = 0;
		for(int i=1;i<totalMonths;i++) {
			if(this.cumulativeCount[i] != 0) {
				this.TCExecutionCountRegression[i] = (this.cumulativeCount[i-1] * input.getRegressionPercent()/100.0 * (this.totalRegressionExecutions / 12.0)) ; 
			}
			else {
				this.TCExecutionCountRegression[i] = 0;
			}
		}
		
		
		//fill TCExecutionCountSmoke
		logger.debug("TC Execution count smoke calculation in progress");
		this.TCExecutionCountSmoke[0] = 0;
		for(int i=1;i<totalMonths;i++) {
			if(this.cumulativeCount[i] != 0) {
				this.TCExecutionCountSmoke[i] = (this.cumulativeCount[i-1] * input.getSmokePercent()/100.0 * (this.totalSmokeExecutions / 12.0)) ; 
			}
			else {
				this.TCExecutionCountSmoke[i] = 0;
			}
		}
		
		
		//fill TCExecutionCountLifeCritical
		logger.debug("TC Execution count life critical calculation in progress");
		this.TCExecutionCountLC[0] = 0;
		for(int i=1;i<totalMonths;i++) {
			if(this.cumulativeCount[i] != 0) {
				this.TCExecutionCountLC[i] = (this.cumulativeCount[i-1] * input.getLifeCriticalPercent()/100.0 * (this.totalLifeCriticalExecutions / 12.0)) ; 
			}
			else {
				this.TCExecutionCountLC[i] = 0;
			}
		}
		
		
		//fill totalCount
		logger.debug("Total count regression calculation in progress");
		for(int i=0;i < totalMonths;i++) {
			this.totalCount[i] = this.TCExecutionCountSmoke[i] + this.TCExecutionCountRegression[i] + this.TCExecutionCountLC[i]; 
		}
		
		
		//fill total development scope
		logger.debug("Total dev. scope calculation in progress");
		for(int i=0;i<totalMonths; i++) {
			this.totalDevelopmentScope[i] = (this.noOfFeasibleTC * this.percentTD[i]/100.0 * (1 + global.getDevEffortFactorPerEnvironment() * (input.getSmokePercent()/100.0 * (input.getSmokeNoOfEnvironment() - 1) + input.getRegressionPercent()/100.0 * (input.getRegressionNoOfEnvironment() - 1) + input.getLifeCriticalPercent()/100.0 * (input.getLifeCriticalNoOfEnvironment() - 1)) ));
		}
		
		logger.debug("Cost calculation begins");
		double[][] unitPrice = global.getUnitPrice().clone();
		
		if((input.getSmokePercent()/100.0 + input.getRegressionPercent()/100.0 + input.getLifeCriticalPercent()/100.0) == 0) {
			throw new ArithmeticException("Sum of Smoke, Regression and Life Critical suite percent can't be 0.");
		}
		else {
			//fill total development cost
			logger.debug("Total dev. cost calculation in progress");
			for(int i=0;i<totalMonths; i++) {
				this.totalDevelopmentCost[i] = this.totalDevelopmentScope[i] * ( (input.getSmokePercent()/100.0 * input.getSmokeSimplePercent()/100.0 + input.getRegressionPercent()/100.0 * input.getRegressionSimplePercent()/100.0 + input.getLifeCriticalPercent()/100.0 * input.getLifeCriticalSimplePercent()/100.0) * unitPrice[this.technologyIndex][this.categoryIndex] + 
						(input.getSmokePercent()/100.0 * input.getSmokeMediumPercent()/100.0 + input.getRegressionPercent()/100.0 * input.getRegressionMediumPercent()/100.0 + input.getLifeCriticalPercent()/100.0 * input.getLifeCriticalMediumPercent()/100.0) * unitPrice[this.technologyIndex][this.categoryIndex + 1] + 
						(input.getSmokePercent()/100.0 * input.getSmokeComplexPercent()/100.0 + input.getRegressionPercent()/100.0 * input.getRegressionComplexPercent()/100.0 + input.getLifeCriticalPercent()/100.0 * input.getLifeCriticalComplexPercent()/100.0) * unitPrice[this.technologyIndex][this.categoryIndex + 2] ) /
						(input.getSmokePercent()/100.0 + input.getRegressionPercent()/100.0 + input.getLifeCriticalPercent()/100.0);
				
				if(i<this.totalMonths/3) {
					this.totalDevelopmentCost[i] *= (1-global.getYearOnePriceReductionFraction());
				}
				else {
					if(i<2*this.totalMonths/3.0) {
						this.totalDevelopmentCost[i] *= (1-global.getYearTwoPriceReductionFraction());
					}
					else {
						this.totalDevelopmentCost[i] *= (1-global.getYearThreePriceReductionFraction());
					}
				}
			}
			
			
			//fill automation execution cost
			logger.debug("Total automation execution cost calculation in progress");
			for(int i=0;i<totalMonths; i++) {
				this.automationExecutionCost[i] = this.totalCount[i] * ( (input.getSmokePercent()/100.0 * input.getSmokeSimplePercent()/100.0 + input.getRegressionPercent()/100.0 * input.getRegressionSimplePercent()/100.0 + input.getLifeCriticalPercent()/100.0 * input.getLifeCriticalSimplePercent()/100.0) * unitPrice[5 + this.technologyIndex][this.categoryIndex] + 
						(input.getSmokePercent()/100.0 * input.getSmokeMediumPercent()/100.0 + input.getRegressionPercent()/100.0 * input.getRegressionMediumPercent()/100.0 + input.getLifeCriticalPercent()/100.0 * input.getLifeCriticalMediumPercent()/100.0) * unitPrice[5 + this.technologyIndex][this.categoryIndex + 1] + 
						(input.getSmokePercent()/100.0 * input.getSmokeComplexPercent()/100.0 + input.getRegressionPercent()/100.0 * input.getRegressionComplexPercent()/100.0 + input.getLifeCriticalPercent()/100.0 * input.getLifeCriticalComplexPercent()/100.0) * unitPrice[5 + this.technologyIndex][this.categoryIndex + 2] )/
						(input.getSmokePercent()/100.0 + input.getRegressionPercent()/100.0 + input.getLifeCriticalPercent()/100.0);
				
				if(i<this.totalMonths/3) {
					this.automationExecutionCost[i] *= (1-global.getYearOnePriceReductionFraction());
				}
				else {
					if(i<2*this.totalMonths/3.0) {
						this.automationExecutionCost[i] *= (1-global.getYearTwoPriceReductionFraction());
					}
					else {
						this.automationExecutionCost[i] *= (1-global.getYearThreePriceReductionFraction());
					}
				}
			}
			logger.debug("Cost Calculation ends");
			
			//fill maintenance cost
			logger.debug("Maintenance cost calculation in progress");
			for(int i=0; i < this.totalMonths; i++) {
				this.maintenanceCost[i] = this.totalCount[i] * 
								( (input.getSmokePercent()/100.0 * input.getSmokeSimplePercent()/100.0 + input.getRegressionPercent()/100.0 * input.getRegressionSimplePercent()/100.0 + input.getLifeCriticalPercent()/100.0 * input.getLifeCriticalSimplePercent()/100.0) * unitPrice[10 + this.technologyIndex][this.categoryIndex] + 
								(input.getSmokePercent()/100.0 * input.getSmokeMediumPercent()/100.0 + input.getRegressionPercent()/100.0 * input.getRegressionMediumPercent()/100.0 + input.getLifeCriticalPercent()/100.0 * input.getLifeCriticalMediumPercent()/100.0) * unitPrice[10 + this.technologyIndex][this.categoryIndex + 1] + 
								(input.getSmokePercent()/100.0 * input.getSmokeComplexPercent()/100.0 + input.getRegressionPercent()/100.0 * input.getRegressionComplexPercent()/100.0 + input.getLifeCriticalPercent()/100.0 * input.getLifeCriticalComplexPercent()/100.0) * unitPrice[10 + this.technologyIndex][this.categoryIndex + 2] )/
								(input.getSmokePercent()/100.0 + input.getRegressionPercent()/100.0 + input.getLifeCriticalPercent()/100.0);
				
				if(i<this.totalMonths/3) {
					this.maintenanceCost[i] *= global.getMaintenancePercentInFirstTwoYears()/100.0 * (1-global.getYearOnePriceReductionFraction());
				}
				else {
					if(i<2*this.totalMonths/3) {
						this.maintenanceCost[i] *= global.getMaintenancePercentInFirstTwoYears()/100.0 * (1-global.getYearTwoPriceReductionFraction());
					}
					else {
						this.maintenanceCost[i] *= global.getMaintenancePercentInThirdYear()/100.0 * (1-global.getYearThreePriceReductionFraction());
					}
				}
			}			
		}
				
		
		//fill maintenance + execution cost
		logger.debug("Maintenance+Execution cost calculation in progress");
		for(int i=0;i<this.totalMonths;i++) {
			this.maintenanceAndExecutionCost[i] = this.maintenanceCost[i] + this.automationExecutionCost[i];
		}
		
		//fill total Billing
		logger.debug("Total billing calculation in progress");
		for(int i=0;i<this.totalMonths;i++) {
			this.totalBilling[i] = this.maintenanceAndExecutionCost[i] + this.totalDevelopmentCost[i];
		}
		
		logger.debug("Manual cost calculation begins");
		if(this.manualExecutionProductivity == 0) {
			throw new ArithmeticException("Manual Execution Productivity can't be 0");
		}
		else {
			//fill manual execution cost
			logger.debug("Manual execution cost calculation in process");
			for(int i=0;i<this.totalMonths; i++) {
				if(this.cumulativeCount[i] == 0) {
					this.manualExecutionCost[i] = 0;
				}
				else {
					this.manualExecutionCost[i] = ((1 + global.getWellsGovernance()) / (20 * this.manualExecutionProductivity)) * (input.getSmokePercent()/100.0 * this.totalSmokeExecutions/12 + input.getRegressionPercent()/100.0 * this.totalRegressionExecutions/12 + input.getLifeCriticalPercent()/100.0 * this.totalLifeCriticalExecutions/12) * 8 * 20 * global.getBlendedRate();
					if(this.totalCount[i] == 0) {
						 this.manualExecutionCost[i] *= input.getTestCases();
					}
					else {
						if(i > 0) {
							this.manualExecutionCost[i] *= (input.getTestCases() - this.cumulativeCount[i-1]);
						}
						else {
							 this.manualExecutionCost[i] *= input.getTestCases();
						}					
					}
				}
			}
			
			//fill WF Spend with automation
			logger.debug("WF spend with automation calculation in progress");
			for(int i=0;i<this.totalMonths; i++) {
				this.WfSpendWithAutomation[i] = this.manualExecutionCost[i] + this.totalBilling[i];
			}
			
			//fill WF Spend without automation
			logger.debug("WF spend without automation calculation in progress");
			for(int i=0;i<this.totalMonths; i++) {
				if(this.WfSpendWithAutomation[i] == 0) {
					this.WfSpendWithoutAutomation[i] = 0;
				}
				else {
					this.WfSpendWithoutAutomation[i] = input.getTestCases() * ((1 + global.getWellsGovernance()) / (20 * this.manualExecutionProductivity)) * (input.getSmokePercent()/100.0 * this.totalSmokeExecutions/12 + input.getRegressionPercent()/100.0 * this.totalRegressionExecutions/12 + input.getLifeCriticalPercent()/100.0 * this.totalLifeCriticalExecutions/12) * 8 * 20 * global.getBlendedRate();
				}			
			}
		}
		logger.debug("Manual execution cost calculation ends");		
		
		
		//fill gain
		logger.debug("Gain calculation in progress");
		for(int i=0;i<this.totalMonths; i++) {
			this.gainInDollar[i] = this.WfSpendWithoutAutomation[i] - this.manualExecutionCost[i] - this.totalDevelopmentCost[i];
		}
		
		//fill gain %
		logger.debug("Gain % calculation in progress");
		for(int i=0;i<this.totalMonths; i++) {
			if(this.WfSpendWithoutAutomation[i] == 0) {
				this.gainPercent[i] = 0;
			}
			else {
				if(this.WfSpendWithoutAutomation[i] == 0) {
					throw new ArithmeticException("WF Spend without automation can't be 0");
				}
				else {
					this.gainPercent[i] = this.gainInDollar[i] / this.WfSpendWithoutAutomation[i] * 100.0;
				}				
			}			
		}
		
		//fill net savings
		logger.debug("Net savings calculation in progress");
		for(int i=0;i<this.totalMonths;i++) {
			this.netSavings[i] = this.WfSpendWithoutAutomation[i] - this.WfSpendWithAutomation[i];
		}
		
		
		
		//fill Summary Values
		logger.debug("Summary & Cost Summary parameter calculation begins");
		
		// fill total Spend w/o automation
		logger.debug("Summary: Total Spend without automation calculation in progress");
		this.totalSpendWithoutAutomationY1 = 0;
		for(int i=0;i<this.totalMonths/3; i++) {
			this.totalSpendWithoutAutomationY1 += this.WfSpendWithoutAutomation[i];
		}
		
		this.totalSpendWithoutAutomationY2 = 0;
		for(int i=this.totalMonths/3 ;i< 2*this.totalMonths/3; i++) {
			this.totalSpendWithoutAutomationY2 += this.WfSpendWithoutAutomation[i];
		}
		
		this.totalSpendWithoutAutomationY3 = 0;
		for(int i= 2*this.totalMonths/3 ;i < this.totalMonths; i++) {
			this.totalSpendWithoutAutomationY3 += this.WfSpendWithoutAutomation[i];
		}
		
		this.totalSpendWithoutAutomation = this.totalSpendWithoutAutomationY1 + this.totalSpendWithoutAutomationY2 + this.totalSpendWithoutAutomationY3;
		
		
		//fill gain per script
		logger.debug("Summary: Gain Per Script calculation in progress");
		if(this.noOfFeasibleTC <= 0) {
			throw new ArithmeticException("No. of feasible TC should be greater than 0");
		}
		else {
			//fill gain per script
			this.gainPerScript = 0;
			for(int i=0;i<this.totalMonths; i++) {
				this.gainPerScript += this.gainInDollar[i] / (double) (this.noOfFeasibleTC);
			}
		}
		
		
		//fill total gain
		logger.debug("Summary: Total gain calculation in progress");
		this.totalGainY1 = 0;
		for(int i=0;i<this.totalMonths/3; i++) {
			this.totalGainY1 += this.gainInDollar[i];
		}
		
		this.totalGainY2 = 0;
		for(int i= this.totalMonths/3 ;i< 2*this.totalMonths/3; i++) {
			this.totalGainY2 += this.gainInDollar[i];
		}
		
		this.totalGainY3 = 0;
		for(int i= 2*this.totalMonths/3 ; i < this.totalMonths; i++) {
			this.totalGainY3 += this.gainInDollar[i];
		}		
		this.totalGain = this.totalGainY1 + this.totalGainY2 + this.totalGainY3;
		
		
		//fill total billing
		logger.debug("Summary: Total billing calculation in progress");
		this.totalBillingToWFY1 = 0;
		for(int i=0;i<this.totalMonths/3; i++) {
			this.totalBillingToWFY1 += this.totalBilling[i];
		}
		
		this.totalBillingToWFY2 = 0;
		for(int i=this.totalMonths/3 ;i< 2*this.totalMonths/3; i++) {
			this.totalBillingToWFY2 += this.totalBilling[i];
		}
		
		this.totalBillingToWFY3 = 0;
		for(int i= 2*this.totalMonths/3 ;i<this.totalMonths; i++) {
			this.totalBillingToWFY3 += this.totalBilling[i];
		}
		
		this.totalBillingToWF = this.totalBillingToWFY1 + this.totalBillingToWFY2 + this.totalBillingToWFY3;
		
		
		
		// fill total Spend with automation for different years
		logger.debug("Summary: Total Spend with automation calculation in progress");
		this.totalSpendWithAutomationY1 = 0;
		for(int i=0;i<this.totalMonths/3; i++) {
			this.totalSpendWithAutomationY1 += this.WfSpendWithAutomation[i];
		}
		
		this.totalSpendWithAutomationY2 = 0;
		for(int i= this.totalMonths/3;i< 2*this.totalMonths/3; i++) {
			this.totalSpendWithAutomationY2 += this.WfSpendWithAutomation[i];
		}
		
		this.totalSpendWithAutomationY3 = 0;
		for(int i= 2*this.totalMonths/3;i<this.totalMonths; i++) {
			this.totalSpendWithAutomationY3 += this.WfSpendWithAutomation[i];
		}
		this.totalSpendWithAutomation = this.totalSpendWithAutomationY1 + this.totalSpendWithAutomationY2 + this.totalSpendWithAutomationY3;
		
		
		// fill total development costs of different years
		logger.debug("Summary: Total Development cost calculation in progress");
		this.developmentCostY1 = 0;
		for(int i=0;i<this.totalMonths/3; i++) {
			this.developmentCostY1 += this.totalDevelopmentCost[i];
		}
		
		this.developmentCostY2 = 0;
		for(int i=this.totalMonths/3;i < 2*this.totalMonths/3; i++) {
			this.developmentCostY2 += this.totalDevelopmentCost[i];
		}
		
		this.developmentCostY3 = 0;
		for(int i= 2*this.totalMonths/3 ;i < this.totalMonths; i++) {
			this.developmentCostY3 += this.totalDevelopmentCost[i];
		}
		
		this.developmentCost = this.developmentCostY1 + this.developmentCostY2 + this.developmentCostY3;
		
		
		// fill total automation execution cost for different years
		logger.debug("Summary: Total automation execution cost calculation in progress");
		this.totalAutomationExecutionCostY1 = 0;
		for(int i=0;i<this.totalMonths/3; i++) {
			this.totalAutomationExecutionCostY1 += this.automationExecutionCost[i];
		}
		
		this.totalAutomationExecutionCostY2 = 0;
		for(int i=this.totalMonths/3 ;i< 2*this.totalMonths/3; i++) {
			this.totalAutomationExecutionCostY2 += this.automationExecutionCost[i];
		}
		
		this.totalAutomationExecutionCostY3 = 0;
		for(int i= 2*this.totalMonths/3 ;i<this.totalMonths; i++) {
			this.totalAutomationExecutionCostY3 += this.automationExecutionCost[i];
		}
		
		this.totalAutomationExecutionCost = this.totalAutomationExecutionCostY1 + this.totalAutomationExecutionCostY2 + this.totalAutomationExecutionCostY3;
		
		
		// fill maintenance cost for different years
		logger.debug("Summary: Maintenance cost calculation in progress");
		this.maintenanceCostY1 = 0;
		for(int i=0;i<this.totalMonths/3; i++) {
			this.maintenanceCostY1 += this.maintenanceCost[i];
		}
		
		this.maintenanceCostY2 = 0;
		for(int i=this.totalMonths/3 ;i< 2*this.totalMonths/3; i++) {
			this.maintenanceCostY2 += this.maintenanceCost[i];
		}		
		
		this.maintenanceCostY3 = 0;
		for(int i= 2*this.totalMonths/3;i<this.totalMonths; i++) {
			this.maintenanceCostY3 += this.maintenanceCost[i];
		}
		
		this.totalMaintenanceCost = this.maintenanceCostY1 + this.maintenanceCostY2 + this.maintenanceCostY3;
		
		
		//fill E+M cost for different years
		logger.debug("Summary: E+M cost calculation in progress");
		this.EplusMCostY1 = this.totalAutomationExecutionCostY1 + this.maintenanceCostY1;
		this.EplusMCostY2 = this.totalAutomationExecutionCostY2 + this.maintenanceCostY2;
		this.EplusMCostY3 = this.totalAutomationExecutionCostY3 + this.maintenanceCostY3;
		this.EplusMCost = this.totalAutomationExecutionCost + this.totalMaintenanceCost;

		
		//fill manual execution cost infeasible
		logger.debug("Summary: Infeasible manual execution cost calculation in progress");
		this.manualExecutionCostInfeasibleY1 = 0;
		for(int i=0;i<this.totalMonths/3; i++) {
			this.manualExecutionCostInfeasibleY1 += this.manualExecutionCost[i];
		}
		
		this.manualExecutionCostInfeasibleY2 = 0;
		for(int i=this.totalMonths/3 ;i< 2*this.totalMonths/3; i++) {
			this.manualExecutionCostInfeasibleY2 += this.manualExecutionCost[i];
		}		
		
		this.manualExecutionCostInfeasibleY3 = 0;
		for(int i= 2*this.totalMonths/3;i<this.totalMonths; i++) {
			this.manualExecutionCostInfeasibleY3 += this.manualExecutionCost[i];
		}
		
		this.manualExecutionCostInfeasible = this.manualExecutionCostInfeasibleY1 + this.manualExecutionCostInfeasibleY2 + this.manualExecutionCostInfeasibleY3;
		
		
		//fill total Gain percent
		logger.debug("Summary: Total gain% calculation in progress");
		try {
			this.totalGainPercent = this.totalGain/this.totalSpendWithoutAutomation;
		}catch(ArithmeticException e) {
			throw new ArithmeticException("Total WF Spend without automation can not be 0");
		}
		
		
		//print output table
		DecimalFormat df = new DecimalFormat(".##");
		for(int i=0;i<this.totalMonths;i++) {
			System.out.print("1," + df.format(this.monthNumber[i]) + "\t");
			System.out.print("2," + df.format(this.percentTD[i]) + "\t");
			System.out.print("3," + df.format(this.testDevelopmentTC[i]) + "\t");
			System.out.print("4," + df.format(this.cumulativeCount[i]) + "\t");
			System.out.print("5," + df.format(this.TCExecutionCountRegression[i]) + "\t");
			System.out.print("6," + df.format(this.TCExecutionCountSmoke[i]) + "\t");
			System.out.print("7," + df.format(this.TCExecutionCountLC[i]) + "\t");
			System.out.print("8," + df.format(this.totalCount[i]) + "\t");
			System.out.print("9," + df.format(this.totalDevelopmentScope[i]) + "\t");
			System.out.print("10," + df.format(this.totalDevelopmentCost[i]) + "\t");
			System.out.print("11," + df.format(this.automationExecutionCost[i]) + "\t");
			System.out.print("12," + df.format(this.maintenanceCost[i]) + "\t");
			System.out.print("13," + df.format(this.maintenanceAndExecutionCost[i]) + "\t");
			System.out.print("14," + df.format(this.totalBilling[i]) + "\t");
			System.out.print("15," + df.format(this.manualExecutionCost[i]) + "\t");
			System.out.print("16," + df.format(this.WfSpendWithAutomation[i]) + "\t");
			System.out.print("17," + df.format(this.WfSpendWithoutAutomation[i]) + "\t");
			System.out.print("18," + df.format(this.gainInDollar[i]) + "\t");
			System.out.print("19," + df.format(this.gainPercent[i]) + "\t");
			System.out.print("20," + df.format(this.netSavings[i]) + "\t");
			System.out.println("");
		}	
		
		
		
		logger.debug("Summary & Cost Summary parameter calculation ends");
		
		logger.debug("JSON Object creation in prgress");
		this.outputJSON = createJSON();
	}
	
	
	/*
	 *  Method to create hashmap of month wise output data 
	 */
    private LinkedHashMap<String, String> createRow(int i) {
    	logger.trace("createRow function entered, with i = " + Integer.toString(i));
		LinkedHashMap<String, String> Headers = new LinkedHashMap<String, String>();
		
		Headers.put("Month", String.format ("%d", this.monthNumber[i]));
		Headers.put("% TD",String.format ("%.2f", this.percentTD[i]));
		Headers.put("Test Development TCs", String.format ("%.0f", this.testDevelopmentTC[i]));
		Headers.put("Cumulative Count", String.format ("%.0f", this.cumulativeCount[i]));
		Headers.put("Regression Execution Count",String.format ("%.0f", this.TCExecutionCountRegression[i]));
		Headers.put("Smoke Execution Count", String.format ("%.0f", this.TCExecutionCountSmoke[i]));
		Headers.put("Life Critical Execution Count", String.format ("%.0f", this.TCExecutionCountLC[i]));
		Headers.put("Total Count", String.format ("%.0f", this.totalCount[i]));
		Headers.put("Total Development Scope", String.format ("%.0f", this.totalDevelopmentScope[i]));
		Headers.put("Development Cost",String.format ("%.2f", this.totalDevelopmentCost[i]));
		Headers.put("Automation Execution Cost", String.format ("%.2f", this.automationExecutionCost[i]));
		Headers.put("Maintenance Cost", String.format ("%.2f", this.maintenanceCost[i]));
		Headers.put("Maintenance & Execution Cost", String.format ("%.2f", this.maintenanceAndExecutionCost[i]));
		Headers.put("Billing to Client", String.format ("%.2f", this.totalBilling[i]));
		Headers.put("Current Manual Execution Cost", String.format ("%.2f", this.manualExecutionCost[i]));
		Headers.put("Client Spend - With Automation Factory", String.format ("%.2f", this.WfSpendWithAutomation[i]));
		Headers.put("Client Spend - Without Automation Factory", String.format ("%.2f", this.WfSpendWithoutAutomation[i]));
		Headers.put("Gain", String.format ("%.2f", this.gainInDollar[i]));
		Headers.put("Gain Percentage", String.format ("%.2f", this.gainPercent[i]));
		Headers.put("Net Savings", String.format ("%.2f", this.netSavings[i]));
		
		logger.trace("createRow function exited");
		return Headers;			
	}
	
	/*
	 *  Method to create hashmap of summary of output data 
	 */
    private LinkedHashMap<String, String> createSummary() {
    	logger.trace("createSummary function entered");
		LinkedHashMap<String, String> mainNode = new LinkedHashMap<String, String>();
		
		mainNode.put("Total WF Spend w/o Automation Factory", String.format ("%.2f", this.totalSpendWithoutAutomation));
		mainNode.put("Total WF Spend with Automation Factory", String.format ("%.2f", this.totalSpendWithAutomation));
		mainNode.put("Gain Per Script", String.format ("%.2f", this.gainPerScript));
		mainNode.put("Development Cost ", String.format ("%.2f", this.developmentCost));
		mainNode.put("Total Gain", String.format ("%.2f", this.totalGain));
		mainNode.put("Automation Execution Cost", String.format ("%.2f", this.totalAutomationExecutionCost));
		mainNode.put("Billing to Wells Fargo", String.format ("%.2f", this.totalBillingToWF));
		mainNode.put("Maintenance Cost", String.format ("%.2f", this.totalMaintenanceCost));
		logger.trace("createRow function exited");
		return mainNode;			
	}
	
    /*
     * method to create JSONObject of year-wise cost summary data
     */
    private JSONObject createYearlyJSON(double Y1, double Y2, double Y3, double overall, JSONObject container, String key) {
    	logger.trace("createYearlyJSON function entered, with Y1 = " + Double.toString(Y1) + ", Y2 = " + Double.toString(Y2) + ", Y3 = " + Double.toString(Y3) + ", overall = " + Double.toString(overall) + ", key = " + key);
    	LinkedHashMap<String, String> temp = new LinkedHashMap<String, String>();
		temp.put("Y1", String.format ("%.2f", Y1));
		temp.put("Y2", String.format ("%.2f", Y2));
		temp.put("Y3", String.format ("%.2f", Y3));
		temp.put("Total", String.format ("%.2f", overall));
		JSONObject yearWise = new JSONObject();
		yearWise.putAll(temp);
		container.put(key, yearWise);
		logger.trace("createYearlyJSON function exited");
		return container;
    }
    
    /*
     * method to create JSONObject of year-wise cost summary data
     */
    private JSONObject createCostSummary() {
    	logger.trace("createCostSummary function entered");
    	JSONObject container = new JSONObject();
		container = createYearlyJSON(this.totalSpendWithoutAutomationY1, this.totalSpendWithoutAutomationY2, this.totalSpendWithoutAutomationY3, this.totalSpendWithoutAutomation, container, "Total WF Spend without Automation Factory");
		container = createYearlyJSON(this.developmentCostY1, this.developmentCostY2, this.developmentCostY3, this.developmentCost, container, "Development Cost");
		container = createYearlyJSON(this.totalGainY1, this.totalGainY2, this.totalGainY3, this.totalGain, container, "Total Gain");
		container = createYearlyJSON(this.totalAutomationExecutionCostY1, this.totalAutomationExecutionCostY2, this.totalAutomationExecutionCostY3, this.totalAutomationExecutionCost, container, "Automation Execution Cost");
		container = createYearlyJSON(this.totalBillingToWFY1, this.totalBillingToWFY2, this.totalBillingToWFY3, this.totalBillingToWF, container, "Billing to Wells Fargo");
		container = createYearlyJSON(this.maintenanceCostY1, this.maintenanceCostY2, this.maintenanceCostY3, this.totalMaintenanceCost, container, "Maintenance Cost");
		container = createYearlyJSON(this.EplusMCostY1, this.EplusMCostY2, this.EplusMCostY3, this.EplusMCost, container, "E+M Cost");
		container = createYearlyJSON(this.manualExecutionCostInfeasibleY1, this.manualExecutionCostInfeasibleY2, this.manualExecutionCostInfeasibleY3, this.manualExecutionCostInfeasible, container, "Manual Execution Cost (Infeasible scripts)");
		container = createYearlyJSON(this.totalSpendWithAutomationY1, this.totalSpendWithAutomationY2, this.totalSpendWithAutomationY3, this.totalSpendWithAutomation, container, "Total WF Spend with Automation Factory");
		container.put("Gain Per Script", String.format ("%.2f", this.gainPerScript));
		container.put("Gain Percent", String.format ("%.2f", this.totalGainPercent));
		
		logger.trace("createCostSummary function exited");
		return container;			
	}
    
	private JSONObject JsonObj(int Month, JSONObject json, JSONObject mainJson) {		
		mainJson.put("M" + Integer.toString(Month), json);			
		return mainJson;
	}	
	
	/*
	 * Method to generate JSON of output variables  
	 */
	public JSONObject createJSON(){
		logger.trace("createJSON function entered");
	    JSONObject mainNode = new JSONObject();
		
		for(int i=0;i<this.totalMonths; i++) {
			LinkedHashMap<String, String> RowData = createRow(i);				
			JSONObject temp = new JSONObject();				
			temp.putAll(RowData);				
			mainNode = JsonObj(i+1, temp, mainNode);
		}
		
		LinkedHashMap<String, String> summary = createSummary();
		JSONObject temp = new JSONObject();				
		temp.putAll(summary);	
		mainNode.put("Summary", temp);
		
		JSONObject costSummary = createCostSummary();
		mainNode.put("Cost Summary", costSummary);	
		
		logger.trace("createJSON function exited");
		return mainNode;
	}
}
