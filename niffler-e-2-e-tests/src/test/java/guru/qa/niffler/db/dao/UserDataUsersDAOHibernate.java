package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.ServiceDB;
import guru.qa.niffler.db.jpa.EntityManagerFactoryProvider;
import guru.qa.niffler.db.jpa.JpaService;
import guru.qa.niffler.db.model.userdata.UserDataUserEntity;

public class UserDataUsersDAOHibernate extends JpaService implements UserDataUsersDAO {

    public UserDataUsersDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(ServiceDB.USERDATA).createEntityManager());
    }

    @Override
    public int createUserInUserData(UserDataUserEntity user) {
        persist(user);
        return 1;
    }

    @Override
    public UserDataUserEntity getUserByUsernameFromUserData(String username) {
        return em.createQuery("select u from UserDataUserEntity u where u.username=username", UserDataUserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public UserDataUserEntity updateUserInUserData(UserDataUserEntity user) {
        return merge(user);
    }

    @Override
    public void deleteUserInUserData(UserDataUserEntity user) {
        remove(user);
    }
}
