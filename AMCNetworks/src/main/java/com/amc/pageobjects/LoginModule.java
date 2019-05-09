package com.amc.pageobjects;

import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.amc.baseclass.AMCBaseClass;

public class LoginModule extends AMCBaseClass {

	XSSFWorkbook workbook = null;
	String[][] dataBook = null;

	@FindBy(id = "userNameInput")
	private WebElement userNameField;

	@FindBy(id = "passwordInput")
	private WebElement passwordField;

	@FindBy(id = "submitButton")
	private WebElement submitButton;


	public LoginModule(WebDriver driver) {
		PageFactory.initElements(driver, this);
		workbook = initializeExcelSheet("AssestsIntegration.xlsx");
		dataBook = getDataFromExcel(workbook, "Login");
	}

	private void enterUsername(int rowNumber) {
		String userName = dataBook[rowNumber][0].toString();
		logStepMessage("Enter user name as:" + userName);
		setData(userNameField, userName);
	}

	private void enterPassword(int rowNumber) {
		String password = dataBook[rowNumber][1].toString();
		logStepMessage("Enter password as:" + password);
		setData(passwordField, password);
	}

	private void clickOnSubmitButton() {
		logger.info("Click On submit Button");
		logStepMessage("Click On submit Button");
		clickOnElement(submitButton);
	}

	private LoginModule login(int rowNumber) throws Exception {
		enterUsername(rowNumber);
		enterPassword(rowNumber);
		attachScreen(driver);
		clickOnSubmitButton();
		attachScreen(driver);
		return this;
	}

	public CreateAssetRightsLogic clickOnRightsLogin(int rowNumber) throws Exception {
		login(rowNumber);
		return new CreateAssetRightsLogic(driver);
	}

	public void validateRLValuesWithWOP(String RLValue, String WOPparameter) throws Exception {
		ApiExecutionTypes apiExecutionTypes = new ApiExecutionTypes();
		ArrayList<String> arrayTest = apiExecutionTypes.getValue("WOP", WOPparameter);
		for (String actualValue : arrayTest) {
			logStepMessage(String.format("Validating WOP API value %s with test data value %s",actualValue, RLValue));
			if (actualValue.equals(RLValue)) {
				Assert.assertEquals(RLValue, actualValue);
			} else {
				Assert.fail(String.format("Expected WOP value was %s but found %s", RLValue, actualValue));
			}
		}
	}

	public void validateRLValuesWithMP(String RLValue, String MPparameter) throws Exception {
		ApiExecutionTypes apiExecutionTypes = new ApiExecutionTypes();
		ArrayList<String> arrayTest = apiExecutionTypes.getValue("MP", MPparameter);
		for (String actualValue : arrayTest) {
			logStepMessage(String.format("Validating MP API value %s with test data value %s",actualValue, RLValue));
			if (actualValue.equals(RLValue)) {
				Assert.assertEquals(RLValue, actualValue);
			} else {
				Assert.fail(String.format("Expected MP value was %s but found %s", RLValue, actualValue));
			}
		}
	}

}
