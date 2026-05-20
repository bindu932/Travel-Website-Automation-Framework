package utils;

import org.openqa.selenium.*;
import driver.DriverFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtil {

    public static String captureScreenshot(String fileName) {

        WebDriver driver = DriverFactory.getDriver();

        // ✅ Replace spaces to avoid file path issues
        fileName = fileName.replaceAll(" ", "_");

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String path = "target/screenshots/" + fileName + ".png";

        File dest = new File(path);

        try {
            dest.getParentFile().mkdirs();  // ✅ ensure folder exists
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("✅ Screenshot stored at: " + path);

        } catch (Exception e) {
            System.out.println("❌ Screenshot failed: " + e.getMessage());
        }

        return path;
    }
}