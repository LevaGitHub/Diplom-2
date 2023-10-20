package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import praktikum.model.Credentials;
import praktikum.model.User;
import praktikum.user.UserChecker;
import praktikum.user.UserGenerator;
import praktikum.user.UserHelper;

public class UserCreateTest {

    private final UserHelper userHelper = new UserHelper();
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserChecker courierChecker = new UserChecker();



    @Test
    @DisplayName("Create User")
    @Description("Проверка создания пользователя с корректными данными")
    public void createUserSuccess() {
        User userData = userGenerator.getRandom();
        ValidatableResponse createResponse = userHelper.create(userData);
        courierChecker.creationSuccess(createResponse);
        userData = userHelper.extractTokenFromResponse(userData, createResponse);
        //TODO: получить токен, хз понадобится ли, удалить за собой юзера
//        ValidatableResponse loginResponse = userHelper.login(userHelper.extractCredentials(userData));
        ValidatableResponse deleteResponse = userHelper.delete(userData.getAccessToken());
        courierChecker.deleteSuccess(deleteResponse);

    }


}
