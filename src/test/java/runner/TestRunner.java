package runner;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
@CucumberOptions(                                                    //cucumber tag which is used to group the scenarios and execute the testcases
        features = "src/test/java/features",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "pretty",                                            // used to formats the console output in a readable way
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber.json",                         //generates the json files for extent and Allure
                "timeline:target/test-output-thread/",               //shows the execution timeline
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",  //sends results to allure
                "rerun:target/rerun.txt"                             // stores the failed scenarios
        },
        monochrome = true,                                            //to avoid the junk characters in output
        publish = false                                               // used to publish the data in cucumber server
)

public class TestRunner extends AbstractTestNGCucumberTests {         //cucumber+TestNG Runs the tests using TestNG features

    @Override
    @DataProvider(parallel = false)                                   //  keep false (single browser)
    public Object[][] scenarios() {                                   // suplies the scenarios for Execution
        return super.scenarios();
    }
}