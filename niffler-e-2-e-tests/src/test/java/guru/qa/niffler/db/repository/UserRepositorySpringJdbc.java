package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.dao.AuthUsersAndUserDataUsersDAOSpringJdbc;

public class UserRepositorySpringJdbc extends AbstractUserRepository {
    public UserRepositorySpringJdbc() {
        super(new AuthUsersAndUserDataUsersDAOSpringJdbc(), new AuthUsersAndUserDataUsersDAOSpringJdbc());
    }
}
