package Scooter.GenerateData.CourierFactory;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class PasswordGenerate {
    private final String password = "109567";

    public String getStaticPassword() {
        return password;
    }
    public String getRandomPassword(Integer numberOfCharacters){
        return RandomStringUtils.randomAlphabetic(numberOfCharacters);
    }

    public String getRandomPassword(){
        return getRandomPassword(7);
    }
}
