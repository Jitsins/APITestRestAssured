package org.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.DataProvider;

import static io.restassured.RestAssured.oauth2;

public class BaseClass {
    public static String baseURI = "https://the-one-api.dev";
    public static String basePath = "v2";
    //public static String oAuthKey = "#Sign up at https://the-one-api.dev/sign-up to get your API key#";
    public static String oAuthKey = "M-b1eJkO6AQxgxJfAAdcZa";

    //region Request Specifications
    public RequestSpecification getBaseSpecWithAuth() {
        return new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .setAuth(oauth2(oAuthKey))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.URI)
                .build();
    }

    public RequestSpecification getBaseSpecWithOutAuth() {
        return new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.URI)
                .build();
    }
    //endregion

    //region Data Providers
    @DataProvider(name = "bookChapters")
    public static Object[][] bookChapters(){
        return new Object[][]{
                {"5cf5805fb53e011a64671582", "5cdc25d4bc17e929cf2461ec", " The Shadow of the Past "},
                {"5cf5805fb53e011a64671582", "5cdc25d4bc17e929cf2461f0", " The Old Forest "},
                {"5cf58077b53e011a64671583", "5cdc25d5bc17e929cf246201", " The Riders of Rohan "},
        };
    }
    //endregion
}
