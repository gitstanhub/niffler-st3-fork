package guru.qa.niffler.pageobjects.pages;


import io.qameta.allure.Step;

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
}
