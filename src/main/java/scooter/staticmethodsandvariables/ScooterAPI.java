package scooter.staticmethodsandvariables;

import scooter.dto.IDCourier;
import scooter.dto.LoginCourier;
import scooter.dto.NewCourier;
import scooter.dto.NewOrder;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;

import java.util.Stack;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class ScooterAPI {
    private static final String handleForCreateCourier = "/api/v1/courier";
    private static final String handleForLoginCourier = "/api/v1/courier/login";
    private static final String handleForCreateOrder = "/api/v1/orders";
    private static final String handleForGetListOfOrder = "/api/v1/orders";
    private final Stack<LoginCourier> couriersToCleanUp;

    public ScooterAPI() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
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

    @Step("This method accepts required data for create request body for create order, sent post request with this data (in json)." +
            " The method returns response for further verification.")
    public Response createOrder(NewOrder givenNewOrder) {
        return given()
                .header("Content-type", "application/json")
                .body(givenNewOrder)
                .when()
                .post(handleForCreateOrder);
    }

    @Step("This method sent get request. The method returns response for further verification.")
    public Response getListOfOrders() {
        return given()
                .when()
                .get(handleForGetListOfOrder);
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
