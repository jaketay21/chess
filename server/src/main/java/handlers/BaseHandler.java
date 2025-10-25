package handlers;

import com.google.gson.Gson;

public abstract class BaseHandler {
    protected final Gson gson = new Gson();

    protected <T> T fromJson(String json, Class<T> clas) {
        return gson.fromJson(json, clas);
    }

    protected String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
