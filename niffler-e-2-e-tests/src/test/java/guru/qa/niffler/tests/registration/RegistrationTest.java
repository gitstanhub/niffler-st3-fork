package guru.qa.niffler.tests.registration;

import com.github.javafaker.Faker;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.jupiter.annotation.DBUser;
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

        registrationPage.verifyRegistrationSuccessfulMessageIsDisplayed();
    }

    @Test
    void userCannotRegisterWithNotMatchingPassword() {
        String username = faker.name().username();
        String firstPassword = faker.internet().password(3, 12);
        String secondPassword = faker.internet().password(5, 10);

        welcomePage
                .openWelcomePage()
                .clickRegisterButton();

        registrationPage
                .fillInUsernameField(username)
                .fillInPasswordField(firstPassword)
                .fillInPasswordConfirmField(secondPassword)
                .clickSignUpButton();

        registrationPage.verifyNotMatchingPasswordValidationErrorIsDisplayed();
    }

    @Test
    void userCannotRegisterWithTooLongPassword() {
        String username = faker.name().username();
        String password = faker.internet().password(13, 15);

        welcomePage
                .openWelcomePage()
                .clickRegisterButton();

        registrationPage
                .fillInUsernameField(username)
                .fillInPasswordField(password)
                .fillInPasswordConfirmField(password)
                .clickSignUpButton();

        registrationPage.verifyWrongLengthPasswordValidationErrorIsDisplayed();
    }

    @DBUser
    @Test
    void userCannotRegisterWithAlreadyExistingUsername(AuthUserEntity user) {
        String username = user.getUsername();
        String password = faker.internet().password(3, 12);

        welcomePage
                .openWelcomePage()
                .clickRegisterButton();

        registrationPage
                .fillInUsernameField(username)
                .fillInPasswordField(password)
                .fillInPasswordConfirmField(password)
                .clickSignUpButton();

        registrationPage.verifyUserAlreadyExistsValidationErrorIsDisplayed(username);
    }

}
