package ku.cs.form.models;

import java.util.ArrayList;
import java.util.Arrays;

public class UserList {
    private ArrayList<User> users;

    public UserList() {
        this.users = new ArrayList<>();
    }
    public void addUser(User user) {
        users.add(user);
    }
    public ArrayList<User> getAllUsers() {
        return users;
    }

}
