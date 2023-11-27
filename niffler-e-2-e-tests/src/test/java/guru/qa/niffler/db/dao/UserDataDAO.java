package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.UserDataUserEntity;

public interface UserDataDAO {

    int createUserInUserData(UserDataUserEntity user);

    UserDataUserEntity getUserByUsernameFromUserData(String username);

    void updateUserInUserData(UserDataUserEntity user);

    void deleteUserInUserData(UserDataUserEntity user);
}
