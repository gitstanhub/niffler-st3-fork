package guru.qa.niffler.api.context;

import java.util.HashMap;
import java.util.Map;

public class SessionStorageContext {

    private static final ThreadLocal<SessionStorageContext> INSTANCE = ThreadLocal.withInitial(SessionStorageContext::new);

    private static final String
            CODE_CHALLENGE_KEY = "CODE_CHALLENGE_KEY",
            CODE_VERIFIER_KEY = "CODE_VERIFIED_KEY",
            TOKEN_KEY = "TOKEY_KEY",
            CODE_KEY = "CODE_KEY";

    private final Map<String, String> store = new HashMap<>();

    public static SessionStorageContext getInstance() {
        return INSTANCE.get();
    }

    public void setCodeChallenge(String codeChallengeValue) {
        store.put(CODE_CHALLENGE_KEY, codeChallengeValue);
    }

    public void setCodeVerifier(String codeVerifierValue) {
        store.put(CODE_VERIFIER_KEY, codeVerifierValue);
    }

    public void setToken(String tokenValue) {
        store.put(TOKEN_KEY, tokenValue);
    }

    public void setCode(String codeValue) {
        store.put(CODE_KEY, codeValue);
    }

    public void clearContext() {
        store.clear();
    }
}
