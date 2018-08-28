package mainPageTest;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;



import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.testng.Assert.assertEquals;



public class CastoramaDesktopTests {



    WebDriver driver = new ChromeDriver();



    @BeforeMethod

    public void beforeEachTest() {


        driver.manage().window().maximize();
        driver.get("http://www.castorama.pl");
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        /*
        Cookie env = new Cookie("bolddevapp", "preprod");
        driver.manage().addCookie(env);
        driver.navigate().refresh();
*/

    }


    @Test

    public void shouldCheckMainPageUrl() {


        WebDriverWait waitForElement = new WebDriverWait(driver, 5);
       // Assert.assertEquals(mainPageUrl, driver.getCurrentUrl());


    }


    @Test

    public void shouldSearchProduct() {

        WebDriverWait waitForElement = new WebDriverWait(driver, 5);
      //  driver.findElement(searchButton).sendKeys("drzwi");
        driver.findElement(By.cssSelector("form#search_mini_form > button")).click();
        waitForElement.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.el")));
        String topName = driver.findElement(By.cssSelector("span.blue-strong")).getText();
        assertEquals(topName, "DRZWI");

    }

    @Test

    public void shouldAddProductToCartFromProductCart() {


        WebDriverWait waitForElement = new WebDriverWait(driver, 5);
        driver.findElement(By.cssSelector("div.el > h2.product-name > a")).click();
        waitForElement.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div#add-to-box-second > button"))).click();
        waitForElement.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.success-add-to-cart")));
        String succesMsg = driver.findElement(By.cssSelector("span.msg-header.text-uppercase")).getText();
        assertEquals("PRODUKT ZOSTAŁ DODANY DO KOSZYKA", succesMsg.toUpperCase());
        //driver.close();


    }

    @Test

    public void shouldSelectStore() {


        WebDriverWait waitForElement = new WebDriverWait(driver, 10);
        driver.findElement(By.cssSelector("button.button.btn-yellow.shop-selection-master")).click();
        driver.findElement(By.cssSelector("div.chosen-container.chosen-container-single")).click();
        driver.findElement(By.xpath("//div[@class='chosen-drop'] // ul[@class='chosen-results'] // li[@data-option-array-index='24']")).click();
        waitForElement.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span#market-name")));
        String currentShop = driver.findElement(By.cssSelector("span#market-name")).getText();
        Assert.assertEquals(currentShop, "Kraków Pilotów");

        // driver.close();

    }

    @Test

    public void shouldCheckNavLinks() {
        int navLinkSize = driver.findElements(By.xpath("//div[@class='top_links'] / a")).size();

        for (int i = 0; i < navLinkSize; i++) {
            driver.findElements(By.xpath("//div[@class='top_links'] / a")).get(i).click();
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.equals("https://www.castorama.pl/inspiracje-i-porady.html")) {
                driver.findElement(By.cssSelector("div.inspiration-top")).isDisplayed();
            }

            if (currentUrl.equals("https://www.castorama.pl/forum/index.php")) {
                driver.findElement(By.cssSelector("div.forum-info-sec > img")).isDisplayed();
            }

            if (currentUrl.equals("https://www.castorama.pl/informacje/sklepy")) {
                String currentPageTitle = driver.findElement(By.cssSelector("div.page-title.cms-page-title")).getText();
                Assert.assertEquals(currentPageTitle, "CASTORAMA SKLEPY");
            }
            if (currentUrl.equals("https://www.castorama.pl/informacje/pomoc")) {
                boolean elementPresent = driver.findElement(By.cssSelector("div.help-header.bold-lazy-load_background-wrapper > div.bold-lazy-load > img")).isDisplayed();
                Assert.assertTrue(elementPresent);
            }

        }

    }

    @Test

    public void shouldCheckRightTopLoginWindow() {
        Actions mouseaction = new Actions(driver);
        WebElement loginNavRight = driver.findElement(By.cssSelector("div.top_links > span.top_links_right > span#account"));
        mouseaction.moveToElement(loginNavRight).build().perform();
        driver.findElement(By.cssSelector("input#mail_login")).isDisplayed();
        driver.findElement(By.cssSelector("input#pass")).isDisplayed();
        driver.findElement(By.cssSelector("button.btn-blue.full-btn")).click();

    }

    @Test

    public void shouldCheckRightTopWishlistWindow() {
        Actions mouseaction = new Actions(driver);
        WebElement loginNavRightWishlist = driver.findElement(By.cssSelector("span.wishlist"));
        mouseaction.moveToElement(loginNavRightWishlist).build().perform();
        WebDriverWait waitForElement = new WebDriverWait(driver, 5);
        waitForElement.until(ExpectedConditions.visibilityOfElementLocated(By.id("wishlist-popup")));
        driver.findElement(By.cssSelector("div#wishlist-popup")).isDisplayed();

    }

    @Test

    public void shouldAddProductToWishListFromMainPage() {
        WebElement productOnMainPage = driver.findElement(By.cssSelector("div.el > h2.product-name > a"));
        Actions mouseaction = new Actions(driver);
        mouseaction.moveToElement(productOnMainPage).build().perform();
        driver.findElement(By.cssSelector("button.button.btn-addToWishlist.simple.btn-blue.btn-wishlist")).click();
        WebDriverWait waitForElement = new WebDriverWait(driver, 5);
        waitForElement.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.success-add-to-wishlist")));
        String currentNumber = driver.findElement(By.cssSelector("div.col-xs-12.col-md-8 > strong")).getText();
        Assert.assertNotNull(currentNumber);
    }


    @Test

    public void shouldMainPageHaveAllElements()

    {
        driver.findElement(By.cssSelector("div.container.header-container")).isDisplayed();
        driver.findElement(By.cssSelector("div.jcarousel.bold-lazy-load_jCarousel.banner-main > ul > li > a > img")).isDisplayed();

    }

    @Test

    public void shouldFindShop() {
        driver.navigate().to("https://www.castorama.pl/informacje/sklepy");
        driver.findElement(By.cssSelector("input#filter-store-list")).sendKeys("80-766");
        /// WebElement shopSearchResult = driver.findElement(By.cssSelector("div.el > h2.product-name > a"));
        driver.findElement(By.cssSelector("div.col-xs-9.col-sm-10")).isDisplayed();
    }

    @Test

    public void shouldAddReviewToProduct() throws InterruptedException {
        driver.findElement(By.cssSelector("div.el > h2.product-name > a")).click();
        driver.navigate().to(driver.getCurrentUrl() + "#product_review");
        WebDriverWait waitForElement = new WebDriverWait(driver, 10);
        driver.findElement(By.cssSelector("div.pull-right > button")).click();
        waitForElement.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#uni-popup-msg")));
        driver.findElement(By.cssSelector("div.input-box > input#nickname_field")).sendKeys("Pawel");
        driver.findElement(By.cssSelector("div#ratingWrapper > label:nth-of-type(5)")).click();
        driver.findElement(By.cssSelector("div.input-box > textarea#review_field")).sendKeys("Tekst testowy");
        waitForElement.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.buttons-set > button")));
        driver.findElement(By.cssSelector("div.buttons-set > button")).click();
        waitForElement.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.msg-content")));

    }


    @Test

    public void shouldCheckCategoryOnInspirationPage()
    {
       // WebDriverWait waitForElement = new WebDriverWait(driver, 10);
        driver.findElement(By.cssSelector("div.top_links > a")).click();
        driver.findElement(By.cssSelector("div.roll_inspiration")).click();
        int cssSize =  driver.findElements(By.cssSelector("div.level1.-ce-capture > ul.level1 > li")).size();
        System.out.println(cssSize);
         // waitForElement.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='level1 -ce-capture']/ul[@class='level1']/li/a")));
        //  String currentTitle = driver.findElement(By.cssSelector("h1.category-title__heading")).getText();
         // Assert.assertEquals(currentTitle,"POKRYCIA DACHOWE");

    }

    @Test

    public void ShouldAddReviewToInsppiration()
    {
        WebDriverWait waitForElement = new WebDriverWait(driver, 10);
        shouldCheckCategoryOnInspirationPage();
        driver.findElement(By.cssSelector("div.col-xs-8.col-md-6.col-lg-7.bold-lazy-load_wrapper.inspiration-list-item > a > img")).click();
        driver.findElement(By.cssSelector("div.pull-right > button[data-action='review-form-show']")).click();
        waitForElement.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#uni-popup-msg")));
        driver.findElement(By.cssSelector("div.input-box > input#nickname_field")).sendKeys("Pawel");
        driver.findElement(By.cssSelector("div#ratingWrapper > label:nth-of-type(5)")).click();
        driver.findElement(By.cssSelector("div.input-box > textarea#review_field")).sendKeys("Tekst testowy");
        waitForElement.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.buttons-set > button")));
        driver.findElement(By.cssSelector("div.buttons-set > button")).click();
        waitForElement.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.msg-content")));

    }


    @Test

    public void shuldAddProductToCartFromMainList() {

        //driver.findElement(By.cssSelector("div.show-more.no-display")).click();
        int productSize = driver.findElements(By.cssSelector("div.el > div.wrap.bold-lazy-load > a")).size();

        for (int i = 0; i < productSize; i++) {
            driver.findElement(By.cssSelector("div.show-more.no-display")).click();
            WebElement productMainPage = (WebElement) driver.findElements(By.cssSelector("div.el > div.wrap.bold-lazy-load > a")).get(i);
            Actions mouseaction = new Actions(driver);
            mouseaction.moveToElement(productMainPage).build().perform();
            productMainPage.click();
            driver.navigate().to("https://www.castorama.pl/");

        }
    }

    @Test

    public void shouldCheckCalculate()
    {
        WebDriverWait waitForElement = new WebDriverWait(driver, 10);
        driver.navigate().to("https://www.castorama.pl/produkty/wykonczenie/drewno-i-drewnopodobne/panele-podlogowe/panele/panel-podlogowy-dab-barrow-ac4-2-47-m2.html");
        driver.findElement(By.cssSelector("div.input-wrapper-product-sidebar > input#productUnitsFirst")).clear();
        driver.findElement(By.cssSelector("div.input-wrapper-product-sidebar > input#productUnitsFirst")).sendKeys("3");
        waitForElement.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.input-wrapper-product-sidebar > input#productUnitsSecond")));
      //  WebElement currentPackageSize = driver.findElement(By.cssSelector("div.input-wrapper-product-sidebar > input#productUnitsSecond")).getAttribute();
       // System.out.println(currentPackageSize);
    }




    @AfterMethod(alwaysRun = true)


    public void catchExceptions(ITestResult result) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy|MM|dd HH:mm:ss");
        Date date = new Date();
        String screenDate = dateFormat.format(date);
        String methodName = result.getName();
        if (!result.isSuccess()) {
            System.out.println("TEST FAILURE - SCREENSHOT DONE!!!");
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(scrFile, new File(("failure_screenshots/" + methodName + " " + screenDate + ".png")));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @AfterMethod

    public void shouldCloseBroswer() {

        driver.close();
    }

}

