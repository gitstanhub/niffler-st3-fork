package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeoutException;

public class UserQueueExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UserQueueExtension.class);

    private static Map<User.UserType, Queue<UserJson>> usersQueue = new ConcurrentHashMap<>();

    static {
        Queue<UserJson> usersWithFriends = new ConcurrentLinkedQueue<>();
        usersWithFriends.add(bindUser("dima", "12345"));
        usersWithFriends.add(bindUser("barsik", "12345"));
        usersQueue.put(User.UserType.WITH_FRIENDS, usersWithFriends);

        Queue<UserJson> usersInSent = new ConcurrentLinkedQueue<>();
        usersInSent.add(bindUser("bee", "12345"));
        usersInSent.add(bindUser("anna", "12345"));
        usersQueue.put(User.UserType.INVITATION_SENT, usersInSent);

        Queue<UserJson> usersInRc = new ConcurrentLinkedQueue<>();
        usersInRc.add(bindUser("valentin", "12345"));
        usersInRc.add(bindUser("pizzly", "12345"));
        usersQueue.put(User.UserType.INVITATION_RECEIVED, usersInRc);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        List<Method> methods = new ArrayList<>();
        methods.add(context.getRequiredTestMethod());

        Arrays.stream(context.getRequiredTestClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(BeforeEach.class))
                .forEach(methods::add);

        List<Parameter> parameters = addHandledParameters(methods);

        Map<User.UserType, UserJson> candidatesForTest = addCandidatesForTest(parameters);

        putCandidatesForTestToContext(context, candidatesForTest);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Map<User.UserType, UserJson> candidatesForTest = context.getStore(NAMESPACE).get(getAllureId(context), Map.class);
        candidatesForTest.forEach((userType, userJson) -> {
            Queue<UserJson> userQueue = usersQueue.get(userType);
            if (userQueue != null) {
                userQueue.add(userJson);
            }
        });
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class)
                && parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        User.UserType userType = parameterContext.getParameter().getAnnotation(User.class).userType();
        return (UserJson) extensionContext.getStore(NAMESPACE).get(getAllureId(extensionContext), Map.class).get(userType);
    }

    private String getAllureId(ExtensionContext context) {
        AllureId allureId = context.getRequiredTestMethod().getAnnotation(AllureId.class);
        if (allureId == null) {
            throw new IllegalStateException("Annotation @AllureId must be present!");
        }
        return allureId.value();
    }

    private static UserJson bindUser(String username, String password) {
        UserJson user = new UserJson();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    private List<Parameter> addHandledParameters(List<Method> handleMethods) {
        return handleMethods.stream()
                .map(Method::getParameters)
                .flatMap(Arrays::stream)
                .filter(parameter -> parameter.getType().isAssignableFrom(UserJson.class))
                .filter(parameter -> parameter.isAnnotationPresent(User.class))
                .toList();
    }

    private Map<User.UserType, UserJson> addCandidatesForTest(List<Parameter> handleParameters) throws TimeoutException {
        Map<User.UserType, UserJson> candidatesForTest = new ConcurrentHashMap<>();

        long timeout = System.currentTimeMillis() + 15000;

        for (Parameter parameter : handleParameters) {

            User parameterAnnotation = parameter.getAnnotation(User.class);
            User.UserType userType = parameterAnnotation.userType();
            Queue<UserJson> usersQueueByType = usersQueue.get(userType);

            UserJson candidateForTest = null;

            while (candidateForTest == null) {
                if (System.currentTimeMillis() > timeout) {
                    throw new TimeoutException();
                }
                candidateForTest = usersQueueByType.poll();
            }

            candidateForTest.setUserType(userType);

            candidatesForTest.put(userType, candidateForTest);
        }

        return candidatesForTest;
    }

    private void putCandidatesForTestToContext(ExtensionContext context, Map<User.UserType, UserJson> candidatesForTest) {
        context.getStore(NAMESPACE).put(getAllureId(context), candidatesForTest);
    }
}
