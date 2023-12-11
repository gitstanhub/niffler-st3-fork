package guru.qa.niffler.tests.base;

import guru.qa.niffler.pageobjects.components.HeaderComponent;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.WebTest;
import guru.qa.niffler.pageobjects.pages.FriendsPage;
import guru.qa.niffler.pageobjects.pages.LoginPage;
import guru.qa.niffler.pageobjects.pages.PeoplePage;
import guru.qa.niffler.pageobjects.pages.ProfilePage;

@WebTest
public abstract class BaseWebTest {

    protected LoginPage loginPage = new LoginPage();
    protected FriendsPage friendsPage = new FriendsPage();
    protected PeoplePage peoplePage = new PeoplePage();
    protected HeaderComponent headerComponent = new HeaderComponent();
    protected ProfilePage profilePage = new ProfilePage();

    protected static final Config CFG = Config.getInstance();
}