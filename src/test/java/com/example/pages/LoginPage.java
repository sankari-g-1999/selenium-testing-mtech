package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By signInButton = By.id("nav-link-accountList");
    private By usernameField = By.id("ap_email");
    private By usernameContinueButton = By.id("continue");
    private By passwordField = By.id("ap_password");
    private By loginButton = By.id("signInSubmit");
    private By errorMessage = By.className("a-alert-heading");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean login(String username, String password) {
        driver.findElement(signInButton).click();
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(usernameContinueButton).click();

        // Check if error message appears after clicking Continue
        if (isErrorDisplayed()) {
            return false; // Invalid username
        }

        // Continue with password entry if no error on username
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
        driver.findElement(loginButton).click();

        // Check if error message appears after submitting password
        return !isErrorDisplayed(); // Returns true if login is successful, false otherwise
    }

    public boolean isErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return true; // Error message is displayed
        } catch (Exception e) {
            return false; // No error message found
        }
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
}
