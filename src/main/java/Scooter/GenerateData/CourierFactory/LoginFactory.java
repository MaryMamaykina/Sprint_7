package Scooter.GenerateData.CourierFactory;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;

public class LoginFactory {

    @DisplayName("Get random login with the required number of letters")
    @Description("This method accepts the number of letters that you want to use for create login." +
            " The method returns the login that generate with RandomStringUtils.")
    public String getRandomLogin(int numberOfCharacters){
        return RandomStringUtils.randomAlphabetic(numberOfCharacters);
    }

    @DisplayName("Get random login with 7 letters")
    @Description("This method returns the login from 7 letters that generate with RandomStringUtils.")
    public String getRandomLogin(){
        return getRandomLogin(7);
    }
}
