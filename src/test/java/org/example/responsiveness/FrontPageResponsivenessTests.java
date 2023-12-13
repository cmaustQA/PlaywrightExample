package org.example.responsiveness;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains tests for checking the responsiveness of a web page on different browsers.
 */
public class FrontPageResponsivenessTests {
    private Page page;
    private Browser browser;

    /**
     * This method is executed after each test. It closes the browser if it's not null.
     */
    @AfterEach
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
    }

    /**
     * This test checks the responsiveness of the web page on Firefox.
     * @throws InterruptedException if the thread is interrupted
     */
    @Test
    @Tag("responsiveness")
    @Tag("firefox")
    public void testFirefox() throws InterruptedException {
        testResponsiveDesign("Firefox");
    }

    /**
     * This test checks the responsiveness of the web page on Chromium.
     * @throws InterruptedException if the thread is interrupted
     */
    @Test
    @Tag("responsiveness")
    @Tag("chromium")
    public void testChromium() throws InterruptedException {
        testResponsiveDesign("Chromium");
    }

    /**
     * This test checks the responsiveness of the web page on Webkit.
     * @throws InterruptedException if the thread is interrupted
     */
    @Test
    @Tag("responsiveness")
    @Tag("webkit")
    public void testWebkit() throws InterruptedException {
        testResponsiveDesign("Webkit");
    }

    /**
     * This method tests the responsiveness of the web page on a specific browser.
     * @param browserType the type of the browser to test on
     * @throws InterruptedException if the thread is interrupted
     */
    private void testResponsiveDesign(String browserType) throws InterruptedException{
        if ("Firefox".equalsIgnoreCase(browserType)) {
            browser = Playwright.create().firefox().launch(new BrowserType.LaunchOptions().setHeadless(true));
        } else if ("Chromium".equalsIgnoreCase(browserType)) {
            browser = Playwright.create().chromium().launch();
        } else if ("Webkit".equalsIgnoreCase(browserType)) {
            browser = Playwright.create().webkit().launch();
        }
        page = browser.newPage();

        // Navigate to the home page
        page.navigate("https://www.freedommortgage.com/");

        // Test desktop view
        testViewportSize("Desktop", 1200, 800, browserType);

        // Test tablet view
        testViewportSize("Tablet", 768, 1024, browserType);

        // Test mobile view
        testViewportSize("Mobile", 375, 667, browserType);
    }

    /**
     * This method tests the visibility of certain elements on the web page at a specific viewport size.
     * @param viewName the name of the view (e.g., "Desktop", "Tablet", "Mobile")
     * @param width the width of the viewport
     * @param height the height of the viewport
     * @param browserType the type of the browser to test on
     */
    private void testViewportSize(String viewName, int width, int height, String browserType) {
        // Set viewport size
        page.setViewportSize(width, height);

        assertTrue(page.isVisible("header")); // Check if header is visible
        assertTrue(page.isVisible("nav")); // Check if navigation menu is visible
        assertTrue(page.isVisible("footer")); // Check if footer is visible

    }
}