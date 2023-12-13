package org.example.accessibility;

import com.deque.html.axecore.playwright.*;
import com.deque.html.axecore.results.AxeResults;
import org.junit.jupiter.api.*;
import com.microsoft.playwright.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for checking the accessibility of the front page of a website.
 * It uses the Playwright library to launch different browsers and the Axe library to perform accessibility scans.
 * All of these test WILL FAIL until the accessibility fixes are implemented.
 */
public class FrontPageAccessibilityTests {

    /**
     * Test to check for accessibility issues in Chromium.
     */
    @Test
    @Tag("accessibility")
    @Tag("chromium")
    void shouldNotHaveAutomaticallyDetectableAccessibilityIssuesChromium() {
        shouldNotHaveAutomaticallyDetectableAccessibilityIssues("chromium");
    }

    /**
     * Test to check for accessibility issues in Firefox.
     */
    @Test
    @Tag("accessibility")
    @Tag("firefox")
    void shouldNotHaveAutomaticallyDetectableAccessibilityIssuesFirefox() {
        shouldNotHaveAutomaticallyDetectableAccessibilityIssues("firefox");
    }

    /**
     * Test to check for accessibility issues in Webkit.
     */
    @Test
    @Tag("accessibility")
    @Tag("webkit")
    void shouldNotHaveAutomaticallyDetectableAccessibilityIssuesWebkit() {
        shouldNotHaveAutomaticallyDetectableAccessibilityIssues("webkit");
    }

    /**
     * This method launches the specified browser, navigates to the website, performs an accessibility scan,
     * and asserts that there are no accessibility violations.
     *
     * @param browserType The type of browser to launch. Can be "chromium", "firefox", or "webkit".
     */
    void shouldNotHaveAutomaticallyDetectableAccessibilityIssues(String browserType) {
        Playwright playwright = Playwright.create();
        Browser browser;
        if ("chromium".equalsIgnoreCase(browserType)) {
            browser = playwright.chromium().launch();
        } else if ("firefox".equalsIgnoreCase(browserType)) {
            browser = playwright.firefox().launch();
        } else {
            browser = playwright.webkit().launch();
        }
        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        page.navigate("https://www.freedommortgage.com/");

        AxeResults accessibilityScanResults = new AxeBuilder(page).analyze();

        assertEquals(Collections.emptyList(), accessibilityScanResults.getViolations(), "Accessibility violations: " + accessibilityScanResults.getViolations());
    }
}