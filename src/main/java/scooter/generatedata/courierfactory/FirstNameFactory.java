package scooter.generatedata.courierfactory;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class FirstNameFactory {
    @Step("This method accepts the number of letters that you want to use for create first name. " +
            "The method returns the first name that generate with RandomStringUtils.")
    public String getRandomFirstName(Integer numberOfCharacters){
        return RandomStringUtils.randomAlphabetic(numberOfCharacters);
    }

    @Step("This method returns the first name from 7 letters that generate with RandomStringUtils.")
    public String getRandomFirstName(){
        return getRandomFirstName(7);
    }
}
