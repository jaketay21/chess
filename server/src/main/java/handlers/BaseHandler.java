package handlers;

import service.AuthService;



import com.google.gson.Gson;

public abstract class BaseHandler {

    protected final AuthService authService;
    protected final Gson gson = new Gson();

    public BaseHandler(AuthService authService) {
        this.authService = authService;
    }

    protected <T> T fromJson(String json, Class<T> clas) {
        return gson.fromJson(json, clas);
    }

    protected String toJson(Object obj) {
        return gson.toJson(obj);
    }

    protected boolean authorized(String authToken){
        return authService.isAuthorized(authToken);
    }
}
