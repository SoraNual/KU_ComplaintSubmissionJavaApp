package ku.cs.form.services;

import ku.cs.form.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDataSource implements DataSource<UserList>{
    private String directoryName;
    private String fileName;

    public UserDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
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

    public User usernamePasswordCheck(String username, String password) {
        UserList userList = readData();
        for(User user : userList.getAllUsers()){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
    public void changeData(User editedUser) {
        UserList userList = readData();
        for(User user : userList.getAllUsers()){
            if(user.getUsername().equals(editedUser.getUsername())){
                user.setPassword(editedUser.getPassword());
                user.setRectangleColor(editedUser.getRectangleColor());
                user.setBackgroundColor(editedUser.getBackgroundColor());
                user.setTextColor(editedUser.getTextColor());
                user.setBorderColor(editedUser.getBorderColor());
                user.setButtonColor(editedUser.getButtonColor());
                writeData(userList);
            }
        }
    }
    @Override
    public UserList readData() {
        UserList userList = new UserList();
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);
            String line = "";
            while ((line = buffer.readLine()) != null) {

                String[] data = line.split(",");
                String class_name = data[3].trim(); // check Class
                if(class_name.equals("staff")){

                    Staff staff = new Staff(data[4].trim(), data[1].trim(), data[2].trim(),data[7].trim()); // add agency
                    staff.setLoginAttempt(Integer.parseInt(data[6].trim()));
                    staff.setUserStatus(data[5].trim());
                    staff.setLoginTime(data[0].trim());
                    userList.addUser(staff);
                }

                else if(class_name.equals("admin")){
                    Admin admin = new Admin(data[4].trim(), data[1].trim(), data[2].trim());
                    admin.setLoginAttempt(Integer.parseInt(data[6].trim()));
                    admin.setUserStatus(data[5].trim());
                    admin.setLoginTime(data[0].trim());
                    userList.addUser(admin);
                }

                else if(class_name.equals("nisit")){
                    Nisit nisit = new Nisit(data[4].trim(), data[1].trim(), data[2].trim()
                            ,data[8].trim() ,data[9].trim() ,data[10].trim() ,data[11].trim() ,data[12].trim());
                    nisit.setLoginAttempt(Integer.parseInt(data[6].trim()));
                    nisit.setUserStatus(data[5].trim());
                    nisit.setLoginTime(data[0].trim());
                    userList.addUser(nisit);
                }
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.close();
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return userList;
    }

    @Override
    public void writeData(UserList userList) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file,StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            for (User user : userList.getAllUsers()){
                String line = user.getLoginTime() + ",";
                if(user instanceof Admin)
                    line += user.getUsername() + "," + user.getPassword() + ",admin," + user.getName() + "," + user.getUserStatus() + "," + user.getLoginAttempt();
                if(user instanceof Staff)
                    line += user.getUsername() + "," + user.getPassword() + ",staff," + user.getName() + "," + user.getUserStatus() + "," + user.getLoginAttempt() + "," + ((Staff) user).getAgency();
                if(user instanceof Nisit)
                    line += user.toString();
                buffer.append(line);
                buffer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.close();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
