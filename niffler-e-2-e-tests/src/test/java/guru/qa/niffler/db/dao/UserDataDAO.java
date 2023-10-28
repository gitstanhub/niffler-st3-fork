package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.AuthUserEntity;
import guru.qa.niffler.db.model.UserDataUserEntity;

public interface UserDataDAO {

    int createUserInUserData(AuthUserEntity user);

    void updateUserInUserData(UserDataUserEntity user);

    UserDataUserEntity getUserByUsernameFromUserData(String username);

    void deleteUserByUsernameInUserData(String username);
}
