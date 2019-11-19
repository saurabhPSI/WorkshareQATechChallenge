package utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import org.testng.annotations.Optional;

import javax.annotation.Nullable;

public class Helper {
	public static final long IMPLICIT_WAIT = 10;
	public static String invisibility = "invisibility";
	public static String visibility = "visibility";
	public static String clickable = "clickable";
	public static Throwable t=null;
	static String date = new SimpleDateFormat("dd-MM-YY").format(new Date());
	static String time = new SimpleDateFormat("HH-mm-ss").format(new Date());
	WebDriverWait wait = new WebDriverWait(TestBase.driver, 25);

	String deviceId = "16f39e3e-820e";

	// Capture Screenshot
	public static String capture(WebDriver driver, String className, String screenShotName) throws IOException {

		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File sourceFile = ts.getScreenshotAs(OutputType.FILE);
			String dest = TestBase.classpath + "/Screenshots/" + className + "_" + date + "/" + screenShotName + "_"
					+ time + ".jpg";
			File destinationFile = new File(dest);
			FileUtils.copyFile(sourceFile, destinationFile);

			return dest;
		} catch (Exception e) {
			System.out.println("Exception while taking screen shot" + e.getMessage());
			return e.getMessage();
		}

	}

	// Provides Explicit wait
	public void explicitWait(String action, WebElement element) {
		if (action.equals(visibility))
			wait.until(ExpectedConditions.visibilityOf(element));
		else if (action.equals(clickable))
			wait.until(ExpectedConditions.elementToBeClickable(element));
		else if (action.equals(invisibility))
			wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public void explicitWait(WebElement element, String attribute, String value) {
			wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
	}


	public static void staticWait(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Log the Status in the Extent Report
	/*@AfterMethod
	public static void logReport(Method method) {
		
	}*/

	// Writes Pass status to Extent Report
	public static void logPassStatus(String message) {
		TestBase.testReporter.log(LogStatus.PASS, message + " Passed Successfully");
	}

	// Writes Fail status to Extent Report
	public static void logFailStatus(String className, String methodName, Throwable ex) throws IOException {
		String screenshot = capture(TestBase.driver, className, methodName);
		TestBase.testReporter.log(LogStatus.FAIL, TestBase.testReporter.addScreenCapture(screenshot),
				ex);
	}

	// Writes Skip status to Extent Report
	public void logSkipStatus(String message) {
		TestBase.testReporter.log(LogStatus.SKIP, message);

	}

	// Press Enter key on the element
	public static void hitEnter(WebElement element) {
		Actions action = new Actions(TestBase.driver);
		action.moveToElement(element).build().perform();
		element.sendKeys(Keys.ENTER);
	}

	// Enters text in the text box
	public static void enterText(WebElement element, String textToEnter) {

		if (textToEnter.matches("")) {
			Actions action = new Actions(TestBase.driver);
			action.doubleClick(element).build().perform();
			element.sendKeys(Keys.BACK_SPACE);
		} else {
			element.clear();
			element.sendKeys(textToEnter);
		}
	}

	// Clicks the element
	public  void clickElement(WebElement element) {
		explicitWait(visibility, element);
		element.click();
	}

	//Sets header in the input JSON
	public static HashMap<String, String> setHeader() {
		HashMap<String, String> headerMap = new HashMap<String, String>();
//		headerMap.put("Content-Type", "application/json");
		headerMap.put("accept", "application/json, text/javascript, */*; q=0.01");
		headerMap.put("cookie: ", "my_csrf_token="+TestBase.myCSRFToken+";my_session_id="+TestBase.mySessionId);

		return headerMap;
	}

	//Returns Response JSON Body with UTF-8 encoding
	public static JSONObject getJSONBody(CloseableHttpResponse response) throws ParseException, IOException {
		String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
		return new JSONObject(responseString);
	}

}
