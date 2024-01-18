import Scooter.StaticMethodsAndVariables.ScooterAPI;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetListOfOrdersTest {
    ScooterAPI scooterAPI = new ScooterAPI();
    @Test
    public void doesGetListOfOrdersReturnListOfOrders(){
        scooterAPI.getListOfOrders().then().body("orders",notNullValue());
    }
}
