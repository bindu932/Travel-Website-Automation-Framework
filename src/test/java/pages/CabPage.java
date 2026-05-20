package pages;

import org.apache.log4j.Logger;
import utils.ConfigReader;
import utils.ExcelUtil;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class CabPage {

    // Logger initialization
    Logger log = Logger.getLogger(CabPage.class);

    WebDriver driver;
    WebDriverWait wait;

    public CabPage(WebDriver driver) {
        this.driver = driver;

        //  Read timeout from config
        wait = new WebDriverWait(driver, Duration.ofSeconds(
                Integer.parseInt(ConfigReader.getProperty("timeout"))
        ));
    }

    public void bookCab() {

        log.info("Starting Cab booking flow");

        //  Read values from config.properties
        String reqMon = ConfigReader.getProperty("reqMonth");
        String from = ConfigReader.getProperty("fromCity").toLowerCase();
        String to = ConfigReader.getProperty("toCity").toLowerCase();

        log.info("Config Data -> From: " + from + " To: " + to + " Month: " + reqMon);

        // Click Cabs tab
        WebElement cabsTab = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Cabs")));
        cabsTab.click();
        log.info("Clicked Cabs tab");

        // Select Outstation
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[normalize-space()='Outstation']"))).click();
        log.info("Selected Outstation option");

        // Click source field
        wait.until(ExpectedConditions.elementToBeClickable(By.id("sourceName"))).click();

        // Enter FROM city
        WebElement fromCity = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("a_FromSector_show")));
        fromCity.sendKeys(from);
        log.info("Entered From City: " + from);

        // Select FROM city dynamically
        By fromOption = By.xpath("//div[@class='auto_sugg_tttl' and contains(normalize-space(),'"
                + from + "')]");

        for (int retry = 0; retry < 3; retry++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(fromOption)).click();
                log.info("Selected From City from suggestions");
                break;
            } catch (StaleElementReferenceException e) {
                log.warn("Retrying From city selection attempt " + (retry + 1));
            }
        }

        // Enter TO city
        WebElement toCity = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("a_ToSector_show")));
        toCity.sendKeys(to);
        log.info("Entered To City: " + to);

        // Select TO city dynamically
        By toOption = By.xpath("//div[@class='auto_sugg_tttl' and contains(normalize-space(),'"
                + to + "')]");

        for (int retry = 0; retry < 3; retry++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(toOption)).click();
                log.info("Selected To City from suggestions");
                break;
            } catch (StaleElementReferenceException e) {
                log.warn("Retrying To city selection attempt " + (retry + 1));
            }
        }

        // Open date picker
        WebElement datePicker = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("datepicker")));
        datePicker.click();
        log.info("Opened date picker");

        // Select required date
        while (true) {

            WebElement month = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("ui-datepicker-title")));

            if (!month.getText().equals(reqMon)) {
                driver.findElement(By.xpath("//a[@data-handler='next']")).click();
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[normalize-space()='23']"))).click();
                log.info("Travel date selected");
                break;
            }
        }

        // Select time (6:30 AM)
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='AM']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[text()='6 Hr.']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[text()='30 Min.']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.className("done_d"))).click();
        log.info("Selected pickup time (6:30 AM)");

        // Click Search
        wait.until(ExpectedConditions.elementToBeClickable(By.className("srch-btn-c"))).click();
        log.info("Clicked Search button");

        // Wait for results
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(),'₹')]")));
        log.info("Cab results loaded");

        // Apply SUV filter
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='suv']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Any']"))).click();
        log.info("Applied SUV filter");

        // Wait after filter
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(),'₹')]")));

        // Fetch all prices
        List<WebElement> prices = driver.findElements(
                By.xpath("//*[contains(text(),'₹')]"));

        int minPrice = Integer.MAX_VALUE;

        log.info("Extracting SUV prices");

        for (WebElement price : prices) {

            String text = price.getText().replaceAll("[^0-9]", "");

            if (!text.isEmpty()) {
                try {
                    int value = Integer.parseInt(text);

                    if (value < minPrice) {
                        minPrice = value;
                    }

                } catch (Exception e) {
                    log.warn("Skipping invalid price format");
                }
            }
        }

        // Write result to Excel
        if (minPrice == Integer.MAX_VALUE) {

            log.warn("No SUV prices found");
            ExcelUtil.writeData("Cab Booking", "No price found");

        } else {

            log.info("Lowest SUV Cab Price: ₹" + minPrice);
            ExcelUtil.writeData("Cab Booking", "Lowest Price: ₹" + minPrice);
        }

        log.info("Cab booking flow completed");
    }
}
