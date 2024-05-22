package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static api.Constants.RECOVERY_URL;

public class PasswordRecoveryPage extends BasePage{
    private final By enterButton = By.xpath(".//a[text()='Войти']");

    public PasswordRecoveryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открытие страницы восстановления пароля")
    public void open() {
        driver.get(RECOVERY_URL);
    }

    @Step("Тап по кнопке «Войти» на странице восстановления пароля")
    public void clickEnter() {
        driver.findElement(enterButton).click();
    }
}