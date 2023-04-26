package lab3;// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class LinkssearcherTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
    driver.get("https://xtool.ru/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void linkssearcher() {

    WebElement link1 = driver.findElement(By.xpath("//a[2]"));
    js.executeScript("arguments[0].click();", link1);

    WebElement link2 = driver.findElement(By.xpath("//div[2]/div[2]/div/ul/li[3]"));
    js.executeScript("arguments[0].click();", link2);

    WebElement link3 = driver.findElement(By.xpath("//div[2]/div[2]/div/ul/li[3]/a"));
    js.executeScript("arguments[0].click();", link3);

    WebElement link4 = driver.findElement(By.xpath("//p[2]/input"));
    js.executeScript("arguments[0].click();", link4);

    driver.findElement(By.xpath("//p[2]/input")).sendKeys("https://www.tune-it.ru");

    WebElement link5 = driver.findElement(By.xpath("//textarea"));
    js.executeScript("arguments[0].click();", link5);

    WebElement link6 = driver.findElement(By.xpath("//main/div"));
    js.executeScript("arguments[0].click();", link6);

    {
      WebElement element = driver.findElement(By.xpath("//div[3]/div/a"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }

    driver.findElement(By.xpath("//textarea")).click();


    driver.findElement(By.xpath("//textarea")).sendKeys("https://www.tune-it.ru");

    driver.findElement(By.xpath("//p[4]/button")).click();
    
  }
}
