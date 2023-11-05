package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.AuthUserEntity;

import java.util.UUID;

public class AuthAndUserDataDAOHibernate implements AuthDAO {
    @Override
    public int createUserInAuth(AuthUserEntity user) {
        return 0;
    }

    @Override
    public void updateUserInAuth(AuthUserEntity authUserEntity) {
    }

    @Override
    public AuthUserEntity getUserByIdFromAuth(UUID userId) {
        return new AuthUserEntity();
    }

    @Override
    public void deleteUserByIdInAuth(UUID userId) {
    }
}
