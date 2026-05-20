***

# Travel Website Automation Framework

### (EaseMyTrip / MakeMyTrip – Selenium Automation Project)

***

## Problem Statement

**Book one-way outstation cab and display the lowest charges**

### Requirements

1. From Delhi to Manali, Himachal Pradesh
2. Pickup from Delhi at 6:30 AM on 23rd December
3. Car type should be SUV
4. Display the lowest available charges

***

## Detailed Description

1. Book one-way outstation cab from Delhi to Manali, Himachal Pradesh by selecting a future date and time, and filter results for SUV cars to display the lowest charges
2. Navigate to Gift Cards section and select Group Gifting, fill in card details using test data and enter invalid email, then capture and display the validation error message
3. On the Hotel booking page, extract all available adult count values, store them in a list, and display the extracted data

(Suggested site: MakeMyTrip; however, EaseMyTrip is used for implementation)

***

## Project Overview

This project is an end-to-end automation framework developed using Selenium WebDriver, Java, Cucumber (BDD), and TestNG.  
It automates real-world travel website scenarios with proper validation, reporting, and data-driven testing.

***

## Test Scenarios

### Cab Booking Scenario

* Navigate to Cab booking section
* Select Outstation (One Way)
* Enter source as Delhi
* Enter destination as Manali
* Select travel date and time
* Apply SUV filter
* Fetch and display the lowest cab price

***

### Gift Card Validation Scenario

* Navigate to More Menu → Gift Cards
* Select Gift Card option
* Fill form using Excel data
* Enter invalid email address
* Click Pay Now
* Capture validation error message:

```
Error: Email address is required and it should be valid
```

* Scroll to email field before capturing screenshot
* Capture screenshot only after validation appears
* Write error message into Excel

***

### Hotel Booking Scenario

* Navigate to Hotel booking section
* Open guest selection dropdown
* Extract all adult count values
* Store values in a list

Example:

```
[2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
```

* Save extracted data into Excel

***

## Key Automation Scope

* Handling validation errors
* Filling forms and capturing messages
* Scrolling web pages
* Extracting dropdown values into collections
* Menu navigation
* Page navigation
* Data-driven testing using Excel
* Screenshot capture at correct execution stage

***

## Technologies Used

| Technology         | Purpose              |
| ------------------ | -------------------- |
| Java               | Programming language |
| Selenium WebDriver | UI automation        |
| Cucumber (BDD)     | Test structure       |
| TestNG             | Execution            |
| Maven              | Build tool           |
| Apache POI         | Excel handling       |
| Log4j              | Logging              |
| Allure Report      | Reporting            |
| Extent Report      | UI reports           |
| Cucumber Reports   | Scenario reports     |

***

## Framework Architecture

This framework follows the Page Object Model design pattern.

```
src/test/java
 ├── pages
 ├── stepdefinitions
 ├── hooks
 ├── utils

src/test/resources
 ├── features
 ├── config.properties

target/
 ├── screenshots
 ├── reports
```

***

## Reporting

### Allure Report

* Detailed test execution
* Screenshot after validation

Run:

```
allure serve target/allure-results
```

***

### Extent Report

* Screenshot stored in:

```
target/screenshots/
```

***

### Cucumber Report

* Scenario-level execution
* Screenshot only for Gift Card scenario

***

## Screenshot Strategy

* Screenshot captured only after clicking Pay Now and validation appears
* Scrolls to email field before capturing
* Avoids early or incorrect screenshots
* Integrated with Allure, Extent, and Cucumber

***

## Excel Data Handling

### Input Data

* Amount
* Quantity
* Sender and receiver details
* Email
* Mobile numbers

### Output Data

Stored as:

```
Gift Card → Validation Error Message  
Hotel → Adult Count List  
Cab → Lowest Price  
```

***

## Execution

Run using Maven:

```
mvn clean test
```

***

## Configuration

Update config.properties:

```
browser=chrome
url=https://www.easemytrip.com/
```

***

## Key Features

* Data-driven automation
* Stable execution using waits and JavaScript
* Proper logging using Log4j
* Accurate screenshot capturing
* Modular and scalable framework

***

## Project Highlights

* Covers real-world automation scenarios
* Implements complete automation lifecycle
* Combines UI automation with data validation and reporting
* Suitable for hackathon evaluation and professional usage

***

## Author

This project is developed as part of Selenium automation testing practice.

***

## Final Output

✔ Problem statement clearly implemented  
✔ All scenarios automated  
✔ Data-driven execution  
✔ Reporting with screenshots  
✔ Clean and structured framework
## Allure Results
<img width="947" height="505" alt="image" src="https://github.com/user-attachments/assets/bd38ee18-161c-49a4-9858-0dc30a969dd0" />


***
