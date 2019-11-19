package pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import base.TestBase;
import utils.Helper;

public class RedLineViewPage extends TestBase {

	@FindBy(xpath = "//*[@id='originalFileName']")
	WebElement originalDocName;

	@FindBy(xpath = "//*[@id='modifiedFileName']")
	WebElement modDocName;

	@FindBys(@FindBy(xpath = "//div[@id='toolbar']/div/div/a"))
	List<WebElement> menuItems;

	@FindBy(xpath = "//*[@id='btnAbout']")
	WebElement aboutIcon;

	@FindBy(xpath = "//span[contains(text(),'Original')]")
	WebElement lblOriginal;

	@FindBy(xpath = "//span[@class='SummaryList_zeroStateMessage_24B']")
	WebElement msgComparision;

	Helper helper = new Helper();

	public RedLineViewPage() {
		PageFactory.initElements(driver, this);
	}

	public RedLineViewPage verifyDocNames(String originalDoc, String modDoc) {
		String expectedOriginalName = originalDoc.substring(originalDoc.lastIndexOf("\\") + 1);
		String actualOriginalName = originalDocName.getText();
		String expectedModName = modDoc.substring(modDoc.lastIndexOf("\\") + 1);
		String actualModName = modDocName.getText();
		Assert.assertEquals(actualOriginalName, expectedOriginalName);
		Assert.assertEquals(actualModName, expectedModName);
		return this;
	}

	public RedLineViewPage verifyMenuItems() {
		for (WebElement menuItem : menuItems) {
			Assert.assertTrue(menuItem.isEnabled());
		}
		Assert.assertTrue(aboutIcon.isEnabled());
		return this;

	}

	public String viewComparision(){
		helper.explicitWait(Helper.visibility, lblOriginal);
		return msgComparision.getText();
	}

}
