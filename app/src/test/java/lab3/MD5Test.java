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

public class MD5Test {
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

        WebElement inputElement = driver.findElement(By.xpath("/html/body/main/section[3]/div/div/div[2]/article/a"));
        js.executeScript("arguments[0].click();", inputElement);

        WebElement textarea = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[2]/div[1]/form/textarea"));
        textarea.sendKeys("yesyesyes");

        WebElement submitBtn = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[2]/div[1]/form/button"));
        js.executeScript("arguments[0].click();", submitBtn);

        Thread.sleep(500);

        WebElement result = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[2]/div[2]/form/div"));
        Assert.assertEquals("c946739aa8e0f1008c32e311076f355f", result.getText());
    }
}
