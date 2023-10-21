package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.AuthUserEntity;
import guru.qa.niffler.db.model.UserDataUserEntity;

import java.util.UUID;

public interface UserDataDAO {

    int createUserInUserData(AuthUserEntity user);

    void updateUserInUserData(UserDataUserEntity user);

    AuthUserEntity getUserByIdFromUserData(UUID userId);

    void deleteUserByIdInUserData(UUID userId);
}
