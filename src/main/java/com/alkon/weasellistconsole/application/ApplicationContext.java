package com.alkon.weasellistconsole.application;

import java.util.HashMap;
import java.util.Map;

import static com.alkon.weasellistconsole.application.Utils.isEmpty;

public class ApplicationContext {

    public static final String MONGO_WRAPPER = "mongoWrapper";
    public static final String USER = "user";
    public static final String EXIT_ERROR = "exitError";

    private Map<String, Object> params;

    public ApplicationContext() {
        this.params = new HashMap<>();
    }

    public void setParam(String key, Object value) {
        this.params.put(key, value);
    }

    public Object getParam(String key) {
        if (isEmpty(key)) {
            return null;
        }
        return this.params.get(key);
    }

    public boolean hasParam(String key) {
        return this.getParam(key) != null;
    }

}
