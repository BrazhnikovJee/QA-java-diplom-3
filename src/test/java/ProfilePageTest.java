import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageObject.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static api.Constants.BASE_URL;
import static org.hamcrest.CoreMatchers.equalTo;

public class ProfilePageTest {

    WebDriverSelection factory = new WebDriverSelection();
    WebDriver driver;
    Properties properties = new Properties();
    LoginPage loginPage;
    RegistrationPage registrationPage;
    MainPage mainPage;
    UserProfilePage userProfilePage;
    String accessToken;
    ProfilePageTestImproved profilePageTestImproved;
    BasePage basePage;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        try (InputStream input = new FileInputStream("src/main/resources/browser.properties")) {
            properties.load(input);
            String chooseDriver = properties.getProperty("browser");
            driver = factory.chooseYourBrowser(chooseDriver);
        } catch (IOException e) {
            e.printStackTrace();
        }
        basePage = new BasePage(driver); // Инициализация BasePage с переданным драйвером
        loginPage = basePage.getLoginPage();
        mainPage = basePage.getMainPage();
        registrationPage = basePage.getRegistrationPage();
        userProfilePage = basePage.getUserProfilePage();
        profilePageTestImproved = basePage.getProfilePageTestImproved();
        profilePageTestImproved.createUserAndSetCookies(driver);
        mainPage.open(driver);
    }

    @After
    @DisplayName("Удаление пользователя")
    public void tearDown() {
        profilePageTestImproved.cleanUpAndQuitDriver(driver, accessToken);
    }

    @Test
    @DisplayName("Переход в личный кабинет по клику на «Личный кабинет»")
    public void checkLoginAccountButton() {
        mainPage.clickLoginAccountButton();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        MatcherAssert.assertThat("Ожидается надпись «Профиль» на странице личного кабинета", userProfilePage.getProfileText(), equalTo("Профиль"));
    }

    @Test
    @DisplayName("Переход из личного кабинета по клику на «Конструктор»")
    public void checkTapToConstructor() {
        mainPage.clickLoginAccountButton();
        UserProfilePage userProfilePage = new UserProfilePage(driver);
        userProfilePage.clickConstructorButton();
        MatcherAssert.assertThat("Надпись 'Оформить заказ' на кнопке в корзине", mainPage.getBasketButtonText(), equalTo("Оформить заказ"));
    }

    @Test
    @DisplayName("Переход из личного кабинета на логотип Stellar Burgers")
    public void checkTapToLogo() {
        mainPage.clickLoginAccountButton();
        userProfilePage.clicklogoButton();
        MatcherAssert.assertThat("Надпись 'Оформить заказ' на кнопке в корзине", mainPage.getBasketButtonText(), equalTo("Оформить заказ"));
    }

    @Test
    @DisplayName("Выход из аккаунта по кнопке «Выйти» в личном кабинете")
    public void checkTapToExit() {
        mainPage.clickLoginAccountButton();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        userProfilePage.clickExitButton();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.waitLoginPage();
        Assert.assertTrue(loginPage.getEnterButton());
    }
}