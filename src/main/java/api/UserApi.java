package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;


import static api.Constants.*;
import static io.restassured.RestAssured.given;

public class UserApi {

    @Step("Создание пользователя")
    public static Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(REGISTER_ENDPOINT);
    }

    @Step("Авторизация пользователя")
    public static Response loginUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(LOGIN_ENDPOINT);
    }

    @Step("Удаление пользователя")
    public static Response deleteUser(String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .header("authorization", accessToken)
                .when()
                .delete(USER_ENDPOINT);
    }
}