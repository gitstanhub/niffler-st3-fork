package guru.qa.niffler.db.mapper;

import guru.qa.niffler.db.model.userdata.CurrencyValues;
import guru.qa.niffler.db.model.userdata.UserDataUserEntity;

import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserDataUserEntityRowMapper implements RowMapper<UserDataUserEntity> {

    public static final UserDataUserEntityRowMapper instance = new UserDataUserEntityRowMapper();

    @Override
    public UserDataUserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDataUserEntity userDataUser = new UserDataUserEntity();

        userDataUser.setId(rs.getObject("id", UUID.class));
        userDataUser.setUsername(rs.getString("username"));
        userDataUser.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
        userDataUser.setFirstName(rs.getString("firstname"));
        userDataUser.setSurname(rs.getString("surname"));
        userDataUser.setPhoto(rs.getBytes("photos"));

        return userDataUser;
    }
}
