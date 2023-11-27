package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.AuthUserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public interface AuthDAO {

    PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    int createUserInAuth(AuthUserEntity user);

    AuthUserEntity getUserByIdFromAuth(UUID userId);

    AuthUserEntity updateUserInAuth(AuthUserEntity user);

    void deleteUserByIdInAuth(UUID userId);
}
