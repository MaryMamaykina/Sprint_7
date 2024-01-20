package scooter.staticmethodsandvariables;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import scooter.dto.IDCourier;
import scooter.dto.LoginCourier;
import scooter.dto.NewCourier;

import java.util.Stack;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class CourierAPI extends ScooterAPI {
    private static final String handleForCreateCourier = "/api/v1/courier";
    private static final String handleForLoginCourier = "/api/v1/courier/login";
    private final Stack<LoginCourier> couriersToCleanUp;

    public CourierAPI() {
        couriersToCleanUp = new Stack<>();
    }

    @Step("This method accepts required data for create request body for create courier, sent post request with this data (in json)," +
            "push this courier's login and password (as an object of the LoginCourier class) in stack (for deleting after). The method returns response for further verification.")
    public Response createCourier(String givenLogin, String givenPassword, String givenFirstName) {
        NewCourier newCourier = new NewCourier(givenLogin, givenPassword, givenFirstName);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourier)
                .when()
                .post(handleForCreateCourier);
        couriersToCleanUp.push(new LoginCourier(givenLogin, givenPassword));
        return response;
    }

    @Step("This method accepts required data for create request body for login courier, sent post request with this data (in json)." +
            " The method returns response for further verification.")
    public Response loginCourierInSystem(String givenLogin, String givenPassword) {
        LoginCourier loginCourier = new LoginCourier(givenLogin, givenPassword);
        return given()
                .header("Content-type", "application/json")
                .body(loginCourier)
                .when()
                .post(handleForLoginCourier);
    }

    @Step("This method accepts required data for create request body for delete courier, sent delete request with this data (in json)." +
            " The method returns response for further verification.")
    public Response deleteCourier(IDCourier idCourier) {
        return given()
                .header("Content-type", "application/json")
                .body(idCourier)
                .when()
                .delete(handleForCreateCourier + "/" + idCourier.getID());
    }

    @Step("The method is used to delete all couriers created during the testing process, all stack data is searched." +
            " If the courier can be logged in using the specified data, it is deleted.")
    public void cleanUp() {
        while (!couriersToCleanUp.isEmpty()) {
            LoginCourier loginCourier = couriersToCleanUp.pop();
            Response response = loginCourierInSystem(loginCourier.getLogin(), loginCourier.getPassword());
            if (response.getStatusCode() == SC_OK) {
                IDCourier idCourier = response.body().as(IDCourier.class);
                deleteCourier(idCourier).then().statusCode(SC_OK);
            }
        }
    }
}
