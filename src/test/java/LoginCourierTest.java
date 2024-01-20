import scooter.generatedata.courierfactory.FirstNameFactory;
import scooter.generatedata.courierfactory.LoginFactory;
import scooter.generatedata.courierfactory.PasswordFactory;
import scooter.staticmethodsandvariables.CourierAPI;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {

    CourierAPI courierAPI = new CourierAPI();
    String login = new LoginFactory().getRandomLogin();
    String password = new PasswordFactory().getRandomPassword();
    String firstName = new FirstNameFactory().getRandomFirstName();

    @Test
    public void doesLoginCourierWorkAndReturnID() {
        courierAPI.createCourier(login, password, firstName);
        courierAPI.loginCourierInSystem(login, password).then().statusCode(SC_OK)
                .and()
                .body("id", notNullValue());
    }

    @Test
    public void doesLoginCourierThatNotExistReturnError() {
        courierAPI.loginCourierInSystem(login, password).then().statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void isLoginCourierWithoutLoginImpossibleAndReturnThereIsNotEnoughDataToLogIn() {
        courierAPI.createCourier(login, password, firstName);
        courierAPI.loginCourierInSystem(null, password).then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void isLoginCourierWithoutPasswordImpossibleAndReturnThereIsNotEnoughDataToLogIn() {
        courierAPI.createCourier(login, password, firstName);
        courierAPI.loginCourierInSystem(login, null).then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void isLoginCourierWithoutLoginAndPasswordImpossibleAndReturnThereIsNotEnoughDataToLogIn() {
        courierAPI.createCourier(login, password, firstName);
        courierAPI.loginCourierInSystem(null, null).then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void doesLoginCourierWithWrongLoginReturnAccountWasNotFound() {
        courierAPI.createCourier(login, password, firstName);
        String wronglogin = new LoginFactory().getRandomLogin();
        courierAPI.loginCourierInSystem(wronglogin, password).then().statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void doesLoginCourierWithWrongPasswordReturnAccountWasNotFound() {
        courierAPI.createCourier(login, password, firstName);
        String wrongpassword = new PasswordFactory().getRandomPassword();
        courierAPI.loginCourierInSystem(login, wrongpassword).then().statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void cleanUp() {
        courierAPI.cleanUp();
    }


}
