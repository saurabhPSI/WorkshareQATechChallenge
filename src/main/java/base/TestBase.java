package base;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import client.RestClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import utils.Helper;

public class TestBase {
    public static Properties prop1; // Property variable declaration
    public static Properties prop2;
    public static WebDriver driver; // Driver variable declaration
    public static String classpath = System.getProperty("user.dir");
    public static String filePath = classpath + "\\src\\main\\java\\testdata\\";
    public static String mySessionId;
    public static String myCSRFToken;

    public static RestClient restClient = new RestClient();
    public static CloseableHttpResponse closeableHttpResponse;
    public static JSONObject jsonObject = new JSONObject();

    //Reporter to initiate the report
    public static ExtentReports reporter = null;
    //testreporter to write the report
    public static ExtentTest testReporter;

    public TestBase() { // Construction
        prop1 = new Properties();
        prop2 = new Properties();
        try {
            FileInputStream fs = new FileInputStream(classpath + "/src/main/java/config/config.properties");
            prop1.load(fs);
            fs = new FileInputStream(classpath + "/src/main/java/config/message.properties");
            prop2.load(fs);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @BeforeSuite // Before Suite Declaration
    public void initialization() {
        //Set the SimpleReport.html with its path
        reporter = new ExtentReports(classpath + "//Reports//SimpleReport.html", true, DisplayOrder.NEWEST_FIRST);
        //Add info to the report
        reporter.assignProject("Compare Online Automation").addSystemInfo("Author Name", "PSI Automation");
        //load the report-config.xml from the config folder
        reporter.loadConfig(new File(classpath + "/src/main/java/config/report-config.xml"));


    }

    public void setDriver(){
        System.setProperty("webdriver.chrome.driver", classpath + "/driver/chromedriver.exe");
        driver = new ChromeDriver(); // Chrome Driver launch
        driver.get(prop1.getProperty("url")); // Enter the URL
        driver.manage().window().maximize(); // MAximize the browser
        driver.manage().timeouts().implicitlyWait(Helper.IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(Helper.IMPLICIT_WAIT, TimeUnit.SECONDS);
    }

    //To start the reporting in the Report file
    @BeforeMethod
    public static void startReport(Method method) {
        testReporter = reporter.startTest(method.getName(), method.getAnnotation(Test.class).description());
    }

    //To end the reporting in the Report file
    @AfterMethod
    public static void reportClose(Method method) {
        if (Helper.t == null) {
            Helper.logPassStatus(method.getName());
        } else {
            try {
                Helper.logFailStatus(method.getDeclaringClass().getSimpleName(), method.getName(), Helper.t);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Helper.t = null;
        reporter.endTest(testReporter);
    }

    @AfterSuite
    public void tearDown() {
        //Flush and close the reporting
        reporter.flush();
        reporter.close();
//        driver.quit();
    }
}
