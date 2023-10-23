package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import praktikum.model.User;
import praktikum.user.UserChecker;
import praktikum.user.UserGenerator;
import praktikum.user.UserHelper;

public class UserLoginTest {

    private final UserHelper userHelper = new UserHelper();
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserChecker userChecker = new UserChecker();

    private User userData = userGenerator.getRandom();


    @Test
    @DisplayName("Login User")
    @Description("Проверка логина пользователя с корректными данными")
    public void loginUserSuccess() {
        userHelper.create(userData);
        ValidatableResponse loginResponse = userHelper.login(userData);
        userChecker.userDataSuccess(loginResponse, userData);
        userData = userHelper.extractTokenFromResponse(userData, loginResponse);
        ValidatableResponse deleteResponse = userHelper.delete(userData.getAccessToken());
        userChecker.deleteSuccess(deleteResponse);
    }


    @Test
    @DisplayName("Login not registered User")
    @Description("Проверка логина пользователя, не зарегистрированного в системе")
    public void loginNotRegistredUserFail() {
        ValidatableResponse loginResponse = userHelper.login(userData);
        userChecker.loginUserFail(loginResponse);
    }

}
