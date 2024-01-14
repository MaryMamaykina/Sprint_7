import Scooter.IDCourier;
import Scooter.LoginCourier;
import Scooter.NewCourier;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    private final String login = "John Legenda";
    private final String password = "109567";
    private final String password2 = "197456";
    private final String firstName = "John";
    private final String firstName2 = "Johnny";

    private IDCourier loginCourierInSystem(String givenLogin, String givenPassword) {
        LoginCourier loginCourier = new LoginCourier(givenLogin, givenPassword);
        Response response = given()
                .header("Content-type", "application/json")
                .body(loginCourier)
                .when()
                .post("/api/v1/courier/login");
        if (response.getStatusCode() == 200) {
            return response.body().as(IDCourier.class);
        } else {
            return null;
        }
    }

    private void deleteCourier(IDCourier idCourier) {
        given()
                .header("Content-type", "application/json")
                .body(idCourier)
                .when()
                .delete("/api/v1/courier/" + idCourier.getID())
                .then().assertThat().statusCode(200);
    }

    @Test
    public void creatingCourierWorks() {
        NewCourier newCourier = new NewCourier(login, password, firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourier)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    public void creatingCourierWithTheSameParametersIsImpossible() {
        NewCourier newCourier = new NewCourier(login, password, firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourier)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(201);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourier)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    public void creatingCourierWithTheSameLoginIsImpossible() {
        NewCourier newCourier = new NewCourier(login, password, firstName);
        NewCourier newCourier2 = new NewCourier(login, password2, firstName2);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourier)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(201);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourier2)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    public void creatingCourierWithoutLoginIsImpossible() {
        NewCourier newCourier = new NewCourier(login, null, firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(new Gson().toJson(newCourier))
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void creatingCourierWithoutPasswordIsImpossible() {
        NewCourier newCourier = new NewCourier(null, password, firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(new Gson().toJson(newCourier))
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void creatingCourierWithoutLoginAndPasswordIsImpossible() {
        NewCourier newCourier = new NewCourier(null, null, firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(new Gson().toJson(newCourier))
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void cleanUp() {
        IDCourier idCourier = loginCourierInSystem(login, password);
        if (idCourier != null) {
            deleteCourier(idCourier);
        }
    }
}
