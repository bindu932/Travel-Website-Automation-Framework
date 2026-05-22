package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.IOException;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if (extent == null) {

            ExtentSparkReporter spark =
                    new ExtentSparkReporter("target/extent-reports/ExtentReport.html");
                     // creates HTML report UI
            try {
                //loads the custom design/settings
                spark.loadXMLConfig("extent-config.xml");

            } //handles the error if config file not found
            catch (IOException e) {
                System.out.println("Failed to load extent config: " + e.getMessage());
            }
            //Initializes main report object
            extent = new ExtentReports();
            extent.attachReporter(spark);
           // Adds the info to the report
            extent.setSystemInfo("Project", "EaseMyTrip Automation");
            extent.setSystemInfo("Tester", "Automation User");
            extent.setSystemInfo("Environment", "QA");
        }
        // returns report object
        return extent;
    }
}