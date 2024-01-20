import scooter.generatedata.courierfactory.FirstNameFactory;
import scooter.generatedata.courierfactory.LoginFactory;
import scooter.generatedata.courierfactory.PasswordFactory;
import scooter.staticmethodsandvariables.ScooterAPI;
import org.junit.After;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

public class CreateCourierTest {
    ScooterAPI scooterAPI = new ScooterAPI();


    @Test
    public void doesCreatingCourierWork() {
        String login = new LoginFactory().getRandomLogin();
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.loginCourierInSystem(login, password).then().statusCode(SC_OK);

    }

    @Test
    public void doesCreatingCourierReturnCode200() {
        String login = new LoginFactory().getRandomLogin();
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        scooterAPI.createCourier(login, password, firstName).then().statusCode(SC_CREATED);
    }

    @Test
    public void doesCreatingCourierReturnReturnBodyOkTrue() {
        String login = new LoginFactory().getRandomLogin();
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        scooterAPI.createCourier(login, password, firstName).then().body("ok", equalTo(true));
    }

    @Test
    public void IsCreatingCourierWithTheSameParametersImpossible() {
        String login = new LoginFactory().getRandomLogin();
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.createCourier(login, password, firstName).then().statusCode(SC_CONFLICT);
    }

    @Test
    public void IsCreatingCourierWithTheSameLoginAndFirstNameButAnotherPasswordImpossible() {
        String login = new LoginFactory().getRandomLogin();
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        String anotherPassword= new PasswordFactory().getRandomPassword();
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.createCourier(login, anotherPassword, firstName).then().statusCode(SC_CONFLICT);
    }

    @Test
    public void IsCreatingCourierWithTheSameLoginAndPasswordButAnotherFirstNameImpossible() {
        String login = new LoginFactory().getRandomLogin();
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        String anotherFirstName = new FirstNameFactory().getRandomFirstName();
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.createCourier(login, password, anotherFirstName).then().statusCode(SC_CONFLICT);
    }

    @Test
    public void IsCreatingCourierWithTheSameLoginButAnotherPasswordAndFirstNameImpossible() {
        String login = new LoginFactory().getRandomLogin();
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        String anotherPassword = new PasswordFactory().getRandomPassword();
        String anotherFirstName = new FirstNameFactory().getRandomFirstName();
        scooterAPI.createCourier(login, password, firstName);
        scooterAPI.createCourier(login, anotherPassword, anotherFirstName).then().statusCode(SC_CONFLICT);
    }

    @Test
    public void IsCreatingCourierWithoutLoginImpossible() {
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        scooterAPI.createCourier(null, password, firstName);
        scooterAPI.loginCourierInSystem(null, password).then().statusCode(not(SC_OK));
    }

    @Test
    public void IsCreatingCourierWithoutPasswordImpossible() {
        String login = new LoginFactory().getRandomLogin();
        String firstName = new FirstNameFactory().getRandomFirstName();
        scooterAPI.createCourier(login, null, firstName);
        scooterAPI.loginCourierInSystem(login, null).then().statusCode(not(SC_OK));
    }

    @Test
    public void IsCreatingCourierWithoutLoginAndPasswordImpossible() {
        String firstName = new FirstNameFactory().getRandomFirstName();
        scooterAPI.createCourier(null, null, firstName);
        scooterAPI.loginCourierInSystem(null, null).then().statusCode(not(SC_OK));
    }

    @Test
    public void doesCreatingCourierWithoutLoginReturnMistake() {
        String password = new PasswordFactory().getRandomPassword();
        String firstName = new FirstNameFactory().getRandomFirstName();
        scooterAPI.createCourier(null, password, firstName)
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void doesCreatingCourierWithoutPasswordReturnMistake() {
        String login = new LoginFactory().getRandomLogin();
        String firstName = new FirstNameFactory().getRandomFirstName();
        scooterAPI.createCourier(login, null, firstName).then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void doesCreatingCourierWithoutLoginAndPasswordReturnMistake() {
        String firstName = new FirstNameFactory().getRandomFirstName();
        scooterAPI.createCourier(null, null, firstName).then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void cleanUp() {
        scooterAPI.cleanUp();
    }
}
