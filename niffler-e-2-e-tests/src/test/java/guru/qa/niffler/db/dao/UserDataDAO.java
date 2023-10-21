package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.AuthUserEntity;

import java.util.UUID;

public interface UserDataDAO {

    int createUserInUserData(AuthUserEntity user);

    void updateUserByIdInUserData(UUID userId);

    AuthUserEntity getUserByIdInUserData(UUID userId);

    void deleteUserByIdInUserData(UUID userId);
}
