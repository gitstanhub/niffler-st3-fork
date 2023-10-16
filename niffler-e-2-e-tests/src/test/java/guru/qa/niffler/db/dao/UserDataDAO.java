package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.UserEntity;

import java.util.UUID;

public interface UserDataDAO {

    int createUserInUserData(UserEntity user);

    UserEntity getUserByIdInUserData(UUID userId);

    void updateUserByIdInUserData(UUID userId);

    void deleteUserByIdInUserData(UUID userId);
}
