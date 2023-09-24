package guru.qa.niffler.test;

import guru.qa.niffler.components.HeaderComponent;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.WebTest;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.PeoplePage;

@WebTest
public abstract class BaseWebTest {

    protected LoginPage loginPage = new LoginPage();
    protected FriendsPage friendsPage = new FriendsPage();
    protected PeoplePage peoplePage = new PeoplePage();
    protected HeaderComponent headerComponent = new HeaderComponent();

    protected static final Config CFG = Config.getInstance();
}
