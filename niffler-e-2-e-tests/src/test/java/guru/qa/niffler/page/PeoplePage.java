package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;

public class PeoplePage {

    public PeoplePage verifyInvitationSentRowIsVisible(String invitedUser){
        SelenideElement peopleRow = $$("td").findBy(text(invitedUser)).closest("tr");
        peopleRow.$$("td").findBy(text("Pending invitation")).shouldBe(Condition.visible);

        return this;
    }

}
