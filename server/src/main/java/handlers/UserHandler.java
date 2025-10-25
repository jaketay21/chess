package handlers;


import models.Authtoken;
import request.RegisterRequest;
import response.RegisterResponse;
import service.AuthService;
import service.UserService;
import io.javalin.http.Context;

import java.util.Map;


public class UserHandler extends BaseHandler{
    private final UserService userService;
    private final AuthService authService;

    public UserHandler(UserService userService, AuthService authService){
        this.userService = userService;
        this.authService = authService;
    }

    public void Register(Context ctx){
        try {
            RegisterRequest request = fromJson(ctx.body(), RegisterRequest.class);

            if (!userService.contains(request.username())) {
                // create auth token and add token to db
                Authtoken token = authService.addAuth(request);
                userService.addUser(request);

                // return auth token as JSON
                ctx.status(200);
                ctx.result(toJson(token));

            } else {
                ctx.status(403); // 403: already taken (for "username already exists")
                ctx.result("{\"message\": \"Error: Username already taken\"}");
            }
        } catch (Exception e) {
            ctx.status(400); // internal server error
            ctx.result("{\"message\": \"Error: " + e.getMessage() + "\"}");
        }
    }



}
