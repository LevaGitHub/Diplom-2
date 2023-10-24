package praktikum;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseRequest {
    protected final String BASE_URI = "https://stellarburgers.nomoreparties.site/";
    protected final String API_PREFIX = "api/";

    protected RequestSpecification sendBaseRequest() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .basePath(API_PREFIX)
                ;
    }
}
