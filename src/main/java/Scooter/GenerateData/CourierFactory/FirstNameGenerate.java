package Scooter.GenerateData.CourierFactory;

import org.apache.commons.lang3.RandomStringUtils;

public class FirstNameGenerate {
    private final String firstName = "John";

    public String getStaticFirstName() {
        return firstName;
    }

    public String getRandomFirstName(Integer numberOfCharacters){
        return RandomStringUtils.randomAlphabetic(numberOfCharacters);
    }

    public String getRandomFirstName(){
        return getRandomFirstName(7);
    }
}
