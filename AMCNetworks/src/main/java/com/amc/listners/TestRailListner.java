package com.amc.listners;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.amc.baseclass.AMCBaseClass;

public class TestRailListner extends AMCBaseClass implements ITestListener {

	HashMap<String, Integer> stringValue = null;
	Properties prop;

	public TestRailListner() {
		stringValue = storeExcelDataInHashMap();
		prop = propHandler.get();
	}

	
	private APIClient initializeTestRailSetup() {
		APIClient client = new APIClient(prop.getProperty("testRailURL"));
		client.setUser(prop.getProperty("testRailUserName"));
		client.setPassword(prop.getProperty("testRailPassword"));
		return client;
	} 

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Test case started");

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onTestSuccess(ITestResult result) {
		int testCaseID = stringValue.get(result.getMethod().getMethodName());
		int testRunID = Integer.parseInt(prop.getProperty("testRailRunID"));
		APIClient client = initializeTestRailSetup();
		@SuppressWarnings("rawtypes")
		Map data = new HashMap();
		data.put("status_id", new Integer(1));
		data.put("comment", "This test case is passed!");
		try {
			client.sendPost("add_result_for_case/" + testRunID + "/" + testCaseID, data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}  
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onTestFailure(ITestResult result) {
		int testCaseID = stringValue.get(result.getMethod().getMethodName());
		int testRunID = Integer.parseInt(prop.getProperty("testRailRunID"));
		APIClient client = initializeTestRailSetup();
		@SuppressWarnings("rawtypes")
		Map data = new HashMap();
		data.put("status_id", new Integer(5));
		data.put("comment", "This test failed due to" + result.getThrowable());
		try {
			client.sendPost("add_result_for_case/" + testRunID + "/" + testCaseID, data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	//	currentDriver.get().quit();  
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onTestSkipped(ITestResult result) {
		int testCaseID = stringValue.get(result.getMethod().getMethodName());
		int testRunID = Integer.parseInt(prop.getProperty("testRailRunID"));
		APIClient client = initializeTestRailSetup();
		@SuppressWarnings("rawtypes")
		Map data = new HashMap();
		data.put("status_id", new Integer(4));
		data.put("comment", "This test skipped due to" + result.getThrowable());
		try {
			client.sendPost("add_result_for_case/" + testRunID + "/" + testCaseID, data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		//currentDriver.get().quit(); 
	} 

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {

	}

}
