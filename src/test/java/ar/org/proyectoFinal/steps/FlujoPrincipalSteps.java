package ar.org.proyectoFinal.steps;

import ar.org.proyectoFinal.config.ConfigReader;
import ar.org.proyectoFinal.pages.DashboardPage;
import ar.org.proyectoFinal.pages.LoginPage;
import ar.org.proyectoFinal.pages.PIMPage;
import ar.org.proyectoFinal.utils.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class FlujoPrincipalSteps {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private PIMPage pimPage;

    // -------------------------
    // GIVEN
    // -------------------------

    @Given("el usuario accede a OrangeHRM")
    public void elUsuarioAccedeAOrangeHRM() {
        loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.goTo();
        Assert.assertTrue(loginPage.isOnLoginPage(), "La página de login no está visible");
    }

    @Given("el usuario esta logueado con usuario {string} y password {string}")
    public void elUsuarioEstaLogueadoConUsuarioYPassword(String usuario, String password) {
        loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.goTo();
        dashboardPage = loginPage.loginAs(usuario, password);
        Assert.assertTrue(dashboardPage.isOnDashboard(),
                "No se visualiza el dashboard después del login");
    }

    // -------------------------
    // WHEN
    // -------------------------

    @When("realiza login")
    public void realizaLogin() {
        dashboardPage = loginPage.loginAs(ConfigReader.get("username"), ConfigReader.get("password"));
    }

    @When("realiza login con usuario {string} y password {string}")
    public void realizaLoginConUsuarioYPassword(String usuario, String password) {
        dashboardPage = loginPage.loginAs(usuario, password);
    }

    @When("navega al modulo PIM")
    public void navegaAlModuloPIM() {
        pimPage = dashboardPage.goToPIM();
        Assert.assertTrue(pimPage.isOnPIMPage(), "No se encuentra en la página PIM");
    }

    @When("busca el empleado {string}")
    public void buscaElEmpleado(String nombreEmpleado) {
        pimPage.searchEmployeeByName(nombreEmpleado);
    }

    @When("cierra sesion")
    public void cierraSesion() {
        loginPage = dashboardPage.logout();
    }

    // -------------------------
    // THEN
    // -------------------------

    @Then("visualiza el dashboard")
    public void visualizaElDashboard() {
        Assert.assertTrue(dashboardPage.isOnDashboard(), "El dashboard no es visible");
    }

    @Then("se muestran resultados")
    public void seMuestranResultados() {
        pimPage.waitForSearchToFinish();
        Assert.assertTrue(pimPage.hasResults() || pimPage.isNoRecordsDisplayed(),
                "La búsqueda no mostró resultados ni el mensaje de 'No Records Found'");
    }

    @Then("vuelve a la pagina de login")
    public void vuelveALaPaginaDeLogin() {
        Assert.assertTrue(loginPage.isOnLoginPage(), "No vuelve a la página de login después del logout");
    }

    @Then("se muestra el mensaje de error {string}")
    public void seMuestraElMensajeDeError(String mensajeEsperado) {
        Assert.assertTrue(loginPage.isErrorDisplayed(), "No se muestra el mensaje de error de login");
        Assert.assertEquals(loginPage.getErrorMessage(), mensajeEsperado);
    }
}
