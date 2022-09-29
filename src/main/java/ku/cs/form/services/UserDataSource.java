package ku.cs.form.services;

import ku.cs.form.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

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
        UserList userList = new UserList();

        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";
            while((line = buffer.readLine()) != null){ //วนลูปแยกคอมมาทีละบรรทัดจนกว่าจะไม่เจอบรรทัด
                String[] data = line.split(",");
                if(data[0].equals(username)&&data[1].equals(password)){
                    if(data[2].trim().equals("admin")){
                        Admin admin = new Admin(data[3],data[0],data[1]);
                        return admin;
                    } else if (data[2].trim().equals("staff")) {
                        Staff staff = new Staff(data[3],data[0],data[1],data[6]);
                        return staff;
                    } else if (data[2].trim().equals("nisit")) {
                        Nisit nisit = new Nisit(data[3],data[0],data[1]);
                        return nisit;
                    }
                };
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
        return null;
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
                String class_name = data[2].trim(); // check Class

                if(class_name.equals("staff")){

                    Staff staff = new Staff(data[3].trim(), data[0].trim(), data[1].trim(),data[6].trim()); // add agency
                    userList.addUser(staff);
                }

                else if(class_name.equals("admin")){
                    Admin admin = new Admin(data[3].trim(), data[0].trim(), data[1].trim());
                    userList.addUser(admin);
                }

                else if(class_name.equals("nisit")){
                    Nisit nisit = new Nisit(data[3].trim(), data[0].trim(), data[1].trim());
                    nisit.setLoginAttempt(Integer.parseInt(data[5].trim()));
                    nisit.setUserStatus(data[4].trim());
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
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);
            String agency = "";
            for (User user : userList.getAllUsers()) {
                if(user instanceof Staff) agency = ((Staff) user).getAgency();
                String line = user.getUsername() + ","
                        + user.getPassword() + ","
                        + user.getClass().getSimpleName().toLowerCase() + ","
                        + user.getName() + ","
                        + user.getUserStatus() + ","
                        + user.getLoginAttempt() + ","
                        + agency;
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
