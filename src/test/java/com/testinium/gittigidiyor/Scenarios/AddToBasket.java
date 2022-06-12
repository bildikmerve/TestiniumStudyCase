package com.testinium.gittigidiyor.Scenarios;

import com.testinium.gittigidiyor.Base.BaseCase;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class AddToBasket extends BaseCase {
    private static final Logger log = Logger.getLogger(AddToBasket.class);
    private String price;
    public AddToBasket (){
        super(log);
    }
    @Test
    public void test (){
        //  opens gittigidiyor
        openUrl("https://www.gittigidiyor.com/");

        // search text and click find button
        sendText(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[1]/div[2]/input"),"Bilgisayar");
        click(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[2]/button"));
        wait(2);

        // close Cookies bar
        click(By.xpath("//*[@id=\"__next\"]/main/div[4]/section[1]/section[2]/a"));

        // navigate until seen pagination bar and click second page
        scrollUntilElementSeen(By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div/div[2]/div/div[3]/div[4]/nav"));
        click(By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div/div[2]/div/div[3]/div[4]/nav/ul/li[3]/a"));
        wait(2);
        checkCurrentPage(By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div/div[2]/div/div[3]/div[4]/nav/ul/li[3]/a"));

        // add a random item
        addToBasketRandomProduct(By.xpath("//*[@id=\"2\"]/li"));
        wait(2);
        click(By.xpath("//*[@id=\"main-header\"]/div[4]/div/div/div/div[3]/div/div[3]/a/div"));

        // check list price vs basket price
        var priceBasket = findElement(By.className("total-price")).findElement(By.tagName("strong")).getText();
        Assert.assertEquals(price,priceBasket);

        //  change amount of item as 2
        var comboBox= findCombobox(By.tagName("select"));
        String value = "2";
        comboBox.selectByValue(value);
        wait(3);
        String comboBoxValue = comboBox.getFirstSelectedOption().getAttribute("value");
        Assert.assertEquals(value,comboBoxValue);
        wait(2);

        // delete item in basket
        click(By.className("btn-delete"));
        Assert.assertEquals("Sepetinizde ürün bulunmamaktadır.",findElement(By.xpath("//*[@id=\"empty-cart-container\"]/div[1]/div[1]/div/div[2]/h2")).getText());

    }

    public void addToBasketRandomProduct (By by){

        List<WebElement> list = getList(by);
        Random rand = new Random();
        WebElement randomElement = list.get(rand.nextInt(list.size() -  1));
        WebElement aTagChild = randomElement.findElement(By.tagName("a"));
        WebElement sTagChild = aTagChild.findElement(By.tagName("section"));
        WebElement spanTagChild = sTagChild.findElement(By.tagName("span"));
        log.info(aTagChild.getAttribute("title") + " " +  spanTagChild.getText());
        scrollUntilElementSeen(randomElement);
        moveToElement(randomElement);
        WebElement button =  randomElement.findElement(By.tagName("footer"));
        button.findElement(By.tagName("button")).click();
        price=spanTagChild.getText();

    }
}
