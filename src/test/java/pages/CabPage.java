package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

import utils.ConfigReader;
import utils.ExcelUtil;

import java.util.List;

public class CabPage extends BasePage {

    // Logger initialization
    Logger log = Logger.getLogger(CabPage.class);

    // -------------------- PAGE FACTORY LOCATORS --------------------

    @FindBy(linkText = "Cabs")
    WebElement cabsTab;

    @FindBy(xpath = "//label[normalize-space()='Outstation']")
    WebElement outstationOption;

    @FindBy(id = "sourceName")
    WebElement sourceField;

    @FindBy(id = "a_FromSector_show")
    WebElement fromCityInput;

    @FindBy(id = "a_ToSector_show")
    WebElement toCityInput;

    @FindBy(id = "datepicker")
    WebElement datePicker;

    @FindBy(className = "ui-datepicker-title")
    WebElement monthTitle;

    @FindBy(xpath = "//a[@data-handler='next']")
    WebElement nextArrow;

    @FindBy(xpath = "//a[normalize-space()='23']")
    WebElement travelDate;

    @FindBy(xpath = "//label[text()='AM']")
    WebElement amOption;

    @FindBy(xpath = "//li[text()='6 Hr.']")
    WebElement sixHour;

    @FindBy(xpath = "//li[text()='30 Min.']")
    WebElement thirtyMin;

    @FindBy(className = "done_d")
    WebElement doneButton;

    @FindBy(className = "srch-btn-c")
    WebElement searchButton;

    @FindBy(xpath = "//span[normalize-space()='suv']")
    WebElement suvFilter;

    @FindBy(xpath = "//span[normalize-space()='Any']")
    WebElement anyFilter;

    // -------------------- CONSTRUCTOR --------------------

    public CabPage(WebDriver driver) {
        super(driver);
    }

    // -------------------- MAIN METHOD --------------------

    public void bookCab() {

        log.info("Starting Cab booking flow");

        // Read values from config.properties
        String reqMon = ConfigReader.getProperty("reqMonth");
        String from = ConfigReader.getProperty("fromCity").toLowerCase();
        String to = ConfigReader.getProperty("toCity").toLowerCase();

        log.info("Config Data -> From: " + from + " To: " + to + " Month: " + reqMon);

        // Click Cabs tab
        wait.until(ExpectedConditions.elementToBeClickable(cabsTab)).click();
        log.info("Clicked Cabs tab");

        // Select Outstation
        wait.until(ExpectedConditions.elementToBeClickable(outstationOption)).click();
        log.info("Selected Outstation option");

        // Click source field
        wait.until(ExpectedConditions.elementToBeClickable(sourceField)).click();

        // Enter FROM city
        wait.until(ExpectedConditions.visibilityOf(fromCityInput)).sendKeys(from);
        log.info("Entered From City: " + from);

        // Dynamic selection for FROM city
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
        wait.until(ExpectedConditions.visibilityOf(toCityInput)).sendKeys(to);
        log.info("Entered To City: " + to);

        // Dynamic selection for TO city
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
        wait.until(ExpectedConditions.elementToBeClickable(datePicker)).click();
        log.info("Opened date picker");

        // Select required date
        while (true) {

            String currentMonth = wait.until(ExpectedConditions.visibilityOf(monthTitle)).getText();

            if (!currentMonth.equals(reqMon)) {
                nextArrow.click();
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(travelDate)).click();
                log.info("Travel date selected");
                break;
            }
        }

        // Select time (6:30 AM)
        wait.until(ExpectedConditions.elementToBeClickable(amOption)).click();
        wait.until(ExpectedConditions.visibilityOf(sixHour)).click();
        wait.until(ExpectedConditions.visibilityOf(thirtyMin)).click();
        wait.until(ExpectedConditions.elementToBeClickable(doneButton)).click();

        log.info("Selected pickup time (6:30 AM)");

        // Click Search
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        log.info("Clicked Search button");

        // Wait for results
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'₹')]")));
        log.info("Cab results loaded");

        // Apply SUV filter
        wait.until(ExpectedConditions.elementToBeClickable(suvFilter)).click();
        wait.until(ExpectedConditions.elementToBeClickable(anyFilter)).click();
        log.info("Applied SUV filter");

        // Wait after filter
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'₹')]")));

        // Fetch all prices
        List<WebElement> prices = driver.findElements(By.xpath("//*[contains(text(),'₹')]"));

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