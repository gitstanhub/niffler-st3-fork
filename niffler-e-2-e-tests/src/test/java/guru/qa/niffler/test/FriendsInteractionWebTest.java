package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Test;

import static guru.qa.niffler.jupiter.annotation.User.UserType.WITH_FRIENDS;

public class FriendsInteractionWebTest extends BaseWebTest {

    @Test
    @AllureId("104")
    void existingFriendsAreAvailable(@User(userType = WITH_FRIENDS) UserJson userWithFriends) {
        loginPage.logInWithUser(userWithFriends);
        headerComponent.clickFriendsButton();
        friendsPage.verifyRemoveFriendButtonAvailable();
    }

    @Test
    @AllureId("105")
    void friendsInvitationSentIsAvailable(@User(userType = WITH_FRIENDS) UserJson userWithFriends) {

    }

    @Test
    @AllureId("106")
    void friendsInvitationReceivedIsAvailable(@User(userType = WITH_FRIENDS) UserJson userWithFriends) {

    }
}
