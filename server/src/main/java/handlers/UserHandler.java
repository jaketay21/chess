package handlers;


import models.Authtoken;
import models.ResponseException;
import request.JoinRequest;
import request.LoginRequest;
import request.RegisterRequest;
import service.AuthService;
import io.javalin.http.Context;
import service.UserService;

import java.util.Map;


public class UserHandler extends BaseHandler{
    private final UserService userService;

    public UserHandler(UserService userService){

        this.userService = userService;
    }

    public void Register(Context ctx)throws ResponseException{

            RegisterRequest request = fromJson(ctx.body(), RegisterRequest.class);
            try {
                request = fromJson(ctx.body(), RegisterRequest.class);
            } catch (Exception e) {
                throw new ResponseException(400); // Bad request
             }

            try {
                Authtoken token = userService.registerUser(request);
                ctx.status(200);
                ctx.result(toJson(token));

            } catch (RuntimeException e ){
                ctx.status(403); // 403: already taken (for "username already exists")
                ctx.result("{String}");
            } catch (Exception e) {
                ctx.status(400); // internal server error
                ctx.result("{\"message\": \"Error: " + e.getMessage() + "\"}");
            }


    }

    public void Login(Context ctx)throws ResponseException{

        LoginRequest request;
        try {
            request = fromJson(ctx.body(), LoginRequest.class);
        } catch (Exception e) {
            throw new ResponseException(400); // Bad request
        }

        try{
            Authtoken token = userService.login(request);
            ctx.status(200);
            ctx.result(toJson(token));
        } catch (ResponseException e) {
            ctx.status(e.getCode());
            ctx.result(toJson(Map.of("message", e.getMessage())));
        }


    }

    public void Logout(Context ctx) throws ResponseException {

        try{
            String token = ctx.header("authorization");
            userService.logout(token);
            ctx.json("{}");
            ctx.status(200);
        }catch (ResponseException e){
            ctx.status(e.getCode());
            ctx.result(toJson(Map.of("message", e.getMessage())));
        }


    }








}
