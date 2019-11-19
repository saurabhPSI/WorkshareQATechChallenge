package resttestcases;

import base.TestBase;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import rest.pages.LoginAPI;
import utils.Helper;

import java.io.IOException;
import java.util.HashMap;

public class LoginAPITest extends TestBase {
    String loginServiceUrl = "api/v1.1/user_sessions.json";

    LoginAPI loginAPI;

    @BeforeTest
    public void init() {
        loginAPI = new LoginAPI();
    }

    @Test
    public void TC1_loginToApplicationAPITest() {
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("accept", "application/json, text/javascript, */*; q=0.01");
        String json = "{\"user_session\":{\"email\":\"saurabh.d@thepsi.com\",\"password\":\"Psi@1234\",\"remember_me\":true}}";
        try {
            jsonObject = loginAPI.LoginToApp_API(prop1.getProperty("serverUrl") + loginServiceUrl, json, headerMap);
            System.out.println(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
