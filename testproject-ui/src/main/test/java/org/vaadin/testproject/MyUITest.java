package org.vaadin.testproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.PauseAction;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.GridElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TextFieldElement;

public class MyUITest extends TestBenchTestCase {
	@Rule
	public ScreenshotOnFailureRule screenshotOnFailureRule = new ScreenshotOnFailureRule(
			this, true);

	@Before
	public void setUp() throws Exception {
		// setDriver(new FirefoxDriver()); // Firefox

		setDriver(new ChromeDriver());

		// To use Chrome, first install chromedriver.exe from
		// http://chromedriver.storage.googleapis.com/index.html
		// on your system path (e.g. C:\Windows\System32\)
		// setDriver(new ChromeDriver()); // Chrome

		// To use Internet Explorer, first install iedriverserver.exe from
		// http://selenium-release.storage.googleapis.com/index.html?path=2.43/
		// on your system path (e.g. C:\Windows\System32\)
		// setDriver(new InternetExplorerDriver()); // IE

		// To test headlessly (without a browser), first install phantomjs.exe
		// from http://phantomjs.org/download.html on your system path
		// (e.g. C:\Windows\System32\)
		// setDriver(new PhantomJSDriver()); // PhantomJS headless browser

		Parameters.setScreenshotErrorDirectory("/Users/jotatu/Desktop/screenshots/errors");
		Parameters.setScreenshotReferenceDirectory("/Users/jotatu/Desktop/screenshots/references");
		Parameters.setMaxScreenshotRetries(2);
		Parameters.setScreenshotComparisonTolerance(1.0);
		Parameters.setScreenshotRetryDelay(10);
		Parameters.setScreenshotComparisonCursorDetection(true);
	}

	/**
	 * Opens the URL where the application is deployed.
	 */
	private void openTestUrl() {
		getDriver().get("http://localhost:8080/testproject");
	}

	@Test
    public void testClickButton() throws Exception {
        // 0st
    	openTestUrl();
        
        // 1st
    	List<ButtonElement> buttons = $(ButtonElement.class).all();
        assertEquals(2, buttons.size());
        
        assertEquals("Username", $(TextFieldElement.class).first().getCaption());
        assertEquals("Password", $(PasswordFieldElement.class).first().getCaption());
        
        // 2nd
        ButtonElement loginButton = $(ButtonElement.class).caption("Login").first();
        loginButton.click();
        
        // 3rd
        testBench().compareScreen("main");
        
        // 4th
        assertEquals("1", $(GridElement.class).first().getCell(0, 0).getText());
        assertEquals("Beginners guide to ice hockey", $(GridElement.class).first().getCell(0, 1).getText());
        
        // 5th
        TextFieldElement filterField = $(TextFieldElement.class).id("filter-textfield");
        filterField.sendKeys("galaxy");
        filterField.sendKeys(Keys.ENTER);
        
        assertEquals("11", $(GridElement.class).first().getCell(0, 0).getText());
        assertThat($(GridElement.class).first().getCell(0, 1).getText(),containsString("galaxy"));
        assertThat($(GridElement.class).first().getCell(1, 1).getText(),containsString("galaxy"));
        assertThat($(GridElement.class).first().getCell(2, 1).getText(),containsString("galaxy"));
        assertThat($(GridElement.class).first().getCell(3, 1).getText(),containsString("galaxy"));
        
        // 6th
        filterField.clear();
        assertEquals("1", $(GridElement.class).first().getCell(0, 0).getText());
        assertEquals("Beginners guide to ice hockey", $(GridElement.class).first().getCell(0, 1).getText());
        
        // 7th
        $(ButtonElement.class).caption("New product").first().click();
        
        TextFieldElement productNameField = $(TextFieldElement.class).caption("Product name").first();
        productNameField.sendKeys("qwerty");
        productNameField.sendKeys(Keys.ENTER);
        
        $(ButtonElement.class).caption("Save").first().click();
        
        filterField = $(TextFieldElement.class).id("filter-textfield");
        filterField.sendKeys("qwerty");
        filterField.sendKeys(Keys.ENTER);
        
        assertThat($(GridElement.class).first().getCell(0, 1).getText(),containsString("qwerty"));
        
    }
}
