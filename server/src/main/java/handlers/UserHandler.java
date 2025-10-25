package handlers;


import service.UserService;

public class UserHandler extends BaseHandler{
    private final UserService userService;

    public UserHandler(UserService userService){
        this.userService = userService;
    }




}
