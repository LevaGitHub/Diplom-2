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

public class OrderGetTest {

    private final UserHelper userHelper = new UserHelper();
    private final UserGenerator userGenerator = new UserGenerator();
    private final OrderHelper orderHelper = new OrderHelper();
    private final OrderChecker orderChecker = new OrderChecker();

    private User userData;
    private Order orderData;

    @Before
    @DisplayName("Test data")
    @Description("Подготовка тестовых данных")
    public void prepareTestData() {
        userData = userGenerator.getRandom();

        orderData = new Order(List.of(BUN_ID, MAIN_ID, SAUCE_ID));
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
    @DisplayName("Get Order by authorized user")
    @Description("Проверка получения заказа авторизованным пользователем")
    public void getOrderByAuthorizedUserSuccess() {
        ValidatableResponse createUserResponse = userHelper.create(userData);
        userData = userHelper.extractTokenFromResponse(userData, createUserResponse);
        orderHelper.create(orderData, userData);
        ValidatableResponse getOrderResponse = orderHelper.get(userData);
        orderChecker.orderDataSuccess(getOrderResponse);
    }

    @Test
    @DisplayName("Get Order by no authorized user")
    @Description("Проверка получения заказа неавторизованным пользователем")
    public void getOrderByNoAuthorizedUserFail() {
        userHelper.create(userData);
        orderHelper.create(orderData, userData);
        ValidatableResponse getOrderResponse = orderHelper.get(userData);
        orderChecker.orderDataUnauthorizedFail(getOrderResponse);
    }

}
