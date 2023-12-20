package guru.qa.niffler.pageobjects.pages;


import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static guru.qa.niffler.locators.pagelocators.RegistrationPageLocators.*;

public class RegistrationPage {

    @Step
    public RegistrationPage fillInUsernameField(String username) {
        $(USERNAME_FIELD).setValue(username);

        return this;
    }

    @Step
    public RegistrationPage fillInPasswordField(String password) {
        $(PASSWORD_FIELD).setValue(password);

        return this;
    }

    @Step
    public RegistrationPage fillInPasswordConfirmField(String passwordConfirmation) {
        $(PASSWORD_CONFIRM_FIELD).setValue(passwordConfirmation);

        return this;
    }

    @Step
    public RegistrationPage clickSignUpButton() {
        $(SIGNUP_BUTTON).click();

        return this;
    }

    @Step
    public void verifyRegistrationSuccessfulMessageIsDisplayed() {
        String registrationSuccessfulMessage = "Congratulations! You've registered!";

        $(byText(registrationSuccessfulMessage)).shouldBe(Condition.visible);
    }

    @Step
    public void verifyNotMatchingPasswordValidationErrorIsDisplayed() {
        String passwordValidationErrorMessage = "Passwords should be equal";

        $(byText(passwordValidationErrorMessage)).shouldBe(Condition.visible);
    }

    @Step
    public void verifyWrongLengthPasswordValidationErrorIsDisplayed() {
        String passwordValidationErrorMessage = "Allowed password length should be from 3 to 12 characters";

        $(byText(passwordValidationErrorMessage)).shouldBe(Condition.visible);
    }

    @Step
    public void verifyUserAlreadyExistsValidationErrorIsDisplayed(String username) {
        String usernameAlreadyExistsErrorMessage = String.format("Username `%s` already exists", username);

        $(byText(usernameAlreadyExistsErrorMessage)).shouldBe(Condition.visible);
    }
}
