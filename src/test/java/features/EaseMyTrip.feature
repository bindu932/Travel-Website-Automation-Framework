Feature: EaseMyTrip Automation

  Scenario: Cab Booking Test Case
    Given launch application
    When perform cab booking
    Then verify cab booking completed

  Scenario: Gift Card Test Case
    Given launch application
    When perform gift card validation
    Then verify gift card validation completed

  Scenario: Hotel Test Case
    Given launch application
    When perform hotel adult extraction
    Then verify hotel data extracted
