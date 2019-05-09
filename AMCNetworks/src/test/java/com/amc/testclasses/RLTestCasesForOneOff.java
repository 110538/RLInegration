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
public class RLTestCasesForOneOff extends AMCBaseClass {

	XSSFWorkbook workbook = null;
	String[][] dataBook = null;
	static String rlAssetIDValue = null;
	static String rlAssetTitleValue = null;

	public RLTestCasesForOneOff() {
		PageFactory.initElements(driver, this);
		workbook = initializeExcelSheet("AssestsIntegration.xlsx");
		dataBook = getDataFromExcel(workbook, "AssetInformation");
	}

	@Test(description = "Validate AMCN id of WOP")
	public void validateAMCNIDForOneOff() throws Exception {
		LoginModule login = new LoginModule(driver);
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForOneOff");
		String rlAssetIDValueString = getDataFromExcel(testCaseNo, 18, "AssetInformation");
		System.out.println(rlAssetIDValueString);
		login.validateRLValuesWithWOP(rlAssetIDValueString, "AMCN ID");
		login.validateRLValuesWithMP(rlAssetIDValueString, "AMCN ID");
	}

	@Test(description = "Validate Asset Title of WOP")
	public void validateAssetTitleForOneOff() throws Exception {
		LoginModule login = new LoginModule(driver);
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForOneOff");
		String rlAssetTitleValueString = getDataFromExcel(testCaseNo, 1, "AssetInformation");
		System.out.println(rlAssetTitleValueString);
		login.validateRLValuesWithWOP(rlAssetTitleValueString, "Title");
		login.validateRLValuesWithMP(rlAssetTitleValueString, "Title");
	}

	@Test(description = "Validate Asset Shpw Type of WOP")
	public void validateAssetShowTypeForOneOff() throws Exception {
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP("Special", "ShowType");
		login.validateRLValuesWithMP("Special", "ShowType");
	}

	@Test(description = "Validate Asset Source of WOP")
	public void validateAssetSourceForOneOff() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForOneOff");
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
	public void validateProductionModeForOneOff() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForOneOff");
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
	public void validateOriginForOneOff() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForOneOff");
		String assetSourceString = dataBook[testCaseNo][5];
		assetSourceString = "Licensed";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(assetSourceString, "Origin");
		if (!assetSourceString.contains("Acquired")) {
			login.validateRLValuesWithMP(assetSourceString, "Origin");
		}
	}

	@Test(description = "Validate Owner Network")
	public void validateOwnerNetworkForOneOFf() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForOneOff");
		String ownerNetwork = dataBook[testCaseNo][5];
		ownerNetwork = ownerNetwork.substring(0, ownerNetwork.indexOf(" "));
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(ownerNetwork, "Owner Network");
		login.validateRLValuesWithMP(ownerNetwork, "Owner Network");
	}

	@Test(description = "Validate Cast Type")
	public void validateCastTypeForOneOff() throws Exception {
		readTestCaseNo("validateCastType");
		String castType = "Director";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(castType, "Cast Type");
	}

	@Test(description = "Validate name of cast member")
	public void validateFirstNameOfCastMemberForOneOff() throws Exception {
		int testCaseNo = readTestCaseNo("validateFirstNameOfCastMemberForOneOff");
		String firstMemberOfCastMember = dataBook[testCaseNo][17];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(firstMemberOfCastMember, "First Name");
	}

	@Test(description = "Validate alternative title of the asset for AKA Type")
	public void validateAlternativeTitleForAKAForOneOff() throws Exception {
		int testCaseNo = readTestCaseNo("validateAlternativeTitleForAKAForOneOff");
		String alternativeTitleForAKA = dataBook[testCaseNo][7];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForAKA, "Alt Title");
	}

	@Test(description = "Validate alternative title type of the asset for AKA Type")
	public void validateAlternativeTitleTypeForAKAForOneOff() throws Exception {
		readTestCaseNo("validateAlternativeTitleTypeForAKAForOneOff");
		String alternativeTitleForAKA = "AKA";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForAKA, "Alt Title Type");
	}

	@Test(description = "Validate alternative title of the asset for Legal Type")
	public void validateAlternativeTitleForLegalForOneOff() throws Exception {
		int testCaseNo = readTestCaseNo("validateAlternativeTitleForLegalForOneOff");
		String alternativeTitleForLegal = dataBook[testCaseNo][8];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForLegal, "Alt Title For Legal");
	}

	@Test(description = "Validate alternative title type of the asset for Legal Type")
	public void validateAlternativeTitleTypeForLegalTypeForOneOff() throws Exception {
		readTestCaseNo("validateAlternativeTitleTypeForLegalTypeForOneOff");
		String alternativeTitleTypeForLegal = "Legal";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleTypeForLegal, "Alt Title Type For Legal");
	}

	@Test(description = "Validate title type of the asset")
	public void validateTitleTypeForOneOff() throws Exception {
		readTestCaseNo("validateTitleTypeForOneOff");
		String titleType = "Episode/Theatrical";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithMP(titleType, "Title Type");
	}

	@Test(description = "Validate release year of the asset")
	public void validateReleaseYearForOneOff() throws Exception {
		int testCaseNo = readTestCaseNo("validateReleaseYearForOneOff");
		String releaseYear = dataBook[testCaseNo][9];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(releaseYear, "Release Year");
		login.validateRLValuesWithMP(releaseYear, "Release Year");
	}

	@Test(description = "Validate TRT of the asset")
	public void validateTRTForOneOff() throws Exception {
		int testCaseNo = readTestCaseNo("validateTRTForOneOff");
		String durationInSeconds = getSecondsForDurationField(dataBook[testCaseNo][3]);
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(durationInSeconds, "Theatrical TRT");
		login.validateRLValuesWithMP(durationInSeconds, "Theatrical TRT");
	}

	@Test(description = "Validate Production Company of the asset")
	public void validateProductionCompanyForOneOff() throws Exception {
		int testCaseNo = readTestCaseNo("validateProductionCompanyForOneOff");
		String productionCompany = dataBook[testCaseNo][6];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(productionCompany, "Production Company");
		login.validateRLValuesWithMP(productionCompany, "Production Company");
	}

}
