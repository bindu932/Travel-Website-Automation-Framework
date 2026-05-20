package pages;

import utils.ExcelUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;
import org.apache.log4j.Logger;

public class CabPage {

    Logger log = Logger.getLogger(CabPage.class);

    WebDriver driver;
    WebDriverWait wait;

    public CabPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void bookCab() {

        log.info("Starting Cab booking flow");

        String reqMon = "December 2026";

        // Click Cabs tab
        log.info("Clicked Cabs tab");
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Cabs"))).click();

        // Select Outstation
        log.info("Selected Outstation option");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[normalize-space()='Outstation']"))).click();

        // Click source field
        wait.until(ExpectedConditions.elementToBeClickable(By.id("sourceName"))).click();

        // Enter FROM city
        log.info("Entered source city: Delhi");
        WebElement fromCity = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("a_FromSector_show")));
        fromCity.sendKeys("Delhi");

        // Delhi selection
        By delhiOption = By.xpath("//div[normalize-space()='delhi']");
        for (int i = 0; i < 3; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(delhiOption)).click();
                break;
            } catch (StaleElementReferenceException e) {
                log.warn("Retrying Delhi selection...");
            }
        }

        // Enter TO city
        log.info("Entered destination city: Manali");
        WebElement toCity = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("a_ToSector_show")));
        toCity.sendKeys("Manali");

        // Manali selection
        By manaliOption = By.xpath("//div[normalize-space()='manali']");
        for (int i = 0; i < 3; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(manaliOption)).click();
                break;
            } catch (StaleElementReferenceException e) {
                log.warn("Retrying Manali selection...");
            }
        }

        // Open date picker
        wait.until(ExpectedConditions.elementToBeClickable(By.id("datepicker"))).click();

        // Select date
        log.info("Selecting date: 23 " + reqMon);
        while (true) {

            WebElement month = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("ui-datepicker-title")));

            if (!month.getText().equals(reqMon)) {
                driver.findElement(By.xpath("//a[@data-handler='next']")).click();
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[normalize-space()='23']"))).click();
                break;
            }
        }

        // Select time
        log.info("Selected travel time: 6 Hr 30 Min");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[text()='6 Hr.']"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[text()='30 Min.']"))).click();

        // Done
        wait.until(ExpectedConditions.elementToBeClickable(By.className("done_d"))).click();

        // Search
        log.info("Clicked Search button");
        wait.until(ExpectedConditions.elementToBeClickable(By.className("srch-btn-c"))).click();

        //  Replace Thread.sleep with wait
        log.info("Waiting for cab results to load");
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(),'₹')]")
        ));

        // SUV checkbox
        By suvCheckbox = By.xpath("//*[@id=\"body\"]/app-root/div[3]/ng-component/div[2]/section[2]/div/div/div[1]/div/div[3]/div[2]/label[3]/div[1]/span[2]");

        WebElement suvElement = wait.until(ExpectedConditions.presenceOfElementLocated(suvCheckbox));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", suvElement);

        for (int i = 0; i < 3; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(suvCheckbox)).click();
                log.info("SUV filter applied");
                break;
            } catch (Exception e) {
                log.warn("Retrying SUV filter click...");
            }
        }

        // Wait after filter
        log.info("Fetching SUV prices");
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(),'₹')]")
        ));

        // Get prices
        List<WebElement> prices = driver.findElements(
                By.xpath("//*[contains(text(),'₹')]"));

        int minPrice = Integer.MAX_VALUE;

        for (WebElement price : prices) {

            String text = price.getText().replaceAll("[^0-9]", "");

            if (!text.isEmpty()) {
                int value = Integer.parseInt(text);

                if (value < minPrice) {
                    minPrice = value;
                }
            }
        }

        // Final output
        if (minPrice == Integer.MAX_VALUE) {
            log.warn("No SUV prices found");
            ExcelUtil.writeData("Cab Booking", "No price found");
        } else {
            log.info("Lowest SUV Cab Price: " + minPrice);
            ExcelUtil.writeData("Cab Booking", "Lowest Price: " + minPrice);
        }
    }
}