package pageObject;

import api.CredentialsRandomizer;
import api.User;
import api.UserApi;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static api.Constants.BASE_URL;
import static org.hamcrest.Matchers.equalTo;

public class ProfilePageTestImproved extends BasePage{

    public ProfilePageTestImproved(WebDriver driver) {
        super(driver);
    }

    @Step("Создание аккаунт и добавление куков")
    public void createUserAndSetCookies(WebDriver driver) {
        User user = CredentialsRandomizer.getNewRandomUser();
        Response response = UserApi.createUser(user);
        String accessToken = response.then().log().all().extract().path("accessToken").toString();
        String refreshToken = response.then().log().all().extract().path("refreshToken").toString();
        Cookie cookie = new Cookie("accessToken", accessToken);
        Cookie cookie1 = new Cookie("refreshToken", refreshToken);
        driver.get(BASE_URL);
        driver.manage().addCookie(cookie);
        driver.manage().addCookie(cookie1);
        driver.navigate().refresh();
        String script = "localStorage.setItem('accessToken', document.cookie.split(';')[0].split('=')[1]);";
        ((JavascriptExecutor) driver).executeScript(script);
        String script1 = "localStorage.setItem('refreshToken', document.cookie.split(';')[1].split('=')[1]);";
        ((JavascriptExecutor) driver).executeScript(script1);
    }

    @Step("Закрытие браузера и удаление аккаунта")
    public void cleanUpAndQuitDriver(WebDriver driver, String accessToken) {
        driver.quit();
        if (accessToken != null) {
            UserApi.deleteUser(accessToken).then().assertThat().body("success", equalTo(true))
                    .and()
                    .body("message", equalTo("User successfully removed"))
                    .and()
                    .statusCode(SC_ACCEPTED);
        }
    }
}

