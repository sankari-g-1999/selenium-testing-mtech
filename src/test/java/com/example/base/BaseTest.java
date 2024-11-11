package com.example.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.example.utils.ConfigLoader;

public class BaseTest {
    public WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set Chrome options for headless mode
        ChromeOptions options = new ChromeOptions();

        if (Boolean.parseBoolean(ConfigLoader.getProperty("headless"))) {
            options.addArguments("--headless"); // Run in headless mode
            options.addArguments("--disable-gpu"); // Applicable on Windows to avoid issues
            options.addArguments("--window-size=1920,1080"); // Optional, to ensure consistent layout
            options.addArguments("--disable-extensions");
            options.addArguments("--no-sandbox"); // Bypass OS security model (for Linux environments)
            options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems on Linux
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
