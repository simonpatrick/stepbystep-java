package com.hedwig.stepbystep.javatutorial.webtest.pageobjects.pagefactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Created by patrick on 15/3/9.
 *
 * @version $Id$
 */


public class BaiduHomePage {

    @FindBy(id="kw1")
    private WebElement keyword;

    @FindBy(id="su")
    private WebElement submit;

    @FindBy(className = "ipt_rec")
    private WebElement voiceRec;

    public WebElement getKeyword() {
        return keyword;
    }

    public void setKeyword(WebElement keyword) {
        this.keyword = keyword;
    }

    public WebElement getSubmit() {
        return submit;
    }

    public void setSubmit(WebElement submit) {
        this.submit = submit;
    }

    public WebElement getVoiceRec() {
        return voiceRec;
    }

    public void setVoiceRec(WebElement voiceRec) {
        this.voiceRec = voiceRec;
    }
}
