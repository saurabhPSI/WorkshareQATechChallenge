package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import base.TestBase;
import pages.FilePickerPage;
import utils.Helper;

import static org.testng.Assert.*;

public class FilePickerPageTest extends TestBase {

	FilePickerPage filePickerPage;


	@BeforeTest(alwaysRun = true)
	public void initializePage() {
		setDriver();
		filePickerPage = new FilePickerPage();
	}

	@Test(priority = 1, description = "Verify the View Changes Button state on the File Picker Page")
	public void TC001_VerifyViewChangesButton() {
		try {
			assertFalse(filePickerPage.viewChangesButtonState());
			filePickerPage.uploadDocumentToCompare(filePath + prop1.getProperty("originalDoc"), "original");
			assertFalse(filePickerPage.viewChangesButtonState());
			filePickerPage.uploadDocumentToCompare(filePath + prop1.getProperty("modifiedDoc"), "modified");
			assertTrue(filePickerPage.viewChangesButtonState());
		} catch (Throwable e) {
			Helper.t = e;
		}
	}

	@Test(priority = 2, description = "Verify encrypt document upload")
	public void T002_VerifyEncryptedDocUpload() {
		try {
			filePickerPage.uploadDocumentToCompare(filePath + prop1.getProperty("originalDoc"), "original")
					.uploadDocumentToCompare(filePath + prop1.getProperty("encryptDoc"), "modified").clickViewChangesBtn()
					.errorMessageForDocument(prop2.getProperty("encrypted_document_message"));
		} catch (Throwable e) {
			Helper.t = e;
		}
	}

	@Test(priority = 3, description = "Verify corrupt document upload")
	public void T001_VerifyCorruptDocUpload() {
		try {
			filePickerPage.uploadDocumentToCompare(filePath + prop1.getProperty("originalDoc"), "original")
					.uploadDocumentToCompare(filePath + prop1.getProperty("corruptDoc"), "modified").clickViewChangesBtn()
					.errorMessageForDocument(prop2.getProperty("corrupted_document_message"));
		} catch (Throwable e) {
			Helper.t = e;
		}
	}

	@Test(priority = 4, description = "Verify invalid extensions document upload")
	public void T001_VerifyInvalidExtensionsDocUpload() {
		try {
			filePickerPage.uploadDocumentToCompare(filePath + prop1.getProperty("originalDoc"), "original")
					.uploadDocumentToCompare(filePath + prop1.getProperty("xlsxExtensionDoc"), "modified")
					.errorMessageForInvalidExtnDocument(prop2.getProperty("invalid_document_message"));
		} catch (Throwable e) {
			Helper.t = e;
		}
	}
}
