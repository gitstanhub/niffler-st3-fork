package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.userdata.UserDataUserEntity;

public interface UserDataDAO {

    int createUserInUserData(UserDataUserEntity user);

    UserDataUserEntity getUserByUsernameFromUserData(String username);

    UserDataUserEntity updateUserInUserData(UserDataUserEntity user);

    void deleteUserInUserData(UserDataUserEntity user);
}
