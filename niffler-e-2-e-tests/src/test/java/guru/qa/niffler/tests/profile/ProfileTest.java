package guru.qa.niffler.tests.profile;

import com.github.javafaker.Faker;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.jupiter.annotation.DBUser;
import guru.qa.niffler.tests.base.BaseWebTest;
import org.junit.jupiter.api.Test;

import java.io.File;

public class ProfileTest extends BaseWebTest {

    Faker faker = new Faker();

//    @ApiLogin
    @DBUser
            (username = "stanislav_four",
                    password = "123456"
            )
    @Test
    void userProfileDetailsCanBeUpdated(AuthUserEntity user) {
        String firstName = faker.funnyName().name();
        String lastName = faker.name().lastName();
        String currency = "USD";

        loginPage.logInWithUser(user);

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

    @DBUser
    @Test
    void newSpendingCategoryCanBeAdded(AuthUserEntity user) {
        String categoryName = faker.commerce().productName();

        loginPage.logInWithUser(user);

        headerComponent.clickProfileButton();

        profilePage
                .fillInCategoryField(categoryName)
                .clickCreateCategoryButton();

        profilePage.verifyCategoryVisible(categoryName);
    }

    @DBUser
    @Test
    void profilePictureCanBeUpdated(AuthUserEntity user) {
        String resourcePath = getClass().getClassLoader().getResource("uploadimages/profile_picture.png").getFile();
        File profilePictureFile = new File(resourcePath);

        loginPage.logInWithUser(user);

        headerComponent.clickProfileButton();

        profilePage
                .clickProfilePictureArea()
                .uploadProfilePicture(profilePictureFile)
                .clickSubmitButton();

        profilePage.verifyProfileUploadCancelNotVisible();
    }
}
