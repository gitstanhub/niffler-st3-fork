package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.db.dao.*;
import guru.qa.niffler.jupiter.annotation.Dao;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;

public class DaoExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        for (Field field : testInstance.getClass().getDeclaredFields()) {
            if ((field.getType().isAssignableFrom(AuthUsersDAO.class) || field.getType().isAssignableFrom(UserDataUsersDAO.class))
                    && field.isAnnotationPresent(Dao.class)) {
                field.setAccessible(true);

                AuthUsersDAO dao;

                if ("hibernate".equals(System.getProperty("db.impl"))) {
                    dao = new AuthUsersDAOHibernate();
                } else if ("spring".equals(System.getProperty("db.impl"))) {
                    dao = new AuthUsersAndUserDataUsersDAOSpringJdbc();
                } else {
                    dao = new AuthUsersAndUserDataUsersDAOSpringJdbc();
                }

                field.set(testInstance, dao);
            }

        }

    }
}
