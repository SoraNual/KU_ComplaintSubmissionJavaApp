package ku.cs.form.services;

import ku.cs.form.models.Nisit;
import ku.cs.form.models.User;
import ku.cs.form.models.UserList;

import java.io.*;

public class NisitRegistration implements Registration {

    private String directoryName;

    private String fileName;
    private UserList userList;
    private UserDataSource userDataSource;

    public NisitRegistration(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        userDataSource = new UserDataSource("data", "users.csv");
        userList = userDataSource.readData();
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if(!file.exists()) {
            file.mkdirs();
        }

        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public String registrationCheck(String name, String username, String password, String confirmPassword) {
        String error = "";
        if(name.isBlank())   error += "ใส่ชื่อมาด้วย!\n";
        if(username.isBlank())   error += "อย่าปล่อยให้ช่อง Username ว่างสิ!\n";
        if(password.isBlank())   error += "ใส่รหัสผ่านมาด้วย!\n";
        if(confirmPassword.isBlank())   error += "โปรดยืนยันรหัสผ่าน!\n";

        if(!password.isBlank() && !confirmPassword.isBlank() && confirmationPasswordCheck(password, confirmPassword))
            error += "รหัสผ่านไม่ตรงกัน!\n";
        if(!usernameValidationCheck(username))     error += "username ของคุณซ้ำ!\n";

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
    public boolean confirmationPasswordCheck(String password, String confirmationPassword){
        if(password.equals(confirmationPassword)) return false;
        return true;
    }

    public void addNisit(Nisit nisit) {
        userList.addUser(nisit);
        userDataSource.writeData(userList);
    }
}
