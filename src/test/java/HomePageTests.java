import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HomePageTests {

    protected WebDriver driver;
    protected JavascriptExecutor jse;
    //Create Browser Driver and JavaScript Executor
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
        String basUrl = "https://grp20224-ct5038.uogs.co.uk/home/index.php";
        driver.get(basUrl);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
    }

    @Test
    public void verifyHomePageTitle() {
        String expectedTitle = "Water Bowser";
        String actualTitle = "";
        actualTitle = driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void verifyLogo(){
        WebElement logo = driver.findElement(By.id("logo_image"));
        Assert.assertTrue(logo.isDisplayed(), "Logo is not displayed");
    }

    // LOGIN TESTS
    public void openLogin(){
        WebElement login = driver.findElement(By.id("loginLink"));
        login.click();
        // wait for modal to load
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    public WebElement returnUsernameField(){
        WebElement username = driver.findElement(By.id("loginInputEmail1"));
        return username;
    }
    public WebElement returnPasswordField(){
        WebElement password = driver.findElement(By.id("loginInputPassword1"));
        return password;
    }
    public WebElement returnSubmitButton(){
        WebElement submit = driver.findElement(By.id("loginSubmit"));
        return submit;
    }
    public void logout(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement logout = driver.findElement(By.id("logoutTab"));
        logout.click();
        // wait for modal to load
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testCorrectLogin(){
        String loginText = "Logged in";

        openLogin();

        WebElement username = returnUsernameField();
        WebElement password = returnPasswordField();
        WebElement submit = returnSubmitButton();

        username.sendKeys("testUser@email.com");
        password.sendKeys("!1Ppaaaaaa");
        submit.click();
        Alert alert=driver.switchTo().alert();
        System.out.println(alert.getText());

        Assert.assertEquals(alert.getText(), loginText);
        alert.accept();
        logout();
    }

    @Test
    public void testIncorrectLogin(){
        String incorrectText = "Incorrect Login";
        openLogin();

        WebElement username = returnUsernameField();
        WebElement password = returnPasswordField();
        WebElement submit = returnSubmitButton();

        username.sendKeys("testWRONG@email.com");
        password.sendKeys("!1Ppxxxxxxx");
        submit.click();
        Alert alert=driver.switchTo().alert();
        System.out.println(alert.getText());

        Assert.assertEquals(alert.getText(), incorrectText);
        alert.accept();
    }

    @Test
    public void testBowserInfo(){
        WebElement bowserInfo = driver.findElement(By.id("viewBowserInformation"));
        bowserInfo.click();
        Set<String> window = driver.getWindowHandles();
        Iterator<String> i = window.iterator();
        String mainWindow = i.next();
        String popupWindow = i.next();
        driver.switchTo().window(popupWindow);
        driver.close();
        driver.switchTo().window(mainWindow);
    }

    // REGISTRATION TESTS
    public void openRegister(){
        WebElement register = driver.findElement(By.id("registrationTab"));
        register.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    public WebElement returnEmailField(){
        WebElement email = driver.findElement(By.id("registerInputEmail"));
        return email;
    }
    public WebElement returnRegPasswordField(){
        WebElement password_create = driver.findElement(By.id("password_create"));
        return password_create;
    }
    public WebElement returnPasswordConfirmField(){
        WebElement password_confirm = driver.findElement(By.id("password_confirm"));
        return password_confirm;
    }
    public WebElement returnRegSubmitButton(){
        WebElement regSubmit = driver.findElement(By.id("registerAccountSubmit"));
        return regSubmit;
    }
    public WebElement returnRegModalClose(){
        WebElement regClose = driver.findElement(By.id("registerModalClose"));
        return regClose;
    }

    @Test
    public void testPasswordsMatch(){
        openRegister();
        WebElement email = returnEmailField();
        WebElement password = returnRegPasswordField();
        WebElement confirmPassword = returnPasswordConfirmField();
        WebElement submit = returnRegSubmitButton();
        WebElement close = returnRegModalClose();

        email.sendKeys("test@email.com");
        password.sendKeys("!1Ppaaaaaa");
        confirmPassword.sendKeys("!1Ppxxxxxx");
        submit.click();
        submit.click();
        String alertType = "Password is strong, but passwords do NOT match!\n\n";

        Alert alert=driver.switchTo().alert();
        String alertText = alert.getText();
        System.out.println(alert.getText());

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        alert.accept();
        Assert.assertEquals(alertText, alertType);
        close.click();
    }

    @Test
    public void testPasswordStrength(){
        openRegister();
        WebElement email = returnEmailField();
        WebElement password = returnRegPasswordField();
        WebElement confirmPassword = returnPasswordConfirmField();
        WebElement submit = returnRegSubmitButton();
        WebElement close = returnRegModalClose();

        email.sendKeys("test@email.com");
        password.sendKeys("password1");
        confirmPassword.sendKeys("password1");
        submit.click();
        submit.click();

        String alertType = "Passwords do NOT match and is NOT strong enough! \n\nMust contain upper and lowercase letters, numbers and symbols\n";

        Alert alert=driver.switchTo().alert();
        String alertText = alert.getText();
        System.out.println(alert.getText());

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        alert.accept();
        Assert.assertEquals(alertText, alertType);
        close.click();
    }

    //Close the Driver
    @AfterClass
    public void afterClass() {
        driver.close();
    }
}