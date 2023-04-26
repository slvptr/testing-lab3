package lab3;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class Base64Test {
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
    public void md5() throws InterruptedException {
        test(firefoxDriver, jsFirefox);
        test(chromeDriver, jsChrome);
    }

    public void test(WebDriver driver, JavascriptExecutor js) throws InterruptedException {
        driver.get("https://xtool.ru/");
        driver.manage().window().setSize(new Dimension(1876, 1080));

        WebElement inputElement = driver.findElement(By.xpath("/html/body/main/section[3]/div/div/div[3]/article/a"));
        js.executeScript("arguments[0].click();", inputElement);

        WebElement encodeTextarea = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[2]/div/form[1]/textarea"));
        WebElement decodeTextarea = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[2]/div/form[2]/textarea"));
        encodeTextarea.sendKeys("yesyesyes");

        WebElement encodeBtn = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[2]/div/form[1]/div/button"));
        WebElement decodeBtn = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[2]/div/form[2]/div/button"));
        js.executeScript("arguments[0].click();", encodeBtn);

        Thread.sleep(500);

        Assert.assertEquals(decodeTextarea.getAttribute("value"), "eWVzeWVzeWVz");
        encodeTextarea.clear();
        js.executeScript("arguments[0].click();", decodeBtn);

        Thread.sleep(500);

        Assert.assertEquals(encodeTextarea.getAttribute("value"), "yesyesyes");
    }
}
