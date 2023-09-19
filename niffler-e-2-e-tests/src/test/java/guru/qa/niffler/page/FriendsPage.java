package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.springframework.util.Assert;

import static com.codeborne.selenide.Selenide.$;

public class FriendsPage {

    private final String REMOVE_FRIEND_BUTTON = "div[data-tooltip-id='remove-friend']";

    @Step
    public FriendsPage verifyRemoveFriendButtonAvailable() {
        $(REMOVE_FRIEND_BUTTON).shouldBe(Condition.visible);
        return this;
    }
}
