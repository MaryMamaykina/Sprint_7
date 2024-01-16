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
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    private final String handleForCreateCourier = "/api/v1/courier";
    private final String handleForLoginCourier = "/api/v1/courier/login";

    private final String login = "John Legenda";
    private final String password = "109567";
    private final String anotherPassword = "197456";
    private final String firstName = "John";
    private final String anotherFirstName = "Johnny";

    private Response createCourier(String givenLogin, String givenPassword, String givenFirstName) {
        NewCourier newCourier = new NewCourier(givenLogin, givenPassword, givenFirstName);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(new Gson().toJson(newCourier))
                .when()
                .post(handleForCreateCourier);
        return response;
    }


    private Response loginCourierInSystem(String givenLogin, String givenPassword) {
        LoginCourier loginCourier = new LoginCourier(givenLogin, givenPassword);
        Response response = given()
                .header("Content-type", "application/json")
                .body(loginCourier)
                .when()
                .post(handleForLoginCourier);
        return response;
    }

    private Response deleteCourier(IDCourier idCourier) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(idCourier)
                .when()
                .delete(handleForCreateCourier + "/" + idCourier.getID());
        return response;
    }

    @Test
    public void doesCreatingCourierWork() {
        createCourier(login, password, firstName);
        loginCourierInSystem(login, password).then().statusCode(SC_OK);
    }

    @Test
    public void doesCreatingCourierReturnCode200() {
        createCourier(login, password, firstName).then().statusCode(SC_CREATED);
    }

    @Test
    public void doesCreatingCourierReturnReturnBodyOkTrue() {
        createCourier(login, password, firstName).then().body("ok", equalTo(true));
    }

    @Test
    public void IsCreatingCourierWithTheSameParametersImpossible() {
        createCourier(login, password, firstName);
        createCourier(login, password, firstName).then().statusCode(SC_CONFLICT);
    }

    @Test
    public void IsCreatingCourierWithTheSameLoginAndFirstNameButAnotherPasswordImpossible() {
        createCourier(login, password, firstName);
        createCourier(login, anotherPassword, firstName).then().statusCode(SC_CONFLICT);
    }

    @Test
    public void IsCreatingCourierWithTheSameLoginAndPasswordButAnotherFirstNameImpossible() {
        createCourier(login, password, firstName);
        createCourier(login, password, anotherFirstName).then().statusCode(SC_CONFLICT);
    }

    @Test
    public void IsCreatingCourierWithTheSameLoginButAnotherPasswordAndFirstNameImpossible() {
        createCourier(login, password, firstName);
        createCourier(login, anotherPassword, anotherFirstName).then().statusCode(SC_CONFLICT);
    }

    @Test
    public void IsCreatingCourierWithoutLoginImpossible() {
        createCourier(null, password, firstName);
        loginCourierInSystem(login, password).then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void IsCreatingCourierWithoutPasswordImpossible() {
        createCourier(login, null, firstName);
        loginCourierInSystem(login, password).then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void IsCreatingCourierWithoutLoginAndPasswordImpossible() {
        createCourier(null, null, firstName);
        loginCourierInSystem(login, password).then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void doesCreatingCourierWithoutLoginReturnMistake() {
        createCourier(null, password, firstName)
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void doesCreatingCourierWithoutPasswordReturnMistake() {
        createCourier(login, null, firstName).then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void doesCreatingCourierWithoutLoginAndPasswordReturnMistake() {
        createCourier(null, null, firstName).then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void cleanUp() {
        Response response = loginCourierInSystem(login, password);
        if (response.getStatusCode() == SC_OK) {
            IDCourier idCourier = response.body().as(IDCourier.class);
            deleteCourier(idCourier).then().statusCode(SC_OK);
        }
    }
}
