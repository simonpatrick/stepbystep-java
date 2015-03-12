package com.hedwig.stepbystep.javatutorial.webtest.pageobjects;

import com.hedwig.stepbystep.javatutorial.webtest.annotation.ElementName;
import com.hedwig.stepbystep.javatutorial.webtest.annotation.PageObject;
import org.openqa.selenium.WebElement;
/**
 * Created by patrick on 15/3/9.
 *
 * @version $Id$
 */

@PageObject(path = "pages/BaiduHomePageResource.xml")
public class BaiduHomeXMLPage {

    private WebElement keyword;

    @ElementName(name = "submit")
    private WebElement submit;
}
