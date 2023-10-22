package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.db.dao.AuthDAO;
import guru.qa.niffler.db.dao.UserDataDAO;
import guru.qa.niffler.db.model.AuthAuthorityEntity;
import guru.qa.niffler.db.model.AuthUserEntity;
import guru.qa.niffler.db.model.Authority;
import guru.qa.niffler.jupiter.annotation.DBUser;
import guru.qa.niffler.jupiter.annotation.Dao;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Arrays;

public class DBUserExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CategoryExtension.class);

    @Dao
    private AuthDAO authDao;

    @Dao
    private UserDataDAO userDataDAO;

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        DBUser dbUserAnnotation = extensionContext.getRequiredTestMethod().getAnnotation(DBUser.class);

        if (dbUserAnnotation != null) {
            AuthUserEntity authUserEntity = createAuthUserEntity(dbUserAnnotation);
            authDao.createUserInAuth(authUserEntity);
            userDataDAO.createUserInUserData(authUserEntity);


        }
    }

    @Override


    @Override


    @Override


    private AuthUserEntity createAuthUserEntity(DBUser dbUser) {
        AuthUserEntity authUserEntity = new AuthUserEntity();

        authUserEntity.setUsername(dbUser.username());
        authUserEntity.setPassword(dbUser.password());
        authUserEntity.setEnabled(true);
        authUserEntity.setAccountNonExpired(true);
        authUserEntity.setAccountNonLocked(true);
        authUserEntity.setCredentialsNonExpired(true);
        authUserEntity.setAuthorities(Arrays.stream(Authority.values())
                .map(authority -> {
                    AuthAuthorityEntity authAuthorityEntity = new AuthAuthorityEntity();
                    authAuthorityEntity.setAuthority(authority);
                    return authAuthorityEntity;
                }).toList());

        return authUserEntity;
    }
}
