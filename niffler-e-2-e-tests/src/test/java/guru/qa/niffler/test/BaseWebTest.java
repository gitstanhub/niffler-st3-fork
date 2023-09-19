package guru.qa.niffler.test;

import guru.qa.niffler.components.HeaderComponent;
import guru.qa.niffler.jupiter.annotation.WebTest;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.LoginPage;

@WebTest
public abstract class BaseWebTest {

    protected LoginPage loginPage = new LoginPage();
    protected FriendsPage friendsPage = new FriendsPage();
    protected HeaderComponent headerComponent = new HeaderComponent();
}
