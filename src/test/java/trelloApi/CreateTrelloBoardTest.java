package trelloApi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;


public class CreateTrelloBoardTest extends BaseTest {

    @Test(priority = 1)
    public void testGetListInfo() {

        RequestSpecification requestSpecification = given()
                .queryParam("key", keyID)
                .queryParam("token", tokenID)
                .pathParam("id", boardId)
                .log().all();
        //.contentType(ContentType.JSON);

        Response response = requestSpecification.when()
                .get("boards/{id}/lists");
        System.out.println(response.body());

        response.then()
                .statusCode(200) ;

        Assert.assertEquals(response.getStatusCode(),200);

        System.out.println("Response code of get list --> "+response.statusCode());

        List<Map<String, ?>> listInfo = response.jsonPath().getList("$");

        listId = listInfo.get(0).get("id").toString();

        for (int i = 0; i < listInfo.size(); i++) {
            System.out.println(listInfo.get(i).get("id").toString());
            System.out.println(listInfo.get(i).get("name").toString());

        }

    }


    @Test (priority = 2)
    public void testCreateCard(){
        //testCreateList();
                    System.out.println("listId = "+ listId); 
     RequestSpecification requestSpecification = given()
             .queryParam("key", keyID)
             .queryParam("token", tokenID)
             .queryParam("name", "Card1")
             .queryParam("idList", listId)
             .contentType(ContentType.JSON)
             .log().all();


     Response response = requestSpecification.when()
             .post("cards");
      System.out.println("Response body after creating card--> "+ response.body());

      System.out.println("Response code from create card -->" + response.statusCode());

      response.then()
              .statusCode(200) ;

      Assert.assertEquals(response.getStatusCode(),200);

      Map<Object, Object> map = response.jsonPath().getMap("$");
              System.out.println("Card ID: " + map.get("id"));
              System.out.println("Card Name: " + map.get("name"));
              cardId = map.get("id").toString();
              System.out.println("Card Id = "+ cardId);
    }   //end of testCreateCard


    @Test (priority = 3)
    public void testUpdateCard(){
        //testCreateCard();
                    System.out.println("CardId = "+ cardId);
     RequestSpecification requestSpecification = given()
             .pathParam("id",cardId)
             .queryParam("key", keyID)
             .queryParam("token", tokenID)
             .queryParam("name", "Card1_Updated")
             .log().all();


     Response response = requestSpecification.when()
             .put("cards/{id}");
      System.out.println("Response body after updating the card--> "+ response.body());
                                                                                             
      System.out.println("Response code from update card -->" + response.statusCode());

      Assert.assertEquals(response.getStatusCode(),200);

      Map<Object, Object> map = response.jsonPath().getMap("$");
              System.out.println("Card ID: " + map.get("id"));
              System.out.println("Card Name: " + map.get("name"));
              cardId = map.get("id").toString();
              System.out.println("Card Id = "+ cardId);
    }   //end of testUpdateCard

    @Test (priority = 4)
    public void testDeleteCard(){
        //testCreateCard();
                    System.out.println("CardId = "+ cardId);
     RequestSpecification requestSpecification = given()
             .pathParam("id",cardId)
             .queryParam("key", keyID)
             .queryParam("token", tokenID)
             .log().all();


     Response response = requestSpecification.when()
             .delete("cards/{id}");

     response.then()
             .statusCode(200) ;
     Assert.assertEquals(response.getStatusCode(),200);
     
     System.out.println("Response body after deleting the card--> "+ response.body());

     System.out.println("Response code from delete card -->" + response.statusCode());

    }   //end of testDeleteCard


}  //end class