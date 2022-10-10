package ku.cs.form.services;

import ku.cs.form.models.Staff;
import ku.cs.form.models.User;
import ku.cs.form.models.UserList;

import java.io.*;
import java.util.Set;
import java.util.TreeMap;

public class StaffChangePassword implements DataSource{
    String dirName;
    String fileName;

    public StaffChangePassword(String dirName, String fileName) {
        this.dirName = dirName;
        this.fileName = fileName;
        checkIfFileExisted();
    }

    public void updatePassword(UserList userList, String username, String newPassword) {

    }

    public void checkIfFileExisted() {
        File file = new File(dirName);
        if(!file.exists()) {
            file.mkdirs();
        }

        String filePath = dirName + File.separator + fileName;
        file = new File(filePath);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public UserList readData() {
        UserList userList = new UserList();

        String path = dirName + File.separator + fileName;
        File file = new File(path);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                User user = new User(
                        data[0].trim(),
                        data[3].trim(),
                        data[1].trim()
                );

                userList.addUser(user);
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

    // เขียนทับใหม่หมดเลย (ไม่ append)
    @Override
    public void writeData(Object o) {
        UserList userList = (UserList) o;

        String path = dirName + File.separator + fileName;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        String line = "";
        try {
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            for (User user : userList.getAllUsers()) {
                line = user.getUsername() + "," +
                       user.getPassword() + "," +
                       user.getUserStatus() + "," +
                       user.getName() + "," +
                       user.getUserStatus() + "," +
                       user.getLoginAttempt();

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
