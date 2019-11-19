package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.Helper;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import static utils.Helper.*;

public class FilePickerPage extends TestBase {

	@FindBy(xpath = "//label[@id='file1Label']")
	WebElement inpOriginalDoc;

	@FindBy(xpath = "//input[@id='file1']")
	WebElement inpOriginalDocFocus;

	@FindBy(xpath = "//label[@id='file1Label']/span")
	WebElement lblUploadedOrigDocName;

	@FindBy(xpath = "//label[@id='file2Label']")
	private WebElement inpModifiedDoc;

	@FindBy(xpath = "//input[@id='file2']")
	WebElement inpModifiedDocFocus;

	@FindBy(xpath = "//label[@id='file2Label']/span")
	WebElement lblUploadedModDocName;

	@FindBy(xpath = "//*[@id='button']")
	private WebElement btnViewChanges;

	@FindBy(xpath = "//*[@id='errorMessage']")
	private WebElement errorMessage;

	Helper helper = new Helper();

	public FilePickerPage() {
		PageFactory.initElements(driver, this);
	}

	public void setClipboardData(String fileLocation) {
		// StringSelection is a class that can be used for copy and paste operations.
		StringSelection stringSelection = new StringSelection(fileLocation);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	public void openFileFromFileSystem(String fileLocation) {
		try {
			// Setting clipboard with file location
			setClipboardData(fileLocation);
			// native key strokes for CTRL, V and ENTER keys
			Robot robot = new Robot();
			robot.delay(3000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public FilePickerPage uploadDocumentToCompare(String filePath, String docForm) throws InterruptedException {
		if (docForm.equalsIgnoreCase("Original")) {
			inpOriginalDoc.click();
			openFileFromFileSystem(filePath);
			helper.explicitWait(inpOriginalDocFocus, "class", "inputfile has-focus");
		} else {
			inpModifiedDoc.click();
			openFileFromFileSystem(filePath);
			helper.explicitWait(inpModifiedDocFocus, "class", "inputfile has-focus");
		}
		return this;
	}

	public boolean viewChangesButtonState() throws InterruptedException {
		return btnViewChanges.isEnabled();
	}

	public FilePickerPage clickViewChangesBtn() {
		btnViewChanges.click();
		staticWait(5000);
		return this;
	}

	public FilePickerPage errorMessageForDocument(String expectedErrorMsg) {
		String actualErrorMsg = errorMessage.getText();
		Assert.assertEquals(actualErrorMsg, expectedErrorMsg);
		return this;
	}

	public FilePickerPage errorMessageForInvalidExtnDocument(String expectedErrorMsg) {
		String actualOrigDocErrorMsg = lblUploadedOrigDocName.getText().replaceAll("\n", " ");
		String actualModDocErrorMsg = lblUploadedModDocName.getText().replaceAll("\n", " ");
		if (actualOrigDocErrorMsg.contains("The allowed extensions")) {
			Assert.assertEquals(actualOrigDocErrorMsg, expectedErrorMsg);
		} else
			Assert.assertEquals(actualModDocErrorMsg, expectedErrorMsg);
		return this;
	}
}
