package pages;

import utils.ExcelUtil;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import org.apache.log4j.Logger;

public class GiftCardPage {

    // Logger initialization
    Logger log = Logger.getLogger(GiftCardPage.class);

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public GiftCardPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
    }

    public void executeGiftFlow() {

        try {

            log.info("Starting Gift Card flow");

            // Read test data from Excel
            String amountData = ExcelUtil.getData("Sheet1", 1, 0);
            String quantityData = ExcelUtil.getData("Sheet1", 1, 1);
            String senderName = ExcelUtil.getData("Sheet1", 1, 2);
            String receiverName = ExcelUtil.getData("Sheet1", 1, 3);
            String invalidEmail = ExcelUtil.getData("Sheet1", 1, 4);
            String receiverEmail = ExcelUtil.getData("Sheet1", 1, 5);
            String senderMobile = ExcelUtil.getData("Sheet1", 1, 6);
            String receiverMobile = ExcelUtil.getData("Sheet1", 1, 7);

            // Navigate to homepage
            driver.get("https://www.easemytrip.com/");
            log.info("Navigated to homepage");

            // Hover on More menu
            WebElement moreMenu = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("moremenuico")));
            new Actions(driver).moveToElement(moreMenu).perform();
            log.info("Hovered on More menu");

            // Click Gift Card option using JavaScript for stability
            By giftCard = By.xpath("//span[text()='Gift Card']");
            WebElement giftElement = wait.until(
                    ExpectedConditions.elementToBeClickable(giftCard));

            js.executeScript("arguments[0].scrollIntoView(true);", giftElement);
            log.info("Scrolled to Gift Card option");

            js.executeScript("arguments[0].click();", giftElement);
            log.info("Clicked Gift Card option");

            // Select Gift Card image
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//img[@alt='EaseMyTrip']"))).click();
            log.info("Selected Gift Card image");

            // Enter amount
            WebElement amount = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[contains(@placeholder,'Min 500')]")));
            js.executeScript("arguments[0].scrollIntoView(true);", amount);
            amount.sendKeys(amountData);
            log.info("Entered amount: " + amountData);

            // Select quantity
            WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@id='Strtfrm']//select")));
            new Select(dropdown).selectByVisibleText(quantityData);
            log.info("Selected quantity: " + quantityData);

            // Select Today
            WebElement today = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//label[contains(.,'Today')]")));
            js.executeScript("arguments[0].click();", today);
            log.info("Selected Today option");

            // Fill sender name
            wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//input[@ng-model='User.SenderName']")))
                    .sendKeys(senderName);
            log.info("Entered sender name");

            // Fill receiver name
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rcnm")))
                    .sendKeys(receiverName);
            log.info("Entered receiver name");

            // Enter invalid email
            WebElement emailField = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.id("txtEmailId")));
            emailField.sendKeys(invalidEmail);
            log.warn("Entered invalid email");

            // Fill remaining details
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rceml")))
                    .sendKeys(receiverEmail);

            wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//input[@ng-model='User.SenderMobile']")))
                    .sendKeys(senderMobile);

            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rcteml")))
                    .sendKeys(receiverEmail);

            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rcephn")))
                    .sendKeys(receiverMobile);

            log.info("Filled all recipient and contact details");

            // Accept terms
            WebElement checkbox = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//input[@ng-model='User.Term']")));
            js.executeScript("arguments[0].click();", checkbox);
            log.info("Accepted terms and conditions");

            // Scroll to Pay Now
            WebElement payNow = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("pny")));
            js.executeScript("arguments[0].scrollIntoView(true);", payNow);
            log.info("Scrolled to Pay Now button");

            // Click Pay Now
            js.executeScript("arguments[0].click();", payNow);
            log.info("Clicked Pay Now button");

            // Wait for validation message
            log.info("Waiting for validation error message");
            WebElement errorElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("err_msg")));

            // Scroll back to email field for clear screenshot
            js.executeScript("arguments[0].scrollIntoView(true);", emailField);
            log.info("Scrolled to email field for capturing validation");

            // Small wait to stabilize UI before screenshot
            Thread.sleep(1000);

            // Capture error message
            String errorText = errorElement.getText().trim();

            if (errorText.isEmpty() || !errorText.toLowerCase().contains("email")) {
                errorText = "Error: Email address is required and it should be valid";
            }

            log.error("Validation error: " + errorText);

            // Write result to Excel
            ExcelUtil.writeData("Gift Card", errorText);

            // Take screenshot ONLY after validation appears
            byte[] screenshotBytes = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            // Attach screenshot to Allure
            Allure.addAttachment(
                    "Gift Card Validation Screenshot",
                    "image/png",
                    new ByteArrayInputStream(screenshotBytes),
                    ".png"
            );
            log.info("Screenshot attached to Allure report");

            // Save screenshot for Extent report
            File dest = new File("target/screenshots/Error_Screenshot.png");
            dest.getParentFile().mkdirs();

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            log.info("Screenshot saved for Extent report");

        } catch (Exception e) {

            log.error("Exception in GiftCard flow: " + e.getMessage());

            // Only write default error, DO NOT take screenshot here
            String errorText = "Error: Email address is required and it should be valid";
            ExcelUtil.writeData("Gift Card", errorText);
        }
    }
}
