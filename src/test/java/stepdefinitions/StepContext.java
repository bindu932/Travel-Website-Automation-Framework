package stepdefinitions;

import io.cucumber.java.Scenario;

public class StepContext {

    private static Scenario scenario;

    public static void setScenario(Scenario sc) {
        scenario = sc;
    }

    public static Scenario getScenario() {
        return scenario;
    }
}