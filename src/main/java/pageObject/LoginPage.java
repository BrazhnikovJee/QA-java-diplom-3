package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage{
    private final By getEmailField = By.xpath(".//input[@type='text']");
    private final By getPasswordField = By.xpath(".//input[@type='password']");
    private final By getEnterButton = By.xpath(".//button[text()='Войти']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ввести почту")
    public void getEnterEmail(String email){
        driver.findElement(getEmailField).sendKeys(email);
    }

    @Step("Ожидание загрузки на странице 'login'")
    public void waitLoginPage() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(getEmailField));
    }

    @Step("Ввести пароль")
    public void getEnterPassword(String password){
        driver.findElement(getPasswordField).sendKeys(password);
    }

    @Step("Тап на кнопке «Войти» на странице 'login'")
    public void clickEnterButton() {
        driver.findElement(getEnterButton).click();
    }

    @Step("Отображение кнопки «Войти» на странице 'login'")
    public  boolean getEnterButton() {
        return driver.findElement(getEnterButton).isDisplayed();
    }

}
