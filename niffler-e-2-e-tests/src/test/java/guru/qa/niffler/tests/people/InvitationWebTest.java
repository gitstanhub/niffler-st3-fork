package guru.qa.niffler.tests.people;

import guru.qa.niffler.api.model.UserJson;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.jupiter.annotation.DBUser;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.tests.base.BaseWebTest;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;
import static guru.qa.niffler.jupiter.annotation.User.UserType.WITH_FRIENDS;
import static guru.qa.niffler.locators.pagelocators.PeoplePageLocators.ADD_FRIEND_BUTTON;

public class InvitationWebTest extends BaseWebTest {

    @DBUser
    @Test
    @AllureId("113")
    void userCanSendFriendInvitation(@User(userType = WITH_FRIENDS) UserJson userWithFriends, AuthUserEntity user) {

        loginPage.logInWithUser(userWithFriends);

        System.out.println("USERNAME IS :" + user.getUsername());

        headerComponent.clickPeopleButton();

        System.out.println("USERNAME IS :" + user.getUsername());

        peoplePage
                .clickAddFriendButtonForUser(user.getUsername());
//
//
//        peoplePage
//                .verifyInvitationIsSentForUser(user.getUsername());


        System.out.println("test");
    }
}
