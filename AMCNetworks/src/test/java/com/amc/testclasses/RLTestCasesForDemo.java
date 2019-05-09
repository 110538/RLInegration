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
public class RLTestCasesForDemo extends AMCBaseClass {

	XSSFWorkbook workbook = null;
	String[][] dataBook = null;
	static String rlAssetIDValue = null;
	static String rlAssetTitleValue = null;

	public RLTestCasesForDemo() {
		PageFactory.initElements(driver, this);
		workbook = initializeExcelSheet("AssestsIntegration.xlsx");
		dataBook = getDataFromExcel(workbook, "AssetInformation");
	}

	@Test(description = "Validate AMCN id of WOP")
	public void validateAMCNIDForDemo() throws Exception {
		LoginModule login = new LoginModule(driver);
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForDemo");
		String rlAssetIDValueString = getDataFromExcel(testCaseNo, 18, "AssetInformation");
		login.validateRLValuesWithWOP(rlAssetIDValueString, "AMCN ID");
		login.validateRLValuesWithMP(rlAssetIDValueString, "AMCN ID");
	}

	@Test(description = "Validate Asset Title of WOP")
	public void validateAssetTitleForDemo() throws Exception {
		LoginModule login = new LoginModule(driver);
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForDemo");
		String rlAssetTitleValueString = getDataFromExcel(testCaseNo, 1, "AssetInformation");
		login.validateRLValuesWithWOP(rlAssetTitleValueString, "Title");
		login.validateRLValuesWithMP(rlAssetTitleValueString, "Title");
	}

	@Test(description = "Validate Asset Shpw Type of WOP")
	public void validateAssetShowTypeForDemo() throws Exception {
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP("Pilot", "ShowType");
		login.validateRLValuesWithMP("Pilot", "ShowType");
	}

	@Test(description = "Validate Asset Source of WOP")
	public void validateAssetSourceForDemo() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForDemo");
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
	public void validateProductionModeForDemo() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForDemo");
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
	public void validateOriginForDemo() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForDemo");
		String assetSourceString = dataBook[testCaseNo][5];
		assetSourceString = "Licensed";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(assetSourceString, "Origin");
		if (!assetSourceString.contains("Acquired")) {
			login.validateRLValuesWithMP(assetSourceString, "Origin");
		}
	}

	@Test(description = "Validate Owner Network")
	public void validateOwnerNetworkForDemo() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForDemo");
		String ownerNetwork = dataBook[testCaseNo][5];
		ownerNetwork = ownerNetwork.substring(0, ownerNetwork.indexOf(" "));
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(ownerNetwork, "Owner Network");
		login.validateRLValuesWithMP(ownerNetwork, "Owner Network");
	}

	@Test(description = "Validate Cast Type")
	public void validateCastTypeForDemo() throws Exception {
		readTestCaseNo("validateCastTypeForDemo");
		String castType = "Director";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(castType, "Cast Type");
	}

	@Test(description = "Validate name of cast member")
	public void validateFirstNameOfCastMemberForDemo() throws Exception {
		int testCaseNo = readTestCaseNo("validateFirstNameOfCastMemberForDemo");
		String firstMemberOfCastMember = dataBook[testCaseNo][17];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(firstMemberOfCastMember, "First Name");
	}

	@Test(description = "Validate alternative title of the asset for AKA Type")
	public void validateAlternativeTitleForAKAForDemo() throws Exception {
		int testCaseNo = readTestCaseNo("validateAlternativeTitleForAKAForDemo");
		String alternativeTitleForAKA = dataBook[testCaseNo][7];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForAKA, "Alt Title");
	}

	@Test(description = "Validate alternative title type of the asset for AKA Type")
	public void validateAlternativeTitleTypeForAKAForDemo() throws Exception {
		readTestCaseNo("validateAlternativeTitleTypeForAKAForDemo");
		String alternativeTitleForAKA = "AKA";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForAKA, "Alt Title Type");
	}

	@Test(description = "Validate alternative title of the asset for Legal Type")
	public void validateAlternativeTitleForLegalForDemo() throws Exception {
		int testCaseNo = readTestCaseNo("validateAlternativeTitleForLegalForDemo");
		String alternativeTitleForLegal = dataBook[testCaseNo][8];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForLegal, "Alt Title For Legal");
	}

	@Test(description = "Validate alternative title type of the asset for Legal Type")
	public void validateAlternativeTitleTypeForLegalTypeForDemo() throws Exception {
		readTestCaseNo("validateAlternativeTitleTypeForLegalTypeForDemo");
		String alternativeTitleTypeForLegal = "Legal";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleTypeForLegal, "Alt Title Type For Legal");
	}

	@Test(description = "Validate title type of the asset")
	public void validateTitleTypeForDemo() throws Exception {
		readTestCaseNo("validateTitleTypeForDemo");
		String titleType = "Episode/Theatrical";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithMP(titleType, "Title Type");
	}

	@Test(description = "Validate release year of the asset")
	public void validateReleaseYearForDemo() throws Exception {
		int testCaseNo = readTestCaseNo("validateReleaseYearForDemo");
		String releaseYear = dataBook[testCaseNo][9];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(releaseYear, "Release Year");
		login.validateRLValuesWithMP(releaseYear, "Release Year");
	}

	@Test(description = "Validate TRT of the asset")
	public void validateTRTForDemo() throws Exception {
		int testCaseNo = readTestCaseNo("validateTRTForDemo");
		String durationInSeconds = getSecondsForDurationField(dataBook[testCaseNo][3]);
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(durationInSeconds, "Theatrical TRT");
		login.validateRLValuesWithMP(durationInSeconds, "Theatrical TRT");
	}

	@Test(description = "Validate Production Company of the asset")
	public void validateProductionCompanyForDemo() throws Exception {
		int testCaseNo = readTestCaseNo("validateProductionCompanyForDemo");
		String productionCompany = dataBook[testCaseNo][6];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(productionCompany, "Production Company");
		login.validateRLValuesWithMP(productionCompany, "Production Company");
	}

}
