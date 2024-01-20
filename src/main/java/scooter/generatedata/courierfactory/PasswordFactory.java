package scooter.generatedata.courierfactory;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class PasswordFactory {

    @Step("This method accepts the number of letters that you want to use for create password." +
            " The method returns the password that generate with RandomStringUtils.")
    public String getRandomPassword(Integer numberOfCharacters){
        return RandomStringUtils.randomAlphabetic(numberOfCharacters);
    }

    @Step("This method returns the password from 7 letters that generate with RandomStringUtils.")
    public String getRandomPassword(){
        return getRandomPassword(7);
    }
}
