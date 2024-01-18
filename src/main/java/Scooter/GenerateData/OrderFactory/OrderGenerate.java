package Scooter.GenerateData.OrderFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderGenerate {
    private final String firstName = "Naruto";
    private final String lastName = "Uchiha";
    private final String address = "Konoha, 142 apt.";
    private final String metroStation = "Сокольники";
    private final String phone = "+7 800 355 35 35";
    private final int rentTime = 1;
    private final String deliveryDate = LocalDate.now().format( DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    private final String comment = "Saske, come back to Konoha";
    private String[] color = {"BLACK"};

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public String[] getColor() {
        return color;
    }
}
