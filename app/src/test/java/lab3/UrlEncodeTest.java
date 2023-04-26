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

public class UrlEncodeTest {
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

        WebElement inputElement = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("/html/body/main/section[3]/div/div/div[5]/article/a")));
        js.executeScript("arguments[0].click();", inputElement);

        WebElement encodeTextarea = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[2]/div/form[1]/div[2]/input"));
        WebElement decodeTextarea = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[2]/div/form[2]/div[2]/input"));
        encodeTextarea.sendKeys("https://xtool.ru/php-online/urlencode_decode/");

        WebElement encodeBtn = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[2]/div/form[1]/div[3]/button"));
        WebElement decodeBtn = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[2]/div/form[2]/div[1]/button"));
        js.executeScript("arguments[0].click();", encodeBtn);

        Thread.sleep(500);

        Assert.assertEquals(decodeTextarea.getAttribute("value"), "https%3A%2F%2Fxtool.ru%2Fphp-online%2Furlencode_decode%2F");
        encodeTextarea.clear();
        js.executeScript("arguments[0].click();", decodeBtn);

        Thread.sleep(500);

        Assert.assertEquals(encodeTextarea.getAttribute("value"), "https://xtool.ru/php-online/urlencode_decode/");
    }
}
