package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.dao.AuthUsersDAOHibernate;
import guru.qa.niffler.db.dao.UserDataUsersDAOHibernate;

public class UserRepositoryHibernate extends AbstractUserRepository {
    public UserRepositoryHibernate() {
        super(new AuthUsersDAOHibernate(), new UserDataUsersDAOHibernate());
    }
}
