package com.amc.testclasses;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.amc.baseclass.AMCBaseClass;
import com.amc.pageobjects.LoginModule;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

@Stories("RLRegressionValidation")
@Features("RLRegressionValidation")
public class RLTestCasesForSeries extends AMCBaseClass {

	XSSFWorkbook workbook = null;
	String[][] dataBook = null;

	public RLTestCasesForSeries() {
		PageFactory.initElements(driver, this);
		workbook = initializeExcelSheet("AssestsIntegration.xlsx");
		dataBook = getDataFromExcel(workbook, "AssetInformation");
	}

	@Test(description = "Validate AMCN id of WOP")
	public void validateAMCNID() throws Exception {
		LoginModule login = new LoginModule(driver);
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeSeries");
		String rlAssetIDValueString = getDataFromExcel(testCaseNo, 18, "AssetInformation");
		login.validateRLValuesWithWOP(rlAssetIDValueString, "AMCN ID");
		login.validateRLValuesWithMP(rlAssetIDValueString, "AMCN ID");
	}

	@Test(description = "Validate Asset Title of WOP")
	public void validateAssetTitle() throws Exception {
		LoginModule login = new LoginModule(driver);
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeSeries");
		String rlAssetTitleValueString = getDataFromExcel(testCaseNo, 1, "AssetInformation");
		login.validateRLValuesWithWOP(rlAssetTitleValueString, "Title");
		login.validateRLValuesWithMP(rlAssetTitleValueString, "Title");
	}

	@Test(description = "Validate Asset Shpw Type of WOP")
	public void validateAssetShowType() throws Exception {
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP("Series", "ShowType");
		login.validateRLValuesWithMP("Series", "ShowType");
	}

	@Test(description = "Validate Asset Source of WOP")
	public void validateAssetSource() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeSeries");
		String assetSourceString = dataBook[testCaseNo][5];
		if (assetSourceString.contains("Acquired")) {
			assetSourceString = "Licensed Acquired";
		} else {
			assetSourceString = "Original";
		}
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(assetSourceString, "Asset Source");
		login.validateRLValuesWithMP(assetSourceString, "Asset Source");
	}

	@Test(description = "Validate Production Mode of WOP")
	public void validateProductionMode() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeSeries");
		String assetSourceString = dataBook[testCaseNo][5];
		if (!assetSourceString.contains("Acquired")) {
			if (assetSourceString.contains("Scripted")) {
				assetSourceString = "Scripted";
			} else if (assetSourceString.contains("UnScripted")) {
				assetSourceString = "UnScripted";
			} else {
				assetSourceString = " ";
			}
			LoginModule login = new LoginModule(driver);
			login.validateRLValuesWithWOP(assetSourceString, "Production Mode");
			login.validateRLValuesWithMP(assetSourceString, "Production Mode");
		}
	}

	@Test(description = "Validate Origin of WOP")
	public void validateOrigin() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeSeries");
		String assetSourceString = dataBook[testCaseNo][5];
		assetSourceString = "Licensed";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(assetSourceString, "Origin");
		if (!assetSourceString.contains("Acquired")) {
			login.validateRLValuesWithMP(assetSourceString, "Origin");
		}
	}

	@Test(description = "Validate Owner Network")
	public void validateOwnerNetwork() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeSeries");
		String ownerNetwork = dataBook[testCaseNo][5];
		ownerNetwork = ownerNetwork.substring(0, ownerNetwork.indexOf(" "));
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(ownerNetwork, "Owner Network");
		login.validateRLValuesWithMP(ownerNetwork, "Owner Network");
	}

	@Test(description = "Validate Cast Type")
	public void validateCastType() throws Exception {
		readTestCaseNo("validateCastType");
		String castType = "Director";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(castType, "Cast Type");
	}

	@Test(description = "Validate name of cast member")
	public void validateFirstNameOfCastMember() throws Exception {
		int testCaseNo = readTestCaseNo("validateFirstNameOfCastMember");
		String firstMemberOfCastMember = dataBook[testCaseNo][17];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(firstMemberOfCastMember, "First Name");
	}

	@Test(description = "Validate alternative title of the asset for AKA Type")
	public void validateAlternativeTitleForAKA() throws Exception {
		int testCaseNo = readTestCaseNo("validateAlternativeTitleForAKA");
		String alternativeTitleForAKA = dataBook[testCaseNo][7];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForAKA, "Alt Title");
	}

	@Test(description = "Validate alternative title type of the asset for AKA Type")
	public void validateAlternativeTitleTypeForAKA() throws Exception {
		readTestCaseNo("validateAlternativeTitleTypeForAKA");
		String alternativeTitleForAKA = "AKA";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForAKA, "Alt Title Type");
	}

	@Test(description = "Validate alternative title of the asset for Legal Type")
	public void validateAlternativeTitleForLegal() throws Exception {
		int testCaseNo = readTestCaseNo("validateAlternativeTitleForLegal");
		String alternativeTitleForLegal = dataBook[testCaseNo][8];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForLegal, "Alt Title For Legal");
	}

	@Test(description = "Validate alternative title type of the asset for Legal Type")
	public void validateAlternativeTitleTypeForLegalType() throws Exception {
		readTestCaseNo("validateAlternativeTitleTypeForLegalType");
		String alternativeTitleTypeForLegal = "Legal";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleTypeForLegal, "Alt Title Type For Legal");
	}

	@Test(description = "Validate title type of the asset")
	public void validateTitleType() throws Exception {
		readTestCaseNo("validateTitleType");
		String titleType = "Series";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(titleType, "Title Type");
		login.validateRLValuesWithMP(titleType, "Title Type");
	}
	
	@Test(description = "Validate total number of episodes in the series")
	public void validateTotalNumberOfEpisodes() throws Exception {
		int testCaseNo = readTestCaseNo("validateTotalNumberOfEpisodes");
		String totalNumberOfEpisodes = dataBook[testCaseNo][16];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithMP(totalNumberOfEpisodes, "TotalNumberOfEpisodes");
	}

}
