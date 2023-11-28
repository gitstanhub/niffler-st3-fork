package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.ServiceDB;
import guru.qa.niffler.db.jpa.EntityManagerFactoryProvider;
import guru.qa.niffler.db.jpa.JpaService;
import guru.qa.niffler.db.model.auth.AuthUserEntity;

import java.util.UUID;

public class AuthDAOHibernate extends JpaService implements AuthDAO {

    public AuthDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(ServiceDB.AUTH).createEntityManager());
    }

    @Override
    public int createUserInAuth(AuthUserEntity user) {
        String nonEncodedPassword = user.getPassword();
        String encodedPassword = pe.encode(user.getPassword());

        user.setPassword(encodedPassword);

        persist(user);

        user.setPassword(nonEncodedPassword);

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
}
