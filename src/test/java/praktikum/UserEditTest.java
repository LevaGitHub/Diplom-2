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

public class UserEditTest {

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
    @DisplayName("Edit User Data")
    @Description("Проверка редактирования данных пользователя (Имя, Email)")
    public void updateUserNameAndEmailSuccess() {
        ValidatableResponse createResponse = userHelper.create(userData);
        userData = userHelper.extractTokenFromResponse(userData, createResponse);
        User editUserData = userGenerator.getRandom();
        editUserData.setPassword(userData.getPassword());
        editUserData.setAccessToken(userData.getAccessToken());
        ValidatableResponse editResponse = userHelper.update(editUserData);
        userChecker.userDataSuccess(editResponse, editUserData);
        ValidatableResponse getResponse = userHelper.get(editUserData.getAccessToken());
        userChecker.userDataSuccess(getResponse, editUserData);
    }


    @Test
    @DisplayName("Edit User Password")
    @Description("Проверка редактирования данных пользователя (Пароль)")
    public void updateUserPasswordSuccess() {
        ValidatableResponse createResponse = userHelper.create(userData);
        userData = userHelper.extractTokenFromResponse(userData, createResponse);
        User editUserData = userGenerator.getRandom();
        editUserData.setName(userData.getName());
        editUserData.setEmail(userData.getEmail());
        editUserData.setAccessToken(userData.getAccessToken());
        ValidatableResponse editResponse = userHelper.update(editUserData);
        userChecker.userDataSuccess(editResponse, editUserData);
        ValidatableResponse loginResponse = userHelper.login(editUserData);
        userChecker.userDataSuccess(loginResponse, editUserData);
        userData = userHelper.extractTokenFromResponse(userData, loginResponse);
    }


    @Test
    @DisplayName("Edit User Data by unauthorized User")
    @Description("Проверка невозможности редактирования данных пользователя неавторизованным пользователем")
    public void updateUserDataFail() {
        ValidatableResponse createResponse = userHelper.create(userData);
        ValidatableResponse editResponse = userHelper.update(userData);
        userChecker.updateUserFail(editResponse);
        userData = userHelper.extractTokenFromResponse(userData, createResponse);
    }

}
