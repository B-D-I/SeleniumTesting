import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HomePageTests {

    protected WebDriver driver;
    protected JavascriptExecutor jse;
    protected WebElement pMassage;
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

    public void openLogin(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
        logout();
    }

    // also test password strength

//    @Test
//    public void testIncorrectLogin(WebElement username, WebElement password, WebElement submit){
//        String incorrectLoginText = "Incorrect Login";
//        username.sendKeys("testUser@email.com");
//        password.sendKeys("xxxxx");
//        submit.click();
//        // view alert
//        Alert alert=driver.switchTo().alert();
//        System.out.println(alert.getText());
//        // test login
//        Assert.assertEquals(alert.getText(), loginText);
//        alert.accept();
//    }
//
//
//    @Test
//    public void testNavs(){
//
//    }

//    @Test()
//    public void testLogin(){
//        WebDriverWait wait = new WebDriverWait(driver,30);
//        // find email and password inputs and insert credentials
//        WebElement username = driver.findElement(By.id("loginInputEmail1"));
//        WebElement password = driver.findElement(By.id("loginInputPassword1"));
//        WebElement submit = driver.findElement(By.id("loginSubmit"));
//        WebElement login = driver.findElement(By.id("loginLink"));
//        username.sendKeys("testUser@email.com");
//        password.sendKeys("!1Ppaaaaaa");
        // open model
//        login.click();

//        // submit login
//        submit.click();
//        // redirect
//        String actualUrl="https://grp20224-ct5038.uogs.co.uk/home/index.php";
//        String expectedUrl= driver.getCurrentUrl();
//        Assert.assertEquals(expectedUrl,actualUrl);
 //   }

//    //Test Case to Create Product
//    //Belows to a group so that tear down works for only this group
//    @Test(groups= {"createProduct"})
//    public void createProduct() {
//        WebElement txtName = driver.findElement(By.name("name"));
//        WebElement txtDesc = driver.findElement(By.name("description"));
//        WebElement txtLat = driver.findElement(By.name("lat"));
//        WebElement txtLng = driver.findElement(By.name("lng"));
//        WebElement btnSubmit = driver.findElement(By.name("submit"));
//
//        Actions builder = new Actions(driver);
//        Action addNameValue = builder.sendKeys(txtName, Keys.CONTROL, "Mobile").build();
//        addNameValue.perform();
//        //builder.pause(2000);
//
//        Action addDescValue = builder.sendKeys(txtDesc, Keys.CONTROL, "iPhone X").build();
//        addDescValue.perform();
//        //builder.pause(2000);
//
//        jse.executeScript("document.getElementsByName('lat')[0].setAttribute('type', 'text')");
//        jse.executeScript("document.getElementsByName('lat')[0].value = '';");
//
//        jse.executeScript("document.getElementsByName('lng')[0].setAttribute('type', 'text')");
//        jse.executeScript("document.getElementsByName('lng')[0].value = '';");
//
//        Action addLatValue = builder.sendKeys(txtLat, Keys.CONTROL, "51.90299028077605").build();
//        addLatValue.perform();
//
//        Action addLngValue = builder.sendKeys(txtLng, Keys.CONTROL, "-2.1029017359495796").build();
//        addLngValue.perform();
//
//        btnSubmit.click();
//
//        pMassage = driver.findElement(By.name("message"));
//
//        Assert.assertEquals(pMassage.getText(), "Product created successfully.");
//    }
//
//    //Tear down to Delete Product
//    @AfterGroups("createProduct")
//    public void deleteProduct() {
//        jse.executeScript("document.getElementsByName('productTableForm')[0].submit();");
//        try {
//            Thread.sleep(2000);
//
//        } catch (java.lang.InterruptedException e) {
//            System.out.println(e.toString());
//        }
//
//        pMassage = driver.findElement(By.name("message"));
//        if(pMassage.getText().equals("product deleted successfully")) {
//            System.out.println("Test Passed: Product deleted successfully");
//        } else {
//            System.out.println("Test Failed: Error " + pMassage.getText());
//        }
//    }

    //Close the Driver
    @AfterClass
    public void afterClass() {
        driver.close();
    }
}