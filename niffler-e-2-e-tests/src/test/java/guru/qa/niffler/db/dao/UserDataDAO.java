package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.AuthUserEntity;
import guru.qa.niffler.db.model.UserDataUserEntity;

public interface UserDataDAO {

    int createUserInUserData(AuthUserEntity user);

    UserDataUserEntity getUserByUsernameFromUserData(String username);

    void updateUserInUserData(UserDataUserEntity user);

    void deleteUserByUsernameInUserData(String username);
}
