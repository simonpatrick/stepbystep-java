package com.hedwig.stepbystep.javatutorial.helpers.webdriver;//package com.hedwig.stepbystep.javatutorial.helpers.webdriver;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.openqa.selenium.*;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import static org.openqa.selenium.support.ui.ExpectedConditions.*;
//
///**
// * WebDriver 执行工具
// */
//public class WebDriverHelper {
//
//    private WebDriverHelper() {
//    }
//
//    private final static Logger logger = LogManager.getLogger(WebDriverHelper.class.getName());
//    private static Long TIMEOUTINSECONDS = 20L;
//    private static long POLLING_INTERVAL = 500;
//
//
//    public static String getEvalName(WebDriver driver, String script, WebElement element) {
//
//        String name = (String) ((JavascriptExecutor) driver).executeScript(
//                script, element);
//        return name;
//    }
//
//    public static void simulateMouseOver(WebDriver driver, WebElement element) {
//        logger.info("mouse over {}" + element);
//        String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');" +
//                "evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} " +
//                "else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript(mouseOverScript, element);
//
//    }
//    /**
//     * 弹出框处理， 返回弹出框的内容
//     *
//     * @param driver
//     * @param handler
//     * @return
//     */
//    public static String handleAlert(WebDriver driver, boolean handler) {
//        try {
//            Alert alert = driver.switchTo().alert();
//            String alertMessage;
//            if (null == alert) return null;
//            if (handler) {
//                alertMessage = alert.getText();
//                alert.accept();
//                logger.info("alert_window_message={}", alertMessage);
//            } else {
//                alertMessage = alert.getText();
//                alert.dismiss();
//                logger.info("alert_window_message={}", alertMessage);
//            }
//
//            return alertMessage;
//        } catch (NoAlertPresentException e) {
//            logger.warn("no_alert_exception={}", e);
//            return null;
//        }
//    }
//
//    /**
//     * get default with default defined out value
//     *
//     * @param driver
//     * @return
//     */
//    public static WebDriverWait getDefaultWait(WebDriver driver) {
//        return new WebDriverWait(driver, TIMEOUTINSECONDS, POLLING_INTERVAL);
//    }
//
//    public static void setWaitTimeOutInSeconds(Long timeOuts) {
//        TIMEOUTINSECONDS = timeOuts;
//    }
//
//    public static void setPollingInterval(long pollingInterval) {
//        POLLING_INTERVAL = pollingInterval;
//    }
//
//    public static void input(WebDriver driver, By by, String text) {
//        getDefaultWait(driver).until(presenceOfElementLocated(by)).sendKeys(text);
//    }
//
//    /**
//     * 点击web element
//     *
//     * @param wait
//     * @param by
//     */
//    public static void click(WebDriverWait wait, By by) {
//        wait.until(elementToBeClickable(by)).click();
//    }
//
//    /**
//     * 点击web element
//     *
//     * @param driver
//     * @param by
//     */
//    public static void click(WebDriver driver, By by) {
//        click(getDefaultWait(driver), by);
//    }
//
//    /**
//     * 点击父容器内的webelement
//     *
//     * @param wait
//     * @param parent
//     * @param locator
//     */
//    public static void click(WebDriverWait wait, WebElement parent, By locator) {
//        wait.until(ModifiedExpectedConditions.elementToBeClickable(parent, locator)).click();
//    }
//
//    public static void click(WebDriver driver, WebElement parent, By locator) {
//        click(getDefaultWait(driver), parent, locator);
//    }
//
//    public static Boolean present(WebDriverWait wait, By locator) {
//        return wait.until(presenceOfElementLocated(locator)) != null;
//    }
//
//    public static Boolean present(WebDriver driver, By locator) {
//        return present(getDefaultWait(driver), locator);
//    }
//
//    public static Boolean present(WebDriver driver, WebDriverWait wait, WebElement parent, By locator) {
//        return wait.until(ModifiedExpectedConditions.presenceOfElementLocated(parent, locator)) != null;
//    }
//
//    public static Boolean present(WebDriver driver, WebElement parent, By locator) {
//        return present(driver, getDefaultWait(driver), parent, locator);
//    }
//
//    public static Boolean notPresent(WebDriver driver, WebDriverWait wait, WebElement el) {
//        return wait.until(stalenessOf(el));
//    }
//
//    public static Boolean notPresent(WebDriver driver, WebElement el) {
//        return notPresent(driver, getDefaultWait(driver), el);
//    }
//
//    public static Boolean visible(WebDriver driver, WebDriverWait wait, By by) {
//        return wait.until(visibilityOfElementLocated(by)) != null;
//    }
//
//    public static Boolean visible(WebDriver driver, By by) {
//        return visible(driver, getDefaultWait(driver), by);
//    }
//
//
//    /**
//     * Determines the web element's visibility inside a sub DOM using driver,
//     * wait, parent and locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @return true if visible
//     */
//    public static Boolean visible(WebDriver driver, WebDriverWait wait,
//                                  WebElement parent, By by) {
//        return wait.until(ModifiedExpectedConditions.visibilityOfElementLocated(parent, by)) != null;
//    }
//
//    /**
//     * Determines the web element's visibility inside a sub DOM using driver,
//     * parent and locator.
//     *
//     * @param driver the WebDriver
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @return true if visible
//     */
//    public static Boolean visible(WebDriver driver, WebElement parent, By by) {
//        return visible(driver, getDefaultWait(driver), parent, by);
//    }
//
//    /**
//     * Determines the web element's invisibility using driver, wait and locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param by     locator used to find the element
//     * @return true if invisible
//     */
//    public static Boolean invisible(WebDriver driver, WebDriverWait wait, By by) {
//        return wait.until(invisibilityOfElementLocated(by));
//    }
//
//    /**
//     * Determines the web element's invisibility using driver and locator.
//     *
//     * @param driver the WebDriver
//     * @param by     locator used to find the element
//     * @return true if invisible
//     */
//    public static Boolean invisible(WebDriver driver, By by) {
//        return invisible(driver, getDefaultWait(driver), by);
//    }
//
//    /**
//     * Determines the web element's invisibility inside a sub DOM using driver,
//     * wait, parent and locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @return true if invisible
//     */
//    public static Boolean invisible(WebDriver driver, WebDriverWait wait,
//                                    WebElement parent, By by) {
//        return wait.until(ModifiedExpectedConditions.invisibilityOfElementLocated(parent, by));
//    }
//
//    /**
//     * Determines the web element's invisibility inside a sub DOM using driver,
//     * parent and locator.
//     *
//     * @param driver the WebDriver
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @return true if invisible
//     */
//    public static Boolean invisible(WebDriver driver, WebElement parent, By by) {
//        return invisible(driver, getDefaultWait(driver), parent, by);
//    }
//
//    /**
//     * 使用WebDriverWait 查找web element
//     *
//     * @param wait
//     * @param by
//     * @return
//     */
//    public static WebElement findElement(WebDriverWait wait,
//                                         By by) {
//        return wait.until(presenceOfElementLocated(by));
//    }
//
//    /**
//     * 使用WebDriverWait 查找web element
//     */
//    public static WebElement findElement(WebDriver driver, By by) {
//        return findElement(getDefaultWait(driver), by);
//    }
//
//    /**
//     * 使用WebDriverWait 查找web element
//     *
//     * @param wait
//     * @param parent 父WebElement
//     * @param by
//     * @return
//     */
//    public static WebElement findElement(WebDriverWait wait,
//                                         WebElement parent, By by) {
//        return wait.until(ModifiedExpectedConditions.presenceOfElementLocated(parent, by));
//    }
//
//    /**
//     * 使用WebDriverWait 查找webelement
//     *
//     * @param driver
//     * @param parent
//     * @param by
//     * @return
//     */
//    public static WebElement findElement(WebDriver driver, WebElement parent,
//                                         By by) {
//        return findElement(getDefaultWait(driver), parent, by);
//    }
//
//    /**
//     * 使用WebDriverWait 查找webelements
//     *
//     * @param wait
//     * @param by
//     * @return
//     */
//    public static List<WebElement> findElements(
//            WebDriverWait wait, By by) {
//        return wait.ignoring(NoSuchElementException.class).until(presenceOfAllElementsLocatedBy(by));
//    }
//
//    /**
//     * 使用WebDriverWait 查找webelements
//     *
//     * @param driver
//     * @param by
//     * @return
//     */
//    public static List<WebElement> findElements(WebDriver driver, By by) {
//        return findElements(getDefaultWait(driver), by);
//    }
//
//    /**
//     * 使用WebDriverWait 查找webelements
//     *
//     * @param wait
//     * @param parent
//     * @param by
//     * @return
//     */
//    public static List<WebElement> findElements(
//            WebDriverWait wait, WebElement parent, By by) {
//        return wait.until(ModifiedExpectedConditions.presenceOfAllElementsLocatedBy(parent, by));
//    }
//
//    /**
//     * 使用WebDriverWait 查找webelements
//     * @param driver
//     * @param parent
//     * @param by
//     * @return
//     */
//    public static List<WebElement> findElements(WebDriver driver,
//                                                WebElement parent, By by) {
//        return findElements(getDefaultWait(driver), parent, by);
//    }
//
//    /**
//     * 使用WebDriverWait 查找select list
//     * @param wait
//     * @param by
//     * @return
//     */
//    public static Boolean selected(WebDriverWait wait, By by) {
//        return wait.until(elementSelectionStateToBe(by, true));
//    }
//
//    /**
//     * 使用WebDriverWait 查找select list
//     * @param driver
//     * @param by
//     * @return
//     */
//    public static Boolean selected(WebDriver driver, By by) {
//        return selected(getDefaultWait(driver), by);
//    }
//
//    /**
//     * Determines the web element is selected inside a sub DOM using driver, wait,
//     * parent and locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @return true if present
//     */
//    public static Boolean selected(WebDriver driver, WebDriverWait wait,
//                                   WebElement parent, By by) {
//        return wait.until(ModifiedExpectedConditions.elementSelectionStateToBe(parent, by, true)) != null;
//    }
//
//    /**
//     * Determines the web element is selected inside a sub DOM using driver,
//     * parent and locator.
//     *
//     * @param driver the WebDriver
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @return true if present
//     */
//    public static Boolean selected(WebDriver driver, WebElement parent, By by) {
//        return selected(driver, getDefaultWait(driver), parent, by);
//    }
//
//    /**
//     * Determines the web element is not selected using driver, wait and locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param by     locator used to find the element
//     * @return true if present
//     */
//    public static Boolean notSelected(WebDriver driver, WebDriverWait wait, By by) {
//        return wait.until(elementSelectionStateToBe(by, false));
//    }
//
//    /**
//     * Determines the web element is not selected using driver and locator.
//     *
//     * @param driver the WebDriver
//     * @param by     locator used to find the element
//     * @return true if present
//     */
//    public static Boolean notSelected(WebDriver driver, By by) {
//        return notSelected(driver, getDefaultWait(driver), by);
//    }
//
//    /**
//     * Determines the web element is not selected inside a sub DOM using driver,
//     * wait, parent and locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @return true if present
//     */
//    public static Boolean notSelected(WebDriver driver, WebDriverWait wait,
//                                      WebElement parent, By by) {
//        return wait.until(ModifiedExpectedConditions.elementSelectionStateToBe(parent, by, false));
//    }
//
//    /**
//     * Determines the web element is not selected inside a sub DOM using driver,
//     * parent and locator.
//     *
//     * @param driver the WebDriver
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @return true if present
//     */
//    public static Boolean notSelected(WebDriver driver, WebElement parent, By by) {
//        return notSelected(driver, getDefaultWait(driver), parent, by);
//    }
//
//    /**
//     * Types the given text using the driver, wait locator, text.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param by     locator used to find the element
//     * @param text   the text to be typed
//     */
//    public static void type(WebDriver driver, WebDriverWait wait, By by,
//                            String text) {
//        wait.until(visibilityOfElementLocated(by)).sendKeys(text);
//    }
//
//    /**
//     * Types the given text using the using driver and locator.
//     *
//     * @param driver the WebDriver
//     * @param by     locator used to find the element
//     * @param text   the text to be typed
//     */
//    public static void type(WebDriver driver, By by, String text) {
//        type(driver, getDefaultWait(driver), by, text);
//    }
//
//    /**
//     * Types the given text on an element inside a sub DOM using driver, wait,
//     * parent, locator and text.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @param text   the text to be typed
//     */
//    public static void type(WebDriver driver, WebDriverWait wait,
//                            WebElement parent, By by, String text) {
//        wait.until(ModifiedExpectedConditions.visibilityOfElementLocated(parent, by)).sendKeys(text);
//    }
//
//    /**
//     * Types the given text on an element inside a sub DOM using driver, parent,
//     * locator and text
//     *
//     * @param driver the WebDriver
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @param text   the text to be typed
//     */
//    public static void type(WebDriver driver, WebElement parent, By by,
//                            String text) {
//        type(driver, getDefaultWait(driver), parent, by, text);
//    }
//
//    /**
//     * Clears the text using the given driver, wait and locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param by     locator used to find the element
//     */
//    public static void clear(WebDriver driver, WebDriverWait wait, By by) {
//        wait.until(visibilityOfElementLocated(by)).clear();
//    }
//
//    /**
//     * Clears the text using the given driver and locator.
//     *
//     * @param driver the WebDriver
//     * @param by     locator used to find the element
//     */
//    public static void clear(WebDriver driver, By by) {
//        clear(driver, getDefaultWait(driver), by);
//    }
//
//    /**
//     * Clears the text on an element inside a sub DOM using driver, wait, parent,
//     * locator and text.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     */
//    public static void clear(WebDriver driver, WebDriverWait wait,
//                             WebElement parent, By by) {
//        wait.until(ModifiedExpectedConditions.visibilityOfElementLocated(parent, by)).clear();
//    }
//
//    /**
//     * Clears the text on an element inside a sub DOM using driver, parent and
//     * locator.
//     *
//     * @param driver the WebDriver
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     */
//    public static void clear(WebDriver driver, WebElement parent, By by) {
//        clear(driver, getDefaultWait(driver), parent, by);
//    }
//
//    /**
//     * Determines the give text is present on a web element using driver, wait and
//     * locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param by     locator used to find the element
//     * @param text   the text to be looked for
//     */
//    @SuppressWarnings("deprecation")
//    public static Boolean textPresent(WebDriver driver, WebDriverWait wait,
//                                      By by, String text) {
//        return wait.until(textToBePresentInElement(by, text));
//    }
//
//    /**
//     * Determines the give text is present on a web element using driver, locator
//     * and text.
//     *
//     * @param driver the WebDriver
//     * @param by     locator used to find the element
//     * @param text   the text to be looked for
//     */
//    public static Boolean textPresent(WebDriver driver, By by, String text) {
//        return textPresent(driver, getDefaultWait(driver), by, text);
//    }
//
//    /**
//     * Determines the give text is present on a web element inside a sub DOM using
//     * driver, wait, parent, locator and text.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @param text   the text to be looked for
//     */
//    public static Boolean textPresent(WebDriver driver, WebDriverWait wait,
//                                      WebElement parent, By by, String text) {
//        return wait.until(ModifiedExpectedConditions.textToBePresentInElement(parent, by, text));
//    }
//
//    /**
//     * Determines the give text is present on a web element inside a sub DOM using
//     * driver, parent, locator, text.
//     *
//     * @param driver the WebDriver
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @param text   the text to be looked for
//     */
//    public static Boolean textPresent(WebDriver driver, WebElement parent, By by,
//                                      String text) {
//        return textPresent(driver, getDefaultWait(driver), parent, by, text);
//    }
//
//    /**
//     * Determines the give text not present on a web element using driver, wait,
//     * locator and text.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param by     locator used to find the element
//     * @param text   the text to be looked for
//     */
//    public static Boolean textNotPresent(WebDriver driver, WebDriverWait wait,
//                                         By by, String text) {
//        return wait.until(invisibilityOfElementWithText(by, text));
//    }
//
//    /**
//     * Determines the give text not present on a web element using driver, locator
//     * and text.
//     *
//     * @param driver the WebDriver
//     * @param by     locator used to find the element
//     * @param text   the text to be looked for
//     */
//    public static Boolean textNotPresent(WebDriver driver, By by, String text) {
//        return textNotPresent(driver, getDefaultWait(driver), by, text);
//    }
//
//    /**
//     * Determines the give text not present on a web element inside a sub DOM
//     * using driver, wait, parent, locator and text.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @param text   the text to be looked for
//     */
//    public static Boolean textNotPresent(WebDriver driver, WebDriverWait wait,
//                                         WebElement parent, By by, String text) {
//        return wait.until(ModifiedExpectedConditions.invisibilityOfElementWithText(parent, by, text));
//    }
//
//    /**
//     * Determines the give text not present on a web element inside a sub DOM
//     * using driver, parent, locator, text.
//     *
//     * @param driver the WebDriver
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @param text   the text to be looked for
//     */
//    public static Boolean textNotPresent(WebDriver driver, WebElement parent,
//                                         By by, String text) {
//        return textNotPresent(driver, getDefaultWait(driver), parent, by, text);
//    }
//
//    /**
//     * Determines the give attribute name and value present on a web element using
//     * driver, wait, locator, attribute name and attribute value.
//     *
//     * @param driver         the WebDriver
//     * @param wait           the WebDriverWait
//     * @param by             locator used to find the element
//     * @param attributeName  the attribute name
//     * @param attributeValue the attribute value
//     */
//    public static Boolean attributeValuePresent(WebDriver driver,
//                                                WebDriverWait wait, By by, String attributeName, String attributeValue) {
//        return wait.until(ModifiedExpectedConditions.attributeValueToBePresentInElement(by, attributeName,
//                attributeValue));
//    }
//
//    /**
//     * Determines the give attribute name and value present on a web element using
//     * driver, locator, attribute name and attribute value.
//     *
//     * @param driver         the WebDriver
//     * @param by             locator used to find the element
//     * @param attributeName  the attribute name
//     * @param attributeValue the attribute value
//     */
//    public static Boolean attributeValuePresent(WebDriver driver, By by,
//                                                String attributeName, String attributeValue) {
//        return attributeValuePresent(driver, getDefaultWait(driver), by,
//                attributeName, attributeValue);
//    }
//
//    /**
//     * Determines the give attribute name and value present on a web element
//     * inside a sub DOM using driver, wait, parent, locator, attribute name and
//     * attribute value.
//     *
//     * @param driver         the WebDriver
//     * @param wait           the WebDriverWait
//     * @param parent         the sub DOM to look
//     * @param by             locator used to find the element
//     * @param attributeName  the attribute name
//     * @param attributeValue the attribute value
//     */
//    public static Boolean attributeValuePresent(WebDriver driver,
//                                                WebDriverWait wait, WebElement parent, By by, String attributeName,
//                                                String attributeValue) {
//        return wait.until(ModifiedExpectedConditions.attributeValueToBePresentInElement(parent, by,
//                attributeName, attributeValue));
//    }
//
//    /**
//     * Determines the give attribute name and value present on a web element
//     * inside a sub DOM using driver, parent, locator, attribute name and
//     * attribute value.
//     *
//     * @param driver         the WebDriver
//     * @param parent         the sub DOM to look
//     * @param by             locator used to find the element
//     * @param attributeName  the attribute name
//     * @param attributeValue the attribute value
//     */
//    public static Boolean attributeValuePresent(WebDriver driver,
//                                                WebElement parent, By by, String attributeName, String attributeValue) {
//        return attributeValuePresent(driver, getDefaultWait(driver), parent, by,
//                attributeName, attributeValue);
//    }
//
//    /**
//     * Gets the attribute value for the give attribute name on a web element using
//     * driver, wait, locator and attribute name.
//     *
//     * @param driver        the WebDriver
//     * @param wait          the WebDriverWait
//     * @param by            locator used to find the element
//     * @param attributeName the attribute name
//     * @return attribute value
//     */
//    public static String getAttributeValue(WebDriver driver, WebDriverWait wait,
//                                           By by, String attributeName) {
//        return findElement(wait, by).getAttribute(attributeName);
//    }
//
//    /**
//     * Gets the attribute value for the give attribute name on a web element using
//     * driver, locator and attribute name.
//     *
//     * @param driver        the WebDriver
//     * @param by            locator used to find the element
//     * @param attributeName the attribute name
//     * @return attribute value
//     */
//    public static String getAttributeValue(WebDriver driver, By by,
//                                           String attributeName) {
//        return getAttributeValue(driver, getDefaultWait(driver), by, attributeName);
//    }
//
//    /**
//     * Gets the attribute value for the give attribute name on a web element
//     * inside a sub DOM using driver, wait, parent, locator and attribute name.
//     *
//     * @param driver        the WebDriver
//     * @param wait          the WebDriverWait
//     * @param parent        the sub DOM to look
//     * @param by            locator used to find the element
//     * @param attributeName the attribute name
//     * @return attribute value
//     */
//    public static String getAttributeValue(WebDriver driver, WebDriverWait wait,
//                                           WebElement parent, By by, String attributeName) {
//        return findElement(wait, parent, by).getAttribute(attributeName);
//    }
//
//    /**
//     * Gets the attribute value for the give attribute name on a web element
//     * inside a sub DOM using driver, parent, locator and attribute name.
//     *
//     * @param driver        the WebDriver
//     * @param parent        the sub DOM to look
//     * @param by            locator used to find the element
//     * @param attributeName the attribute name
//     * @return attribute value
//     */
//    public static String getAttributeValue(WebDriver driver, WebElement parent,
//                                           By by, String attributeName) {
//        return getAttributeValue(driver, getDefaultWait(driver), parent, by,
//                attributeName);
//    }
//
//    /**
//     * Gets the text from a web element using driver, wait and locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param by     locator used to find the element
//     * @return text
//     */
//    public static String getText(WebDriver driver, WebDriverWait wait, By by) {
//        return findElement(wait, by).getText();
//    }
//
//    /**
//     * Gets the text from a web element using driver and locator.
//     *
//     * @param driver the WebDriver
//     * @param by     locator used to find the element
//     * @return text
//     */
//    public static String getText(WebDriver driver, By by) {
//        return getText(driver, getDefaultWait(driver), by);
//    }
//
//    /**
//     * Gets the text from a web element inside a sub DOM using driver, wait,
//     * parent and locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @return text
//     */
//    public static String getText(WebDriver driver, WebDriverWait wait,
//                                 WebElement parent, By by) {
//        return findElement(wait, parent, by).getText();
//    }
//
//    /**
//     * Gets the text from a web element inside a sub DOM using driver, parent and
//     * locator.
//     *
//     * @param driver the WebDriver
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @return text
//     */
//    public static String getText(WebDriver driver, WebElement parent, By by) {
//        return getText(driver, getDefaultWait(driver), parent, by);
//    }
//
//    /**
//     * Gets the value from a web element using driver, wait and locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param by     locator used to find the element
//     * @return value
//     */
//    public static String getValue(WebDriver driver, WebDriverWait wait, By by) {
//        return getAttributeValue(driver, by, "value");
//    }
//
//    /**
//     * Gets the value from a web element using driver and locator.
//     *
//     * @param driver the WebDriver
//     * @param by     locator used to find the element
//     * @return value
//     */
//    public static String getValue(WebDriver driver, By by) {
//        return getValue(driver, getDefaultWait(driver), by);
//    }
//
//    /**
//     * Gets the value from a web element inside a sub DOM using driver, wait,
//     * parent and locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @return value
//     */
//    public static String getValue(WebDriver driver, WebDriverWait wait,
//                                  WebElement parent, By by) {
//        return getAttributeValue(driver, wait, parent, by, "value");
//    }
//
//    /**
//     * Gets the value from a web element inside a sub DOM using driver, parent and
//     * locator.
//     *
//     * @param driver the WebDriver
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @return value
//     */
//    public static String getValue(WebDriver driver, WebElement parent, By by) {
//        return getValue(driver, getDefaultWait(driver), parent, by);
//    }
//
//    /**
//     * Gets the Select element using using driver, wait and locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param by     locator used to find the element
//     * @return Select object
//     */
//    public static Select getSelectElement(WebDriver driver, WebDriverWait wait,
//                                          By by) {
//        return new Select(findElement(wait, by));
//    }
//
//    /**
//     * Gets the Select element using driver and locator.
//     *
//     * @param driver the WebDriver
//     * @param by     locator used to find the element
//     * @return Select object
//     */
//    public static Select getSelectElement(WebDriver driver, By by) {
//        return getSelectElement(driver, getDefaultWait(driver), by);
//    }
//
//    /**
//     * Gets the Select element inside a sub DOM using driver, wait, parent and
//     * locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @return Select value
//     */
//    public static Select getSelectElement(WebDriver driver, WebDriverWait wait,
//                                          WebElement parent, By by) {
//        return new Select(findElement(wait, parent, by));
//    }
//
//    /**
//     * Gets the Select element inside a sub DOM using driver, parent and locator.
//     *
//     * @param driver the WebDriver
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     * @return Select value
//     */
//    public static Select getSelectElement(WebDriver driver, WebElement parent,
//                                          By by) {
//        return getSelectElement(driver, getDefaultWait(driver), parent, by);
//    }
//
//    /**
//     * Hovers on element using driver, wait and locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param by     locator used to find the element
//     */
//    public static void hover(WebDriver driver, WebDriverWait wait, By by) {
//        new Actions(driver).moveToElement(findElement(driver, by)).build()
//                .perform();
//    }
//
//    /**
//     * Hovers on element using driver and locator.
//     *
//     * @param driver the WebDriver
//     * @param by     locator used to find the element
//     */
//    public static void hover(WebDriver driver, By by) {
//        findElement(driver, by);
//        hover(driver, getDefaultWait(driver), by);
//    }
//
//    /**
//     * Hovers on element inside a sub DOM using driver, wait, parent and locator.
//     *
//     * @param driver the WebDriver
//     * @param wait   the WebDriverWait
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     */
//    public static void hover(WebDriver driver, WebDriverWait wait,
//                             WebElement parent, By by) {
//        new Actions(driver).moveToElement(findElement(driver, parent, by)).build()
//                .perform();
//    }
//
//    /**
//     * Hovers on element inside a sub DOM using driver, parent and locator.
//     *
//     * @param driver the WebDriver
//     * @param parent the sub DOM to look
//     * @param by     locator used to find the element
//     */
//    public static void hover(WebDriver driver, WebElement parent, By by) {
//        hover(driver, getDefaultWait(driver), parent, by);
//    }
//
//    /**
//     * Use this method only when browser based scrolling is not implemented in the
//     * application code Scrolls vertically If isUpward is true scrolls bottom to
//     * top and vice versa
//     *
//     * @param driver
//     * @param locator
//     * @param vertPixelsToScroll
//     * @param pixelsPerDrag
//     * @param isUpward
//     */
//    public static void scrollNonBrowserScrollersVertically(WebDriver driver,
//                                                           By locator, int vertPixelsToScroll, int pixelsPerDrag, boolean isUpward) {
//        Actions dragger = new Actions(driver);
//        WebElement draggablePartOfScrollbar = null;
//        draggablePartOfScrollbar = driver.findElement(locator);
//        if (locator != null) {
//            if (isUpward) {
//                for (int currentPos = 0; currentPos >= (0 - vertPixelsToScroll); currentPos = currentPos
//                        - pixelsPerDrag) {
//                    dragger.moveToElement(draggablePartOfScrollbar).clickAndHold()
//                            .moveByOffset(0, currentPos).release().perform();
//                    draggablePartOfScrollbar = driver.findElement(locator);
//                }
//            } else {
//                for (int currentPos = 0; currentPos <= vertPixelsToScroll; currentPos = currentPos
//                        + pixelsPerDrag) {
//                    dragger.moveToElement(draggablePartOfScrollbar).clickAndHold()
//                            .moveByOffset((currentPos - pixelsPerDrag), currentPos).release()
//                            .perform();
//                    draggablePartOfScrollbar = driver.findElement(locator);
//                }
//            }
//        }
//    }
//
//    /**
//     * Use this method only when browser based scrolling is not implemented in the
//     * application code. Scrolls horizontally If isRightToLeft parameter is true
//     * then it will scroll from right to left, else it will scroll left to right.
//     *
//     * @param driver
//     * @param locator
//     * @param horPixelsToScroll
//     * @param pixelsPerDrag
//     * @param isRightToLeft
//     */
//    public static void scrollNonBrowserScrollersHorizontally(WebDriver driver,
//                                                             By locator, int horPixelsToScroll, int pixelsPerDrag,
//                                                             boolean isRightToLeft) {
//        Actions dragger = new Actions(driver);
//        WebElement draggablePartOfScrollbar = driver.findElement(locator);
//        if (locator != null) {
//            if (isRightToLeft) {
//                for (int currentPos = 0; currentPos >= (0 - horPixelsToScroll); currentPos = currentPos
//                        - pixelsPerDrag) {
//                    dragger.moveToElement(draggablePartOfScrollbar).clickAndHold()
//                            .moveByOffset(currentPos, 0).release().perform();
//                }
//            } else {
//                for (int currentPos = 0; currentPos <= horPixelsToScroll; currentPos = currentPos
//                        + pixelsPerDrag) {
//                    dragger.moveToElement(draggablePartOfScrollbar).clickAndHold()
//                            .moveByOffset(currentPos, 0).release().perform();
//                }
//            }
//        }
//    }
//
//    /**
//     * Scroll to bottom of the page, this method supports only browser based
//     * scrolling
//     *
//     * @param driver
//     */
//    public static void scrollToBottom(WebDriver driver) {
//        ((JavascriptExecutor) driver)
//                .executeScript("window.scrollTo(0,document.body.scrollHeight);");
//    }
//
//    /**
//     * Scroll back to top of the page this method supports only browser based
//     * scrolling
//     *
//     * @param driver
//     */
//    public static void scrollTop(WebDriver driver) {
//        ((JavascriptExecutor) driver)
//                .executeScript("return document.documentElement.scrollTop");
//    }
//
//    public static int getRandomIndexForDD(int min, int max) {
//        int index = (int) (Math.random() * (max - min)) + min;
//        if (index >= max)
//            index = index - 1;
//        return index;
//    }
//
//    /**
//     * This method randomly selects an option from the drop down. if the select
//     * box uses select tag and options uses option tags like shown. <select>
//     * <option> optionToSelect </option> </select>
//     *
//     * @param driver
//     * @param by
//     */
//
//    public static void selectOptionFromDropDown(WebDriver driver, By by) {
//        WebElement select = driver.findElement(by);
//        List<WebElement> options = select.findElements(By.tagName("option"));
//        int randomIndexToSelect = getRandomIndexForDD(1, options.size());
//        options.get(randomIndexToSelect).click();
//    }
//
//    /**
//     * Selects option from drop down with a given text if the select box uses
//     * select tag and options uses option tags like shown. <select> <option>
//     * optionToSelect </option> </select>
//     *
//     * @param driver
//     * @param optionToSelect
//     */
//    public static void selectOptionFromDropDown(WebDriver driver, By by,
//                                                String optionToSelect) {
//        WebElement select = driver.findElement(by);
//        List<WebElement> options = select.findElements(By.tagName("option"));
//        for (WebElement option : options) {
//            if (optionToSelect.equals(option.getText())) {
//                option.click();
//                break;
//            }
//        }
//    }
//
//    /**
//     * Selects option from drop down with a given option index, if the select box
//     * uses select tag and options uses option tags like shown. <select> <option>
//     * optionToSelect </option> </select>
//     *
//     * @param driver
//     * @param optionToSelect
//     * @param optIdx
//     */
//    public static void selectOptionFromDropDown(WebDriver driver, By by,
//                                                String optionToSelect, int optIdx) {
//        WebElement select = driver.findElement(by);
//        List<WebElement> options = select.findElements(By.tagName("option"));
//        options.get(optIdx).click();
//    }
//
//    /**
//     * This method randomly selects an option from the list of the options. Such
//     * as <li>or a list of menu items created with <div>. Make sure that you
//     * provide the absolute locator of Menu items
//     *
//     * @param driver
//     * @param by
//     */
//    public static void selectOptionRandomlyFromMenuList(WebDriver driver, By by) {
//        List<WebElement> allSelectableOptions = driver.findElements(by);
//        int randomIndexToSelect = getRandomIndexForDD(1,
//                allSelectableOptions.size());
//        allSelectableOptions.get(randomIndexToSelect).click();
//    }
//
//
//    /**
//     * 设置窗体大小
//     *
//     * @param driver
//     * @param width
//     * @param height
//     * @param fleft
//     * @param ftop
//     */
//    public static void setWindowPosition(WebDriver driver, int width, int height, int fleft, int ftop) {
//        driver.manage().window().setPosition(new Point(fleft, ftop));
//        driver.manage().window().setSize(new Dimension(width, height));
//    }
//
//    /**
//     * 最大化浏览器
//     *
//     * @param driver
//     */
//    public static void maxWindow(WebDriver driver) {
//        driver.manage().window().maximize();
//    }
//
//    public static class ScreenShotUtils {
//
//        private ScreenShotUtils() {
//        }
//
//        private static final String SCREENSHOT_PATH = "target/screenshots/";
//        private static final String TEST_SCREENSHOT_PATH = "simple-report/screenshots/";
//        private static final String PIC_SUFFIX = ".jpg";
//        private static String classPath = ScreenShotUtils.class.getClassLoader().getResource("").getPath();
//
//        @Deprecated
//        public static String takeScreenshot(WebDriver driver) {
//            File file = FileHelper.createFile(SCREENSHOT_PATH, generateFileName());
//            File pic = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            try {
//                FileUtils.copyFile(pic, file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return file.getAbsolutePath();
//        }
//
//        public static void takeScreenshot(WebDriver driver, String path) {
//            File file = FileHelper.createFile(path);
//            File pic = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            try {
//                FileUtils.copyFile(pic, file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        public static String takeScreenshotForSimpleReport(WebDriver driver) {
//            String fileName = generateFileName();
//            FileHelper.createDir(classPath + TEST_SCREENSHOT_PATH);
//            String jpgPath = classPath + TEST_SCREENSHOT_PATH + fileName;
//            takeScreenshot(driver, jpgPath);
//            return "screenshots/" + fileName;
//        }
//
//        private static String generateFileName() {
//            return "screenshot-" + System.currentTimeMillis() + PIC_SUFFIX;
//        }
//    }
//
//
//}
