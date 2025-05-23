package org.ebusahin.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void initDriver(String driverName) {
        if (driverThreadLocal.get() == null) {
            String env = System.getProperty("env", "local").toLowerCase(); // default "local"
            boolean isDocker = env.equals("docker");

            switch (driverName.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = getChromeOptions();

                    if (isDocker) {
                        chromeOptions.addArguments("--headless=new");
                        chromeOptions.addArguments("--no-sandbox");
                        chromeOptions.addArguments("--disable-dev-shm-usage");
                        chromeOptions.addArguments("--disable-gpu");
                    } else {
                        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
                        validateDriver("chromedriver.exe");
                    }

                    driverThreadLocal.set(new ChromeDriver(chromeOptions));
                    break;

                case "firefox":
                    if (isDocker) {
                        throw new UnsupportedOperationException("Firefox is not supported in Docker");
                    } else {
                        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
                        validateDriver("geckodriver.exe");
                        driverThreadLocal.set(new FirefoxDriver());
                    }
                    break;

                case "safari":
                    if (isDocker) {
                        throw new UnsupportedOperationException("Safari is not supported in Docker");
                    }
                    driverThreadLocal.set(new SafariDriver());
                    break;

                default:
                    throw new RuntimeException("Invalid browser name: " + driverName);
            }

            WebDriver driver = driverThreadLocal.get();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();

        // Popup ve parola yöneticisi gibi şeyleri devre dışı bırak
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        chromePrefs.put("profile.password_manager_leak_detection", false);
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        return chromeOptions;
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }

    private static void validateDriver(String driverFileName) {
        File driverFile = new File("drivers/" + driverFileName);
        if (!driverFile.exists()) {
            throw new RuntimeException("Driver not found: " + driverFile.getAbsolutePath());
        }
        if (!driverFile.canExecute()) {
            driverFile.setExecutable(true);
        }
    }
}