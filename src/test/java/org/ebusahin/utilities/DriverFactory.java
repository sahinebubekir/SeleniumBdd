package org.ebusahin.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(String browserName) {
        try {
            String seleniumUrl = System.getProperty("selenium.url", "http://localhost:4444/wd/hub");
            URL remoteAddress = new URL(seleniumUrl);

            if (browserName.equalsIgnoreCase("chrome")) {
                ChromeOptions options = getChromeOptions();

                driver.set(new RemoteWebDriver(remoteAddress, options));

            } else if (browserName.equalsIgnoreCase("firefox")) {
                FirefoxOptions options = new FirefoxOptions();
                options.addPreference("signon.rememberSignons", false);
                options.addPreference("dom.disable_beforeunload", true);
                options.addPreference("dom.webnotifications.enabled", false);
                options.addPreference("permissions.default.desktop-notification", 2);

                driver.set(new RemoteWebDriver(remoteAddress, options));

            } else {
                throw new RuntimeException("Unsupported browser: " + browserName);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid Selenium Grid URL");
        }
    }

    private static ChromeOptions getChromeOptions() {
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        chromePrefs.put("profile.password_manager_leak_detection", false);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        return options;
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}