import Scooter.DTO.NewOrder;
import Scooter.StaticMethodsAndVariables.ScooterAPI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderParameterizedColorTest {
    private final List<String> color;

    public OrderParameterizedColorTest(List<String> color) {
        this.color = color;
    }

    ScooterAPI scooterAPI = new ScooterAPI();

    @Parameterized.Parameters
    public static Object[] getColor() {
        return new Object[]{
                List.of("BLACK"),
                List.of("GREY"),
                List.of("BLACK", "GREY"),
                List.of()
        };
    }

    @Test
    public void parameterizedTestDoesCreateOrderWithDifferentColorReturnStatusCode201() {

        NewOrder order = new NewOrder(color);
        order.setColor(color);
        scooterAPI.createOrder(order).then().statusCode(SC_CREATED);
    }

    @Test
    public void parameterizedTestDoesCreateOrderWithDifferentColorReturnTrack() {

        NewOrder order = new NewOrder(color);
        order.setColor(color);
        scooterAPI.createOrder(order).then().body("track", notNullValue());
    }
}
