package com.hedwig.stepbystep.javatutorial.jbehave.pages;

import com.hedwig.stepbystep.javatutorial.jbehave.utils.WebdriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class HomePage extends BasePage{

	private WebDriver driver;
	
	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public String getSelectedCountry(){
		By locator = By.cssSelector(".lan_country_selector a");
		WebElement country = WebdriverUtils.getVisibleElement(driver, locator);
		return country.getText().trim();
	}
	
}
