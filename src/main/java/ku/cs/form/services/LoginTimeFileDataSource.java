package ku.cs.form.services;

import ku.cs.form.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class LoginTimeFileDataSource implements DataSource<UserList> {

    private String directoryName;
    private String fileName;

    public LoginTimeFileDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
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
                System.out.println(data);
                String class_name = data[1].trim(); // check Class

                if(class_name.equals("staff")){

                    Staff staff = new Staff(null, data[0].trim(), null,data[2].trim()); // add agency
                    staff.setLoginTime(data[3].trim());
                    userList.addUser(staff);
                    System.out.println(staff);
                }

                else if(class_name.equals("admin")){
                    Admin admin = new Admin(null, data[0].trim(), null);
                    admin.setLoginTime(data[2].trim());
                    userList.addUser(admin);
                }

                else if(class_name.equals("nisit")){
                    Nisit nisit = new Nisit(data[0].trim(), data[1].trim(), null);
                    nisit.setLoginTime(data[2].trim());
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
    public void writeData(UserList users) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            for (User user : users.getAllUsers()){
                String line = "";
                if(user instanceof Admin)
                    line = user.getUsername() + ",admin," + user.getLoginTime();
                if(user instanceof Staff)
                    line = user.getUsername() + ",staff," + ((Staff) user).getAgency() + "," + user.getLoginTime();
                if(user instanceof Nisit)
                    line = user.getUsername() + ",nisit," + user.getLoginTime();
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

