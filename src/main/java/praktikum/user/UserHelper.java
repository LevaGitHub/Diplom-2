package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.BaseRequest;
import praktikum.model.Credentials;
import praktikum.model.User;

public class UserHelper extends BaseRequest {
    protected final String CREATE_USER_METHOD_PATH = "auth/register";
    protected final String LOGIN_METHOD_PATH = "auth/login";
    protected final String DELETE_USER_METHOD_PATH = "auth/user";


    @Step("Отправка запроса на создание пользователя")
    public ValidatableResponse create(User user) {
        return sendBaseRequest()
                .body(user)
                .when()
                .post(CREATE_USER_METHOD_PATH)
                .then().log().all()
                ;
    }

    @Step("Получение токенов из запроса")
    public User extractTokenFromResponse(User user, ValidatableResponse response) {
        //TODO: проверить обрезку токена
//        user.setAccessToken((response.extract().path("accessToken")).toString().substring(7));
        user.setAccessToken((response.extract().path("accessToken")));
        user.setRefreshToken(response.extract().path("refreshToken"));
        return user;
    }

     public Credentials extractCredentials(User user){
        return Credentials.from(user);
     }

    @Step("Отправка запроса на логин")
    public ValidatableResponse login(Credentials creds) {
        return sendBaseRequest()
                .body(creds)
                .when()
                .post(LOGIN_METHOD_PATH)
                .then().log().all()
                ;
    }

    @Step("Удаление пользователя")
    public ValidatableResponse  delete(String token) {
        return sendBaseRequest()
                .header("Authorization",token)
                .when()
                .delete(DELETE_USER_METHOD_PATH)
                .then().log().all()
                ;
    }




}
