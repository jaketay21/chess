package handlers;


import models.Authtoken;
import request.LoginRequest;
import request.RegisterRequest;
import response.RegisterResponse;
import service.AuthService;
import service.RegisterService;
import service.UserService;
import io.javalin.http.Context;

import java.util.Map;


public class UserHandler extends BaseHandler{
    private final UserService userService;
    private final RegisterService registerService;

    public UserHandler(UserService userService, AuthService authService, RegisterService registerService){
        super(authService);
        this.userService = userService;
        this.registerService = registerService;

    }

    public void Register(Context ctx){

            RegisterRequest request = fromJson(ctx.body(), RegisterRequest.class);

            try {
                Authtoken token = registerService.registerUser(request);
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

    public void Login(Context ctx){
        try{
            LoginRequest request = fromJson(ctx.body(), LoginRequest.class);
            if(userService.contains(request.usernaame())){
                if(userService.matchesPassword(request)) {
                    Authtoken token = authService.addAuth(request.usernaame());
                    ctx.status(200);
                    ctx.result(toJson(token));
                }
            }else{
                ctx.status(400); // internal server error
                ctx.result("{\"message\": \"Error: " + "\"}");
            }

        }catch (Exception e){
            ctx.status(400); // internal server error
            ctx.result("{\"message\": \"Error: " + e.getMessage() + "\"}");
        }
    }

  /*  public void Logout(Context ctx){
        try{
            String token = ctx.header("authorization");
            if(authService.isAuthorized(token)){

            }
        }
    }*/






}
