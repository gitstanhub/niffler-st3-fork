package guru.qa.niffler.tests.base;

import guru.qa.niffler.pageobjects.components.HeaderComponent;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.WebTest;
import guru.qa.niffler.pageobjects.pages.*;

@WebTest
public abstract class BaseWebTest {

    protected WelcomePage welcomePage = new WelcomePage();
    protected LoginPage loginPage = new LoginPage();
    protected FriendsPage friendsPage = new FriendsPage();
    protected PeoplePage peoplePage = new PeoplePage();
    protected HeaderComponent headerComponent = new HeaderComponent();
    protected ProfilePage profilePage = new ProfilePage();
    protected RegistrationPage registrationPage = new RegistrationPage();
    protected MainPage mainPage = new MainPage();

    protected static final Config CFG = Config.getInstance();
}