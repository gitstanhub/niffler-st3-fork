package guru.qa.niffler.tests.profile;

import com.github.javafaker.Faker;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.jupiter.annotation.DBUser;
import guru.qa.niffler.pageobjects.pages.ProfilePage;
import guru.qa.niffler.tests.base.BaseWebTest;
import org.junit.jupiter.api.Test;

public class ProfileTest extends BaseWebTest {

    @DBUser
            (username = "stanislav_three",
                    password = "123456"
            )
    @Test
    void userProfileDetailsCanBeUpdated(AuthUserEntity user) {
        Faker faker = new Faker();

        String firstName = faker.funnyName().name();
        String lastName = faker.name().lastName();
        String currency = "USD";

        loginPage
                .logInWithUser(user);

        headerComponent.clickProfileButton();

        profilePage
                .fillInFirstNameField(firstName)
                .fillInSurnameField(lastName)
                .selectCurrency(currency)
                .clickSubmitButton();

        profilePage.verifyNameFieldContains(firstName);
        profilePage.verifySurnameFieldContains(lastName);
        profilePage.verifyCurrencyIsSelected(currency);
    }
}
