package guru.qa.niffler.db.mapper;

import guru.qa.niffler.db.model.auth.AuthAuthorityEntity;
import guru.qa.niffler.db.model.auth.Authority;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthAuthorityEntityRowMapper implements RowMapper<List<AuthAuthorityEntity>> {

    public static final AuthAuthorityEntityRowMapper instance = new AuthAuthorityEntityRowMapper();

    @Override
    public List<AuthAuthorityEntity> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<AuthAuthorityEntity> authoritiesList = new ArrayList<>();

        while (rs.next()) {
            AuthAuthorityEntity authority = new AuthAuthorityEntity();
            authority.setAuthority(Authority.valueOf(rs.getString("authority")));
            authoritiesList.add(authority);
        }

        return authoritiesList;
    }
}
