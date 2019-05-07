package trelloApi;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseTest {
    String keyID = "0697ace29da135af1009cc535346c753";
    String tokenID = "70192f4e5cd20b4e9e0609d9fd0c241654ed824f6820618fff9a707321fdba18";
    String boardId;
    String listId;
    String cardId;


    @BeforeSuite
    public void setUp()
    {
        RestAssured.baseURI = "https://api.trello.com/1/";
        //String boardId = createBoardTest();
    }

     // String createBoardTest() {
    @BeforeTest
        public void createBoardTest() {
        RequestSpecification requestSpecification = given()
                .queryParam("key", keyID)
                .queryParam("token", tokenID)
                .queryParam("name", "B1")
                .contentType(ContentType.JSON)
                .log().all();

        Response response = requestSpecification.when().
                post("boards/");
        System.out.println(response.body());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response()
                .jsonPath()
                .getMap("$");
        Map<Object, Object> map = response.jsonPath().getMap("$");
        System.out.println("Board ID: " + map.get("id"));
        System.out.println("Board Name: " + map.get("name"));
        boardId = map.get("id").toString();
        System.out.println(boardId);
        Assert.assertEquals(map.get("name").toString(), "B1");
        //return boardId;
    }


       @AfterTest
       private void tearDown(){
        System.out.println("After test");
        Response response =  given()
                .pathParam("boardID",boardId)
                .queryParam("key", keyID)
                .queryParam("token", tokenID).
                        when().
                        delete("boards/{boardId}");


        //response.then();
               //.statusCode(200);

    }

}