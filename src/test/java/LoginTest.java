import api.UserApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageObject.*;
import api.CredentialsRandomizer;
import api.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static api.Constants.BASE_URL;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {
    WebDriverSelection factory = new WebDriverSelection();
    WebDriver driver;
    Properties properties = new Properties();
    LoginPage loginPage;
    User user;
    MainPage mainPage;
    RegistrationPage registrationPage;
    private String accessToken;
    PasswordRecoveryPage passwordRecoveryPage;
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
        passwordRecoveryPage = basePage.getPasswordRecoveryPage();
        profilePageTestImproved = basePage.getProfilePageTestImproved();
        user = CredentialsRandomizer.getNewRandomUser();
        Response response = UserApi.createUser(user);
        accessToken = response.then().log().all().extract().path("accessToken").toString();
    }

    @After
    public void teatDown() {
        profilePageTestImproved.cleanUpAndQuitDriver(driver, accessToken);
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void loginUserOnTheMainPage() {
        mainPage.open(driver);
        mainPage.clickLoginAccountButton();
        loginPage.getEnterEmail(user.getEmail());
        loginPage.getEnterPassword(user.getPassword());
        loginPage.clickEnterButton();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        MatcherAssert.assertThat("Надпись 'Оформить заказ' на кнопке", mainPage.getBasketButtonText(), equalTo("Оформить заказ"));
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void loginUserOnTheAccountPage() {
        mainPage.open(driver);
        mainPage.clickLoginAccountButton();
        loginPage.getEnterEmail(user.getEmail());
        loginPage.getEnterPassword(user.getPassword());
        loginPage.clickEnterButton();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        MatcherAssert.assertThat("Надпись 'Оформить заказ' на кнопке", mainPage.getBasketButtonText(), equalTo("Оформить заказ"));
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void checkLoginButtonRegistrationPage() {
        registrationPage.open(driver);
        registrationPage.clickEnter();
        loginPage.getEnterEmail(user.getEmail());
        loginPage.getEnterPassword(user.getPassword());
        loginPage.clickEnterButton();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        MatcherAssert.assertThat("Надпись 'Оформить заказ' на кнопке", mainPage.getBasketButtonText(), equalTo("Оформить заказ"));
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void checkLoginButtonRecoveryPasswordPage() {
        passwordRecoveryPage.open();
        passwordRecoveryPage.clickEnter();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.getEnterEmail(user.getEmail());
        loginPage.getEnterPassword(user.getPassword());
        loginPage.clickEnterButton();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        MatcherAssert.assertThat("Надпись 'Оформить заказ' на кнопке", mainPage.getBasketButtonText(), equalTo("Оформить заказ"));
    }
}
