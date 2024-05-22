package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static api.Constants.BASE_URL;

public class MainPage extends BasePage{
    private final By accountButton = By.xpath("//a[contains(@href, '/account')]");
    private final By basketButton = By.xpath("//button[text()= 'Оформить заказ']");
    private final By bunsButton = By.xpath("//*[text()= 'Булки']");
    private final By sauceButton = By.xpath("//*[text()= 'Соусы']");
    private final By fillingButton = By.xpath("//*[text()= 'Начинки']");
    private static final By currentMenu = By.xpath("//div[contains(@class,'tab_tab__1SPyG tab_tab_type_current__2BEPc')]");


    public MainPage(WebDriver driver) {
        super(driver);
    }
    private void waitUntilClickableAndClick(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    @Step("Открытие главной страницы")
    public void open(WebDriver driver) {
        driver.get(BASE_URL);
    }

    @Step("Получение текста на кнопке в Корзине")
    public String getBasketButtonText() {
        return driver.findElement(basketButton).getText();
    }

    @Step("Тап по кнопке «Личный кабинет»")
    public void clickLoginAccountButton() {
        waitUntilClickableAndClick(accountButton);
    }

    @Step("Тап по вкладке «Булки»")
    public void clickBunsButton() {
        waitUntilClickableAndClick(bunsButton);
    }

    @Step("Тап по вкладке «Соусы»")
    public void clickSauceButton() {
        waitUntilClickableAndClick(sauceButton);
    }

    @Step("Тап по вкладке «Начинки»")
    public void clickFillingButton() {
        waitUntilClickableAndClick(fillingButton);
    }

    @Step("Получить выбранный раздел")
    public String getTextFromSelectedMenu() {
        return driver.findElement(currentMenu).getText();
    }
}