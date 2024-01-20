import scooter.staticmethodsandvariables.OrderAPI;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetListOfOrdersTest {
    OrderAPI orderAPI = new OrderAPI();

    @Test
    public void doesGetListOfOrdersReturnListOfOrdersAndCode200() {
        orderAPI.getListOfOrders().then().statusCode(SC_OK)
                .and()
                .body("orders", notNullValue());
    }
}
