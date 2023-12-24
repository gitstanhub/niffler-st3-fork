package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.ServiceDB;
import guru.qa.niffler.db.jpa.EntityManagerFactoryProvider;
import guru.qa.niffler.db.jpa.JpaService;
import guru.qa.niffler.db.model.userdata.FriendsEntity;

import java.util.UUID;

public class UserDataFriendsDAOHibernate extends JpaService implements UserDataFriendsDAO {

    public UserDataFriendsDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(ServiceDB.USERDATA).createEntityManager());
    }

    @Override
    public FriendsEntity getFriendRequestByFriendId(UUID friendId) {

        return em.createQuery("SELECT f FROM FriendsEntity f WHERE f.friend.id = :friendId", FriendsEntity.class)
                    .setParameter("friendId", friendId)
                    .setMaxResults(1).getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public void deleteFriendRequestInUserData(FriendsEntity friendsEntity) {
         remove(friendsEntity);
    }
}
