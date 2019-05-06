package trelloApi;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CreateBoardTest {

    @Test
    public void createBoard() {
        RequestSpecification requestSpecification=given()
                .queryParam("key", "0697ace29da135af1009cc535346c753")
                .queryParam("token", "c9e7631e79903c345e53384685719427ffde19e39a2855cf0bfabd8e26516286")
                .queryParam("name", "Retro").log().all()
                .contentType(ContentType.JSON);

        Response response=requestSpecification.when()
                .post("https://api.trello.com/1/boards/");
                System.out.println(response.body());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .body()
                .jsonPath().getMap("$");

        Map<String, ?> map = response.jsonPath().getMap("$");
                        String ExpectedBoardId=map.get("id").toString();
                        String ExpectedBoardName =map.get("name").toString();


         //Assert.assertEquals(ExpectedResult.).toString(),actualData.get(i).get("id").toString());


        Assert.assertEquals("Eve",actualData.get(0).get("first_name").toString());



















    }

}
