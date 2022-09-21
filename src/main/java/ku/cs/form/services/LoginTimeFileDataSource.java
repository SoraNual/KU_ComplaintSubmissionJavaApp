package ku.cs.form.services;

import ku.cs.form.models.*;

import java.io.*;

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
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);
            String line = "";
            while ((line = buffer.readLine()) != null) {

                String[] data = line.split(",");

                String class_name = data[2].trim(); // check Class

                if(class_name.equals("staff")){
                    Staff staff = new Staff(data[0].trim(), data[1].trim(), null,data[3].trim()); // add agency
                    staff.setLoginTime(data[4].trim());
                    userList.addUser(staff);
                }

                else if(class_name.equals("admin")){
                    Admin admin = new Admin(data[0].trim(), data[1].trim(), null);
                    admin.setLoginTime(data[3].trim());
                    userList.addUser(admin);
                }

                else if(class_name.equals("nisit")){
                    Nisit nisit = new Nisit(data[0].trim(), data[1].trim(), null,"0xb399ffff");
                    nisit.setLoginTime(data[3].trim());
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

    }
}

