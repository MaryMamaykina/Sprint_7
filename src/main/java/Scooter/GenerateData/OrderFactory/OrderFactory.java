package Scooter.GenerateData.OrderFactory;
import Scooter.DTO.NewOrder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderFactory {
    public static NewOrder getByColors(List<String> colors){
        return new NewOrder("Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "Сокольники",
                "+7 800 355 35 35",
                1,
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "Saske, come back to Konoha",
                colors);

    }
}
