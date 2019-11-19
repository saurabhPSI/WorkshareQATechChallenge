package rest.pages;

import base.TestBase;
import org.apache.http.Header;
import org.json.JSONObject;
import utils.Helper;

import java.io.IOException;
import java.util.HashMap;

public class LoginAPI extends TestBase {



    public JSONObject LoginToApp_API(String finalUrl, String json, HashMap<String, String> headerMap) throws IOException {
        closeableHttpResponse = restClient.post(finalUrl, json, headerMap);

        Header[] cookieHeaders = closeableHttpResponse.getHeaders("Set-Cookie");
        myCSRFToken = cookieHeaders[0].getValue().substring(cookieHeaders[0].getValue().indexOf("=") + 1,
                cookieHeaders[0].getValue().indexOf(";"));
        mySessionId = cookieHeaders[1].getValue().substring(cookieHeaders[1].getValue().indexOf("=") + 1,
                cookieHeaders[1].getValue().indexOf(";"));
        return Helper.getJSONBody(closeableHttpResponse);
    }



}
