package guru.qa.niffler.tests.mainpage;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.DBUser;
import guru.qa.niffler.jupiter.annotation.Spend;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.api.model.CurrencyValues;
import guru.qa.niffler.api.model.SpendJson;
import guru.qa.niffler.api.model.UserJson;
import guru.qa.niffler.pageobjects.pages.WelcomePage;
import guru.qa.niffler.tests.base.BaseWebTest;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static guru.qa.niffler.jupiter.annotation.User.UserType.WITH_FRIENDS;

@Isolated
public class SpendingWebTest extends BaseWebTest {
    private static final String user = "dima";

//    @BeforeEach
//    void doLogin(@User(userType = WITH_FRIENDS) UserJson userForTest) {
//        WelcomePage welcomePage = Selenide.open("http://127.0.0.1:3000/main", WelcomePage.class);
//        welcomePage.clickLoginButton();
//        $("input[name='username']").setValue(userForTest.getUsername());
//        $("input[name='password']").setValue(userForTest.getPassword());
//        $("button[type='submit']").click();
//    }

    @Category(
            username = user,
            category = "Рыбалка"
    )
    @Spend(
            username = user,
            description = "Рыбалка на Ладоге",
            category = "Рыбалка",
            amount = 14000.00,
            currency = CurrencyValues.RUB
    )
    @Test
    @AllureId("100")
    @Disabled
    void spendingShouldBeDeletedAfterDeleteAction(SpendJson createdSpend) {

        $(".spendings__content tbody")
                .$$("tr")
                .find(text(createdSpend.getDescription()))
                .$$("td")
                .first()
                .$("td")
                .scrollTo()
                .click();

        Allure.step(
                "Delete spending",
                () -> $(byText("Delete selected")).click())
        ;
        Allure.step(
                "Check spendings",
                () -> $(".spendings__content tbody")
                        .$$("tr")
                        .shouldHave(size(0))
        );
    }

    @DBUser
    @Test
    @AllureId("101")
    void spendingCannotBeAddedWithoutCategory(AuthUserEntity user) {

        loginPage.logInWithUser(user);

        mainPage.clickAddNewSpendingButton();

        mainPage.verifyCategoryNotSelectedErrorIsDisplayed();
    }
}