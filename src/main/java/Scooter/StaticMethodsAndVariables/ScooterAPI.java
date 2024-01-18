package Scooter.StaticMethodsAndVariables;

import Scooter.DTO.IDCourier;
import Scooter.DTO.LoginCourier;
import Scooter.DTO.NewCourier;
import Scooter.DTO.NewOrder;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import java.util.Stack;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class ScooterAPI {
    private final String handleForCreateCourier = "/api/v1/courier";
    private final String handleForLoginCourier = "/api/v1/courier/login";
    private final String handleForCreateOrder = "/api/v1/orders";
    private final String handleForGetListOfOrder = "/api/v1/orders";
    private final Stack<LoginCourier> couriersToCleanUp;

    public Response createCourier(String givenLogin, String givenPassword, String givenFirstName) {
        NewCourier newCourier = new NewCourier(givenLogin, givenPassword, givenFirstName);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(new Gson().toJson(newCourier))
                .when()
                .post(handleForCreateCourier);
        couriersToCleanUp.push(new LoginCourier(givenLogin, givenPassword));
        return response;
    }

    public Response loginCourierInSystem(String givenLogin, String givenPassword) {
        LoginCourier loginCourier = new LoginCourier(givenLogin, givenPassword);
        Response response = given()
                .header("Content-type", "application/json")
                .body(new Gson().toJson(loginCourier))
                .when()
                .post(handleForLoginCourier);
        return response;
    }

    public Response deleteCourier(IDCourier idCourier) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(idCourier)
                .when()
                .delete(handleForCreateCourier + "/" + idCourier.getID());
        return response;
    }

    public Response createOrder(NewOrder givenNewOrder){
        Response response = given()
                .header("Content-type", "application/json")
                .body(givenNewOrder)
                .when()
                .post(handleForCreateOrder);
        return response;
    }

    public Response getListOfOrders(){
        Response response = given()
                .when()
                .get(handleForGetListOfOrder);
        return response;
    }


    public ScooterAPI() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        couriersToCleanUp = new Stack<>();

    }

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
