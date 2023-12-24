package guru.qa.niffler.pageobjects.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static guru.qa.niffler.locators.pagelocators.MainPageLocators.ADD_NEW_SPENDING_BUTTON;

public class MainPage {

    @Step
    public MainPage clickAddNewSpendingButton() {
        $(ADD_NEW_SPENDING_BUTTON).click();

        return this;
    }

    @Step
    public void verifyCategoryNotSelectedErrorIsDisplayed() {
        String categoryNotSelectedErrorMessage = "Category is required";

        $(byText(categoryNotSelectedErrorMessage)).shouldBe(Condition.visible);
    }
}
