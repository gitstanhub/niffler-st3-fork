package guru.qa.niffler.tests.friends;


import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.api.model.UserJson;
import guru.qa.niffler.tests.base.BaseWebTest;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static guru.qa.niffler.jupiter.annotation.User.UserType.WITH_FRIENDS;

public class FriendsWebTest extends BaseWebTest {

//    @BeforeEach
//    void doLogin(@User(userType = WITH_FRIENDS) UserJson userForTest) {
//        Selenide.open("http://127.0.0.1:3000/main");
//        $("a[href*='redirect']").click();
//        $("input[name='username']").setValue(userForTest.getUsername());
//        $("input[name='password']").setValue(userForTest.getPassword());
//        $("button[type='submit']").click();
//    }

    @Disabled
    @Test
    @AllureId("101")
    void friendShouldBeDisplayedInTable0(@User(userType = WITH_FRIENDS) UserJson userForTest) throws InterruptedException {
        Thread.sleep(3000);
    }

    @Disabled
    @Test
    @AllureId("102")
    void friendShouldBeDisplayedInTable1(@User(userType = WITH_FRIENDS) UserJson userForTest) throws InterruptedException {
        Thread.sleep(3000);
    }

    @Disabled
    @Test
    @AllureId("103")
    void friendShouldBeDisplayedInTable2(@User(userType = WITH_FRIENDS) UserJson userForTest) throws InterruptedException {
        Thread.sleep(3000);
    }
}
