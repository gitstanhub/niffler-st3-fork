package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.db.dao.AuthDAO;
import guru.qa.niffler.db.dao.AuthAndUserDataDAOHibernate;
import guru.qa.niffler.db.dao.AuthAndUserDataDAOJdbc;
import guru.qa.niffler.db.dao.AuthAndUserDataDAOSpringJdbc;
import guru.qa.niffler.db.dao.UserDataDAO;
import guru.qa.niffler.jupiter.annotation.Dao;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;

public class DaoExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        for (Field field : testInstance.getClass().getDeclaredFields()) {
            if ((field.getType().isAssignableFrom(AuthDAO.class) || field.getType().isAssignableFrom(UserDataDAO.class))
                    && field.isAnnotationPresent(Dao.class)) {
                field.setAccessible(true);

                AuthDAO dao;

                if ("hibernate".equals(System.getProperty("db.impl"))) {
                    dao = new AuthAndUserDataDAOHibernate();
                } else if ("spring".equals(System.getProperty("db.impl"))) {
                    dao = new AuthAndUserDataDAOSpringJdbc();
                } else {
                    dao = new AuthAndUserDataDAOSpringJdbc();
                }

                field.set(testInstance, dao);
            }

        }

    }
}