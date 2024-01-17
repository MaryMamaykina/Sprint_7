package Scooter.GenerateData.CourierFactory;

import org.apache.commons.lang3.RandomStringUtils;

public class LoginGenerate {
    private final String login = "John Legenda";

    public String getStaticLogin() {
        return login;
    }
    public String getRandomLogin(int numberOfCharacters){
        return RandomStringUtils.randomAlphabetic(numberOfCharacters);
    }

    public String getRandomLogin(){
        return getRandomLogin(7);
    }
}
