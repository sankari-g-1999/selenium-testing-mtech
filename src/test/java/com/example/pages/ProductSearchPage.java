package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductSearchPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By searchBox = By.id("twotabsearchtextbox");
    private By searchButton = By.id("nav-search-submit-button");
    private By firstProduct = By.cssSelector(".s-main-slot .s-result-item h2 a");
    private By noResultsMessage = By.xpath("//*[contains(text(),'did not match any products')]");

    public ProductSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean searchProduct(String productName) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(productName);
        driver.findElement(searchButton).click();

        // Check if no results message appears
        if (isNoResultsMessageDisplayed()) {
            System.out.println("No results found for product: " + productName);
            return false;
        }

        // Check if at least one product is found
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(firstProduct));
            return true;
        } catch (Exception e) {
            System.out.println("Error finding product: " + e.getMessage());
            return false;
        }
    }

    public boolean isNoResultsMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(noResultsMessage));
            return true;
        } catch (Exception e) {
            return false; // No "no results" message displayed
        }
    }

    public void selectFirstProduct() {
        // Clicks on the first product link in the search results
        wait.until(ExpectedConditions.elementToBeClickable(firstProduct)).click();
    }
}
