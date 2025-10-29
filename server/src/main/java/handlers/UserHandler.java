package handlers;

import io.javalin.http.Context;
import models.Authtoken;
import models.ResponseException;
import request.LoginRequest;
import request.RegisterRequest;
import service.UserService;

import java.util.Map;

public class UserHandler extends BaseHandler {
    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public void Register(Context ctx) {
        try {
            RegisterRequest request;
            try {
                request = fromJson(ctx.body(), RegisterRequest.class);
            } catch (Exception e) {
                throw new ResponseException(400); // Bad request (invalid JSON)
            }

            // Validate missing fields
            if (request.username() == null || request.password() == null || request.email() == null) {
                throw new ResponseException(400);
            }

            Authtoken token = userService.registerUser(request);
            ctx.status(200);
            ctx.result(toJson(token));

        } catch (ResponseException e) {
            ctx.status(e.getCode());
            ctx.result(toJson(Map.of("message", e.getMessage())));
        }
    }

    public void Login(Context ctx) {
        try {
            LoginRequest request;
            try {
                request = fromJson(ctx.body(), LoginRequest.class);
            } catch (Exception e) {
                throw new ResponseException(400); // invalid JSON → 400
            }

            if (request.username() == null || request.password() == null) {
                throw new ResponseException(400); // missing fields → 400
            }

            Authtoken token = userService.login(request); // login throws 401 if bad username/password
            ctx.status(200);
            ctx.result(toJson(token));

        } catch (ResponseException e) {
            ctx.status(e.getCode());
            ctx.result(toJson(Map.of("message", e.getMessage())));
        }
    }


    public void Logout(Context ctx) {
        try {
            String token = ctx.header("authorization");
            if (token == null || token.isBlank()) {
                throw new ResponseException(401);
            }

            userService.logout(token);
            ctx.status(200);
            ctx.result("{}"); // ✅ Correct JSON empty object

        } catch (ResponseException e) {
            ctx.status(e.getCode());
            ctx.result(toJson(Map.of("message", e.getMessage())));
        }
    }
}