package stepdefinitions;

import io.cucumber.java.en.*;
import pages.*;
import driver.DriverFactory;
import org.openqa.selenium.WebDriver;

public class EaseMyTripSteps {

    WebDriver driver = DriverFactory.getDriver();

    CabPage cab;
    GiftCardPage gift;
    HotelPage hotel;

    // COMMON STEP
    @Given("launch application")
    public void launch_application() {
        // handled by Hooks (browser already opened)
    }

    // CAB BOOKING

    @When("perform cab booking")
    public void cab_booking() {
        cab = new CabPage(driver);
        cab.bookCab();
    }

    @Then("verify cab booking completed")
    public void verify_cab_booking() {
        System.out.println("Cab booking executed successfully");
    }

    // GIFT CARD

    @When("perform gift card validation")
    public void gift_card() {
        gift = new GiftCardPage(driver);
        gift.executeGiftFlow();

        // to fail test case if needed
        // throw new RuntimeException("Gift Card flow failed");
    }

    @Then("verify gift card validation completed")
    public void verify_gift_card() {
        System.out.println("Gift card validation completed");
    }

    //  HOTEL

    @When("perform hotel adult extraction")
    public void hotel_step() {
        hotel = new HotelPage(driver);
        hotel.getAdultList();
    }

    @Then("verify hotel data extracted")
    public void verify_hotel() {
        System.out.println("Hotel data extracted successfully");
    }
}
