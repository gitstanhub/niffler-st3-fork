package guru.qa.niffler.pageobjects.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static guru.qa.niffler.locators.pagelocators.PeoplePageLocators.ADD_FRIEND_BUTTON;

public class PeoplePage {

    @Step
    public PeoplePage verifyInvitationSentRowIsVisible(String invitedUser){
        SelenideElement peopleRow = $$("td").findBy(text(invitedUser)).closest("tr");
        peopleRow.$$("td").findBy(text("Pending invitation")).shouldBe(Condition.visible);

        return this;
    }

    @Step
    public PeoplePage clickAddFriendButtonForUser(String username) {

        $$("tr").filterBy(text(username)).first()
                .find(ADD_FRIEND_BUTTON)
                .scrollIntoView("{behavior: \"smooth\", block: \"center\", inline: \"center\"}")
                .shouldBe(Condition.visible)
                .click();

        return this;
    }

    @Step
    public PeoplePage verifyInvitationIsSentForUser(String username) {
        String invitationSentText = "Pending invitation";

        $$("tr").filterBy(text(username)).first()
                .find(ADD_FRIEND_BUTTON)
                .scrollIntoView("{behavior: \"smooth\", block: \"center\", inline: \"center\"}")
                .shouldHave(Condition.text(invitationSentText));

        return this;
    }

}