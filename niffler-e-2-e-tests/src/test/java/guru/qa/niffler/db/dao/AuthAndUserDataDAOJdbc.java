package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.DataSourceProvider;
import guru.qa.niffler.db.ServiceDB;
import guru.qa.niffler.db.model.Authority;
import guru.qa.niffler.db.model.AuthAuthorityEntity;
import guru.qa.niffler.db.model.CurrencyValues;
import guru.qa.niffler.db.model.AuthUserEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuthAndUserDataDAOJdbc implements AuthDAO, UserDataDAO {

    private static DataSource authDs = DataSourceProvider.INSTANCE.getDataSource(ServiceDB.AUTH);
    private static DataSource userdataDs = DataSourceProvider.INSTANCE.getDataSource(ServiceDB.USERDATA);

    @Override
    public int createUserInAuth(AuthUserEntity user) {
        int createdRows = 0;

        try (Connection conn = authDs.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement usersPs = conn.prepareStatement(
                    "INSERT INTO users (username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) " +
                            "VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

                 PreparedStatement authorityPs = conn.prepareStatement(
                         "INSERT INTO authorities (user_id, authority) " +
                                 "VALUES (?, ?)")) {

                usersPs.setString(1, user.getUsername());
                usersPs.setString(2, pe.encode(user.getPassword()));
                usersPs.setBoolean(3, user.getEnabled());
                usersPs.setBoolean(4, user.getAccountNonExpired());
                usersPs.setBoolean(5, user.getAccountNonLocked());
                usersPs.setBoolean(6, user.getCredentialsNonExpired());

                createdRows = usersPs.executeUpdate();
                UUID generatedUserId;

                try (ResultSet generatedKeys = usersPs.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedUserId = UUID.fromString(generatedKeys.getString("id"));
                    } else {
                        throw new IllegalStateException("Can`t obtain id from given ResultSet");
                    }
                }

                for (Authority authority : Authority.values()) {
                    authorityPs.setObject(1, generatedUserId);
                    authorityPs.setString(2, authority.name());
                    authorityPs.addBatch();
                    authorityPs.clearParameters();
                }

                authorityPs.executeBatch();
                user.setId(generatedUserId);
                conn.commit();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                conn.rollback();
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return createdRows;
    }

    @Override
    public void updateUserInAuth(AuthUserEntity user) {
        try (Connection connection = authDs.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement userPs = connection.prepareStatement(
                    "UPDATE users SET username = ?, password = ?, enabled = ?, account_non_expired = ?, " +
                            "account_non_locked = ?, credentials_non_expired = ?"
            )) {
                userPs.setString(1, user.getUsername());
                userPs.setString(2, pe.encode(user.getPassword()));
                userPs.setBoolean(3, user.getEnabled());
                userPs.setBoolean(4, user.getAccountNonExpired());
                userPs.setBoolean(5, user.getAccountNonLocked());
                userPs.setBoolean(6, user.getCredentialsNonExpired());

                userPs.executeUpdate();

                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthUserEntity getUserByIdInAuth(UUID userId) {
        AuthUserEntity user = new AuthUserEntity();
        List<AuthAuthorityEntity> authAuthorityEntityList = new ArrayList<>();

        try (Connection connection = userdataDs.getConnection()) {

            try (PreparedStatement userPs = connection.prepareStatement(
                    "SELECT * FROM users WHERE id = ?")) {
                userPs.setObject(1, userId);
                userPs.execute();

                ResultSet usersResultSet = userPs.getResultSet();
                while (usersResultSet.next()) {
                    user.setId(usersResultSet.getObject("id", UUID.class));
                    user.setUsername(usersResultSet.getString("username"));
                    user.setEnabled(usersResultSet.getBoolean("enabled"));
                    user.setAccountNonExpired(usersResultSet.getBoolean("account_non_locked"));
                    user.setAccountNonLocked(usersResultSet.getBoolean("credentials_non_expired"));

                    try (PreparedStatement authorityPs = connection.prepareStatement(
                            "SELECT * FROM where user_id = ?")) {
                        authorityPs.setObject(1, userId);
                        authorityPs.execute();

                        ResultSet authorityResultSet = authorityPs.getResultSet();
                        while (authorityResultSet.next()) {
                            AuthAuthorityEntity authority = new AuthAuthorityEntity();
                            authority.setAuthority(Authority.valueOf(authorityResultSet.getString("authority")));
                            authAuthorityEntityList.add(authority);
                        }
                        user.setAuthorities(authAuthorityEntityList);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public void deleteUserByIdInAuth(UUID userId) {
        try (Connection connection = userdataDs.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement authorityPs = connection.prepareStatement(
                    "DELETE FROM authorities WHERE user_id = ?");
                 PreparedStatement userPs = connection.prepareStatement(
                         "DELETE FROM users WHERE id = ?")
            ) {
                authorityPs.setObject(1, userId);
                userPs.setObject(1, userId);
                authorityPs.execute();
                userPs.execute();

                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int createUserInUserData(AuthUserEntity user) {
        int createdRows = 0;
        try (Connection conn = userdataDs.getConnection()) {
            try (PreparedStatement usersPs = conn.prepareStatement(
                    "INSERT INTO users (username, currency) " +
                            "VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {

                usersPs.setString(1, user.getUsername());
                usersPs.setString(2, CurrencyValues.RUB.name());

                createdRows = usersPs.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return createdRows;
    }

    @Override
    public void updateUserByIdInUserData(UUID userId) {

    }

    @Override
    public AuthUserEntity getUserByIdInUserData(UUID userId) {

    }

    @Override
    public void deleteUserByIdInUserData(UUID userId) {

    }
}
