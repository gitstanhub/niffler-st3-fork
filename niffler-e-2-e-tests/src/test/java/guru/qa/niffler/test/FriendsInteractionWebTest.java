package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Test;

import static guru.qa.niffler.jupiter.annotation.User.UserType.*;

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
    void friendsInvitationSentIsAvailable(@User(userType = INVITATION_SENT) UserJson userWithSentInvite, @User(userType = INVITATION_RECEIVED) UserJson userWithReceivedInvite) {
        loginPage.logInWithUser(userWithSentInvite);
        headerComponent.clickPeopleButton();
        peoplePage.verifyInvitationSentRowIsVisible(userWithReceivedInvite.getUsername());
    }

    @Test
    @AllureId("106")
    void friendsInvitationReceivedIsAvailable(@User(userType = INVITATION_SENT) UserJson userWithSentInvite, @User(userType = INVITATION_RECEIVED) UserJson userWithReceivedInvite) {
        loginPage.logInWithUser(userWithReceivedInvite);
        headerComponent.clickFriendsButton();
        friendsPage.verifyInvitationReceivedFromUser(userWithSentInvite.getUsername());
    }
}
