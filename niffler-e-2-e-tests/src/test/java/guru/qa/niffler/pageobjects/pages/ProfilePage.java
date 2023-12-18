package guru.qa.niffler.pageobjects.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
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
        $$(CURRENCY_DROPDOWN_OPTION).findBy(Condition.text(currency)).click();

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

    @Step
    public void verifyNameFieldContains(String firstName) {
        $(FIRST_NAME_FIELD).getValue().contains(firstName);
    }

    @Step
    public void verifySurnameFieldContains(String lastName) {
        $(SURNAME_FIELD).getValue().contains(lastName);
    }

    @Step
    public void verifyCurrencyIsSelected(String currency) {
        $(CURRENCY_DROPDOWN).shouldHave(Condition.exactText(currency));
    }

    @Step
    public void verifyCategoryVisible(String categoryName) {
        $$(CATEGORY_ITEM).findBy(Condition.exactText(categoryName)).shouldBe(Condition.visible);
    }

    @Step
    public ProfilePage clickProfilePictureArea() {
        $(PROFILE_PICTURE_AREA).click();

        return this;
    }

    @Step
    public ProfilePage uploadProfilePicture(File profilePictureFile) {
        $(PROFILE_PICTURE_UPLOAD_BUTTON).uploadFile(profilePictureFile);

        return this;
    }
}
