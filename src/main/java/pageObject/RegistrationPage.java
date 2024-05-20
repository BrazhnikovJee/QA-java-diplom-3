package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static api.Constants.REGISTER_URL;

public class RegistrationPage extends BasePage{
    private final By getNameField = By.xpath(".//label[text() = 'Имя']/../input[contains(@name, 'name')]");
    private final By getEmailField = By.xpath(".//label[text() = 'Email']/../input[contains(@name, 'name')]");
    private final By getPasswordField = By.xpath(".//label[text() = 'Пароль']/../input[contains(@type, 'password')]");
    public final By getIncorrectPasswordMessage = By.xpath(".//p[text()='Некорректный пароль']");
    private static final By getRegistrationButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By enterButton = By.xpath(".//a[text()='Войти']");

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открытие страницы Регистрации")
    public void open(WebDriver driver) {
        driver.get(REGISTER_URL);
    }

    @Step("Тап по кнопке «Войти»")
    public void clickEnter() {
        driver.findElement(enterButton).click();
    }

    @Step("Ввести имя")
    public void enterName(String name) {
        driver.findElement(getNameField).sendKeys(name);
    }

    @Step("Ввести почту")
    public void enterEmail(String email) {
        driver.findElement(getEmailField).sendKeys(email);
    }

    @Step("Ввести пароль")
    public void enterPassword(String password) {
        driver.findElement(getPasswordField).sendKeys(password);
    }

    @Step("Нажать на кнопку зарегистрироваться")
    public void clickRegistrationButton(){
        driver.findElement(getRegistrationButton).click();
    }
    @Step("Получить ошибку пароля")
    public String passwordMistakeGetText() {
        return driver.findElement(getIncorrectPasswordMessage).getText();
    }
}
