package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.model.Order;
import praktikum.model.User;
import praktikum.order.OrderChecker;
import praktikum.order.OrderHelper;
import praktikum.user.UserGenerator;
import praktikum.user.UserHelper;

import java.util.List;
import java.util.Objects;

import static praktikum.Constants.*;

public class OrderCreateTest {

    private final UserHelper userHelper = new UserHelper();
    private final UserGenerator userGenerator = new UserGenerator();
    private final OrderHelper orderHelper = new OrderHelper();
    private final OrderChecker orderChecker = new OrderChecker();

    private User userData;
    private ValidatableResponse createUserResponse;


    @Before
    @DisplayName("Test data")
    @Description("Подготовка тестовых данных")
    public void prepareTestData() {
        userData = userGenerator.getRandom();
        createUserResponse = userHelper.create(userData);
    }


    @After
    @DisplayName("Delete User")
    @Description("Удаление созданных тестовых данных")
    public void deleteTestData() {
        if (!Objects.isNull(userData.getAccessToken())) {
            userHelper.delete(userData.getAccessToken());
        }
    }


    @Test
    @DisplayName("Create Order authorized User")
    @Description("Проверка создания заказа авторизованным пользователем")
    public void createOrderAuthorizedUserSuccess() {
        userData = userHelper.extractTokenFromResponse(userData, createUserResponse);
        Order orderData = new Order(List.of(BUN_ID, MAIN_ID, SAUCE_ID));
        ValidatableResponse creareOrderResponse =  orderHelper.create(orderData);
        orderChecker.userDataSuccess(creareOrderResponse);
    }

    @Test
    @DisplayName("Create Order no authorized User")
    @Description("Проверка создания заказа неавторизованным пользователем")
    public void createOrderNoAuthorizedUserSuccess() {
        Order orderData = new Order(List.of(BUN_ID, MAIN_ID, SAUCE_ID));
        ValidatableResponse creareOrderResponse =  orderHelper.create(orderData);
        orderChecker.userDataSuccess(creareOrderResponse);
        userData = userHelper.extractTokenFromResponse(userData, createUserResponse);
    }

    @Test
    @DisplayName("Create Order without ingredients")
    @Description("Проверка создания заказа без ингредиентов")
    public void createOrderWithoutIngredientsFail() {
        userData = userHelper.extractTokenFromResponse(userData, createUserResponse);
        Order orderData = new Order(List.of());
        ValidatableResponse creareOrderResponse =  orderHelper.create(orderData);
        orderChecker.userDataWithoutIngredientsFail(creareOrderResponse);

    }

    @Test
    @DisplayName("Create Order with wrong ingredients hash")
    @Description("Проверка создания заказа с неверным хешем ингредиента")
    public void createOrderWrongIngredientFail() {
        userData = userHelper.extractTokenFromResponse(userData, createUserResponse);
        Order orderData = new Order(List.of(WRONG_INGREDIENT_ID));
        ValidatableResponse creareOrderResponse =  orderHelper.create(orderData);
        orderChecker.userDataWrongIngredientsFail(creareOrderResponse);
    }

}
