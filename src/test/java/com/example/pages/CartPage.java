package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locator for the first "Add to Cart" button
    private By addToCartButton = By.id("a-autoid-1-announce");
    // Locator for the toaster confirmation message (update if necessary)
    private By cartConfirmationToaster = By.className("a-changeover-inner");
    private By cartButton = By.id("nav-cart");
    private By cartItem = By.cssSelector(".sc-list-body");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Clicks the first "Add to Cart" button and waits for the confirmation toaster.
     * 
     * @return true if the item is added successfully, false otherwise.
     */
    public boolean addToCart() {
        try {
            // Wait for the first "Add to Cart" button to be clickable and click it
            WebElement addToCartButtonElement = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));

            // Scroll to the button to ensure it's in view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButtonElement);
            addToCartButtonElement.click();

            // Wait for the confirmation toaster to appear
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartConfirmationToaster));
            System.out.println("Item added to cart successfully via toaster confirmation.");

            // Wait for the toaster to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(cartConfirmationToaster));
            System.out.println("Toaster disappeared, proceeding with further actions.");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to add product to cart: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifies if the item has been added to the cart by navigating to the cart
     * page.
     *
     * @return true if cart contains at least one item, false otherwise
     */
    public boolean verifyCartContents() {
        try {
            // Click the cart button to go to the cart page
            wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();

            // Check if there is at least one item in the cart
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartItem));
            System.out.println("Verified cart contents successfully.");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to verify cart contents: " + e.getMessage());
            return false;
        }
    }
}
