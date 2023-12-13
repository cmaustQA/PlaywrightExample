package org.example.end2end;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import org.junit.jupiter.api.*;

/**
 * This class contains end-to-end tests for the Home Affordability Calculator feature on a website.
 * It uses the Playwright library to launch different browsers and perform actions on the website.
 */
public class HomeAffordabilityCalculatorTests {
    private Playwright playwright;
    private Browser browser;

    /**
     * This method sets up the Playwright instance before each test.
     */
    @BeforeEach
    public void setUp() {
        playwright = Playwright.create();
    }

    /**
     * This method closes the browser after each test.
     */
    @AfterEach
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
    }

    /**
     * This test checks the Home Affordability Calculator feature on Chromium.
     */
    @Test
    @Tag("HACend2end")
    @Tag("chromium")
    public void chromiumTest() {
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        runTest();
    }

    /**
     * This test checks the Home Affordability Calculator feature on Firefox.
     */
    @Test
    @Tag("HACend2end")
    @Tag("firefox")
    public void firefoxTest() {
        browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true));
        runTest();
    }

    /**
     * This test checks the Home Affordability Calculator feature on Webkit.
     */
    @Test
    @Tag("HACend2end")
    @Tag("webkit")
    public void webkitTest() {
        browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(true));
        runTest();
    }

    /**
     * This method contains the steps for the Home Affordability Calculator test.
     * It navigates to the website, interacts with the Home Affordability Calculator, and checks the results.
     */
    private void runTest() {
        BrowserContext context = browser.newContext();

            Page page = context.newPage();
            page.navigate("https://www.freedommortgage.com/");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home affordability calculator").setExact(true)).click();
            assertThat(page.locator("h1")).containsText("Home affordability calculator");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Annual Gross Income")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Annual Gross Income")).fill("10,9000");
            page.getByLabel("Monthly Debt Payments").click();
            page.getByLabel("Monthly Debt Payments").fill("900");
            page.getByLabel("Down Payment").click();
            page.getByLabel("Down Payment").fill("3,0000");
            page.getByLabel("Loan Term (Years)").click();
            page.getByLabel("Loan Term (Years)").fill("15");
            page.getByLabel("Interest rate").click();
            page.getByLabel("Interest rate").fill("6.00");
            page.getByLabel("Taxes & Insurance").click();
            page.getByLabel("Taxes & Insurance").fill("5000");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Get your Estimate")).click();
            assertThat(page.locator("#result-text-desktop")).containsText("Based on the values you provided, you may be able to afford a home worth up to $239,473");
            page.getByPlaceholder("First Name").click();
            page.getByPlaceholder("First Name").fill("Test");
            page.getByPlaceholder("First Name").press("Tab");
            page.getByPlaceholder("Last Name").click();
            page.getByPlaceholder("Last Name").fill("Name");
            page.getByPlaceholder("Email").click();
            page.getByPlaceholder("Email").fill("test@name.com");
            page.getByPlaceholder("(###) ###-####").click();
            page.getByPlaceholder("(###) ###-####").fill("9805092091");
            page.getByLabel("Property State").selectOption("IL");
            page.getByLabel("Loan Type").selectOption("FHA");
            page.getByLabel("Current Customer").selectOption("Yes");
            page.locator("label").filter(new Locator.FilterOptions().setHasText("No")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("GET YOUR RESULTS")).click();
            assertThat(page.locator("#calc-results-section")).containsText("Your results have been emailed to you. A loan advisor will reach out to you soon.");

    }
}
