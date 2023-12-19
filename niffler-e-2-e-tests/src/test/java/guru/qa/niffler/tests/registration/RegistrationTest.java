package guru.qa.niffler.tests.registration;

import com.github.javafaker.Faker;
import guru.qa.niffler.tests.base.BaseWebTest;
import org.junit.jupiter.api.Test;

public class RegistrationTest extends BaseWebTest {

    Faker faker = new Faker();

    @Test
    void userCanBeRegistered() {
        String username = faker.name().username();
        String password = faker.internet().password(3, 12);

        welcomePage
                .openWelcomePage()
                .clickRegisterButton();

        registrationPage
                .fillInUsernameField(username)
                .fillInPasswordField(password)
                .fillInPasswordConfirmField(password)
                .clickSignUpButton();

        System.out.println("test");

    }

}
