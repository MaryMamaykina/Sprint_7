package Scooter.GenerateData.CourierFactory;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;

public class FirstNameFactory {
    @DisplayName("Get random first name with the required number of letters")
    @Description("This method accepts the number of letters that you want to use for create first name. " +
            "The method returns the first name that generate with RandomStringUtils.")
    public String getRandomFirstName(Integer numberOfCharacters){
        return RandomStringUtils.randomAlphabetic(numberOfCharacters);
    }

    @DisplayName("Get random first name with 7 letters")
    @Description("This method returns the first name from 7 letters that generate with RandomStringUtils.")
    public String getRandomFirstName(){
        return getRandomFirstName(7);
    }
}
