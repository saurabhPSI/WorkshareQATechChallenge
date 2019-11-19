package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.TestBase;
import pages.FilePickerPage;
import pages.RedLineViewPage;
import utils.Helper;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class RedLineViewPageTest extends TestBase {

    RedLineViewPage redLineViewPage;
    FilePickerPage filePickerPage;

    @BeforeTest(alwaysRun = true)
    public void initializePage() {
        redLineViewPage = new RedLineViewPage();
        filePickerPage = new FilePickerPage();
    }

    @Test(priority = 1, description = "Verify document names on Red Line View Page")
    public void T001_VerifyDocumentNames() {
        try {
            filePickerPage
                    .uploadDocumentToCompare(filePath + prop1.getProperty("originalDoc"), "original")
                    .uploadDocumentToCompare(filePath + prop1.getProperty("modifiedDoc"), "modified")
                    .clickViewChangesBtn();
            redLineViewPage.verifyDocNames(filePath + prop1.getProperty("originalDoc"), filePath + prop1.getProperty("modifiedDoc"));
        } catch (Throwable e) {
            Helper.t = e;
        }
    }

    @Test(priority = 2, description = "Verify menu items on Red Line View Page")
    public void T002_VerifyMenuItems() {
        try {
            redLineViewPage.verifyMenuItems();
        } catch (Throwable e) {
            Helper.t = e;
        }
    }

    @Test(priority = 3, description = "Verify the comparision of documents with same content")
    public void T003_VerifyMenuItems() {
        try {
            filePickerPage.uploadDocumentToCompare(filePath + prop1.getProperty("originalDoc"), "original")
                    .uploadDocumentToCompare(filePath + prop1.getProperty("copiedDoc"), "modified")
                    .clickViewChangesBtn();
            assertEquals(redLineViewPage.viewComparision(), prop2.getProperty("noChange_comparision_message"));
        } catch (Throwable e) {
            Helper.t = e;
        }
    }
}
