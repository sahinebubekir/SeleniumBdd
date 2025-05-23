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
            switch (driverName.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = getChromeOptions();
                    setChromeDriverPathIfLocal();
                    driverThreadLocal.set(new ChromeDriver(chromeOptions));
                    break;

                case "firefox":
                    setGeckoDriverPathIfLocal();
                    driverThreadLocal.set(new FirefoxDriver());
                    break;

                case "safari":
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

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        // Popup engelleme ve şifre hatırlatma kapatma ayarları (local için de geçerli)
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        if (isCiEnvironment()) {
            // CI / Docker ortamı için headless ve Linux uyumlu ayarlar
            options.addArguments("--headless=new"); // veya "--headless"
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
        } else {
            // Localde headless değil, GUI ile açılıyor (istersen buraya ekstra local opsiyon ekleyebilirsin)
        }
        return options;
    }

    private static void setChromeDriverPathIfLocal() {
        if (!isCiEnvironment()) {
            String userDir = System.getProperty("user.dir");
            if (userDir.contains("/Users") || userDir.contains("/home")) {
                validateDriver("chromedriver");
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
            } else if (userDir.contains("C:\\Users")) {
                validateDriver("chromedriver.exe");
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
            }
        }
    }

    private static void setGeckoDriverPathIfLocal() {
        if (!isCiEnvironment()) {
            String userDir = System.getProperty("user.dir");
            if (userDir.contains("C:\\Users")) {
                validateDriver("geckodriver.exe");
                System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
            } else {
                validateDriver("geckodriver");
                System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
            }
        }
    }

    private static boolean isCiEnvironment() {
        // Jenkins veya Docker ortamını buradan kontrol et (env var, system prop vs)
        // Örneğin, "CI" ortam değişkeni varsa true döndürebilirsin
        String ci = System.getenv("CI");
        return ci != null && ci.equalsIgnoreCase("true");
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