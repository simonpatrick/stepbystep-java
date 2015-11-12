package com.hedwig.stepbystep.javatutorial.webtest.pageobjects.pageresource;

import com.hedwig.stepbystep.javatutorial.webtest.annotation.PageObject;
import org.openqa.selenium.WebElement;

/**
 * Created by patrick on 15/3/9.
 *
 * @version $Id$
 */

@PageObject
public class BaiduHomePageResource {

        private WebElement keyword;
        private WebElement submit;
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
