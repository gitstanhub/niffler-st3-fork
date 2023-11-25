package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.AuthUserEntity;

import java.util.UUID;

public class AuthAndUserDataDAOHibernate implements AuthDAO {
    @Override
    public int createUserInAuth(AuthUserEntity user) {
        return 0;
    }

    @Override
    public AuthUserEntity updateUserInAuth(AuthUserEntity user) {
        return null;
    }

    @Override
    public AuthUserEntity getUserByIdFromAuth(UUID userId) {
        return new AuthUserEntity();
    }

    @Override
    public void deleteUserInAuth(AuthUserEntity user) {
    }
}
