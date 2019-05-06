package trelloApi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;



public class CreateBoardTest extends BaseTest {

    @Test
    public void testGetListInfo() {
        RestAssured.baseURI = "1/boards/";
        RequestSpecification requestSpecification = given()
                .queryParam("key", keyID)
                .queryParam("token", tokenID)
                .pathParam("boardID", boardId);

        Response response = requestSpecification.when()
                .get("1/boards/");
        System.out.println(response.body());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response()
                .jsonPath()
                .getMap("$");

        System.out.println(response.statusCode());

        List<Map<String, ?>> listInfo = response.jsonPath().getList("$");

       for (int i = 0; i < listInfo.size(); i++) {
       System.out.println(listInfo.get(i).get("id").toString());
       System.out.println(listInfo.get(i).get("name").toString());

    }

    }

}

