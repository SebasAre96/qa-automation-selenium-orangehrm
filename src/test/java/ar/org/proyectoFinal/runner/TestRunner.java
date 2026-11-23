package ar.org.proyectoFinal.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Para filtrar por tags desde consola:
 *   mvn clean test -Dcucumber.filter.tags="@smoke"
 */
@CucumberOptions(
        features = "classpath:features",
        glue = {
                "ar.org.proyectoFinal.steps",
                "ar.org.proyectoFinal.runner"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    // El driver es ThreadLocal, así que es seguro paralelizar.
    // La cantidad de hilos se controla con data-provider-thread-count en testng.xml.
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
