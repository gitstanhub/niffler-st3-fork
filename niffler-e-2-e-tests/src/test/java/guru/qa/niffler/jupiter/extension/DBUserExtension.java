package guru.qa.niffler.jupiter.extension;

import com.github.javafaker.Faker;

import guru.qa.niffler.db.dao.*;
import guru.qa.niffler.db.model.auth.AuthAuthorityEntity;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.db.model.auth.Authority;
import guru.qa.niffler.db.model.userdata.CurrencyValues;
import guru.qa.niffler.db.model.userdata.UserDataUserEntity;

import guru.qa.niffler.jupiter.annotation.DBUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.*;

import java.util.Arrays;
import java.util.Objects;

public class DBUserExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CategoryExtension.class);
    private static final String USER_KEY = "context_user_";

    AuthUserEntity authUserEntity;
    UserDataUserEntity userDataUserEntity;

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        String authUserEntityKey = USER_KEY + "authUserEntity";
        String userDataUserEntityKey = USER_KEY + "userDataUserEntity";

        DBUser dbUserAnnotation = getDbUserAnnotation(extensionContext);
        if (dbUserAnnotation != null) {
            authUserEntity = createAuthUserEntity(dbUserAnnotation);
            userDataUserEntity = createUserDataUserEntity(authUserEntity);

            persistUserEntity(authUserEntity, userDataUserEntity);
            extensionContext.getStore(NAMESPACE).put(authUserEntityKey, authUserEntity);
            extensionContext.getStore(NAMESPACE).put(userDataUserEntityKey, userDataUserEntity);
        }
//        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .isAssignableFrom(AuthUserEntity.class) &&
                extensionContext.getTestMethod().isPresent() &&
                extensionContext.getTestMethod().get().isAnnotationPresent(DBUser.class)
                || Arrays.stream(extensionContext.getRequiredTestClass().getDeclaredMethods())
                .anyMatch(method -> method.isAnnotationPresent(BeforeEach.class)
                        && method.isAnnotationPresent(DBUser.class));

    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        String authUserEntityKey = USER_KEY + "authUserEntity";

        return extensionContext.getStore(DBUserExtension.NAMESPACE)
                .get(authUserEntityKey, AuthUserEntity.class);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        AuthDAO authDao = new AuthDAOHibernate();
        UserDataDAO userDataDAO = new UserDataDAOHibernate();

        String authUserEntityKey = USER_KEY + "authUserEntity";
        String userDataUserEntityKey = USER_KEY + "userDataUserEntity";

        AuthUserEntity authUserEntity = extensionContext.getStore(DBUserExtension.NAMESPACE)
                .get(authUserEntityKey, AuthUserEntity.class);

        UserDataUserEntity userDataUserEntity = extensionContext.getStore(NAMESPACE)
                .get(userDataUserEntityKey, UserDataUserEntity.class);


        userDataDAO.deleteUserInUserData(userDataUserEntity);
        authDao.deleteUserInAuth(authUserEntity);
    }

    private AuthUserEntity createAuthUserEntity(DBUser dbUser) {
        Faker faker = new Faker();
        AuthUserEntity authUserEntity = new AuthUserEntity();

        authUserEntity.setUsername(dbUser.username().isEmpty() ? faker.name().username() : dbUser.username());
        authUserEntity.setPassword(dbUser.password().isEmpty() ? faker.internet().password() : dbUser.password());
        authUserEntity.setEnabled(true);
        authUserEntity.setAccountNonExpired(true);
        authUserEntity.setAccountNonLocked(true);
        authUserEntity.setCredentialsNonExpired(true);
        authUserEntity.setAuthorities(Arrays.stream(Authority.values())
                .map(authority -> {
                    AuthAuthorityEntity authAuthorityEntity = new AuthAuthorityEntity();
                    authAuthorityEntity.setAuthority(authority);
                    authAuthorityEntity.setUser(authUserEntity);
                    return authAuthorityEntity;
                }).toList());

        return authUserEntity;
    }

    private UserDataUserEntity createUserDataUserEntity(AuthUserEntity authUserEntity) {

        UserDataUserEntity userDataUserEntity = new UserDataUserEntity();

        userDataUserEntity.setUsername(authUserEntity.getUsername());
        userDataUserEntity.setCurrency(CurrencyValues.EUR);

        return userDataUserEntity;
    }

    private DBUser getDbUserAnnotation(ExtensionContext extensionContext) {

        DBUser dbUserAnnotation = extensionContext.getRequiredTestMethod().getAnnotation(DBUser.class);
        if (dbUserAnnotation != null) {
            return dbUserAnnotation;
        }

        return Arrays.stream(extensionContext.getRequiredTestClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(BeforeEach.class))
                .map(method -> method.getAnnotation(DBUser.class))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private void persistUserEntity(AuthUserEntity authUserEntity, UserDataUserEntity userDataUserEntity) {
        AuthDAO authDao = new AuthDAOHibernate();
        UserDataDAO userDataDAO = new UserDataDAOHibernate();

        authDao.createUserInAuth(authUserEntity);
        userDataDAO.createUserInUserData(userDataUserEntity);
    }
}
