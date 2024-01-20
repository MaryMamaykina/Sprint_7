package scooter.staticmethodsandvariables;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import scooter.dto.NewOrder;

import static io.restassured.RestAssured.given;

public class OrderAPI extends ScooterAPI {
    private static final String handleForCreateOrder = "/api/v1/orders";
    private static final String handleForGetListOfOrder = "/api/v1/orders";

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
}
