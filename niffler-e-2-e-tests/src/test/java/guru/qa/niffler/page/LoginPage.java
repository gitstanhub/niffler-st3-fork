package guru.qa.niffler.page;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.db.model.AuthUserEntity;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    @Step
    public LoginPage logInWithUser(UserJson userJson) {
        open();
        fillInForm(userJson);
        clickSignInButton();
        return this;
    }

    @Step
    public LoginPage logInWithUser(AuthUserEntity user) {
        open();
        fillInForm(user);
        clickSignInButton();
        return this;
    }

    @Step
    public void verifyStatsSectionIsVisible() {
        $(".main-content__section-stats").should(visible);
    }

    private void open() {
        Selenide.open("http://127.0.0.1:3000/main");
        $("a[href*='redirect']").click();
    }

    private void fillInForm(AuthUserEntity user) {
        $("input[name='username']").setValue(user.getUsername());
        $("input[name='password']").setValue(user.getPassword());
    }

    private void fillInForm(UserJson userJson) {
        $("input[name='username']").setValue(userJson.getUsername());
        $("input[name='password']").setValue(userJson.getPassword());
    }

    private void clickSignInButton() {
        $("button[type='submit']").click();
    }
}
