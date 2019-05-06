package trelloApi;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseTest {
    String keyID = "";
    String tokenID = "";
    String boardId;


    @BeforeSuite
    public void setUp()
    {
        RestAssured.baseURI = "https://api.trello.com/";
        String boardId = createBoardTest();
    }

    public String createBoardTest() {
        RequestSpecification requestSpecification = given()
                .queryParam("key", keyID)
                .queryParam("token", tokenID)
                .queryParam("name", "Retro").log().all()
                .contentType(ContentType.JSON);

        Response response = requestSpecification.when().
                post("1/boards/");
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
        Assert.assertEquals(map.get("name").toString(), "Retro");
        return boardId;
    }


       @AfterTest
       private void tearDown(){

        Response response =  given()
                .pathParam("boardID",boardId)
                .queryParam("key", keyID)
                .queryParam("token", tokenID).
                        when().
                        delete("1/boards/{boardId}");


        //response.then();
               //.statusCode(200);

    }

}