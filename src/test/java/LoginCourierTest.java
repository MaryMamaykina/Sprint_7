import Scooter.GenerateData.CourierFactory.FirstNameGenerate;
import Scooter.GenerateData.CourierFactory.LoginGenerate;
import Scooter.GenerateData.CourierFactory.PasswordGenerate;
import Scooter.StaticMethodsAndVariables.ScooterAPI;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {

    ScooterAPI scooterAPI = new ScooterAPI();
    String login = new LoginGenerate().getRandomLogin();
    String password = new PasswordGenerate().getRandomPassword();
    String firstName = new FirstNameGenerate().getRandomFirstName();

    @Test
    public void doesLoginCourierWork() {
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.loginCourierInSystem(login, password).then().statusCode(SC_OK);
    }

    @Test
    public void doesLoginCourierReturnID() {
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.loginCourierInSystem(login, password).then().body("id", notNullValue());
    }

    @Test
    public void doesLoginCourierThatNotExistReturnError() {
        scooterAPI.loginCourierInSystem(login, password).then().statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void isLoginCourierWithoutLoginImpossible() {
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.loginCourierInSystem(null, password).then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void isLoginCourierWithoutPasswordImpossible() {
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.loginCourierInSystem(login, null).then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void isLoginCourierWithoutLoginAndPasswordImpossible() {
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.loginCourierInSystem(null, null).then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void doesLoginCourierWithoutLoginReturnThereIsNotEnoughDataToLogIn() {
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.loginCourierInSystem(null, password).then()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void doesLoginCourierWithoutPasswordReturnThereIsNotEnoughDataToLogIn() {
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.loginCourierInSystem(login, null).then()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void doesLoginCourierWithoutLoginAndPasswordReturnThereIsNotEnoughDataToLogIn() {
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.loginCourierInSystem(null, null).then()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void doesLoginCourierWithWrongLoginReturnAccountWasNotFound() {
        scooterAPI.createCourier(login, password, firstName);
        String wronglogin = new LoginGenerate().getRandomLogin();
        scooterAPI.loginCourierInSystem(wronglogin, password).then().statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void doesLoginCourierWithWrongPasswordReturnAccountWasNotFound() {
        scooterAPI.createCourier(login, password, firstName);
        String wrongpassword = new PasswordGenerate().getRandomPassword();
        scooterAPI.loginCourierInSystem(login, wrongpassword).then().statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void cleanUp() {
        scooterAPI.cleanUp();
    }


}
