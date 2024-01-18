package Scooter.GenerateData.CourierFactory;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;

public class PasswordFactory {
    @DisplayName("Get random password with the required number of letters")
    @Description("This method accepts the number of letters that you want to use for create password." +
            " The method returns the password that generate with RandomStringUtils.")
    public String getRandomPassword(Integer numberOfCharacters){
        return RandomStringUtils.randomAlphabetic(numberOfCharacters);
    }

    @DisplayName("Get random password with 7 letters")
    @Description("This method returns the password from 7 letters that generate with RandomStringUtils.")
    public String getRandomPassword(){
        return getRandomPassword(7);
    }
}
