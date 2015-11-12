package com.hedwig.stepbystep.javatutorial.jbehave.pages;

import com.hedwig.stepbystep.javatutorial.jbehave.utils.WebdriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class CountrySelectorPage extends BasePage{

	private WebDriver driver;
	
	public CountrySelectorPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public HomePage selectCountry(String country){
		By locator = By.linkText(country);
		WebElement countryLink = WebdriverUtils.getClickableElement(driver, locator);
		countryLink.click();
		return new HomePage(driver);
	}	

}
