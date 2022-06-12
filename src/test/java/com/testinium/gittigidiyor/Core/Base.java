package com.testinium.gittigidiyor.Core;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Base {
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;
    public WebDriver getWebDriver() {
        return webDriver;
    }

    public Base()  {

    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void init () {
        PropertyConfigurator.configure("src/test/java/resources/log4j.properties");
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/testinium/gittigidiyor/Drivers/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        setWebDriver(driver);
        WebDriverWait wait = new WebDriverWait(driver, 60);
        setWait(wait);
    }
    public  void finish(){
        webDriver.quit();
    }


    public WebDriverWait getWait() {
        return webDriverWait;
    }

    public void setWait(WebDriverWait webDriverWait) {
        this.webDriverWait = webDriverWait;
    }
}
