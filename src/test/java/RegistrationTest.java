import api.User;
import api.UserApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.BasePage;
import pageObject.ProfilePageTestImproved;
import pageObject.RegistrationPage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

import api.CredentialsRandomizer;

import static api.Constants.*;

public class RegistrationTest {
    WebDriverSelection factory = new WebDriverSelection();
    WebDriver driver;
    Properties properties = new Properties();
    RegistrationPage registrationPage;
    User user;
    private String accessToken;
    ProfilePageTestImproved profilePageTestImproved;
    BasePage basePage;

    @Before
    public void setUp() {
        try (InputStream input = new FileInputStream("src/main/resources/browser.properties")) {
            properties.load(input);
            String chooseDriver = properties.getProperty("browser");
            driver = factory.chooseYourBrowser(chooseDriver);
        } catch (IOException e) {
            e.printStackTrace();
        }
        basePage = new BasePage(driver);
        profilePageTestImproved = basePage.getProfilePageTestImproved();
        driver.get(REGISTER_URL);
        registrationPage = basePage.getRegistrationPage();
        user = CredentialsRandomizer.getNewRandomUser();

    }

    @After
    public void teatDown() {

        profilePageTestImproved.cleanUpAndQuitDriver(driver, accessToken);
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void registrationSuccessUser() {
        registrationPage.enterName(user.getName());
        registrationPage.enterEmail(user.getEmail());
        registrationPage.enterPassword(user.getPassword());
        registrationPage.clickRegistrationButton();
        (new WebDriverWait(driver, Duration.ofSeconds(3))).until(ExpectedConditions.urlToBe(LOGIN_URL));
        Assert.assertEquals("Пользователь не зарегистрирован", LOGIN_URL, driver.getCurrentUrl());
        Response response = UserApi.loginUser(user);
        accessToken = response.then().log().all().extract().path("accessToken").toString();
    }

    @Test
    @DisplayName("Ошибка для некорректного пароля. Минимальный пароль — шесть символов.")
    public void registrationIncorrectPassword() {
        registrationPage.enterName(user.getName());
        registrationPage.enterEmail(user.getEmail());
        registrationPage.enterPassword("123");
        registrationPage.clickRegistrationButton();
        Assert.assertNotEquals(LOGIN_URL, driver.getCurrentUrl());
        Assert.assertEquals("Некорректный пароль", registrationPage.passwordMistakeGetText());
    }
}
