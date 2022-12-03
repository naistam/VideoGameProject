package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

public class TC_VideoGameAPI {
    
	@Test(priority=1)
	public void test_getAllVideoGames()
	{
		given()
		.when()
			.get("http://localhost:8080/app/videogames")
		.then()
			.statusCode(200);
		
		
	}
	@Test(priority=2)
	public void test_AddNewGame()
	{
		HashMap data = new HashMap();
		data.put("id", "106");
		data.put("name", "Super man");
		data.put("releasedate", "2022-11-25T05:43:51.333Z");
		data.put("reviewScore", "5");
		data.put("category", "Adventure" );
		data.put("rating", "Universal");
	    
		Response res = 
				  given()
						.contentType("application/json")
						.body(data)
				.when()
					.post("http://localhost:8080/app/videogames")
				.then()
				      .statusCode(200)
				      .log().body()
				      .extract().response();
		
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
		
	}
	@Test(priority=3)
	public void test_getVideoGame()
	{
		given()
		.when()
		      .get("http://localhost:8080/app/videogames/106")
		.then()
		      .statusCode(200)
		      .body("videogame.id",equalTo( "106"))
		      .body("videogame.name", equalTo("Super man"));
	}
	@Test(priority=4)
	public void test_updateVideoGame(){
		HashMap data = new HashMap();
		data.put("id", "106");
		data.put("name", "Batman");
		data.put("releasedate", "2022-11-25T05:45:51.333Z");
		data.put("reviewScore", "3");
		data.put("category", "Adventure" );
		data.put("rating", "Universal");
	    
		given()
		      .contentType("application/json")
		      .body(data)
		.when()
		      .put("http://localhost:8080/app/videogames/106")
		.then()
		      .statusCode(200)
		      .log().body()
		      .body("videogame.id", equalTo("106"))
		      .body("videogame.name", equalTo("Batman"));
		      
	}
	@Test(priority=5)
	public void test_deleteVideoGame()
	{
		Response res = 
		given()
	    .when()
	          .delete("http://localhost:8080/app/videogames/106")
	    .then()
	          .statusCode(200)
	          .log().body()
	          .extract().response();
	     String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
	          
	}
}
