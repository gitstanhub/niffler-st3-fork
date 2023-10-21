package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.db.dao.AuthDAO;
import guru.qa.niffler.db.dao.UserDataDAO;
import guru.qa.niffler.db.model.Authority;
import guru.qa.niffler.db.model.AuthAuthorityEntity;
import guru.qa.niffler.db.model.AuthUserEntity;
import guru.qa.niffler.jupiter.annotation.Dao;
import guru.qa.niffler.jupiter.extension.DaoExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@ExtendWith(DaoExtension.class)
public class LoginTest extends BaseWebTest {

    @Dao
    private AuthDAO authDAO;
    @Dao
    private UserDataDAO userDataDAO;
    private AuthUserEntity user;

    @BeforeEach
    void createUser() {
        user = new AuthUserEntity();
        user.setUsername("valentin_2");
        user.setPassword("12345");
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setAuthorities(Arrays.stream(Authority.values())
                .map(a -> {
                    AuthAuthorityEntity ae = new AuthAuthorityEntity();
                    ae.setAuthority(a);
                    return ae;
                }).toList());
        authDAO.createUserInAuth(user);
        userDataDAO.createUserInUserData(user);
    }

    @AfterEach
    void deleteUser() {
        userDataDAO.deleteUserByIdInUserData(user.getId());
        authDAO.deleteUserByIdInAuth(user.getId());

    }

    @Test
    void mainPageShouldBeVisibleAfterLogin() {
        Selenide.open("http://127.0.0.1:3000/main");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue(user.getUsername());
        $("input[name='password']").setValue(user.getPassword());
        $("button[type='submit']").click();
        $(".main-content__section-stats").should(visible);
    }
}
