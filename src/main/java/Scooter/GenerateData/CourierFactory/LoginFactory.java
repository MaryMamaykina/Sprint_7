package Scooter.GenerateData.CourierFactory;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class LoginFactory {

    @Step("This method accepts the number of letters that you want to use for create login." +
            " The method returns the login that generate with RandomStringUtils.")
    public String getRandomLogin(int numberOfCharacters){
        return RandomStringUtils.randomAlphabetic(numberOfCharacters);
    }

    @Step("This method returns the login from 7 letters that generate with RandomStringUtils.")
    public String getRandomLogin(){
        return getRandomLogin(7);
    }
}
