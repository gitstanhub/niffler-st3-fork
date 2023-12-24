package guru.qa.niffler.tests.people;

import guru.qa.niffler.api.model.UserJson;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.jupiter.annotation.DBUser;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.tests.base.BaseWebTest;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Test;

import static guru.qa.niffler.jupiter.annotation.User.UserType.WITH_FRIENDS;

public class InvitationWebTest extends BaseWebTest {

    @DBUser
    @Test
    @AllureId("113")
    void userCanSendFriendInvitation(@User(userType = WITH_FRIENDS) UserJson userWithFriends, AuthUserEntity user) {

        loginPage.logInWithUser(userWithFriends);
        headerComponent.clickPeopleButton();

        peoplePage
                .clickAddFriendButtonForUser(user.getUsername())
                .verifyInvitationIsSentForUser(user.getUsername());
    }
}
