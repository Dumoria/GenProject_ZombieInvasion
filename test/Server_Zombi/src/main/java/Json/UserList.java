package Json;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private final List<Json.UserJson> users = new ArrayList<Json.UserJson>();

    public List<Json.UserJson> getUsers() {
        return users;
    }
    public boolean exist(Json.UserJson user){
        for(Json.UserJson u:users){
            if(u.equals(user)){
                return true;
            }
        }
        return false;
    }
    public void addUser(Json.UserJson newUser){
        users.add(newUser);
    }
}
