package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.BaseRequest;
import praktikum.model.Order;


public class OrderHelper extends BaseRequest {

    protected final String ORDERS_METHOD_PATH = "orders";


    @Step("Отправка запроса на создание заказа")
    public ValidatableResponse create(Order order) {
        return sendBaseRequest()
                .body(order.getIngredientsListAsJson())
                .when()
                .post(ORDERS_METHOD_PATH)
                .then().log().all()
                ;
    }
}
