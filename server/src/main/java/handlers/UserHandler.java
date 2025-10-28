package handlers;


import models.Authtoken;
import request.LoginRequest;
import request.RegisterRequest;
import service.AuthService;
import io.javalin.http.Context;
import service.UserService;


public class UserHandler extends BaseHandler{
    private final UserService userService;

    public UserHandler(UserService userService, AuthService authService){
        super(authService);
        this.userService = userService;
    }

    public void Register(Context ctx){

            RegisterRequest request = fromJson(ctx.body(), RegisterRequest.class);

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

    public void Login(Context ctx)throws RuntimeException{
        LoginRequest request = fromJson(ctx.body(), LoginRequest.class);
        try{
            Authtoken token = userService.login(request);
            ctx.status(200);
            ctx.result(toJson(token));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }


    }

    public void Logout(Context ctx) throws RuntimeException{

        try{
            String token = ctx.header("authorization");
            userService.logout(token);
            ctx.json("{}");
            ctx.status(200);
        }catch (RuntimeException e){
            ctx.status(400);
        }


    }








}
