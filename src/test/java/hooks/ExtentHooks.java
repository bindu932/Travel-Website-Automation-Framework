package hooks;

import com.aventstack.extentreports.*;
import io.cucumber.java.*;
import reports.ExtentManager;

public class ExtentHooks {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Before
    public void startTest(Scenario scenario) {
        ExtentTest extentTest = extent.createTest(scenario.getName());
        test.set(extentTest);
    }

    @After
    public void endTest(Scenario scenario) {

        if (scenario.isFailed()) {
            test.get().fail("Test Failed");
        } else {
            test.get().pass("Test Passed");
        }

        extent.flush();
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}
