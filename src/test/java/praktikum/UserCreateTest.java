package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import praktikum.model.User;
import praktikum.user.UserChecker;
import praktikum.user.UserGenerator;
import praktikum.user.UserHelper;

import java.util.Objects;

public class UserCreateTest {

    private final UserHelper userHelper = new UserHelper();
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserChecker userChecker = new UserChecker();

    private User userData = userGenerator.getRandom();

    @After
    @DisplayName("Delete User")
    @Description("Удаление созданных тестовых данных")
    public void deleteTestData() {
        if (!Objects.isNull(userData.getAccessToken())) {
            ValidatableResponse deleteResponse = userHelper.delete(userData.getAccessToken());
            userChecker.deleteSuccess(deleteResponse);
        }
    }


    @Test
    @DisplayName("Create User")
    @Description("Проверка создания пользователя с корректными данными")
    public void createUserSuccess() {
        ValidatableResponse createResponse = userHelper.create(userData);
        userChecker.userDataSuccess(createResponse, userData);
        userData = userHelper.extractTokenFromResponse(userData, createResponse);
    }


    @Test
    @DisplayName("Create exists User")
    @Description("Проверка невозможности повторного создания существующего пользователя")
    public void createAlreadyExistsUserFail() {
        ValidatableResponse createResponse = userHelper.create(userData);
        userData = userHelper.extractTokenFromResponse(userData, createResponse);
        createResponse = userHelper.create(userData);
        userChecker.createExistsUserFail(createResponse);
    }


    @Test
    @DisplayName("Create User without required fields")
    @Description("Проверка невозможности создания пользователя без обязательного атрибута")
    public void createUserWithoutRequiredFieldsFail() {
        userData.setEmail("");
        ValidatableResponse createResponse = userHelper.create(userData);
        userChecker.createUserWithoutRequiredFieldsFail(createResponse);
    }

}
