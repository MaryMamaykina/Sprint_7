import Scooter.IDCourier;
import Scooter.LoginCourier;
import Scooter.NewCourier;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    private final String login = "John Legenda";
    private final String wrongLogin = "John Doe";
    private final String password = "109567";
    private final String wrongPassword = "197456";
    private final String firstName = "John";
    private final String handleForLoginCourier = "/api/v1/courier/login";


    private void createCourier(String givenLogin, String givenPassword, String givenFirstName) {
        NewCourier newCourier = new NewCourier(givenLogin, givenPassword, givenFirstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourier)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(201);
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
    public void courierCanLogIn() {
        createCourier(login, password, firstName);
        LoginCourier loginCourier = new LoginCourier(login, password);
        Response response = given()
                .header("Content-type", "application/json")
                .body(loginCourier)
                .when()
                .post(handleForLoginCourier);
        IDCourier idCourier = response.body().as(IDCourier.class);
        MatcherAssert.assertThat(idCourier.getID(), notNullValue());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @After
    public void cleanUp() {
        LoginCourier loginCourier = new LoginCourier(login, password);
        Response response = given()
                .header("Content-type", "application/json")
                .body(loginCourier)
                .when()
                .post(handleForLoginCourier);
        IDCourier idCourier;
        if (response.getStatusCode() == 200) {
            idCourier = response.body().as(IDCourier.class);
            deleteCourier(idCourier);
        }
    }
}
