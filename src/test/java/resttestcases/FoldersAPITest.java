package resttestcases;

import base.TestBase;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import rest.pages.FolderAPI;
import utils.Helper;

import java.io.IOException;

public class FoldersAPITest extends TestBase {
    FolderAPI folderAPI;
    String getAllFoldersServiceUrl = "api/v1.6/folders.json";
    String editFolderServiceUrl = "api/v1.3/folders/";
    String finalUri;

    @BeforeTest
    public void init(){
        folderAPI = new FolderAPI();
    }


    @Test
    public void TC3_renameFolderTest(){
        try{
            folderAPI
                    .getAllFolders(prop1.getProperty("serverUrl")+getAllFoldersServiceUrl, Helper.setHeader());
            String folderName = "Test";
            String folderUUID = folderAPI.getFolderUUID(folderName);
            String json = "{\"folder\":{\"name\":\""+folderName+"\",\"uuid\":\""+folderUUID+"\"}}";
            String editedFolderName = folderAPI
                    .modifyFolderName(prop1.getProperty("serverUrl")+editFolderServiceUrl+folderUUID+".json", json, Helper.setHeader())
                    .getModifiedFolderName();
            System.out.println("\n \n"+editedFolderName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
