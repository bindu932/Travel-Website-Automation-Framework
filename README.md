
***

# Travel Website Automation Framework

### (EaseMyTrip / MakeMyTrip – Selenium Automation Project)

***

##  Project Overview

This project is an **end-to-end automation framework** developed using **Selenium WebDriver, Java, Cucumber (BDD), and TestNG**, designed to automate real-world travel website scenarios.

The framework is built to solve the following automation problem:

***

##  Problem Statement

**Book one-way outstation cab and display the lowest charges**

### Requirements:

1. From **Delhi → Manali, Himachal Pradesh**
2. Pickup time: **6:30 AM, 23rd December**
3. Car Type: **SUV**
4. Display the **lowest available fare**

***

##  Detailed Use Cases

The framework automates the following three key scenarios:

***

### 1. Cab Booking Scenario

* Navigate to Cab booking section
* Select **Outstation (One Way)**
* Enter source: **Delhi**
* Enter destination: **Manali**
* Select travel **date and time**
* Apply filter: **SUV**
* Fetch and display **lowest cab price**

***

###  2. Gift Card Validation Scenario

* Navigate through **More Menu → Gift Cards**
* Select Gift Card
* Fill form using **Excel test data**
* Enter **invalid email address**
* Click **Pay Now**
* Capture validation error message:

```
Error: Email address is required and it should be valid
```

* Scroll to email field before capturing screenshot
* Capture screenshot only after validation
* Store error message in Excel

***

###  3. Hotel Booking Scenario

* Navigate to **Hotel section**
* Open guest selection dropdown
* Extract all available **adult count values**
* Store values in a list

Example output:

```
[2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
```

* Write extracted values into Excel

***

##  Key Automation Scope Covered

* Handling alerts and validation messages
* Filling forms and capturing error messages
* Scrolling web pages dynamically
* Extracting dropdown values and storing them in collections
* Navigating menus and submenus
* Navigating across pages
* Data-driven testing using Excel
* Capturing screenshots at correct execution stage

***

## ️ Technologies Used

| Technology         | Purpose                       |
| ------------------ | ----------------------------- |
| Java               | Programming language          |
| Selenium WebDriver | Browser automation            |
| Cucumber (BDD)     | Behavior-driven testing       |
| TestNG             | Test execution engine         |
| Maven              | Build & dependency management |
| Apache POI         | Excel read/write              |
| Log4j              | Logging                       |
| Allure Report      | Advanced reporting            |
| Extent Report      | UI reporting                  |
| Cucumber Reports   | Scenario reports              |

***

##  Framework Architecture

This framework follows **Page Object Model (POM)** design pattern.

```
src/test/java
 ├── pages               # Page classes (UI actions)
 ├── stepdefinitions     # Step definitions
 ├── hooks               # Setup & teardown
 ├── utils               # Utilities (Excel, screenshots, config)

src/test/resources
 ├── features            # Feature files
 ├── config.properties   # Browser & URL config

target/
 ├── screenshots         # Screenshots for reports
 ├── reports             # Generated reports
```

***

##  Reporting

###  Allure Report

* Step-level execution
* Screenshot captured after validation
* Run using:

```bash
allure serve target/allure-results
```

***

###  Extent Report

* Screenshot saved in:

```
target/screenshots/
```

***

###  Cucumber Report

* Scenario-level execution
* Screenshot attached only for **Gift Card scenario**

***

##  Screenshot Strategy

* Screenshot captured **ONLY after clicking Pay Now and validation appears**
* Scrolls to **email field before capturing**
* Avoids incorrect screenshots (like menu/hover state)
* Integrated with:
    * Allure
    * Extent Reports
    * Cucumber (via Hooks)

***

##  Excel Data Handling

###  Input Data (Sheet1)

* Amount
* Quantity
* Names
* Email
* Mobile numbers

###  Output Data

Stored in Excel:

```
Gift Card → Validation Error Message
Hotel → Adult Count List
Cab → Lowest Price
```

***

## ️ Execution

Run using Maven:

```bash
mvn clean test
```

***

##  Configuration

Update `config.properties`:

```properties
browser=chrome
url=https://www.easemytrip.com/
```

***

##  Key Features

* Data-driven framework using Excel
* Robust synchronization using WebDriverWait
* JavaScript execution for stable UI operations
* Error handling without failing test execution
* Clean logging for debugging
* Modular and scalable design
* Accurate screenshot capturing at correct stage

***

## Project Highlights

* Covers real-world testing scenarios
* Implements full automation lifecycle
* Combines UI automation + data validation + reporting
* Designed for **hackathon evaluation and industry-level usage**

***

##  Author

This automation framework is developed as part of a Selenium-based testing project.

***

# FINAL OUTPUT

Your GitHub now clearly shows:

```
✔ Problem understanding
✔ Automation coverage
✔ Framework design
✔ Tools & technology
✔ Real-world scenarios
✔ Reporting capability
```

***


