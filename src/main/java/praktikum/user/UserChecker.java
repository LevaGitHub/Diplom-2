package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.is;
import static praktikum.Constants.*;

public class UserChecker {

    @Step("Проверка ответа на запрос создания пользователя")
    public void creationSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_200)
                .body("success", is(true));
    }

    @Step("Проверка ответа на запрос удаления пользователя")
    public void deleteSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_202)
                .body("success", is(true))
                .and()
                .body("message", is( "User successfully removed"));
    }

    @Step("Проверка ответа на запрос повторного создания пользователя")
    public void createExistsUserFail(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_403)
                .body("success", is(false))
                .and()
                .body("message", is( "User already exists"));
    }


    @Step("Проверка ответа на запрос создания пользователя без обязательных полей")
    public void createUserWithoutRequiredFieldsFail(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_403)
                .body("success", is(false))
                .and()
                .body("message", is( "Email, password and name are required fields"));
    }
//    {
//        "success": false,
//            "message": "Email, password and name are required fields"
//    }
}
