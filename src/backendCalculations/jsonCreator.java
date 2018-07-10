package backendCalculations;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.LinkedHashMap;

public class jsonCreator implements Serializable {

    private static String[] test = new String[17];

    private static JSONObject mainNode = new JSONObject();
    private static JSONObject finalJSON = new JSONObject();
    
    private static String PercentageTestDevelopment = "";
    private static String DevelopmentTestCases = "";
    private static String CumulativeCount = "";
    private static String RegressionExecutionCount = "";
    private static String SmokeExecutionCount = "";
    private static String LifeCriticalExecutionCount = "";
    private static String TotalCount = "";
    private static String TotalDevelopmentScope = "";
    private static String DevelopmentCost = "";
    private static String ExecutionCost = "";
    private static String MaintenanceCost = "";
    private static String BillingToClient = "";
    private static String CuurentManualExecutionCost = "";
    private static String ClientSpend_WAF = "";
    private static String ClientSpend_WoAF = "";
    private static String Gain = "";
    private static String GainP = "";

    /**
     * 
     * @param Data - Store the values of the output table each row in an array and pass it on this function
     */
    
    private static void setValues(String[] Data){

        PercentageTestDevelopment = Data[0];
        DevelopmentTestCases = Data[1];
        CumulativeCount = Data[2];
        RegressionExecutionCount = Data[3];
        SmokeExecutionCount = Data[4];
        LifeCriticalExecutionCount = Data[5];
        TotalCount = Data[6];
        TotalDevelopmentScope = Data[7];
        DevelopmentCost = Data[8];
        ExecutionCost = Data[9];
        MaintenanceCost = Data[10];
        BillingToClient = Data[11];
        CuurentManualExecutionCost = Data[12];
        ClientSpend_WAF = Data[13];
        ClientSpend_WoAF = Data[14];
        Gain = Data[15];
        GainP = Data[16];
        
    }
    private static void clear(){

        PercentageTestDevelopment  = ""; 
        DevelopmentTestCases  = ""; 
        CumulativeCount  = ""; 
        RegressionExecutionCount  = ""; 
        SmokeExecutionCount  = ""; 
        LifeCriticalExecutionCount  = ""; 
        TotalCount  = ""; 
        TotalDevelopmentScope  = ""; 
        DevelopmentCost  = ""; 
        ExecutionCost  = ""; 
        MaintenanceCost  = "";
        BillingToClient  = "";
        CuurentManualExecutionCost  = "";
        ClientSpend_WAF  = "";
        ClientSpend_WoAF  = "";
        Gain  = "";
        GainP  = "";

    }

    private static LinkedHashMap<String, String> createRow(String PTD, String TDTC, String CC, String REC, String SEC, String LCEC, String TC,
                                                           String TDS, String DC, String EC, String MC, String BWF, String CMEC, String CSWAF,
                                                           String CSWoAF, String Gain, String GainP) {

        LinkedHashMap<String, String> Headers = new LinkedHashMap<String, String>();

        Headers.put("Percentage Test Development", PTD);
        Headers.put("Test Development Test Cases", TDTC);
        Headers.put("Cumulative Count", CC);
        Headers.put("Regression Execution Count", REC);
        Headers.put("Smoke Execution Count", SEC);
        Headers.put("Life Critical Execution Count", LCEC);
        Headers.put("Total Count", TC);
        Headers.put("Total Development Scope", TDS);
        Headers.put("Development Cost", DC);
        Headers.put("Execution Cost", EC);
        Headers.put("Maintenance Cost", MC);
        Headers.put("Billing to Client", BWF);
        Headers.put("Current Manual Execution Cost", CMEC);
        Headers.put("Client Spend - With Automation Factory", CSWAF);
        Headers.put("Client Spend - Without Automation Factory", CSWoAF);
        Headers.put("Gain", Gain);
        Headers.put("Gain Percentage", GainP);

        return Headers;

    }

    @Deprecated
    private static JSONObject JsonObj(int Month, JSONObject json, JSONObject mainJson) {

        mainJson.put("M" + Integer.toString(Month), json);

        return mainJson;
    }
    
    

    @Deprecated
    private static JSONObject createJSON(int Month){

        LinkedHashMap<String, String> RowData = createRow(PercentageTestDevelopment, DevelopmentTestCases, CumulativeCount, 
                RegressionExecutionCount, SmokeExecutionCount, LifeCriticalExecutionCount, TotalCount, TotalDevelopmentScope,
                DevelopmentCost, ExecutionCost, MaintenanceCost, BillingToClient, CuurentManualExecutionCost, ClientSpend_WAF,
                ClientSpend_WoAF, Gain, GainP);

        JSONObject temp = new JSONObject();

        temp.putAll(RowData);

        mainNode = JsonObj(Month, temp, mainNode);

        clear();
        
        return mainNode;

    }
}
