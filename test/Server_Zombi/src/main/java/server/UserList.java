package server;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private final List<UserJson> users = new ArrayList<UserJson>();

    public List<UserJson> getUsers() {
        return users;
    }
}
