import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageObject.BasePage;
import pageObject.MainPage;
import pageObject.ProfilePageTestImproved;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static api.Constants.BASE_URL;

public class MainPageTest {
    WebDriverSelection factory = new WebDriverSelection();
    WebDriver driver;
    Properties properties = new Properties();
    MainPage mainPage;
    String accessToken;
    BasePage basePage;
    ProfilePageTestImproved profilePageTestImproved;

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

        basePage = new BasePage(driver);
        profilePageTestImproved = basePage.getProfilePageTestImproved();
        profilePageTestImproved.createUserAndSetCookies(driver);
        mainPage = basePage.getMainPage();

    }

    @After
    @DisplayName("Удаление пользователя")
    public void tearDown() {
        profilePageTestImproved.cleanUpAndQuitDriver(driver, accessToken);
    }

    @Test
    @DisplayName("Переход к разделу «Булки»")
    public void checkLinkToBuns() {
        mainPage.clickSauceButton();
        mainPage.clickBunsButton();
        Assert.assertEquals("Выбран некорректный раздел", "Булки", mainPage.getTextFromSelectedMenu());
    }

    @Test
    @DisplayName("Переход к разделу «Соусы»")
    public void checkLinkToSauce() {
        mainPage.clickSauceButton();
        Assert.assertEquals("Выбран некорректный раздел", "Соусы", mainPage.getTextFromSelectedMenu());
    }

    @Test
    @DisplayName("Переход к разделу «Начинки»")
    public void checkLinkToFilling() {
        mainPage.clickFillingButton();
        Assert.assertEquals("Выбран некорректный раздел", "Начинки", mainPage.getTextFromSelectedMenu());
    }
}