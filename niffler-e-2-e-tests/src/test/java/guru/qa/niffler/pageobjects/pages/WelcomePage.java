package guru.qa.niffler.pageobjects.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static guru.qa.niffler.locators.pagelocators.WelcomePageLocators.LOGIN_BUTTON;
import static guru.qa.niffler.locators.pagelocators.WelcomePageLocators.REGISTER_BUTTON;

public class WelcomePage {

    @Step
    public WelcomePage openWelcomePage() {
            Selenide.open("http://127.0.0.1:3000/main");

            return this;
    }

    @Step
    public WelcomePage clickLoginButton() {
        $(LOGIN_BUTTON).click();

        return this;
    }

    @Step
    public WelcomePage clickRegisterButton() {
        $(REGISTER_BUTTON).click();

        return this;
    }

    @Step
    public void verifyWelcomeSplashIsDisplayed() {
        $(byText("Welcome to magic journey with Niffler. The coin keeper")).shouldBe(Condition.visible);
    }
}
