package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.model.Order;
import praktikum.model.User;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static praktikum.Constants.*;

public class OrderChecker {


    @Step("Проверка данных заказа")
    public void userDataSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_200)
                .body("success", is(true));
    }

    @Step("Проверка данных заказа без ингредиентов")
    public void userDataWithoutIngredientsFail(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_400)
                .body("success", is(false))
                .and()
                .body("message", equalTo(MSG_INGREDIENT_MUST_BE_PROVIDED));
    }

    @Step("Проверка данных заказа c неверным хешем ингредиентов")
    public void userDataWrongIngredientsFail(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_500);
    }

    @Step("Проверка данных заказа")
    public void orderDataSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_200)
                .body("success", is(true));
    }

    @Step("Проверка запроса получения данных заказа неавторизованным пользователем")
    public void orderDataUnauthorizedFail(ValidatableResponse response) {
        response.assertThat()
                .statusCode(STATUS_CODE_401)
                .body("success", is(false))
                .and()
                .body("message", equalTo(MSG_USER_UNAUTHORIZED)); {
        }
    }
}
