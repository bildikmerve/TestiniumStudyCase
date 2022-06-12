package com.testinium.gittigidiyor.Base;

import com.testinium.gittigidiyor.Core.Base;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseCase {
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;
    private Logger log;


    public BaseCase ( Logger log) {
        this.log=log;
    }
    @Before
    public void BeforeStart() {
        var base = new Base();
        base.init();
        this.webDriver = base.getWebDriver();
        webDriverWait = base.getWait();
    }

    public void openUrl(String url){
        try {
            webDriver.get(url);
            wait(2);
            //Assert.assertEquals(url,webDriver.getCurrentUrl());
            //log.info(webDriver.getCurrentUrl() + " Url başarılı bir şekilde açıldı.");
        }
       catch(Exception ex) {
        log.error("Url açılaya çalışırken hata ile karşılaşıldı.");
       }
    }

    public void wait(int seconds) {
        try {
            synchronized (webDriverWait) {
                webDriverWait.wait(seconds * 1000);
            }
        } catch (Exception ex) {
            log.error("Beklerken hata ile karşılaşıldı.");
            webDriver.close();
        }
    }


    public void click (By by){
        try {
            webDriver.findElement(by).click();
            //log.info(by + " elementine tıklandı.");
            wait(2);
        }
        catch (Exception ex) {
            log.error(by + " elementine tıklanamadı.");
            webDriver.close();
        }
    }

    public void sendText (By by, String text){
        try {
            webDriver.findElement(by).sendKeys(text);
            log.info(text + " li text yazıldı.");
        }
        catch (Exception ex) {
            log.error(text + "  li text yazılamadı.");
            webDriver.close();
        }
    }

    public List<WebElement> getList (By by){
        try {
            return webDriver.findElements(by);
        }
        catch (Exception ex) {
            log.error(by + " lı liste bulunamadı.");
            webDriver.close();
        }
        return null;
    }


    public ArrayList<String> getText(List<WebElement> elementList, By by){
        ArrayList<String> texts = new ArrayList<>();
        try {
            for (WebElement element : elementList){
                String text = element.findElement(by).getText();
                texts.add(text);
            }
        }
        catch (Exception ex){
            log.error("Listeye eklenemedi.");
            webDriver.close();
        }
        return texts;
    }


    public void scrollUntilElementSeen (WebElement element){
        try {
            Actions actions = new Actions(webDriver);
            actions.moveToElement(element);
            actions.perform();
        }

        catch (Exception ex){
            log.error("Element bulunamadı.");
        }

    }

    public void scrollUntilElementSeen (By by){
        try {
            WebElement element = webDriver.findElement(by);
            Actions actions = new Actions(webDriver);
            actions.moveToElement(element);
            actions.perform();
            }

        catch (Exception ex){
            log.error("Element bulunamadı.");
        }

    }

    public void checkCurrentPage (By by) {
        try {
            String elementAttribute = webDriver.findElement(by).getAttribute("aria-current");
            Assert.assertEquals("true", elementAttribute);

        } catch (Exception ex) {
        }
    }

    public void moveToElement(WebElement element){
        Actions action = new Actions(webDriver);
        action.moveToElement(element).build().perform();
    }

    public WebElement findElement(By by){
        WebElement element = null;
        try {
            element =  webDriver.findElement(by);
        }
        catch (Exception ex) {
            log.error("element bulunamadı.");
        }
        return  element;
    }

    public Select findCombobox (By by){
        Select comboBox = null;
        try {
            var element = webDriver.findElement(by);
            comboBox = new Select(element);
        }
        catch (Exception ex){
            log.error("Combobox bulunamadı");
        }
        return comboBox;
    }



}
