package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.BaseRequest;
import praktikum.model.Order;
import praktikum.model.User;

import java.util.Objects;


public class OrderHelper extends BaseRequest {

    protected final String ORDERS_METHOD_PATH = "orders";


    @Step("Отправка запроса на создание заказа")
    public ValidatableResponse create(Order order, User user) {
        if (!Objects.isNull(user.getAccessToken())) {
            return sendBaseRequest()
                    .body(order.getIngredientsListAsJson())
                    .when()
                    .post(ORDERS_METHOD_PATH)
                    .then().log().all()
                    ;
        } else {
            return createWithOutAuth(order);
        }
    }


    private ValidatableResponse createWithOutAuth(Order order) {
        return sendBaseRequest()
                .body(order.getIngredientsListAsJson())
                .when()
                .post(ORDERS_METHOD_PATH)
                .then().log().all()
                ;
    }


    @Step("Получение номера заказа")
    public Order extractOrderNumberFromResponse(Order order, ValidatableResponse response) {
        order.setNumber((response.extract().path("order.number")));
        return order;
    }


    @Step("Отправка запроса на  получение заказа")
    public ValidatableResponse get(User user) {
        if (!Objects.isNull(user.getAccessToken())) {
            return sendBaseRequest()
                    .header("Authorization",user.getAccessToken())
                    .when()
                    .get(ORDERS_METHOD_PATH)
                    .then().log().all()
                    ;
        } else {
            return getWithOutAuth();
        }
    }


    private ValidatableResponse getWithOutAuth(){
        return sendBaseRequest()
                .when()
                .get(ORDERS_METHOD_PATH)
                .then().log().all()
                ;
    }
}
