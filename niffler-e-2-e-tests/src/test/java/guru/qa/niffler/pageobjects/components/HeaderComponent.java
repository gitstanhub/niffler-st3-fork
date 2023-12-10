package guru.qa.niffler.pageobjects.components;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class HeaderComponent {

    private final String FRIENDS_BUTTON = "a[href='/friends']";
    private final String PEOPLE_BUTTON = "a[href='/people']";


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
}