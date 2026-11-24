package ar.org.proyectoFinal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PIMPage extends BasePage {


    private By employeeNameInput = By.xpath("//input[@placeholder='Type for hints...']");
    private By searchButton = By.cssSelector("button[type='submit']");
    private By resultRow = By.cssSelector(".oxd-table-body .oxd-table-card");
    private By noRecordsMessage = By.xpath("//span[text()='No Records Found']");

    public PIMPage(WebDriver driver) {
        super(driver);
    }

    public PIMPage searchEmployeeByName(String name) {
        type(employeeNameInput, name);
        click(searchButton);
        return this;
    }

    /**
     * Espera a que la búsqueda termine: aparece al menos un resultado o el mensaje "No Records Found".
     */
    public PIMPage waitForSearchToFinish() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(resultRow),
                ExpectedConditions.visibilityOfElementLocated(noRecordsMessage)
        ));
        return this;
    }

    public boolean hasResults() {
        try {
            return isElementVisible(resultRow);
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isNoRecordsDisplayed() {
        return isElementVisible(noRecordsMessage);
    }

    public boolean isOnPIMPage() {
        return waitForUrlContains("pim");
    }
}
