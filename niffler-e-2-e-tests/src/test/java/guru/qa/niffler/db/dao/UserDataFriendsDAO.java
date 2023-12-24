package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.userdata.FriendsEntity;

import java.util.UUID;

public interface UserDataFriendsDAO {

    FriendsEntity getFriendRequestByFriendId(UUID friendId);

    void deleteFriendRequestInUserData(FriendsEntity friendsEntity);
}
