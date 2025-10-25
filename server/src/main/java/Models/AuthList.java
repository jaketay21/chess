package Models;

import java.util.HashMap;

public class AuthList extends HashMap<String, Authtoken> {
    public AuthList(){
        super();
    }

    public void clearAuths(){
        super.clear();
    }
}
