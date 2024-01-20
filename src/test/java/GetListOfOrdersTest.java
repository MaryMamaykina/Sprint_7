import scooter.staticmethodsandvariables.ScooterAPI;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetListOfOrdersTest {
    ScooterAPI scooterAPI = new ScooterAPI();
    @Test
    public void doesGetListOfOrdersReturnListOfOrdersAndCode200(){
        scooterAPI.getListOfOrders().then().statusCode(SC_OK)
                .and()
                .body("orders",notNullValue());
    }
}
