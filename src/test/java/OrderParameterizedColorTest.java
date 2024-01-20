import scooter.generatedata.orderfactory.OrderFactory;
import scooter.staticmethodsandvariables.OrderAPI;
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

    OrderAPI orderAPI = new OrderAPI();

    @Parameterized.Parameters(name = "color: {0}")
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
        orderAPI.createOrder(OrderFactory.getByColors(color)).then().statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue());
    }
}
