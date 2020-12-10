package org.api.test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.*;

public class AppTest extends BaseClass
{
    @Test(description = "Verify that the user is able to hit the endpoint 'book' successfully without any authentication")
    public void verifyEndPointBookWithoutAuth(Method method) {
        System.out.println("Test Name:        " + method.getName());
        System.out.println("Method Description: " + method.getAnnotation(Test.class).description());
        given()
            .spec(getBaseSpecWithOutAuth())
        .when()
            .get("book")
        .then()
            .statusCode(200);

        System.out.println("Status code 200 returned successfully");
    }

    @Test(description = "Verify that the user is able to hit the endpoint 'book' successfully with authentication")
    public void verifyEndPointBookWithAuth(Method method) {
        System.out.println("Test Name:        " + method.getName());
        System.out.println("Method Description: " + method.getAnnotation(Test.class).description());

        given()
            .spec(getBaseSpecWithAuth())
        .when()
            .get("book")
        .then()
            .statusCode(200);

        System.out.println("Status code 200 returned successfully");
    }

    @Test(description = "Verify all book names and book count in Lord of the rings series")
    public void verifyBookDetails(Method method) {
        System.out.println("Test Name:        " + method.getName());
        System.out.println("Method Description: " + method.getAnnotation(Test.class).description());

        List<String> bookNames = new ArrayList<>();
        bookNames.add("The Fellowship Of The Ring");
        bookNames.add("The Return Of The King");
        bookNames.add("The Two Towers");

        given()
                .spec(getBaseSpecWithOutAuth())
                .when()
                .get("book")
                .then()
                .assertThat()
                .body("docs.name", hasItems(bookNames.toArray()))
                .body("total", equalTo(3));

        System.out.printf("All books: %s successfully verified and count is %d", bookNames.toString(), bookNames.size());
    }

    @Test(description = "Verify chapters in Books of Lord of the Rings", dataProvider = "bookChapters")  //Parameterized Test
    public void verifyBookChapters(String bookId, String chapterId, String chapterName, Method method) {
        System.out.println("Test Name:        " + method.getName());
        System.out.println("Method Description: " + method.getAnnotation(Test.class).description());

        given()
            .spec(getBaseSpecWithOutAuth())
            .pathParam("bookId", bookId)
        .when()
            .get("book/{bookId}/chapter")
        .then()
            .assertThat()
                .body("docs.find {it._id == '" + chapterId + "'}.chapterName", equalTo(chapterName));

        System.out.printf("Verified that for BookId: %s and ChapterId: %s, the chapter name must be : %s", bookId, chapterId, chapterName);
    }

    @AfterMethod
    public void afterTest(){
        System.out.println("\n\n");
    }
}
