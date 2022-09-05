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

    public void sortByLoginTime() {

        for(int i = 0;i < users.size();i++){
            User latest_login = users.get(0); //assign lasted login is users[0]
            int latest_login_index = 0;
            for(int j = 0;j < users.size() - i;j++){
                //compare with compareTo in LocalDateTime lib
                if(latest_login.getLoginTime().compareTo(users.get(j).getLoginTime()) > 0){
                    latest_login = users.get(j);
                    latest_login_index = j;
                }
            }
            //remove latest_login to back of UserList
            users.remove(latest_login_index);
            users.add(latest_login);
        }
    }

}
