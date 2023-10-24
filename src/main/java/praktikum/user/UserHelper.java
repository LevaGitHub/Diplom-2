package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import praktikum.BaseRequest;
import praktikum.model.User;

import java.util.Objects;


public class UserHelper extends BaseRequest {
    protected final String CREATE_USER_METHOD_PATH = "auth/register";
    protected final String LOGIN_METHOD_PATH = "auth/login";
    protected final String AUTH_USER_METHOD_PATH = "auth/user";


    @Step("Отправка запроса на создание пользователя")
    public ValidatableResponse create(User user) {
        return sendBaseRequest()
                .body(user.getUserDataForCreateUpdate())
                .when()
                .post(CREATE_USER_METHOD_PATH)
                .then().log().all()
                ;
    }


    @Step("Получение токенов из запроса")
    public User extractTokenFromResponse(User user, ValidatableResponse response) {
        user.setAccessToken((response.extract().path("accessToken")));
        user.setRefreshToken(response.extract().path("refreshToken"));
        return user;
    }


    @Step("Отправка запроса на логин")
    public ValidatableResponse login(User user) {
        return sendBaseRequest()
                .body(user.getUserDataForLogin())
                .when()
                .post(LOGIN_METHOD_PATH)
                .then().log().all()
                ;
    }


    @Step("Отправка запроса на удаление пользователя")
    public ValidatableResponse  delete(String token) {
        return sendBaseRequest()
                .header("Authorization",token)
                .when()
                .delete(AUTH_USER_METHOD_PATH)
                .then().log().all()
                ;
    }


    @Step("Отправка запроса на обновление пользователя")
    public ValidatableResponse update(User user) {
        if (!Objects.isNull(user.getAccessToken())) {
            return sendBaseRequest()
                    .header("Authorization",user.getAccessToken())
                    .body(user.getUserDataForCreateUpdate())
                    .when()
                    .patch(AUTH_USER_METHOD_PATH)
                    .then().log().all()
                    ;
        } else {
            return updateWithOutAuth(user);
        }
    }

    private ValidatableResponse updateWithOutAuth(User user){
        return sendBaseRequest()
                .body(user.getUserDataForCreateUpdate())
                .when()
                .patch(AUTH_USER_METHOD_PATH)
                .then().log().all()
                ;
    }

    @Step("Отправка запроса на получение данных пользователя")
    public ValidatableResponse  get(String token) {
        return sendBaseRequest()
                .header("Authorization",token)
                .when()
                .get(AUTH_USER_METHOD_PATH)
                .then().log().all()
                ;
    }




}
