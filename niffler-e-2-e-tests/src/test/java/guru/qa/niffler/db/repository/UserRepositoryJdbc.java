package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.dao.AuthUsersAndUserDataUsersDAOJdbc;

public class UserRepositoryJdbc extends AbstractUserRepository {
    public UserRepositoryJdbc() {
        super(new AuthUsersAndUserDataUsersDAOJdbc(), new AuthUsersAndUserDataUsersDAOJdbc());
    }
}
