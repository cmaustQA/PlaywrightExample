package org.example.negative;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.util.Map;

/**
 * This class contains negative test cases for account creation.
 * It uses Playwright for browser automation and JUnit for assertions.
 */
@Tag("negative")
@Tag("chromium")
public class AccountCreationNegativeTests {

    private static Browser browser;
    private static Page page;

    // Map to hold the data-testid attributes of the fields
    private final Map<String, String> fields = Map.of(
            "loanNumber", "[data-testid=loanNumber]",
            "ssn", "[data-testid=ssn]",
            "mobile", "[data-testid=mobile]",
            "email", "[data-testid=email]",
            "confirmEmail", "[data-testid=confirmEmail]",
            "password", "[data-testid=Password]",
            "confirmPassword", "[data-testid=confirmPassword]"
    );

    /**
     * This method sets up the browser and navigates to the account creation page.
     */
    @BeforeAll
    public static void setUp() {
        Playwright playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        BrowserContext context = browser.newContext();
        page = context.newPage();
        page.navigate("https://account.freedommortgage.com/");
    }

    /**
     * This test case validates the loan number field with an invalid input.
     */
    @Test
    public void testLoanNumberValidation() {
        testFieldValidation("loanNumber", "1234567890", "Backspace", "You must have a valid 10-digit loan number from Freedom Mortgage to continue. Your loan number appears on your monthly statement.");
    }

    /**
     * This test case validates the SSN field cannot accept an invalid input.
     */
    @Test
    public void testInvalidSSNInput() {
        page.waitForSelector(fields.get("ssn"));
        page.fill(fields.get("ssn"), "abc");
        page.fill(fields.get("ssn"), "!@#$%^&*()");
        String ssnPlaceholder = page.locator(fields.get("ssn")).getAttribute("placeholder");
        Assertions.assertEquals("XXX-XX-XXXX", ssnPlaceholder, "Placeholder text is not as expected.");
    }

    /**
     * This test case validates the SSN field with an invalid input.
     */
    @Test
    public void testSSNError() {
        testFieldValidation("ssn", "123456789", "Backspace", "You must enter the Social Security Number associated with your loan to continue.");
    }

    /**
     * This test case validates the mobile field with an invalid input.
     */
    @Test
    public void testInvalidMobileInputAlpha() {
        testFieldValidation("mobile", "abc", null, "Please enter a valid mobile number.");
    }

    /**
     * This test case validates the mobile field with an incomplete input.
     */
    @Test
    public void testInvalidMobileInputIncomplete() {
        testFieldValidation("mobile", "444", null, "Please enter a valid mobile number.");
    }

    /**
     * This test case validates the mobile field with an input of all zeroes.
     */
    @Test
    public void testInvalidMobileInputZeroes() {
        testFieldValidation("mobile", "0000000000", null, "Please enter a valid mobile number.");
    }

    /**
     * This test case validates the email field with an invalid input.
     */
    @Test
    public void testInvalidEmailInput() {
        testInvalidEmailInput("email");
    }

    /**
     * This test case validates the confirm email field with an invalid input.
     */
    @Test
    public void testInvalidConfirmEmailInput() {
        testInvalidEmailInput("confirmEmail");
    }

    /**
     * This test case validates that the email and confirm email fields match.
     */
    @Test
    public void testEmailMismatch() {
        testFieldMismatch("email", "confirmEmail", "abc@def.com", "def@abc.com", "Email Address and Confirm Email Address must match.");
    }

    /**
     * This test case validates that the password and confirm password fields match.
     */
    @Test
    public void testPasswordMismatch() {
        testFieldMismatch("password", "confirmPassword", "abc", "def", "Password and Confirm Password must match.");
    }

    /**
     * This method tests that two fields match.
     */
    private void testFieldMismatch(String field1, String field2, String input1, String input2, String expectedText) {
        page.waitForSelector(fields.get(field1));
        page.waitForSelector(fields.get(field2));
        page.fill(fields.get(field1), input1);
        page.fill(fields.get(field2), input2);
        page.keyboard().press("Tab");
        String actualText = page.textContent("body");
        Assertions.assertTrue(actualText.contains(expectedText), "Text is not displayed as expected.");
    }

    /**
     * This method tests the validation of a field.
     */
    private void testFieldValidation(String field, String input, String key, String expectedText) {
        page.waitForSelector(fields.get(field));
        page.fill(fields.get(field), input);
        if (key != null) {
            page.keyboard().press(key);
            page.keyboard().press("Tab");
        }
        String actualText = page.textContent("body");
        Assertions.assertTrue(actualText.contains(expectedText), "Text is not displayed as expected.");
    }

    /**
     * This method tests the email field with various invalid inputs.
     */
    private void testInvalidEmailInput(String emailField) {
        String[] invalidEmails = {"abc", "abc@", "abc@.com", "@.com", "abc@d.", "@def.com"};
        for (String invalidEmail : invalidEmails) {
            testFieldValidation(emailField, invalidEmail, "Tab", "Please enter valid E-mail Address. Email address must be 40 characters or less.");
        }
    }

    /**
     * This method closes the browser after all tests are run.
     */
    @AfterAll
    public static void tearDown() {
        browser.close();
    }
}