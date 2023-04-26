package lab3;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class JsonDecodeTest {
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
    public void jsonDecode() throws InterruptedException {
        test(firefoxDriver, jsFirefox, waitFirefox);
        test(chromeDriver, jsChrome, waitChrome);
    }

    public void test(WebDriver driver, JavascriptExecutor js, Wait<WebDriver> wait) throws InterruptedException {
        driver.get("https://xtool.ru/");
        driver.manage().window().setSize(new Dimension(1876, 1080));

        WebElement loginBtn = driver.findElement(By.xpath("/html/body/nav/div/div/div[2]/a[2]"));
        js.executeScript("arguments[0].click();", loginBtn);

        WebElement emailInput = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div/form/div[1]/input[1]")));
        emailInput.sendKeys("callmepedro@yandex.ru");

        WebElement passwordInput = driver.findElement(By.xpath("/html/body/div[1]/div/div/form/div[1]/input[2]"));
        passwordInput.sendKeys("qwe123");

        WebElement enterBtn = driver.findElement(By.xpath("/html/body/div[1]/div/div/form/div[2]/button[2]"));
        js.executeScript("arguments[0].click();", enterBtn);

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("/html/body/nav/div/div/div[2]/div/div[2]/div[2]/a")));
        driver.navigate().to("https://xtool.ru/");

        WebElement inputElement =  wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("/html/body/main/section[3]/div/div/div[1]/article/a[2]")));
        js.executeScript("arguments[0].click();", inputElement);

        WebElement textarea = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[2]/div[1]/form/textarea"));
        textarea.sendKeys("{\"yes\":\"no\"}");

        Thread.sleep(500);

        WebElement submitBtn = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[2]/div[1]/form/button"));
        js.executeScript("arguments[0].click();", submitBtn);

        Thread.sleep(1000);

        WebElement result = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[2]/div[2]/form/div/div[1]/pre"));
        Assert.assertEquals("{\n" +
                "    \"yes\": \"no\"\n" +
                "}", result.getText());
    }
}
