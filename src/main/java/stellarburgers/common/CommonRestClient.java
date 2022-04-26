package stellarburgers.common;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static stellarburgers.common.Constants.BASE_URL;
import static io.restassured.http.ContentType.JSON;

// общий класс для формирования REST запросов
public class CommonRestClient {
    protected RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setContentType(JSON)
                .setBaseUri(BASE_URL)
                .build();
    }
}
