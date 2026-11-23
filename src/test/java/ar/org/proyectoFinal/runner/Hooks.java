package ar.org.proyectoFinal.runner;

import ar.org.proyectoFinal.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("INICIANDO SCENARIO: " + scenario.getName());
        DriverManager.initDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                System.out.println("SCENARIO FALLIDO: " + scenario.getName());
                attachScreenshot(scenario);
            } else {
                System.out.println("SCENARIO EXITOSO: " + scenario.getName());
            }
        } finally {
            DriverManager.quitDriver();
        }
    }

    /** Adjunta una captura al reporte (Cucumber HTML y Allure) cuando el escenario falla. */
    private void attachScreenshot(Scenario scenario) {
        try {
            WebDriver driver = DriverManager.getDriver();
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Captura del fallo");
        } catch (Exception e) {
            System.out.println("No se pudo tomar la captura: " + e.getMessage());
        }
    }
}
