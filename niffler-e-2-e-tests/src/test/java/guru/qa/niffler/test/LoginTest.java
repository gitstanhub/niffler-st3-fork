package guru.qa.niffler.test;

import guru.qa.niffler.db.model.AuthUserEntity;
import guru.qa.niffler.jupiter.annotation.DBUser;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseWebTest {

    @DBUser(
            username = "bear_ber",
            password = "12345"
    )

    @Test
    void mainPageShouldBeVisibleAfterLogin(AuthUserEntity user) {
        loginPage
                .logInWithUser(user)
                .verifyStatsSectionIsVisible();
    }
}