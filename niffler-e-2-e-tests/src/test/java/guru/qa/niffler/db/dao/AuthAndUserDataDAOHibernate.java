package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.ServiceDB;
import guru.qa.niffler.db.jpa.EntityManagerFactoryProvider;
import guru.qa.niffler.db.jpa.JpaService;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.db.model.auth.Authority;
import guru.qa.niffler.db.model.userdata.UserDataUserEntity;

import java.util.UUID;

public class AuthAndUserDataDAOHibernate extends JpaService implements AuthDAO, UserDataDAO {

    public AuthAndUserDataDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(ServiceDB.AUTH).createEntityManager());
    }

    @Override
    public int createUserInAuth(AuthUserEntity user) {
        user.setPassword(pe.encode(user.getPassword()));

        persist(user);
        return 1;
    }

    @Override
    public AuthUserEntity getUserByIdFromAuth(UUID userId) {
        return em.createQuery("select u from AuthUserEntity u where u.id=:userId", AuthUserEntity.class)
                .setParameter("id", userId)
                .getSingleResult();
    }

    @Override
    public AuthUserEntity updateUserInAuth(AuthUserEntity user) {
        return merge(user);
    }

    @Override
    public void deleteUserInAuth(AuthUserEntity user) {
        remove(user);
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
