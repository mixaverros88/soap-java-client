package com.verros;

import org.apache.wss4j.common.ext.WSPasswordCallback;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PasswordCallbackHandler implements CallbackHandler {

    Map<String, String> passwords = new HashMap<>();


    public PasswordCallbackHandler() {
        passwords.put("test", "test123");
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback: callbacks
             ) {
            WSPasswordCallback pc = (WSPasswordCallback) callback;
            String password = passwords.get( pc.getIdentifier() );
            pc.setPassword(password);
            return;
        }
    }
}
