package com.hedwig.stepbystep.javatutorial.webtest.pageobjects;

import com.hedwig.stepbystep.javatutorial.webtest.annotation.*;
import org.openqa.selenium.WebElement;
/**
 * Created by patrick on 15/3/9.
 *
 * @version $Id$
 */

@PageObject(path = "pages/BaiduHomePageResource.xml")
@Actions(actions={@Action(name="登陆",operations = {
        @ElementOperation(name = "keyword"),@ElementOperation(name="submit")
}),
@Action(name="logout",operations = {
        @ElementOperation(name="submit")
})
})
public class BaiduHomeXMLPage {

    private WebElement keyword;

    @ElementName(name = "submit")
    private WebElement submit;
}
