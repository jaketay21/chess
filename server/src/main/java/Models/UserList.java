package Models;

import java.util.HashMap;

public class UserList extends HashMap<String, User> {
    public UserList(){
        super();
    }

    public void clearUsers(){
        super.clear();
    }
}
