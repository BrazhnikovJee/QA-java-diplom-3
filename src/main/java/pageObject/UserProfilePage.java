package pageObject;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserProfilePage extends BasePage{

    private final By profile = By.xpath(".//a[text()='Профиль']");
    private final By constructorButton = By.xpath(".//p[text()='Конструктор']");
    private final By logoButton = By.xpath("//*[contains(@class, 'AppHeader_header__logo__2D0X2')]");
    private final By exitButton = By.xpath(".//button[text()='Выход']");

    public UserProfilePage(WebDriver driver) {
        super(driver);
    }

    @Step("Получение текста «Профиль» на странице личного кабинета")
    public String getProfileText() {
        return driver.findElement(profile).getText();
    }

    @Step("Тап по кнопке «Конструктор»")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Тап по кнопке «Лого»")
    public void clicklogoButton() {
        driver.findElement(logoButton).click();
    }

    @Step("Тап по кнопке «Выход» на странице личного кабинета")
    public void clickExitButton() {
        driver.findElement(exitButton).click();
    }
}