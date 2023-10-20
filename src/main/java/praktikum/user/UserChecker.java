package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.is;
import static praktikum.Constants.STATUS_CODE_200;
import static praktikum.Constants.STATUS_CODE_202;

public class UserChecker {

    @Step("Проверка ответа на запрос создания пользователя")
    public void creationSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_200)
                .body("success", is(true));
    }

//    {
//        "success": true,
//        "user": {
//        "email": "test-data3205@yandex.ru",
//                "name": "Username2"
//    },
//        "accessToken": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1MzA1NTNhOWVkMjgwMDAxYjM3MWM3NSIsImlhdCI6MTY5NzY2NjM2MiwiZXhwIjoxNjk3NjY3NTYyfQ.rovxYJU8Or75HAzGKGaruPtai3gnEgdI06HOangosLk",
//        "refreshToken": "22300bf65f17e401fac786ce563b262995556e24882e8eaed3ead7b776fd067e897fb20eedd5ce14"
//    }


    @Step("Проверка ответа на запрос удаления пользователя")
    public void deleteSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_202)
                .body("success", is(true))
                .and()
                .body("message", is( "User successfully removed"));
    }

//    {
//        "success": true,
//            "message": "User successfully removed"
//    }
}
