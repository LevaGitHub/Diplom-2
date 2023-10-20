package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import praktikum.model.User;
import praktikum.user.UserChecker;
import praktikum.user.UserGenerator;
import praktikum.user.UserHelper;

import java.util.Objects;

public class UserCreateTest {

    private final UserHelper userHelper = new UserHelper();
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserChecker courierChecker = new UserChecker();

    private User userData = userGenerator.getRandom();

    @After
    @DisplayName("Delete User")
    @Description("Удаление созданных тестовых данных")
    public void deleteTestData() {
        if (!Objects.isNull(userData.getAccessToken())) {
            ValidatableResponse deleteResponse = userHelper.delete(userData.getAccessToken());
            courierChecker.deleteSuccess(deleteResponse);
        }
    }

    @Test
    @DisplayName("Create User")
    @Description("Проверка создания пользователя с корректными данными")
    public void createUserSuccess() {
        ValidatableResponse createResponse = userHelper.create(userData);
        courierChecker.creationSuccess(createResponse);
        userData = userHelper.extractTokenFromResponse(userData, createResponse);
    }

    @Test
    @DisplayName("Create exists User")
    @Description("Проверка невозможности повторного создания существующего пользователя")
    public void createAlreadyExistsUserFail() {
        ValidatableResponse createResponse = userHelper.create(userData);
        userData = userHelper.extractTokenFromResponse(userData, createResponse);
        createResponse = userHelper.create(userData);
        courierChecker.createExistsUserFail(createResponse);
    }

    @Test
    @DisplayName("Create exists User")
    @Description("Проверка невозможности повторного создания существующего пользователя")
    public void createUserWithoutRequiredFieldsFail() {
        userData.setEmail("");
        ValidatableResponse createResponse = userHelper.create(userData);
        courierChecker.createUserWithoutRequiredFieldsFail(createResponse);
    }

}
