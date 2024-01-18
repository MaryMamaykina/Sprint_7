import Scooter.DTO.NewOrder;
import Scooter.GenerateData.OrderFactory.OrderFactory;
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
        scooterAPI.createOrder(OrderFactory.getByColors(color)).then().statusCode(SC_CREATED);
    }

    @Test
    public void parameterizedTestDoesCreateOrderWithDifferentColorReturnTrack() {
        scooterAPI.createOrder(OrderFactory.getByColors(color)).then().body("track", notNullValue());
    }
}
