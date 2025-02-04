package guru.qa.niffler.tests.login;

import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.jupiter.annotation.DBUser;
import guru.qa.niffler.tests.base.BaseWebTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseWebTest {

    @DBUser
    @BeforeEach
    void superTestMethod(AuthUserEntity user) {
        System.out.println("Here's the user from the before each method: " + user.getUsername());
    }

    @DBUser
    @Test
    void mainPageShouldBeVisibleAfterLogin(AuthUserEntity user) {
        loginPage
                .logInWithUser(user)
                .verifyStatsSectionIsVisible();
    }

    @DBUser
    @Test
    void userCanLogOutAfterLogIn(AuthUserEntity user) {
        loginPage
                .logInWithUser(user);

        headerComponent
                .clickLogoutButton();

        welcomePage
                .verifyWelcomeSplashIsDisplayed();
    }
}
