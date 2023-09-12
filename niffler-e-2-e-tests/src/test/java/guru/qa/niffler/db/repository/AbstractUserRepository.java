package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.dao.AuthUsersDAO;
import guru.qa.niffler.db.dao.UserDataUsersDAO;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.db.model.userdata.CurrencyValues;
import guru.qa.niffler.db.model.userdata.UserDataUserEntity;

public abstract class AbstractUserRepository implements UserRepository {
    private final AuthUsersDAO authUsersDAO;
    private final UserDataUsersDAO udUsersDAO;

    protected AbstractUserRepository(AuthUsersDAO authUsersDAO, UserDataUsersDAO udUsersDAO) {
        this.authUsersDAO = authUsersDAO;
        this.udUsersDAO = udUsersDAO;
    }

    @Override
    public void createUserForTest(AuthUserEntity user) {
        authUsersDAO.createUserInAuth(user);
        udUsersDAO.createUserInUserData(fromAuthUser(user));
    }

    @Override
    public void removeAfterTest(AuthUserEntity user) {
        UserDataUserEntity userInUd = udUsersDAO.getUserByUsernameFromUserData(user.getUsername());
        udUsersDAO.deleteUserInUserData(userInUd);
        authUsersDAO.deleteUserInAuth(user);
    }

    private UserDataUserEntity fromAuthUser(AuthUserEntity user) {
        UserDataUserEntity userdataUser = new UserDataUserEntity();
        userdataUser.setUsername(user.getUsername());
        userdataUser.setCurrency(CurrencyValues.EUR);
        return userdataUser;
    }
}
