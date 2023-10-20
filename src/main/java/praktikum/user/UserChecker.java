package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.model.User;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static praktikum.Constants.*;

public class UserChecker {

    @Step("Проверка ответа на запрос создания/логина пользователя")
    public void createOrLoginSuccess(ValidatableResponse response, User user) {
        response.assertThat()
                .statusCode(STATUS_CODE_200)
                .body("success", is(true))
                .and()
                .body("user.email", equalTo( user.getEmail()))
                .and()
                .body("user.name", equalTo(user.getName()));
    }

    @Step("Проверка ответа на запрос удаления пользователя")
    public void deleteSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_202)
                .body("success", is(true))
                .and()
                .body("message", is( MSG_USER_REMOVE_SUCCESS));
    }

    @Step("Проверка ответа на запрос повторного создания пользователя")
    public void createExistsUserFail(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_403)
                .body("success", is(false))
                .and()
                .body("message", is( MSG_USER_ALREADY_EXISTS));
    }


    @Step("Проверка ответа на запрос создания пользователя без обязательных полей")
    public void createUserWithoutRequiredFieldsFail(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_403)
                .body("success", is(false))
                .and()
                .body("message", is( MSG_USER_DATA_REQUIRED));
    }

    @Step("Проверка ответа на запрос авторизации с невалидными данными")
    public void loginUserFail(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_401)
                .body("success", is(false))
                .and()
                .body("message", is(MSG_USER_AUTH_DATA_INCORRECT));
    }

}
