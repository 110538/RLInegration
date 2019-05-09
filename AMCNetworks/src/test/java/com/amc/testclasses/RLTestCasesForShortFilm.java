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
public class RLTestCasesForShortFilm extends AMCBaseClass {

	XSSFWorkbook workbook = null;
	String[][] dataBook = null;
	static String rlAssetIDValue = null;
	static String rlAssetTitleValue = null;

	public RLTestCasesForShortFilm() {
		PageFactory.initElements(driver, this);
		workbook = initializeExcelSheet("AssestsIntegration.xlsx");
		dataBook = getDataFromExcel(workbook, "AssetInformation");
	}

	@Test(description = "Validate AMCN id of WOP")
	public void validateAMCNIDForShortFilm() throws Exception {
		LoginModule login = new LoginModule(driver);
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForShortFilm");
		String rlAssetIDValueString = getDataFromExcel(testCaseNo, 18, "AssetInformation");
		login.validateRLValuesWithWOP(rlAssetIDValueString, "AMCN ID");
		login.validateRLValuesWithMP(rlAssetIDValueString, "AMCN ID");
	}

	@Test(description = "Validate Asset Title of WOP")
	public void validateAssetTitleForShortFilm() throws Exception {
		LoginModule login = new LoginModule(driver);
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForShortFilm");
		String rlAssetTitleValueString = getDataFromExcel(testCaseNo, 1, "AssetInformation");
		login.validateRLValuesWithWOP(rlAssetTitleValueString, "Title");
		login.validateRLValuesWithMP(rlAssetTitleValueString, "Title");
	}

	@Test(description = "Validate Asset Shpw Type of WOP")
	public void validateAssetShowTypeForShortFilm() throws Exception {
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP("Short Film", "ShowType");
		login.validateRLValuesWithMP("Short Film", "ShowType");
	}

	@Test(description = "Validate Asset Source of WOP")
	public void validateAssetSourceForShortFilm() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForShortFilm");
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
	public void validateProductionModeForShortFilm() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForShortFilm");
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
	public void validateOriginForShortFilm() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForShortFilm");
		String assetSourceString = dataBook[testCaseNo][5];
		assetSourceString = "Licensed";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(assetSourceString, "Origin");
		if (!assetSourceString.contains("Acquired")) {
			login.validateRLValuesWithMP(assetSourceString, "Origin");
		}
	}

	@Test(description = "Validate Owner Network")
	public void validateOwnerNetworkForShortFilm() throws Exception {
		int testCaseNo = readTestCaseNo("createNewRLAssetOfTypeForShortFilm");
		String ownerNetwork = dataBook[testCaseNo][5];
		ownerNetwork = ownerNetwork.substring(0, ownerNetwork.indexOf(" "));
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(ownerNetwork, "Owner Network");
		login.validateRLValuesWithMP(ownerNetwork, "Owner Network");
	}

	@Test(description = "Validate Cast Type")
	public void validateCastTypeForShortFilm() throws Exception {
		readTestCaseNo("validateCastTypeForShortFilm");
		String castType = "Director";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(castType, "Cast Type");
	}

	@Test(description = "Validate name of cast member")
	public void validateFirstNameOfCastMemberForShortFilm() throws Exception {
		int testCaseNo = readTestCaseNo("validateFirstNameOfCastMemberForShortFilm");
		String firstMemberOfCastMember = dataBook[testCaseNo][17];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(firstMemberOfCastMember, "First Name");
	}

	@Test(description = "Validate alternative title of the asset for AKA Type")
	public void validateAlternativeTitleForAKAForShortFilm() throws Exception {
		int testCaseNo = readTestCaseNo("validateAlternativeTitleForAKAForShortFilm");
		String alternativeTitleForAKA = dataBook[testCaseNo][7];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForAKA, "Alt Title");
	}

	@Test(description = "Validate alternative title type of the asset for AKA Type")
	public void validateAlternativeTitleTypeForAKAForShortFilm() throws Exception {
		readTestCaseNo("validateAlternativeTitleTypeForAKAForShortFilm");
		String alternativeTitleForAKA = "AKA";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForAKA, "Alt Title Type");
	}

	@Test(description = "Validate alternative title of the asset for Legal Type")
	public void validateAlternativeTitleForLegalForShortFilm() throws Exception {
		int testCaseNo = readTestCaseNo("validateAlternativeTitleForLegalForShortFilm");
		String alternativeTitleForLegal = dataBook[testCaseNo][8];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleForLegal, "Alt Title For Legal");
	}

	@Test(description = "Validate alternative title type of the asset for Legal Type")
	public void validateAlternativeTitleTypeForLegalTypeForShortFilm() throws Exception {
		readTestCaseNo("validateAlternativeTitleTypeForLegalTypeForShortFilm");
		String alternativeTitleTypeForLegal = "Legal";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(alternativeTitleTypeForLegal, "Alt Title Type For Legal");
	}

	@Test(description = "Validate title type of the asset")
	public void validateTitleTypeForShortFilm() throws Exception {
		readTestCaseNo("validateTitleTypeForShortFilm");
		String titleType = "Episode/Theatrical";
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithMP(titleType, "Title Type");
	}

	@Test(description = "Validate release year of the asset")
	public void validateReleaseYearForShortFilm() throws Exception {
		int testCaseNo = readTestCaseNo("validateReleaseYearForShortFilm");
		String releaseYear = dataBook[testCaseNo][9];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(releaseYear, "Release Year");
		login.validateRLValuesWithMP(releaseYear, "Release Year");
	}

	@Test(description = "Validate TRT of the asset")
	public void validateTRTForShortFilm() throws Exception {
		int testCaseNo = readTestCaseNo("validateTRTForShortFilm");
		String durationInSeconds = getSecondsForDurationField(dataBook[testCaseNo][3]);
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(durationInSeconds, "Theatrical TRT");
		login.validateRLValuesWithMP(durationInSeconds, "Theatrical TRT");
	}

	@Test(description = "Validate Production Company of the asset")
	public void validateProductionCompanyForShortFilm() throws Exception {
		int testCaseNo = readTestCaseNo("validateProductionCompanyForShortFilm");
		String productionCompany = dataBook[testCaseNo][6];
		LoginModule login = new LoginModule(driver);
		login.validateRLValuesWithWOP(productionCompany, "Production Company");
		login.validateRLValuesWithMP(productionCompany, "Production Company");
	}

}
