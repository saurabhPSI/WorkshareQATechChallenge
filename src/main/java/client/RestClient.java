package client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    CloseableHttpResponse closebaleHttpResponse;

    // 1. GET Method:
    public CloseableHttpResponse get(String url, HashMap<String, String> headerMap)
            throws ClientProtocolException, IOException {
        HttpGet httpget = new HttpGet(url); // http get request
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpget.addHeader(entry.getKey(), entry.getValue());
        }
        closebaleHttpResponse = httpClient.execute(httpget); // hit the GET URL
        return closebaleHttpResponse;
    }

    // 2. POST Method:
    public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap)
            throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url); // http post request
        StringEntity se = new StringEntity(entityString);
        se.setContentEncoding("UTF-8");
        se.setContentType("application/json");
        httppost.setEntity(se);
        // for headers:
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httppost.addHeader(entry.getKey(), entry.getValue());
        }
        closebaleHttpResponse = httpClient.execute(httppost);
        return closebaleHttpResponse;
    }

    // PUT Method:
    public CloseableHttpResponse put(String url, String entityString, HashMap<String, String> headerMap)
            throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url); // http put request
        StringEntity se = new StringEntity(entityString);
        se.setContentEncoding("UTF-8");
        se.setContentType("application/json");
        httpPut.setEntity(se); // for payload
        // for headers
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpPut.addHeader(entry.getKey(), entry.getValue());
        }
        closebaleHttpResponse = httpClient.execute(httpPut);
        return closebaleHttpResponse;
    }

    //Delete
    public CloseableHttpResponse delete(String url, HashMap<String, String> headerMap)
            throws ClientProtocolException, IOException {
        HttpDelete httpdelete = new HttpDelete(url); // http delete request
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpdelete.addHeader(entry.getKey(), entry.getValue());
        }
        closebaleHttpResponse = httpClient.execute(httpdelete); // hit the delete URL
        return closebaleHttpResponse;
    }
}
