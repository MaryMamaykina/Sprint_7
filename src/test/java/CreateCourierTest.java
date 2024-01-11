import Scooter.IDCourier;
import Scooter.LoginCourier;
import Scooter.NewCourier;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CreateCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    @Test
    public void CreateNewCourier(){
        String login = "JohnDoe";
        String password = "109567";
        String firstName = "John";
        NewCourier newCourier = new NewCourier(login, password , firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourier)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(201);
        LoginCourier loginCourier = new LoginCourier(login,password);
        IDCourier idCourier = given()
                .header("Content-type", "application/json")
                .body(loginCourier)
                .when()
                .post("/api/v1/courier/login")
                .body()
                .as(IDCourier.class);
        given()
                .header("Content-type", "application/json")
                .body(idCourier)
                .when()
                .delete("/api/v1/courier/" + idCourier.getID());
    }
}
