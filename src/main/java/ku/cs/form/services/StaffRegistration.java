package ku.cs.form.services;

import ku.cs.form.models.Staff;
import ku.cs.form.models.User;
import ku.cs.form.models.UserList;

import java.io.*;

public class StaffRegistration implements Registration {
    private UserDataSource userDataSource;
    private UserList userList;

    public StaffRegistration() {
        userDataSource = new UserDataSource("data", "users.csv");
        userList = userDataSource.readData();
    }
    @Override
    public String registrationCheck(String name, String username, String password, String confirmPassword) {
        String error = "";
        if(name.isBlank())   error += "ใส่ชื่อมาด้วย!\n";
        if(username.isBlank())   error += "อย่าปล่อยให้ช่อง Username ว่างสิ!\n";
        if(password.isBlank())   error += "ใส่รหัสผ่านมาด้วย!\n";
        if(confirmPassword.isBlank())   error += "โปรดยืนยันรหัสผ่าน!\n";
        if(!password.isBlank() && !confirmPassword.isBlank() && !confirmationPasswordCheck(password, confirmPassword))
            error += "รหัสผ่านไม่ตรงกัน!\n";
        if(!usernameValidationCheck(username)) error += "username ของคุณซ้ำ!\n";
        return error;
    }

    @Override
    public boolean usernameValidationCheck(String newUserName) {
        for(User user : userList.getAllUsers()){
            if(newUserName.equals(user.getUsername())){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean confirmationPasswordCheck(String password, String confirmationPassword) {
        return password.equals(confirmationPassword);
    }

    public void addStaff(Staff staff) {
        userList.addUser(staff);
        userDataSource.writeData(userList);
    }
}
