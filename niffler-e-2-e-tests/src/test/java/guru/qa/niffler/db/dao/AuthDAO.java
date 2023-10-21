package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.AuthUserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public interface AuthDAO {

    PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    int createUserInAuth(AuthUserEntity user);

    void updateUserInAuth(AuthUserEntity authUserEntity);

    AuthUserEntity getUserByIdFromAuth(UUID userId);

    void deleteUserByIdInAuth(UUID userId);
}
