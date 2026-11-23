package ar.org.proyectoFinal.utils;

import ar.org.proyectoFinal.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Crea y guarda el WebDriver por hilo (ThreadLocal), lo que deja el framework
 * listo para ejecución en paralelo.
 *
 * Parámetros: -Dbrowser=chrome|firefox (default chrome) y -Dheadless=true|false (default false).
 */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("El driver no fue inicializado. ¿Corrió el hook @Before?");
        }
        return driver;
    }

    public static void initDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = ConfigReader.getBoolean("headless", false);

        WebDriver driver;
        switch (browser) {
            case "chrome":
                driver = createChrome(headless);
                break;
            case "firefox":
                driver = createFirefox(headless);
                break;
            default:
                throw new IllegalArgumentException("Browser no soportado: " + browser + " (usar chrome o firefox)");
        }

        if (!headless) {
            driver.manage().window().maximize();
        }
        DRIVER.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }

    private static WebDriver createChrome(boolean headless) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--remote-allow-origins=*");

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            // Necesarios para correr dentro de contenedores / runners de CI
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
        }

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefox(boolean headless) {

        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        }
        return new FirefoxDriver(options);
    }
}
