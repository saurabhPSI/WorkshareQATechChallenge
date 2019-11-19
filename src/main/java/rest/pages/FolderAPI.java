package rest.pages;

import base.TestBase;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Helper;

import java.io.IOException;
import java.util.HashMap;

public class FolderAPI extends TestBase {

    public FolderAPI getAllFolders(String finalUri, HashMap<String, String> headerMap) throws IOException {
        closeableHttpResponse = restClient.get(finalUri, headerMap );
        jsonObject = Helper.getJSONBody(closeableHttpResponse);
        return this;
    }

    public FolderAPI modifyFolderName(String finalUri, String json, HashMap<String, String> headerMap) throws IOException {
        closeableHttpResponse = restClient.put(finalUri, json, headerMap);
        jsonObject = Helper.getJSONBody(closeableHttpResponse);
        return this;
    }

    public String getModifiedFolderName() throws IOException{
        return String.valueOf(jsonObject.get("name"));
    }

    public String getFolderUUID(String folderName) {
        String folderUUID = null;
        JSONArray jsonArray = (JSONArray) jsonObject.get("folders");
        for(int i=0; i< jsonArray.length();i++){
             jsonObject = (JSONObject) jsonArray.get(i);
            if(jsonObject.get("name").equals(folderName)){
                folderUUID = (String) jsonObject.get("uuid");
                break;
            }
        }
        return folderUUID;
    }
}
