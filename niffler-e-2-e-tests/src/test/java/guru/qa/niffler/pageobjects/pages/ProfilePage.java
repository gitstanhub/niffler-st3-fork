package guru.qa.niffler.pageobjects.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static guru.qa.niffler.locators.pagelocators.ProfilePageLocators.*;

public class ProfilePage {

    @Step
    public ProfilePage fillInFirstNameField(String firstName) {
        $(FIRST_NAME_FIELD).setValue(firstName);

        return this;
    }

    @Step
    public ProfilePage fillInSurnameField(String surname) {
        $(SURNAME_FIELD).setValue(surname);

        return this;
    }

    @Step
    public ProfilePage selectCurrency(String currency) {
        $(CURRENCY_DROPDOWN).click();
        $(CURRENCY_DROPDOWN).selectOptionByValue(currency);

        return this;
    }

    @Step
    public ProfilePage clickSubmitButton() {
        $(SUBMIT_BUTTON).click();

        return this;
    }

    @Step
    public ProfilePage fillInCategoryField(String category) {
        $(CATEGORY_FIELD).setValue(category);

        return this;
    }

    @Step
    public ProfilePage clickCreateCategoryButton() {
        $(CREATE_CATEGORY_BUTTON).click();

        return this;
    }
}
