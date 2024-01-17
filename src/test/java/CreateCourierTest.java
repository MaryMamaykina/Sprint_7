import Scooter.GenerateData.CourierFactory.FirstNameGenerate;
import Scooter.GenerateData.CourierFactory.LoginGenerate;
import Scooter.GenerateData.CourierFactory.PasswordGenerate;
import Scooter.StaticMethodsAndVariables.ScooterAPI;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {
    ScooterAPI scooterAPI = new ScooterAPI();


    @Test
    public void doesCreatingCourierWork() {
        String login = new LoginGenerate().getRandomLogin();
        String password = new PasswordGenerate().getRandomPassword();
        String firstName = new FirstNameGenerate().getRandomFirstName();
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.loginCourierInSystem(login, password).then().statusCode(SC_OK);

    }

    @Test
    public void doesCreatingCourierReturnCode200() {
        String login = new LoginGenerate().getRandomLogin();
        String password = new PasswordGenerate().getRandomPassword();
        String firstName = new FirstNameGenerate().getRandomFirstName();
        scooterAPI.createCourier(login, password, firstName).then().statusCode(SC_CREATED);
    }

    @Test
    public void doesCreatingCourierReturnReturnBodyOkTrue() {
        String login = new LoginGenerate().getRandomLogin();
        String password = new PasswordGenerate().getRandomPassword();
        String firstName = new FirstNameGenerate().getRandomFirstName();
        scooterAPI.createCourier(login, password, firstName).then().body("ok", equalTo(true));
    }

    @Test
    public void IsCreatingCourierWithTheSameParametersImpossible() {
        String login = new LoginGenerate().getRandomLogin();
        String password = new PasswordGenerate().getRandomPassword();
        String firstName = new FirstNameGenerate().getRandomFirstName();
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.createCourier(login, password, firstName).then().statusCode(SC_CONFLICT);
    }

    @Test
    public void IsCreatingCourierWithTheSameLoginAndFirstNameButAnotherPasswordImpossible() {
        String login = new LoginGenerate().getRandomLogin();
        String password = new PasswordGenerate().getRandomPassword();
        String firstName = new FirstNameGenerate().getRandomFirstName();
        String anotherPassword= new PasswordGenerate().getRandomPassword();
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.createCourier(login, anotherPassword, firstName).then().statusCode(SC_CONFLICT);
    }

    @Test
    public void IsCreatingCourierWithTheSameLoginAndPasswordButAnotherFirstNameImpossible() {
        String login = new LoginGenerate().getRandomLogin();
        String password = new PasswordGenerate().getRandomPassword();
        String firstName = new FirstNameGenerate().getRandomFirstName();
        String anotherFirstName = new FirstNameGenerate().getRandomFirstName();
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.createCourier(login, password, anotherFirstName).then().statusCode(SC_CONFLICT);
    }

    @Test
    public void IsCreatingCourierWithTheSameLoginButAnotherPasswordAndFirstNameImpossible() {
        String login = new LoginGenerate().getRandomLogin();
        String password = new PasswordGenerate().getRandomPassword();
        String firstName = new FirstNameGenerate().getRandomFirstName();
        String anotherPassword = new PasswordGenerate().getRandomPassword();
        String anotherFirstName = new FirstNameGenerate().getRandomFirstName();
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.createCourier(login, anotherPassword, anotherFirstName).then().statusCode(SC_CONFLICT);
    }

    @Test
    public void IsCreatingCourierWithoutLoginImpossible() {
        String password = new PasswordGenerate().getRandomPassword();
        String firstName = new FirstNameGenerate().getRandomFirstName();
        scooterAPI.createCourier(null, password, firstName);
        scooterAPI.loginCourierInSystem(null, password).then().statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void IsCreatingCourierWithoutPasswordImpossible() {
        String login = new LoginGenerate().getRandomLogin();
        String firstName = new FirstNameGenerate().getRandomFirstName();
        scooterAPI.createCourier(login, null, firstName);
        scooterAPI.loginCourierInSystem(login, null).then().statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void IsCreatingCourierWithoutLoginAndPasswordImpossible() {
        String firstName = new FirstNameGenerate().getRandomFirstName();
        scooterAPI.createCourier(null, null, firstName);
        scooterAPI.loginCourierInSystem(null, null).then().statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void doesCreatingCourierWithoutLoginReturnMistake() {
        String password = new PasswordGenerate().getRandomPassword();
        String firstName = new FirstNameGenerate().getRandomFirstName();
        scooterAPI.createCourier(null, password, firstName)
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void doesCreatingCourierWithoutPasswordReturnMistake() {
        String login = new LoginGenerate().getRandomLogin();
        String firstName = new FirstNameGenerate().getRandomFirstName();
        scooterAPI.createCourier(login, null, firstName).then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void doesCreatingCourierWithoutLoginAndPasswordReturnMistake() {
        String firstName = new FirstNameGenerate().getRandomFirstName();
        scooterAPI.createCourier(null, null, firstName).then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void cleanUp() {
        scooterAPI.cleanUp();
    }
}
