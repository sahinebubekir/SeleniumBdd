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

    // Thread-local WebDriver (her thread kendi driver'ını alır)
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    //Singleton
    private DriverFactory() {
    }

    public static void initDriver(String driverName) {

        if (driverThreadLocal.get() == null) {
            switch (driverName) {
                case "chrome":
                    final Map<String, Object> chromePrefs = new HashMap<>();
                    chromePrefs.put("credentials_enable_service", false);
                    chromePrefs.put("profile.password_manager_enabled", false);
                    chromePrefs.put("profile.password_manager_leak_detection", false); // <======== This is the important one

                    final ChromeOptions options = new ChromeOptions();
                    options.setExperimentalOption("prefs", chromePrefs);

                    validateDriver("chromedriver.exe");
                    if(System.getProperty("user.dir").contains("/Users")){

                        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
                }else if (System.getProperty("user.dir").contains("C:\\Users")){
                    System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
                }else{
                        options.addArguments("--headless=new"); // veya "--headless"
                        options.addArguments("--no-sandbox");
                        options.addArguments("--disable-dev-shm-usage");
                        options.addArguments("--disable-gpu"); // opsiyonel
                    }
                    driverThreadLocal.set(new ChromeDriver(options));
                    break;
                case "firefox":
                    validateDriver("geckodriver.exe");
                    System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
                    driverThreadLocal.set(new FirefoxDriver());
                    break;
                case "safari":
                    // Safari için genelde system property gerekmez
                    driverThreadLocal.set(new SafariDriver());
                    break;
                default:
                    throw new RuntimeException("Invalid browser name: " + driverName);
            }
        }
        WebDriver driver = driverThreadLocal.get();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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