package com.amc.pageobjects;

import java.awt.AWTException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.amc.baseclass.AMCBaseClass;

public class CreateAssetRightsLogic extends AMCBaseClass {

	// public static ThreadLocal<String> rlAssetIDString = new
	// ThreadLocal<String>();
	HashMap<String, String> hashMapString = new HashMap<String, String>();

	@FindBy(xpath = "//span[contains(text(),'Asset Maintenance')]")
	private WebElement assetMaintenanceLink;

	@FindBy(xpath = "//span[contains(text(),'New')]/parent::a")
	private WebElement assetMaintenanceNewButton;

	@FindBy(xpath = "//span[contains(text(),'Program')]/parent::a")
	private WebElement assetMaintenanceProgramButton;

	@FindBy(xpath = "//span[contains(text(),'Theatrical')]/parent::a")
	private WebElement assetMaintenanceTheatricalButton;

	@FindBy(id = "lstAssetType")
	private WebElement assetType;

	@FindBy(id = "txtAssetTitle")
	private WebElement assetTitle;

	@FindBy(name = "imgVendor")
	private WebElement assetVendor;

	@FindBy(id = "txtName")
	private WebElement assetVendorName;

	@FindBy(name = "btnFind")
	private WebElement findButton;

	@FindBy(id = "btnSelect")
	private WebElement selectButton;

	@FindBy(id = "lstSBU")
	private WebElement sourceBusinessUnit;

	@FindBy(name = "WMEDuration")
	private WebElement assetDuration;

	@FindBy(id = "lstAssetStatus")
	private WebElement assetStatus;

	@FindBy(id = "lstAsset_Source")
	private WebElement assetSource;

	@FindBy(xpath = "//span[contains(text(),'Save')]/parent::a")
	private WebElement saveButton;

	@FindBy(xpath = "//span[@id='lblMsg']")
	private WebElement infoMessage;

	@FindBy(xpath = "//span[contains(text(),'Details')]")
	private WebElement detailsTab;

	@FindBy(xpath = "//span[contains(text(),'General')]")
	private WebElement generalTab;

	@FindBy(xpath = "//span[contains(text(),'Contributors')]")
	private WebElement contributorsTab;

	@FindBy(xpath = "//table[@data-ig='x:74111462.5:mkr:dataTbl.hdn']/tbody/tr/td[2]")
	private WebElement firstAlternativeTitle;

	@FindBy(xpath = "//table[@data-ig='x:74111462.5:mkr:dataTbl.hdn']/tbody/tr[2]/td[2]")
	private WebElement secondAlternativeTitle;

	@FindBy(xpath = "//table[@data-ig='x:74111462.5:mkr:dataTbl.hdn']/tbody/tr/td[3]")
	private WebElement firstAlternativeTitleType;

	@FindBy(xpath = "//table[@data-ig='x:74111462.5:mkr:dataTbl.hdn']/tbody/tr[2]/td[3]")
	private WebElement secondAlternativeTitleType;

	@FindBy(xpath = "//table[@data-ig='x:74111462.5:mkr:dataTbl.hdn']/tbody/tr/td[4]")
	private WebElement firstAlternativeTitleNetwork;

	@FindBy(xpath = "//table[@data-ig='x:74111462.5:mkr:dataTbl.hdn']/tbody/tr[2]/td[4]")
	private WebElement secondAlternativeTitleNetwork;

	@FindBy(id = "DetailsTab1_Group1_lstReleaseYear")
	private WebElement releaseYear;

	@FindBy(xpath = "//iframe[contains(@src,'/Asset_Maintenance/Home.aspx')]")
	private WebElement assetMaintainanceFrame;

	@FindBy(xpath = "//iframe[contains(@src,'/Asset_Maintenance/Details.aspx')]")
	private WebElement assetMaintainanceDetailsFrame;

	@FindBy(xpath = "//iframe[contains(@src,'/Asset_Maintenance/Contributors_Tab.aspx')]")
	private WebElement assetMaintainanceContributorsTabFrame;

	@FindBy(xpath = "//iframe[contains(@src,'/asset_maintenance/contEdit.aspx')]")
	private WebElement assetMaintainanceContributorsEditFrame;

	@FindBy(xpath = "//iframe[contains(@src,'/List_Maintenance/Contributor_Search.aspx')]")
	private WebElement assetMaintainanceContributorsSearchFrame;

	@FindBy(id = "btnEdit")
	private WebElement assetMaintainanceContributorsButtonEdit;

	@FindBy(id = "btnAdd")
	private WebElement assetMaintainanceContributorsButtonAdd;

	@FindBy(id = "btnSelect")
	private WebElement assetMaintainanceContributorsSelectButton;

	@FindBy(id = "txtName")
	private WebElement assetMaintainanceContributorsName;

	@FindBy(id = "DetailsTab1_btnsaveDetails")
	private WebElement detailsTabSaveButton;

	@FindBy(id = "btnSave")
	private WebElement assetMaintainanceContributorsButtonSave;

	@FindBy(xpath = "//span[contains(text(),'Asset Navigator')]/parent::a")
	private WebElement assetNavigatorMenu;

	@FindBy(xpath = "//iframe[contains(@src,'/Asset_Maintenance/AssetNavigator.aspx')]")
	private WebElement assetNavigatorFrame;

	@FindBy(xpath = "//span[@title='Add Season']/parent::a")
	private WebElement addSeason;

	@FindBy(xpath = "//div[@id='uwgConrtibutors']//table//table/tbody[2]/tr/td/div/table/tbody/tr/td[2]")
	public WebElement seriesDataElement;

	@FindBy(xpath = "//*[@id=\"uwgConrtibutors\"]/table/tbody/tr[1]/td[1]/table/tbody[2]/tr/td/div[2]/table/tbody/tr/td[7]")
	public WebElement directorDataElement;

	@FindBy(xpath = "//div[@id='uwgConrtibutors']/table/tbody//table/tbody[2]/tr/td/div[2]/table/tbody/tr[2]/td[2]")
	public WebElement episodeDataElement;

	@FindBy(xpath = "//table[@data-ig='x:1715484532.5:mkr:dataTbl.hdn']//tbody/tr[2]/td[7]")
	public WebElement episodeDirectorDataElement;

	@FindBy(id = "txtUserDefinedName")
	public WebElement episodeSeasonName;

	@FindBy(id = "txtEpisodeOrder")
	public WebElement episodeOrder;

	@FindBy(xpath = "//table[@id='dtInitialAirDate']//input")
	public WebElement episodeAirDate;

	@FindBy(id = "btnOK")
	public WebElement okButton;

	@FindBy(id = "lblAssetIDVal")
	public WebElement rlAssetID;

	@FindBy(xpath = "//div[@id='uwgAkaTitle_EditorControl1']/div/table/tbody/tr/td[2]/img")
	public WebElement firstAlternativeTitleTypeImage;

	@FindBy(xpath ="//div[@id='uwgAkaTitle_EditorControl2']/div/table/tbody/tr/td[2]/img")
	public WebElement secondAlternativeTitleTypeImage;
	
	@FindBy(xpath = "//a[text()='AKA']")
	public WebElement akaTitleType;

	@FindBy(xpath = "//div[@id='uwgAkaTitle_EditorControl2']/div/table/tbody/tr/td[2]/img")
	public WebElement firstAlternativeTitleTypeNetwork;

	@FindBy(xpath = "//a[text()='AMC']")
	public WebElement firstAlternativeTitleTypeNetworkAsAMC;

	@FindBy(xpath = "//a[text()='Legal']")
	public WebElement secondLeaglTitleType;

	XSSFWorkbook workbook = null;
	String[][] dataBook = null;

	public CreateAssetRightsLogic(WebDriver driver) {
		PageFactory.initElements(driver, this);
		workbook = initializeExcelSheet("AssestsIntegration.xlsx");
		dataBook = getDataFromExcel(workbook, "AssetInformation");
	}

	private CreateAssetRightsLogic clickOnAssetMaintenance() {
		driver.switchTo().frame(0);
		logStepMessage("Click on Asset Maintenance Link");
		waitTillElementVisible(assetMaintenanceLink);
		assetMaintenanceLink.click();
		driver.switchTo().defaultContent();
		return this;
	}

	private CreateAssetRightsLogic clickOnNewButton() {
		driver.switchTo().frame(1);
		logStepMessage("Click on New Button");
		waitTillElementVisible(assetMaintenanceNewButton);
		assetMaintenanceNewButton.click();
		return this;
	}

	private CreateAssetRightsLogic clickOnProgramButton(int rowNumber) {
		String assetTypeString = dataBook[rowNumber][0];
		if (assetTypeString.contains("Theatrical")) {
			clickOnTheatricalButton();
		} else {
			logStepMessage("Click on Program Button");
			waitTillElementVisible(assetMaintenanceProgramButton);
			assetMaintenanceProgramButton.click();
		}
		return this;
	}

	private CreateAssetRightsLogic clickOnTheatricalButton() {
		logStepMessage("Click on Theatrical Button");
		waitTillElementVisible(assetMaintenanceTheatricalButton);
		assetMaintenanceTheatricalButton.click();
		return this;
	}

	private CreateAssetRightsLogic selectAssetType(int rowNumber) {
		String assetTypeString = dataBook[rowNumber][0];
		logStepMessage(String.format("Select asset type as %s", assetTypeString));
		selectValueFromDropdownUsingVisibleText(assetType, assetTypeString);
		return this;
	}

	private String enterAssetTitle(int rowNumber) throws InterruptedException {
		String assetTitleString = dataBook[rowNumber][1] + RandomStringUtils.randomAlphanumeric(3);
		logStepMessage(String.format("Select asset title as %s", assetTitleString));
		Thread.sleep(2000);
		setData(assetTitle, assetTitleString);
		return assetTitleString;
	}

	private CreateAssetRightsLogic selectVendorForAsset(int rowNumber) {
		String vendorName = dataBook[rowNumber][6];
		logStepMessage("Click on Asset Vendor");
		clickOnElement(assetVendor);
		driver.switchTo().defaultContent();
		driver.switchTo()
				.frame(driver.findElement(By.xpath("//iframe[@src=\"/List_Maintenance/Enterprise_PopupSearch.aspx?"
						+ "&entitytype=External&title=Vendor&type=Vendor&opt=yes&multiselect=yes&LEVEL=1&From=AM\"]")));
		setData(assetVendorName, vendorName);
		logStepMessage(String.format("Search for vendor %s", vendorName));
		clickOnElement(findButton);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement element = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath(String.format("//td[contains(text(),'%s')]", vendorName)))));
		logStepMessage(String.format("Select vendor %s", vendorName));
		clickOnElement(element);
		clickOnElement(selectButton);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(2);
		return this;
	}

	private CreateAssetRightsLogic selectSourceBusinessUnit(int rowNumber) {
		waitTillElementVisible(sourceBusinessUnit);
		String assetSourceBusinessString = dataBook[rowNumber][2];
		logStepMessage(String.format("Select source business unit as %s", assetSourceBusinessString));
		selectValueFromDropdownUsingVisibleText(sourceBusinessUnit, assetSourceBusinessString);
		return this;
	}

	private CreateAssetRightsLogic enterAssetDuration(int rowNumber) throws InterruptedException {
		String assetDurationText = dataBook[rowNumber][3].replaceAll(":", "");
		logStepMessage(String.format("Enter asset duration as %s", assetDurationText));
		setData(assetDuration, assetDurationText);
		return this;
	}

	private CreateAssetRightsLogic selectAssetStatus(int rowNumber) {
		String assetStatusString = dataBook[rowNumber][4];
		logStepMessage(String.format("Select asset status as %s", assetStatusString));
		selectValueFromDropdownUsingVisibleText(assetStatus, assetStatusString);
		return this;
	}

	private CreateAssetRightsLogic selectAssetSource(int rowNumber) {
		String assetSourceString = dataBook[rowNumber][5];
		logStepMessage(String.format("Select asset source as %s", assetSourceString));
		selectValueFromDropdownUsingVisibleText(assetSource, assetSourceString);
		return this;
	}

	private CreateAssetRightsLogic clickOnSaveButton() {
		Reporter.log("Click on New Button", true);
		waitTillElementVisible(saveButton);
		saveButton.click();
		return this;
	}

	private CreateAssetRightsLogic clickOnGeneralTab() {
		Reporter.log("Click on General Tab", true);
		waitTillElementVisible(generalTab);
		generalTab.click();
		return this;
	}

	private CreateAssetRightsLogic clickOnSaveButtonOnContrinutorsTab() {
		Reporter.log("Click on Save Button on Contributors tab", true);
		waitTillElementVisible(assetMaintainanceContributorsButtonSave);
		assetMaintainanceContributorsButtonSave.click();
		return this;
	}
	
	private CreateAssetRightsLogic enterDetailsForFirstAlternativeTitle(String alternativeTitle) throws Exception {
		clickOnElement(firstAlternativeTitle);
		logStepMessage(String.format("Set AKA alternative title as %s", alternativeTitle));
		setTextUsingRobotClass(alternativeTitle, firstAlternativeTitle);
		clickOnElement(firstAlternativeTitleType);
		Reporter.log("Select alternative title type as AKA");
		waitTillElementVisible(firstAlternativeTitleTypeImage);
		clickOnElement(firstAlternativeTitleTypeImage);
		waitTillElementVisible(akaTitleType);
		clickOnElement(akaTitleType);
		clickOnElement(firstAlternativeTitleNetwork);
		logStepMessage("Select alternative title network as AMC");
		waitTillElementVisible(firstAlternativeTitleTypeNetwork);
		clickOnElement(firstAlternativeTitleTypeNetwork);
		waitTillElementVisible(firstAlternativeTitleTypeNetworkAsAMC);
		clickOnElement(firstAlternativeTitleTypeNetworkAsAMC);
		return this;
	}
	
	private CreateAssetRightsLogic enterDetailsForSecondAlternativeTitle(String alternativeTitle) throws Exception {
		clickOnElement(secondAlternativeTitle);
		Reporter.log(String.format("Set Legal alternative title as %s", alternativeTitle));
		setTextUsingRobotClass(alternativeTitle, secondAlternativeTitle);
		clickOnElement(secondAlternativeTitleType);
		logStepMessage("Select alternative title type as Legal");
		waitTillElementVisible(firstAlternativeTitleTypeImage);
		clickOnElement(firstAlternativeTitleTypeImage);
		waitTillElementVisible(secondLeaglTitleType);
		clickOnElement(secondLeaglTitleType);
		clickOnElement(secondAlternativeTitleNetwork);
		logStepMessage("Select alternative title network as AMC");
		waitTillElementVisible(secondAlternativeTitleTypeImage);
		clickOnElement(secondAlternativeTitleTypeImage);
		waitTillElementVisible(firstAlternativeTitleTypeNetworkAsAMC);
		clickOnElement(firstAlternativeTitleTypeNetworkAsAMC);
		return this;
	}

	private CreateAssetRightsLogic enterDetailsTabInformation(int rowNumber) throws Exception {
		String alternativeTitleForAKA = dataBook[rowNumber][7];
		String alternativeTitleForLegal = dataBook[rowNumber][8];
		String releaseYearText = dataBook[rowNumber][9];
		enterDetailsForFirstAlternativeTitle(alternativeTitleForAKA);
		enterDetailsForSecondAlternativeTitle(alternativeTitleForLegal);
		logStepMessage(String.format("Select release year as %s", releaseYearText));
		selectValueFromDropdownUsingVisibleText(releaseYear, releaseYearText);
		return this;
	}

	private void switchToAssetMaintenceFrame() {
		Reporter.log("Switching to Asset Maintence Frame", true);
		driver.switchTo().frame(assetMaintainanceFrame);
	}

	private void switchToAssetMaintenceDetailsFrame() {
		Reporter.log("Switching to Asset Maintence Details Frame", true);
		driver.switchTo().frame(assetMaintainanceDetailsFrame);
	}

	private void clickOnSaveButtonOnDetailsTab() {
		Reporter.log("Click on Save Button On Details Tab", true);
		clickOnElement(detailsTabSaveButton);
	}

	private void switchToAssetMaintenanceFrame() {
		Reporter.log("Switch to asset maintenance frame");
		driver.switchTo().frame(assetMaintainanceFrame);
	}

	private void clickOnContributorsTab() {
		Reporter.log("Click on Contributors Tab", true);
		clickOnElement(contributorsTab);
	}

	private void switchToAssetMaintenanceContributorsTabFrame() {
		Reporter.log("Switch to asset maintenance contributors tab frame");
		driver.switchTo().frame(assetMaintainanceContributorsTabFrame);
	}

	private void switchToAssetMaintenanceContributorsEditFrame() {
		Reporter.log("Switch to asset maintenance contributors edit frame");
		driver.switchTo().frame(assetMaintainanceContributorsEditFrame);
	}

	private void switchToAssetMaintenanceContributorsSearchFrame() {
		Reporter.log("Switch to asset maintenance contributors search frame");
		driver.switchTo().frame(assetMaintainanceContributorsSearchFrame);
	}

	private void clickOnContributorsTabEditButton() {
		Reporter.log("Click on Contributors Tab Edit Button", true);
		clickOnElement(assetMaintainanceContributorsButtonEdit);
	}

	private void clickOnContributorsTabAddButton() {
		Reporter.log("Click on Contributors Tab Add Button", true);
		clickOnElement(assetMaintainanceContributorsButtonAdd);
	}

	private void clickOnContributorsTabFindButton() {
		Reporter.log("Click on Contributors Tab Find Button", true);
		clickOnElement(findButton);
	}

	private void clickOnContributorsTabSelectButton() {
		Reporter.log("Click on Contributors Tab Select Button", true);
		clickOnElement(assetMaintainanceContributorsSelectButton);
	}

	private String findContributorDirectorForSeries(int rowNumber) {
		String contributorDirectorName = dataBook[rowNumber][10];
		Reporter.log("Find contributor");
		assetMaintainanceContributorsName.sendKeys(contributorDirectorName);
		return contributorDirectorName;
	}

	private String findContributorDirectorForEpisode(int rowNumber) {
		String contributorDirectorName = dataBook[rowNumber][11];
		Reporter.log("Find contributor");
		assetMaintainanceContributorsName.sendKeys(contributorDirectorName);
		return contributorDirectorName;
	}

	private String giveRLAssetID() {
		return rlAssetID.getText();
	}

	// This function is to enter information on General Tab on RL Asset Creation
	private String[] enterRLDetailsForGeneralTab(int rowNumber) throws InterruptedException {
		String rlAssetID = "RLA" + giveRLAssetID();
		selectAssetType(rowNumber);
		String assetTitle = enterAssetTitle(rowNumber);
		selectVendorForAsset(rowNumber);
		selectSourceBusinessUnit(rowNumber);
		enterAssetDuration(rowNumber);
		selectAssetStatus(rowNumber);
		selectAssetSource(rowNumber);
		clickOnSaveButton();
		waitTillElementVisible(infoMessage);
		String[] rlValues = { rlAssetID, assetTitle };
		return rlValues;
	}

	// This function is to enter information on Details Tab on RL Asset Creation
	private void enterRLDetailsOnDetailsTab(int rowNumber) throws Exception {
		logStepMessage("Click on Details Tab");
		clickOnElement(detailsTab);
		driver.switchTo().defaultContent();
		switchToAssetMaintenceFrame();
		switchToAssetMaintenceDetailsFrame();
		enterDetailsTabInformation(rowNumber);
		clickOnSaveButtonOnDetailsTab();
	}

	// This function is to enter information on Contributors Tab on RL Asset
	// Creation
	private void enterRLDetailsOnContributorsTab(int rowNumber, String assetTitle)
			throws InterruptedException, AWTException {
		driver.switchTo().defaultContent();
		switchToAssetMaintenanceFrame();
		clickOnContributorsTab();
		switchToAssetMaintenanceContributorsTabFrame();
		clickOnContributorsTabEditButton();
		driver.switchTo().defaultContent();
		switchToAssetMaintenanceContributorsEditFrame();
		clickOnContributorsTabAddButton();
		driver.switchTo().defaultContent();
		switchToAssetMaintenanceContributorsSearchFrame();
		String contributorDirectorName = findContributorDirectorForSeries(rowNumber);
		clickOnContributorsTabFindButton();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement element2 = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.xpath(String.format("(//td[contains(text(),'%s')])[1]", contributorDirectorName)))));
		element2.click();
		clickOnContributorsTabSelectButton();
		driver.switchTo().defaultContent();
		switchToAssetMaintenanceContributorsEditFrame();
		Reporter.log("Select Series from the grid", true);
		Thread.sleep(2000);
		clickOnElement(seriesDataElement);
		driver.findElement(By.xpath("//img[@data-ig='x:1808348613.4:mkr:ButtonImage']")).click();
		List<WebElement> elements1 = driver.findElements(By.xpath("//*[@id=\"form1\"]/div[4]/div/ul/li/a"));
		for (WebElement element : elements1) {
			if (element.getText().equalsIgnoreCase(assetTitle)) {
				element.click();
				break;
			}
		}
		Thread.sleep(2000);
		Reporter.log("Select type as Director from the grid", true);
		clickOnElement(directorDataElement);
		WebElement elementText = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//img[@data-ig='x:1808348611.4:mkr:ButtonImage']"))));
		elementText.click();
		List<WebElement> elements2 = driver.findElements(By.xpath("//*[@id=\"form1\"]/div[6]//ul/li"));
		for (WebElement element : elements2) {
			if (element.getText().equals("Director")) {
				element.click();
				break;
			}
		}
		clickOnSaveButtonOnContrinutorsTab();
		Thread.sleep(1000);
		driver.switchTo().defaultContent();
		switchToAssetMaintenanceFrame();
		clickOnGeneralTab();
		waitTillElementVisible(saveButton);
		clickOnSaveButton();
	}

	public String[] createNewRLAsset(int rowNumber) throws Exception {
		clickOnAssetMaintenance();
		clickOnNewButton();
		clickOnProgramButton(rowNumber);
		String[] rlAssetValues = enterRLDetailsForGeneralTab(rowNumber);
		attachScreen(driver);
		enterRLDetailsOnDetailsTab(rowNumber);
		attachScreen(driver);
		enterRLDetailsOnContributorsTab(rowNumber, rlAssetValues[1]);
		attachScreen(driver);
		return rlAssetValues;
	}

	// This method is for adding new season and episode under series
	public CreateAssetRightsLogic addNewSeasonAndEpisode(int rowNumber) {
		String epsiodeName = dataBook[rowNumber][12];
		String seasonName = dataBook[rowNumber][13];
		String episodeOrderData = dataBook[rowNumber][14];
		String episodeAirDateData = dataBook[rowNumber][15];
		logStepMessage("Click on Asset Navigator Menu");
		clickOnElement(assetNavigatorMenu);
		driver.switchTo().defaultContent();
		logStepMessage("Switch to asset navigator frame");
		driver.switchTo().frame(assetNavigatorFrame);
		logStepMessage("Click on Add Season Button");
		clickOnElement(addSeason);
		logStepMessage("Switch to default content");
		driver.switchTo().defaultContent();
		logStepMessage("Switch to Add Item frame");
		driver.switchTo()
				.frame(driver.findElement(By.xpath("//iframe[contains(@src,'/Asset_Maintenance/AddItem.aspx')]")));
		logStepMessage(String.format("Enter season name as %s", seasonName));
		setData(episodeSeasonName, seasonName);
		logStepMessage("Click on Save Button");
		clickOnElement(assetMaintainanceContributorsButtonSave);
		driver.switchTo().defaultContent();
		logStepMessage("Switch to asset navigator frame");
		driver.switchTo().frame(assetNavigatorFrame);
		logStepMessage("Click on Season");
		driver.findElement(By.xpath(String.format("//a[contains(text(),'%s')]", seasonName))).click();
		logStepMessage("Click on Add Episode Button");
		clickOnElement(addSeason);
		driver.switchTo().defaultContent();
		driver.switchTo()
				.frame(driver.findElement(By.xpath("//iframe[contains(@src,'/Asset_Maintenance/AddItem.aspx')]")));
		logStepMessage(String.format("Episode episode name as %s", epsiodeName));
		setData(episodeSeasonName, epsiodeName);
		logStepMessage(String.format("Episode episode order as %s", episodeOrderData));
		setData(episodeOrder, episodeOrderData);
		logStepMessage(String.format("Episode episode air date as %s", episodeAirDateData));
		setData(episodeAirDate, episodeAirDateData);
		logStepMessage("Click on Save Button");
		clickOnElement(assetMaintainanceContributorsButtonSave);
		driver.switchTo().defaultContent();
		Reporter.log("Switch to asset navigator frame", true);
		driver.switchTo().frame(assetNavigatorFrame);
		logStepMessage("Click on Ok Button");
		clickOnElement(okButton);
		driver.switchTo().defaultContent();
		driver.switchTo()
				.frame(driver.findElement(By.xpath("//iframe[contains(@src,'/Asset_Maintenance/Home.aspx')]")));
		clickOnSaveButton();
		return this;
	}

	// This method is to assign contributors at episode level
	public CreateAssetRightsLogic assignContributorsToEpisode(int rowNumber) throws Exception {
		String epsiodeName = dataBook[rowNumber][12];
		addNewSeasonAndEpisode(rowNumber);
		clickOnContributorsTab();
		switchToAssetMaintenanceContributorsTabFrame();
		clickOnContributorsTabEditButton();
		driver.switchTo().defaultContent();
		switchToAssetMaintenanceContributorsEditFrame();
		clickOnContributorsTabAddButton();
		driver.switchTo().defaultContent();
		switchToAssetMaintenanceContributorsSearchFrame();
		String contributorsDirectorName = findContributorDirectorForEpisode(rowNumber);
		clickOnContributorsTabFindButton();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement element2 = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.xpath(String.format("(//td[contains(text(),'%s')])[1]", contributorsDirectorName)))));
		element2.click();
		clickOnContributorsTabSelectButton();
		driver.switchTo().defaultContent();
		switchToAssetMaintenanceContributorsEditFrame();
		Reporter.log("Select Episode from the grid", true);
		Thread.sleep(2000);
		clickOnElement(episodeDataElement);
		driver.findElement(By.xpath("//div[@id='uwgConrtibutors_ctl00']/div/table/tbody/tr/td[2]/img")).click();
		List<WebElement> elements1 = driver.findElements(By.xpath("//form[@id='form1']/div[4]/div/ul/li/a"));
		for (WebElement element : elements1) {
			if (element.getText().contains(epsiodeName)) {
				element.click();
				break;
			}
		}
		Reporter.log("Select type as Director from the grid", true);
		clickOnElement(episodeDirectorDataElement);
		Thread.sleep(2000);
		WebElement elementText = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//div[@id='uwgConrtibutors_ctl02']/div/table/tbody/tr/td[2]/img"))));
		elementText.click();
		List<WebElement> elements2 = driver.findElements(By.xpath("//form[@id='form1']/div[6]/div/ul/li/a"));
		for (WebElement element : elements2) {
			if (element.getText().equals("Director")) {
				element.click();
				break;
			}
		}
		clickOnSaveButtonOnContrinutorsTab();
		driver.switchTo().defaultContent();
		switchToAssetMaintenanceFrame();
		clickOnGeneralTab();
		waitTillElementVisible(saveButton);
		clickOnSaveButton();
		return this;
	}

	// Execute database query
	public CreateAssetRightsLogic executeDataBaseQuery() throws Exception {
		Thread.sleep(20000);
		String query = getDataFromExcel(0, 0, "SQLQuery");
		// query = query.replaceAll("%s", rlAssetIDString.get());
		Connection connect = conn.get();
		Statement statment = connect.createStatement();
		ResultSet set = statment.executeQuery(query);
		while (set.next()) {
			ResultSetMetaData metaData = set.getMetaData();
			int count = metaData.getColumnCount();
			String columnName[] = new String[count];
			for (int i = 1; i <= count; i++) {
				columnName[i - 1] = metaData.getColumnLabel(i);
				hashMapString.put(columnName[i - 1], set.getString(i));
				System.out.println(hashMapString.get(columnName[i - 1]));
			}
		}
		return this;
	}

}
