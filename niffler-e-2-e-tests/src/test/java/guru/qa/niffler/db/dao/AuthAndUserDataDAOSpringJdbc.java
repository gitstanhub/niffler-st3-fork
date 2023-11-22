package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.DataSourceProvider;
import guru.qa.niffler.db.ServiceDB;
import guru.qa.niffler.db.mapper.AuthAuthorityEntityRowMapper;
import guru.qa.niffler.db.mapper.AuthUserEntityRowMapper;
import guru.qa.niffler.db.mapper.UserDataUserEntityRowMapper;
import guru.qa.niffler.db.model.*;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

public class AuthAndUserDataDAOSpringJdbc implements AuthDAO, UserDataDAO {

    private final TransactionTemplate authTemplate;
    private final TransactionTemplate userdataTemplate;
    private final JdbcTemplate authJdbcTemplate;
    private final JdbcTemplate userdataJdbcTemplate;

    public AuthAndUserDataDAOSpringJdbc() {
        JdbcTransactionManager authTm = new JdbcTransactionManager(
                DataSourceProvider.INSTANCE.getDataSource(ServiceDB.AUTH));
        JdbcTransactionManager userdataTm = new JdbcTransactionManager(
                DataSourceProvider.INSTANCE.getDataSource(ServiceDB.USERDATA));

        this.authTemplate = new TransactionTemplate(authTm);
        this.userdataTemplate = new TransactionTemplate(userdataTm);
        this.authJdbcTemplate = new JdbcTemplate(authTm.getDataSource());
        this.userdataJdbcTemplate = new JdbcTemplate(userdataTm.getDataSource());
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public int createUserInAuth(AuthUserEntity user) {
        return authTemplate.execute(status -> {
            KeyHolder kh = new GeneratedKeyHolder();

            authJdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement("INSERT INTO users (username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) " +
                        "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername());
                ps.setString(2, pe.encode(user.getPassword()));
                ps.setBoolean(3, user.getEnabled());
                ps.setBoolean(4, user.getAccountNonExpired());
                ps.setBoolean(5, user.getAccountNonLocked());
                ps.setBoolean(6, user.getCredentialsNonExpired());
                return ps;
            }, kh);

            final UUID userId = (UUID) kh.getKeyList().get(0).get("id");
            user.setId(userId);

            authJdbcTemplate.batchUpdate("INSERT INTO authorities (user_id, authority) VALUES (?, ?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1, userId);
                    ps.setObject(2, Authority.values()[i].name());
                }

                @Override
                public int getBatchSize() {
                    return Authority.values().length;
                }
            });
            return 1;
        });
    }

    @Override
    public AuthUserEntity getUserByIdFromAuth(UUID userId) {
        AuthUserEntity user = authJdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                AuthUserEntityRowMapper.instance,
                userId
        );

        if (user != null) {
            List<AuthAuthorityEntity> authorities = authJdbcTemplate.queryForObject(
                    "SELECT * FROM authorities where user_id = ?",
                    AuthAuthorityEntityRowMapper.instance,
                    userId
            );

            user.setAuthorities(authorities);
        }

        return user;
    }

    @Override
    public AuthUserEntity updateUserInAuth(AuthUserEntity user) {
        return authTemplate.execute(status -> {

            authJdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement("UPDATE users SET password = ?, enabled = ?, " +
                        "account_non_expired = ?, account_non_locked = ?, credentials_non_expired = ? " +
                        "WHERE id = ?");
                ps.setString(1, pe.encode(user.getPassword()));
                ps.setBoolean(2, user.getEnabled());
                ps.setBoolean(3, user.getAccountNonLocked());
                ps.setBoolean(4, user.getAccountNonLocked());
                ps.setBoolean(5, user.getCredentialsNonExpired());
                ps.setObject(6, user.getId());
                return ps;
            });

            return getUserByIdFromAuth(user.getId());
        });
    }

    @Override
    public void deleteUserInAuth(UUID userId) {
        authTemplate.executeWithoutResult(status ->
                authJdbcTemplate.update("DELETE FROM authorities WHERE user_id = ?", userId));

        authTemplate.executeWithoutResult(status ->
                authJdbcTemplate.update("DELETE FROM users WHERE id = ?", userId));
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public int createUserInUserData(AuthUserEntity user) {
        return userdataTemplate.execute(status -> {
            userdataJdbcTemplate.update(
                    "INSERT INTO users (username, currency) VALUES (?, ?)",
                    user.getUsername(),
                    CurrencyValues.EUR.name()
            );
            return 1;
        });
    }

    @Override
    public UserDataUserEntity getUserByUsernameFromUserData(String username) {
        return userdataJdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE username = ?",
                UserDataUserEntityRowMapper.instance,
                username
        );
    }

    @Override
    public void updateUserInUserData(UserDataUserEntity user) {
        userdataTemplate.executeWithoutResult(status ->
                userdataJdbcTemplate.update(
                        "UPDATE users SET currency = ?, firstname = ?, surname = ? " +
                                "WHERE id = ?",
                        user.getCurrency(), user.getFirstName(), user.getSurname(), user.getId()));
    }

    @Override
    public void deleteUserInUserData(String username) {
        userdataTemplate.executeWithoutResult(status ->
                userdataJdbcTemplate.update("DELETE FROM users WHERE username = ?", username));
    }
}
