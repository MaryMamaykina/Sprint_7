import scooter.generatedata.courierfactory.FirstNameFactory;
import scooter.generatedata.courierfactory.LoginFactory;
import scooter.generatedata.courierfactory.PasswordFactory;
import scooter.staticmethodsandvariables.CourierAPI;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

public class CreateCourierTest {
    CourierAPI courierAPI = new CourierAPI();


    @Test
    public void doesCreatingCourierWorkReturnCode200AndBodyOkTrue() {
        String login = new LoginFactory().getRandomLogin();
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        courierAPI.createCourier(login, password, firstName).then().statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        courierAPI.loginCourierInSystem(login, password).then().statusCode(SC_OK);

    }

    @Test
    public void IsCreatingCourierWithTheSameParametersImpossible() {
        String login = new LoginFactory().getRandomLogin();
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        courierAPI.createCourier(login, password, firstName);
        courierAPI.createCourier(login, password, firstName).then().statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    public void IsCreatingCourierWithTheSameLoginAndFirstNameButAnotherPasswordImpossible() {
        String login = new LoginFactory().getRandomLogin();
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        String anotherPassword = new PasswordFactory().getRandomPassword();
        courierAPI.createCourier(login, password, firstName);
        courierAPI.createCourier(login, anotherPassword, firstName).then().statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    public void IsCreatingCourierWithTheSameLoginAndPasswordButAnotherFirstNameImpossible() {
        String login = new LoginFactory().getRandomLogin();
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        String anotherFirstName = new FirstNameFactory().getRandomFirstName();
        courierAPI.createCourier(login, password, firstName);
        courierAPI.createCourier(login, password, anotherFirstName).then().statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    public void IsCreatingCourierWithTheSameLoginButAnotherPasswordAndFirstNameImpossible() {
        String login = new LoginFactory().getRandomLogin();
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        String anotherPassword = new PasswordFactory().getRandomPassword();
        String anotherFirstName = new FirstNameFactory().getRandomFirstName();
        courierAPI.createCourier(login, password, firstName);
        courierAPI.createCourier(login, anotherPassword, anotherFirstName).then().statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    public void IsCreatingCourierWithoutLoginImpossible() {
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        courierAPI.createCourier(null, password, firstName)
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        courierAPI.loginCourierInSystem(null, password).then().statusCode(not(SC_OK));
    }

    @Test
    public void IsCreatingCourierWithoutPasswordImpossible() {
        String login = new LoginFactory().getRandomLogin();
        String firstName = new FirstNameFactory().getRandomFirstName();
        courierAPI.createCourier(login, null, firstName)
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        courierAPI.loginCourierInSystem(login, null).then().statusCode(not(SC_OK));
    }

    @Test
    public void IsCreatingCourierWithoutLoginAndPasswordImpossible() {
        String firstName = new FirstNameFactory().getRandomFirstName();
        courierAPI.createCourier(null, null, firstName)
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        courierAPI.loginCourierInSystem(null, null).then().statusCode(not(SC_OK));
    }

    @After
    public void cleanUp() {
        courierAPI.cleanUp();
    }
}
