package guru.qa.niffler.pageobjects.components;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static guru.qa.niffler.locators.componentlocators.HeaderComponentLocators.*;

public class HeaderComponent {

    @Step
    public HeaderComponent clickFriendsButton() {
        $(FRIENDS_BUTTON).click();
        return this;
    }

    @Step
    public HeaderComponent clickPeopleButton() {
        $(PEOPLE_BUTTON).click();
        return this;
    }

    @Step
    public HeaderComponent clickProfileButton() {
        $(PROFILE_BUTTON).click();
        return this;
    }
}