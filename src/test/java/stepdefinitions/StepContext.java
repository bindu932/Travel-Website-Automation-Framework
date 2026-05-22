package stepdefinitions;

import io.cucumber.java.Scenario;

public class StepContext {          //helper utility class

    private static Scenario scenario; //static variable to store the current scenario

    public static void setScenario(Scenario sc) {
        scenario = sc;
    }

    public static Scenario getScenario() {
        return scenario;
    }
}//To store and share the Cucumber Scenario object
// across different parts of the framework
// Static means Accessible anywhere without creating object