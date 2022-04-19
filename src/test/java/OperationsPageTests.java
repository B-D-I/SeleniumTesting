import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class OperationsPageTests {

    protected WebDriver driver;
    protected JavascriptExecutor jse;

    @BeforeClass
    public void testSetup() {
        System.setProperty("webdriver.gecko.driver", "C:\\Program Files\\Gecko\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        jse = (JavascriptExecutor) driver;
    }

    @BeforeMethod
    public void openBrowser() {
        String basUrl = "https://grp20224-ct5038.uogs.co.uk/operations/operations.php";
        driver.get(basUrl);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
    }

    @Test
    public void verifyHomePageTitle() {
        String expectedTitle = "Water Bowser Operations";
        String actualTitle = "";
        actualTitle = driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void testNavigation(){
        WebElement home = driver.findElement(By.id("homeLink"));
        home.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String actualUrl="https://grp20224-ct5038.uogs.co.uk/home/index.php";
        String expectedUrl= driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl,actualUrl);
    }

    //Close the Driver
    @AfterClass
    public void afterClass() {
        driver.close();
    }
}
