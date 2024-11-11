package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.LoginPage;
import com.example.pages.ProductSearchPage;
import com.example.pages.CartPage;
import com.example.utils.ConfigLoader;
import com.example.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class AmazonTest extends BaseTest {

    @DataProvider(name = "amazonData")
    public Object[][] amazonData() throws IOException {
        List<List<String>> data = ExcelReader
                .readExcel(ConfigLoader.getProperty("xlsx_file_path"));

        Object[][] testData = new Object[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            testData[i] = data.get(i).toArray(new String[0]);
        }
        return testData;
    }

    @Test(dataProvider = "amazonData")
    public void testAmazonFlow(String testScenario, String username, String password, String product) {

        driver.get(ConfigLoader.getProperty("amazon_url"));

        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        boolean loginSuccess = loginPage.login(username, password);

        if (testScenario.equals("valid_login")) {
            Assert.assertTrue(loginSuccess, "Login failed. Check your credentials and try again.");

            // Step 2: Search for a product (only if login is successful)
            ProductSearchPage productSearchPage = new ProductSearchPage(driver);
            boolean searchSuccess = productSearchPage.searchProduct(product);
            Assert.assertTrue(searchSuccess, "Product search failed. Product may not be available.");

            // Step 3: Add the first product to the cart
            CartPage cartPage = new CartPage(driver);
            boolean addedToCart = cartPage.addToCart();
            Assert.assertTrue(addedToCart, "Failed to add product to cart");

            // Step 4: Verify cart contents
            boolean cartContentsVerified = cartPage.verifyCartContents();
            Assert.assertTrue(cartContentsVerified, "Cart does not contain the expected item");
        } else if (testScenario.equals("invalid_login")) {
            Assert.assertFalse(loginSuccess, "Expected login failure but login was successful");
            String actualErrorMessage = loginPage.getErrorMessage();
            System.out.println("Error message for login failure: " + actualErrorMessage);
            String expectedErrorMessage = "There was a problem"; // Update based on the actual Amazon error message
            Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
                    "Error message did not match expected.");
        }
    }
}
