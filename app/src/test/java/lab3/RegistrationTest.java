package lab3;// Generated by Selenium IDE
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;


import java.time.Duration;
import java.util.*;

public class RegistrationTest {
    private WebDriver chromeDriver, firefoxDriver;
    JavascriptExecutor jsChrome, jsFirefox;
    Wait<WebDriver> waitChrome, waitFirefox;

    @Before
    public void setUp() {
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        firefoxDriver = new FirefoxDriver();
        firefoxDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        jsChrome = (JavascriptExecutor) chromeDriver;
        jsFirefox = (JavascriptExecutor) firefoxDriver;

        waitChrome = new FluentWait<WebDriver>(chromeDriver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(NoSuchElementException.class);
        waitFirefox = new FluentWait<WebDriver>(firefoxDriver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(NoSuchElementException.class);
    }
    @After
    public void tearDown() {
        chromeDriver.quit();
        firefoxDriver.quit();
    }
    @Test
    public void registration() throws InterruptedException {
        test(firefoxDriver, jsFirefox, waitFirefox);
        test(chromeDriver, jsChrome, waitChrome);
    }

    public void test(WebDriver driver, JavascriptExecutor js, Wait<WebDriver> wait) throws InterruptedException {
        driver.get("https://xtool.ru/");
        driver.manage().window().setSize(new Dimension(1876, 1080));

        WebElement registerBtn = driver.findElement(By.xpath("/html/body/nav/div/div/div[2]/a[1]"));
        js.executeScript("arguments[0].click();", registerBtn);

        WebElement emailInput = driver.findElement(By.xpath("/html/body/main/div/div[2]/form/table/tbody/tr/td/input"));
        emailInput.sendKeys("callmepedro@yandex.ru");

        WebElement agreement = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("/html/body/main/div/div[2]/form/input[1]")));
        js.executeScript("arguments[0].click();", agreement);

        WebElement regBtn = driver.findElement(By.xpath("/html/body/main/div/div[2]/form/input[2]"));
        js.executeScript("arguments[0].click();", regBtn);

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "Данный e-mail уже зарегестрирован в системе");
    }
}
