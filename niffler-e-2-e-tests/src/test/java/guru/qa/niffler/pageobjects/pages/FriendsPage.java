package guru.qa.niffler.pageobjects.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FriendsPage {

    private final String REMOVE_FRIEND_BUTTON = "div[data-tooltip-id='remove-friend']";

    @Step
    public FriendsPage verifyRemoveFriendButtonAvailable() {
        $(REMOVE_FRIEND_BUTTON).shouldBe(Condition.visible);
        return this;
    }

    @Step
    public FriendsPage verifyInvitationReceivedFromUser(String senderUser) {
        SelenideElement peopleRow = $$("td").findBy(text(senderUser)).closest("tr");
        peopleRow.$("div[data-tooltip-id='submit-invitation']").shouldBe(Condition.visible);

        return this;
    }
}