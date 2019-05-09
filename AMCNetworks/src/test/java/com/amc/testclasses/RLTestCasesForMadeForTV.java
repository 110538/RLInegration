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
public class RLTestCasesForMadeForTV extends AMCBaseClass {

	XSSFWorkbook workbook = null;
	String[][] dataBook = null;
	static String rlAssetIDValue = null;
	static String rlAssetTitleValue = null;

	public RLTestCasesForMadeForTV() {
		PageFactory.initElements(driver, this);
		workbook = initializeExcelSheet("AssestsIntegration.xlsx");
		dataBook = getDataFromExcel(workbook, "AssetInformation");
	}

	@Test(description = "Validate AMCN id of WOP")
	public void validateAMCNIDForMadeForTV() throws Exception {
		LoginModule login = new LoginModule(driver);
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForMadeForTV");
		String rlAssetIDValueString = getDataFromExcel(testCaseNo, 18, "AssetInformation");
		System.out.println(rlAssetIDValueString);
		login.validateRLValuesWithWOP(rlAssetIDValueString, "AMCN ID");
		login.validateRLValuesWithMP(rlAssetIDValueString, "AMCN ID");
	}

	@Test(description = "Validate Asset Title of WOP")
	public void validateAssetTitleForMadeForTV() throws Exception {
		LoginModule login = new LoginModule(driver);
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForMadeForTV");
		String rlAssetTitleValueString = getDataFromExcel(testCaseNo, 1, "AssetInformation");
		System.out.println(rlAssetTitleValueString);
		login.validateRLValuesWithWOP(rlAssetTitleValueString, "Title");
		login.validateRLValuesWithMP(rlAssetTitleValueString, "Title");
	}

	@Test(description = "Validate Asset Shpw Type of WOP")
	public void validateAssetShowTypeForMadeForTV() throws Exception {
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP("Movie", "ShowType");
		login.validateRLValuesWithMP("Movie", "ShowType");
	}

	@Test(description = "Validate Asset Source of WOP")
	public void validateAssetSourceForMadeForTV() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForMadeForTV");
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
	public void validateProductionModeForMadeForTV() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForMadeForTV");
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
	public void validateOriginForMadeForTV() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForMadeForTV");
		String assetSourceString = dataBook[testCaseNo][5];
		assetSourceString = "Licensed";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(assetSourceString, "Origin");
		if (!assetSourceString.contains("Acquired")) {
			login.validateRLValuesWithMP(assetSourceString, "Origin");
		}
	}

	@Test(description = "Validate Owner Network")
	public void validateOwnerNetworkForMadeForTV() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForMadeForTV");
		String ownerNetwork = dataBook[testCaseNo][5];
		ownerNetwork = ownerNetwork.substring(0, ownerNetwork.indexOf(" "));
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(ownerNetwork, "Owner Network");
		login.validateRLValuesWithMP(ownerNetwork, "Owner Network");
	}

	@Test(description = "Validate Cast Type")
	public void validateCastTypeForMadeForTV() throws Exception {
		readTestCaseNo("validateCastTypeForMadeForTV");
		String castType = "Director";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(castType, "Cast Type");
	}

	@Test(description = "Validate name of cast member")
	public void validateFirstNameOfCastMemberForMadeForTV() throws Exception {
		int testCaseNo = readTestCaseNo("validateFirstNameOfCastMemberForMadeForTV");
		String firstMemberOfCastMember = dataBook[testCaseNo][17];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(firstMemberOfCastMember, "First Name");
	}

	@Test(description = "Validate alternative title of the asset for AKA Type")
	public void validateAlternativeTitleForAKAForMadeForTV() throws Exception {
		int testCaseNo = readTestCaseNo("validateAlternativeTitleForAKAForMadeForTV");
		String alternativeTitleForAKA = dataBook[testCaseNo][7];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForAKA, "Alt Title");
	}

	@Test(description = "Validate alternative title type of the asset for AKA Type")
	public void validateAlternativeTitleTypeForAKAForMadeForTV() throws Exception {
		readTestCaseNo("validateAlternativeTitleTypeForAKAForMadeForTV");
		String alternativeTitleForAKA = "AKA";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForAKA, "Alt Title Type");
	}

	@Test(description = "Validate alternative title of the asset for Legal Type")
	public void validateAlternativeTitleForLegalForMadeForTV() throws Exception {
		int testCaseNo = readTestCaseNo("validateAlternativeTitleForLegalForMadeForTV");
		String alternativeTitleForLegal = dataBook[testCaseNo][8];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForLegal, "Alt Title For Legal");
	}

	@Test(description = "Validate alternative title type of the asset for Legal Type")
	public void validateAlternativeTitleTypeForLegalTypeForMadeForTV() throws Exception {
		readTestCaseNo("validateAlternativeTitleTypeForLegalTypeForMadeForTV");
		String alternativeTitleTypeForLegal = "Legal";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleTypeForLegal, "Alt Title Type For Legal");
	}

	@Test(description = "Validate title type of the asset")
	public void validateTitleTypeForMadeForTV() throws Exception {
		readTestCaseNo("validateTitleTypeForMadeForTV");
		String titleType = "Episode/Theatrical";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithMP(titleType, "Title Type");
	}

	@Test(description = "Validate release year of the asset")
	public void validateReleaseYearForMadeForTV() throws Exception {
		int testCaseNo = readTestCaseNo("validateReleaseYearForMadeForTV");
		String releaseYear = dataBook[testCaseNo][9];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(releaseYear, "Release Year");
		login.validateRLValuesWithMP(releaseYear, "Release Year");
	}

	@Test(description = "Validate TRT of the asset")
	public void validateTRTForMadeForTV() throws Exception {
		int testCaseNo = readTestCaseNo("validateTRTForMadeForTV");
		String durationInSeconds = getSecondsForDurationField(dataBook[testCaseNo][3]);
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(durationInSeconds, "Theatrical TRT");
		login.validateRLValuesWithMP(durationInSeconds, "Theatrical TRT");
	}

	@Test(description = "Validate Production Company of the asset")
	public void validateProductionCompanyForMadeForTV() throws Exception {
		int testCaseNo = readTestCaseNo("validateProductionCompanyForMadeForTV");
		String productionCompany = dataBook[testCaseNo][6];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(productionCompany, "Production Company");
		login.validateRLValuesWithMP(productionCompany, "Production Company");
	}

}
