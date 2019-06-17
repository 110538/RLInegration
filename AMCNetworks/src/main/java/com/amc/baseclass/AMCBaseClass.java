package com.amc.baseclass;

import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.amc.pageobjects.ApiExecutionTypes;
import com.amc.pageobjects.CreateAssetRightsLogic;
import com.amc.pageobjects.LoginModule;

import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

public class AMCBaseClass {

	public static WebDriver driver;
	public static ThreadLocal<WebDriver> currentDriver = new ThreadLocal<WebDriver>();
	public static ThreadLocal<XSSFWorkbook> excelWorkBook = new ThreadLocal<XSSFWorkbook>();
	public static ThreadLocal<String> targetURL = new ThreadLocal<String>();
	public static ThreadLocal<Properties> propHandler = new ThreadLocal<Properties>();
	public static ThreadLocal<Connection> conn = new ThreadLocal<Connection>();
	public static Connection connect;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private XSSFSheet sheet;
	private XSSFCell cell = null;
	protected XSSFWorkbook workBook;
	private Properties prop = null;
	public static String rlAssetIDValue = null;
	public static String rlAssetTitleValue = null;
	public static boolean flag = true;
	public static HashMap<String, String> rlAssetValuesID = new HashMap<String, String>();
	public static HashMap<String, Integer> rlAssetValuesCondition = new HashMap<String, Integer>();
	public static HashMap<String, Integer> rlAssetRowNumber = new HashMap<String, Integer>();

	@Parameters({ "appURL", "browserType" })
	@BeforeSuite(alwaysRun = true)
	public void initializeTestBaseSetup(String appURL, String browserType) {
		try {
			setDriver(appURL, browserType);
			targetURL.set(appURL);
			connectToESBDatabase();
		} catch (Exception e) {
			System.out.println("................" + e.getMessage());
		}
	}

	public void createDifferentRLAssets() throws Exception {
		XSSFCell cell;
		prop = readPropertyFile();
		String[] assetTypesValue = { assetTypes.Series.getAssetType(),assetTypes.OneOff.getAssetType(), 
				assetTypes.Special.getAssetType(), assetTypes.Demo.getAssetType(), 
				assetTypes.Documentary.getAssetType(),
				assetTypes.Pilot.getAssetType(), assetTypes.MadeForTV.getAssetType(),
				assetTypes.MusicPerformance.getAssetType(), assetTypes.FeatureLength.getAssetType()
				,assetTypes.ShortFilm.getAssetType()};
		int[] testCaseNumber = { assetTypes.Series.getTestCaseNumber(),assetTypes.OneOff.getTestCaseNumber(), 
				assetTypes.Special.getTestCaseNumber(), assetTypes.Demo.getTestCaseNumber(), 
				assetTypes.Documentary.getTestCaseNumber(),
				assetTypes.Pilot.getTestCaseNumber(), assetTypes.MadeForTV.getTestCaseNumber(),
				assetTypes.MusicPerformance.getTestCaseNumber(), assetTypes.FeatureLength.getTestCaseNumber()
				,assetTypes.ShortFilm.getTestCaseNumber()};
		workBook = initializeExcelSheet(prop.getProperty("TestExecutionFileName"));
		LoginModule login = new LoginModule(driver);
		CreateAssetRightsLogic logic = new CreateAssetRightsLogic(driver);
		login.clickOnRightsLogin(1);
		for (int i = 0; i < assetTypesValue.length; i++) {
			try {
				rlAssetRowNumber.put(assetTypesValue[i], testCaseNumber[i]);
				String[] rlAssetValues = logic.createNewRLAsset(testCaseNumber[i]);
				if (assetTypesValue[i].equalsIgnoreCase("Series")) {
					logic.assignContributorsToEpisode(testCaseNumber[i]);
				}
				logStepMessage("====================================================");
				logStepMessage(assetTypesValue[i] + " " + "Get Created");
				logStepMessage("====================================================");
				logStepMessage("====================================================");
				logStepMessage(assetTypesValue[i] + " " + rlAssetValues[0]);
				logStepMessage(assetTypesValue[i] + " " + rlAssetValues[1]);
				logStepMessage("====================================================");
				rlAssetIDValue = rlAssetValues[0];
				rlAssetTitleValue = rlAssetValues[1];
				rlAssetValuesID.put(assetTypesValue[i], rlAssetIDValue);
				rlAssetValuesCondition.put(assetTypesValue[i], 1);
				FileOutputStream outputStream = new FileOutputStream(System.getProperty("user.dir")
						+ prop.getProperty("workSpaceExcelPath") + "\\" + prop.getProperty("TestExecutionFileName"));
				cell = workBook.getSheet("AssetInformation").getRow(testCaseNumber[i]).getCell(18);
				cell.setCellValue(rlAssetIDValue);
				cell = workBook.getSheet("AssetInformation").getRow(testCaseNumber[i]).getCell(1);
				cell.setCellValue(rlAssetTitleValue);
				workBook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				driver.navigate().refresh();
			} catch (Exception e1) {
				try {
					logStepMessage("====================================================");
					logStepMessage(assetTypesValue[i] + " " + "Did not Get Created");
					logStepMessage("====================================================");
					driver.switchTo().alert().accept();
					rlAssetValuesCondition.put(assetTypesValue[i], 2);
				} catch (Exception e2) {
					driver.navigate().refresh();
					rlAssetValuesCondition.put(assetTypesValue[i], 2);
				}
			}
		}
		
		logStepMessage(" Assets Creation Completed Waiting 5mts for API Response and Validation..... ");
		
		Thread.sleep(300000);
	}

	@Parameters({ "assetType" })
	@BeforeTest
	public void executeAPI(String assetType) throws Exception {
		if (!assetType.equals("rlAssetCreation")) {
			int actualValue = rlAssetValuesCondition.get(assetType);
			if (actualValue == 1) {
				flag = true;
				String rlAssetValue = rlAssetValuesID.get(assetType);
				ApiExecutionTypes apiExecutionTypes = new ApiExecutionTypes();
				apiExecutionTypes.APIResponse("WOPAPI", rlAssetValue);
				apiExecutionTypes.APIResponse("MPAPI", rlAssetValue);
			} else {
				logStepMessage(assetType + "Did not get created");
				flag = false;
			}
		}
	}

	@Parameters({ "assetType" })
	@BeforeClass
	public void checkExecutionCondition(String assetType) throws Exception {
		if (!flag) {
			Assert.fail(assetType + " did not get created");
		}
	}

	@Parameters({ "assetType" })
	@AfterTest
	public void setExcelValuesToInitialValue(String assetType) throws Exception {
		if (!assetType.equals("rlAssetCreation")) {
		workBook = excelWorkBook.get();
		int rowNumber = rlAssetRowNumber.get(assetType);
		FileOutputStream outputStream = new FileOutputStream(System.getProperty("user.dir")
				+ prop.getProperty("workSpaceExcelPath") + "\\" + prop.getProperty("TestExecutionFileName"));
		workBook.getSheet("AssetInformation").getRow(rowNumber).getCell(1).setCellValue("Program" + assetType + "Test");
		workBook.write(outputStream);
		outputStream.close();
		}
	}
	
	public String getSecondsForDurationField(String rlDuration) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	    Date reference = dateFormat.parse("00:00:00");
	    Date date = dateFormat.parse(rlDuration);
	    String seconds = String.valueOf((date.getTime() - reference.getTime()) / 1000L);
	    return seconds;
	}

	public Properties readPropertyFile() {
		prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(System.getProperty("user.dir") + "\\" + "ConfigProperties");
			// load a properties file
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}

	public XSSFWorkbook initializeExcelSheet(String fileName) {
		prop = readPropertyFile();
		File file = new File(System.getProperty("user.dir") + prop.getProperty("workSpaceExcelPath") + "\\" + fileName);
		FileInputStream files = null;
		try {
			files = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			workBook = new XSSFWorkbook(files);
		} catch (IOException e) {
			e.printStackTrace();
		}
		excelWorkBook.set(workBook);
		return workBook;
	}

	public WebDriver getDriver() {
		return currentDriver.get();
	}

	// Function to load excel data in hashmap
	public HashMap<String, Integer> storeExcelDataInHashMap() {
		propHandler.set(readPropertyFile());
		HashMap<String, Integer> testCaseRailID = new HashMap<String, Integer>();
		initializeExcelSheet(prop.getProperty("TestRailExcelFile"));
		String sheetName = "ExecutionModel";
		workBook = excelWorkBook.get();
		sheet = workBook.getSheet(sheetName);
		int maxcount = sheet.getLastRowNum();
		try {
			for (int testcase = 1; testcase <= maxcount; testcase++) {
				String testCaseName = getDataFromExcel(testcase, 0, sheetName);
				int testCaseID = Integer.parseInt(getDataFromExcel(testcase, 3, sheetName));
				testCaseRailID.put(testCaseName, testCaseID);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return testCaseRailID;

	}

	// Function for take the screen shot in allure report
	@Attachment("Screenshot")
	public static byte[] attachScreen(WebDriver driver) {
		logStep("Taking screenshot");
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	public File attachScreenFile() {
		logStep("Taking screenshot");
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	}

	@Attachment("Error_Screenshot")
	public static byte[] attachScreen_TestCaseError(WebDriver driver) {
		logStep("Taking screenshot");
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	public String[][] getDataFromExcel(XSSFWorkbook workbook, String sheetName) {
		String[][] excelData = null;
		try {
			int colCount = 0, rowCount = 0;
			sheet = workbook.getSheet(sheetName);
			colCount = sheet.getRow(0).getPhysicalNumberOfCells();
			rowCount = sheet.getPhysicalNumberOfRows();
			excelData = new String[rowCount][colCount];
			for (int Nrow = 0; Nrow < rowCount; Nrow++) {
				sheet.getRow(Nrow);
				for (int Ncolumn = 0; Ncolumn < colCount; Ncolumn++) {
					cell = sheet.getRow(Nrow).getCell(Ncolumn);
					DataFormatter df = new DataFormatter();
					excelData[Nrow][Ncolumn] = df.formatCellValue(cell);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return excelData;
	}

	public String getDataFromExcel(int rownum, int colnum, String sheetName) throws Exception {
		workBook = excelWorkBook.get();
		sheet = workBook.getSheet(sheetName);
		XSSFCell cell = sheet.getRow(rownum).getCell(colnum);
		DataFormatter df = new DataFormatter();
		String data = df.formatCellValue(cell);
		return data;
	}

	public int readTestCaseNo(String TestcaseNo) {
		String sheetName = "ExecutionModel";
		int testDatarowNo = 0;
		workBook = excelWorkBook.get();
		sheet = workBook.getSheet(sheetName);
		int maxcount = sheet.getLastRowNum();
		String[] rows = null;
		try {
			for (int testcase = 1; testcase <= maxcount; testcase++) {
				String testCaseID = getDataFromExcel(testcase, 0, sheetName);
				if (testCaseID.equalsIgnoreCase(TestcaseNo)) {
					String row = getDataFromExcel(testcase, 1, sheetName);
					if (row.contains(",")) {
						rows = row.split(",");
					} else if (row.contains("")) {
						rows = row.split(" ");
					}
					for (String rowno : rows) {
						testDatarowNo = Integer.parseInt(rowno);
						System.out.println("test data is  :" + testCaseID + "-----" + testDatarowNo);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return testDatarowNo;
	}

	protected void setDriver(String appURL, String browserType) throws Exception {
		switch (browserType) {
		case "firefox":
			driver = initalizeFirefoxDriver(appURL);
			break;
		case "chrome":
			driver = initalizeChromeDriver(appURL);
			break;
		case "ie":
			driver = initalizeIEDriver(appURL);
			break;
		case "FireFoxForBrowserStack":
			driver = initalizeFirefoxDriverForBrowserStack(appURL);
			break;
		case "ChromeForBrowserStack":
			driver = initalizeChromeDriverForBrowserStack(appURL);
			break;
		default:
			initalizeFirefoxDriver(appURL);
		}
	}

	private synchronized WebDriver initalizeFirefoxDriver(String appURL) {
		System.setProperty("webdriver.gecko.driver", ".\\drivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		currentDriver.set(driver);
		ThreadGuard.protect(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(appURL);
		return driver;
	}

	private synchronized WebDriver initalizeFirefoxDriverForBrowserStack(String appURL) throws Exception {
		prop = propHandler.get();
		String userName = prop.getProperty("browerStackUserName");
		String serverAddress = prop.getProperty("browerStackServerAddress");
		String accessKey = prop.getProperty("browerStackAccessKey");
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setBrowserName("firefox");
		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capability.setCapability(CapabilityType.PLATFORM, prop.getProperty("platform"));
		capability.setCapability(CapabilityType.BROWSER_VERSION, prop.getProperty("browerVersion"));
		driver = new RemoteWebDriver(new URL("http://" + userName + ":" + accessKey + "@" + serverAddress + "/wd/hub"),
				capability);
		currentDriver.set(driver);
		ThreadGuard.protect(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(appURL);
		return driver;
	}

	private synchronized WebDriver initalizeChromeDriver(String appURL) throws Exception {
		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("-incognito");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		// driver = new RemoteWebDriver(new URL("http://127.0.0.1:8080/wd/hub"),
		// capabilities);
		driver = new ChromeDriver(options);
		ThreadGuard.protect(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		currentDriver.set(driver);
		driver.manage().window().maximize();
		driver.get(appURL);
		return driver;
	}

	private synchronized WebDriver initalizeChromeDriverForBrowserStack(String appURL) throws Exception {
		prop = propHandler.get();
		String userName = prop.getProperty("browerStackUserName");
		String serverAddress = prop.getProperty("browerStackServerAddress");
		String accessKey = prop.getProperty("browerStackAccessKey");
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setBrowserName("chrome");
		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capability.setCapability(CapabilityType.PLATFORM, prop.getProperty("chromeBrowserPlatform"));
		capability.setCapability(CapabilityType.BROWSER_VERSION, prop.getProperty("chromeBrowerVersion"));
		driver = new RemoteWebDriver(new URL("http://" + userName + ":" + accessKey + "@" + serverAddress + "/wd/hub"),
				capability);
		// driver = new RemoteWebDriver(new
		// URL("http://localhost:4444/wd/hub"),capability);
		currentDriver.set(driver);
		ThreadGuard.protect(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(appURL);
		return driver;
	}

	private synchronized WebDriver initalizeIEDriver(String appURL) throws MalformedURLException {
		System.setProperty("webdriver.ie.driver", ".\\drivers\\IEDriverServer.exe");
		InternetExplorerOptions options = new InternetExplorerOptions();
		DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
		cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		cap.setCapability(CapabilityType.PLATFORM, Platform.WIN10);
		cap.setCapability(CapabilityType.BROWSER_VERSION, "11.590.17134.0");
		// driver = new RemoteWebDriver(new
		// URL("http://localhost:4444/wd/hub"),options);
		//System.out.println(driver);
		driver = new InternetExplorerDriver(options);
		currentDriver.set(driver);
		ThreadGuard.protect(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(appURL);
		return driver;
	}

	// Function to determine start time of page load
	public long startTimeOfPageLoading() {
		return System.currentTimeMillis();
	}

	public void setTextUsingRobotClass(String text, WebElement element) throws AWTException {
		Actions action = new Actions(driver);
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);
		element.sendKeys(Keys.ENTER);
		action.sendKeys(element, Keys.chord(Keys.CONTROL, "v")).build().perform();
		element.sendKeys(Keys.TAB);
	}

	public void timeTakenForCompletePageLoad(long startTime, String pageName) {
		long finish = System.currentTimeMillis();
		long totalTimeMiliSec = finish - startTime;
		long totaltimesec = totalTimeMiliSec / 1000;
		float totalTimeMin = totaltimesec / 60f;
		logStepMessage(String.format("Time time taken on %s: " + totalTimeMin + " " + "minutes", pageName));
	}

	@Step("{0}")
	public static void logStep(String stepName) {

	}

	public void logStepMessage(String Message) {
		Reporter.log(Message, true);
		logStep(Message);
	}

	// Click functionality by Java Script
	public void clickOnElementUsingJavaScript(WebElement el, WebDriver driver) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", el);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	// Point To Element
	public void pointToElement(WebElement e1, WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", e1);
	}

	public WebElement waitTillElementVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		WebElement elementloaded = wait.until(ExpectedConditions.visibilityOf(element));
		return elementloaded;
	}

	public WebElement waitTillElementToBeClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		WebElement elementloaded = wait.until(ExpectedConditions.elementToBeClickable(element));
		return elementloaded;
	}

	public WebElement clickOnElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		WebElement elementClicked = wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
		return elementClicked;
	}

	public void setData(WebElement element, String text) {
		waitTillElementVisible(element);
		element.clear();
		element.sendKeys(text);
	}

	public void waitForInvisibilityOfElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	// Wait for Page Load
	public void waitTillPageLoaded(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(driver, 30);
		try {
			wait.until(expectation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Move to Element Function
	public void moveToElement(WebElement element, WebDriver driver) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	// Function for provide wait for VerifyPage Title
	public void verifypageTitle(String str, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.titleContains(str));
	}

	public void scrollDownThePage(WebDriver driver, int position) throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(String.format("window.scrollBy(0,%d)", "", position));
		} catch (Exception e) {
			e.getMessage();
			throw e;
		}
	}

	public void scrollUpThePage(WebDriver driver) throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,-250)");
		} catch (Exception e) {
			e.getMessage();
			throw e;
		}
	}

	// Read value from JavaScript
	public String getValueFromJS(String locator, WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		String value = (String) executor.executeScript(String.format("return $('#%s').val()", locator));
		return value;
	}

	public String getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String localDate = dtf.format(LocalDate.now());
		return localDate;
	}

	public String getOneYearFromCurrentYear() {
		DateFormat newDate = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date nextYear = cal.getTime();
		String nextYearDate = newDate.format(nextYear);
		return nextYearDate;
	}

	public String getTwoYearFromCurrentYear() {
		DateFormat newDate = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 2);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date nextYear = cal.getTime();
		String nextYearDate = newDate.format(nextYear);
		return nextYearDate;
	}

	public static String getThreeYearFromCurrentYear() {
		DateFormat newDate = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 3);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date nextYear = cal.getTime();
		String nextYearDate = newDate.format(nextYear);
		return nextYearDate;
	}

	public void selectValueFromDropdownUsingVisibleText(WebElement element, String visibleText) {
		Select select = new Select(element);
		select.selectByVisibleText(visibleText);
	}

	public static void selectValueFromDropdownUsingIndex(WebElement element, int index) {
		Select select = new Select(element);
		select.selectByIndex(index);
	}

	public void waitTillTextTobePresentInElement(String str, WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.textToBePresentInElement(element, str));
	}

	public void waitTillURLMatches(WebDriver driver, String url) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.urlToBe(url));
	}

	public void waitTillURLContains(WebDriver driver, String url) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.urlContains(url));
	}

	public void switchToChildWindow(String parentWindow, String expectedURL) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		Set<String> handler = driver.getWindowHandles();
		Iterator<String> newWindow = handler.iterator();
		while (newWindow.hasNext()) {
			String child_window = newWindow.next();
			if (!parentWindow.equals(child_window)) {
				driver.switchTo().window(child_window);
				break;
			}
		}
		wait.until(ExpectedConditions.urlContains(expectedURL));
		attachScreen(driver);
		Assert.assertTrue(driver.getCurrentUrl().equals(expectedURL));
		driver.close();
	}

	public Connection connectToESBDatabase() throws Exception {
		String dbURL = getDataFromExcel(1, 0, "DBConnectionInfo");
		String dbUserName = getDataFromExcel(1, 1, "DBConnectionInfo");
		String dbPassword = getDataFromExcel(1, 2, "DBConnectionInfo");
		// String query = getDataFromExcel(0, 0, "SQLQuery");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String URL = dbURL;
			String Username = dbUserName;
			String Password = dbPassword;
			System.out.println("Driver Loaded");
			connect = DriverManager.getConnection(URL, Username, Password);
			conn.set(connect);
			if (connect != null) {
				System.out.println("Connected to the Database...");
			}
			Statement stmt = connect.createStatement();
			System.out.println("Connection successfull" + stmt.toString());
			// query = query.replaceAll("%s","RLA218");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e1) {
			e1.getMessage();
		}
		return connect;
	}

	@AfterSuite
	public void close() {
		driver.quit();
	}

}
