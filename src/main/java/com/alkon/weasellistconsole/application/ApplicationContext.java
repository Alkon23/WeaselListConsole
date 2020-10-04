package com.alkon.weasellistconsole.application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.alkon.weasellistconsole.application.Utils.isEmpty;

public class ApplicationContext {

    private static ApplicationContext instance = getInstance();

    public static final String MONGO_WRAPPER = "mongoWrapper";
    public static final String USER = "user";
    public static final String EXIT_ERROR = "exitError";

    private Map<String, Object> params;

    private ApplicationContext() {
        this.params = new HashMap<>();
    }

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
            try {
                PropertyFile.readProperties();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    public static void setParam(String key, Object value) {
        instance.params.put(key, value);
    }

    public static Object getParam(String key) {
        if (isEmpty(key)) {
            return null;
        }
        return instance.params.get(key);
    }

    public static boolean hasParam(String key) {
        return getParam(key) != null;
    }

}
