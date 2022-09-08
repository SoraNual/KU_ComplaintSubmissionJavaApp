package ku.cs.form.services;

import ku.cs.form.models.*;

import java.io.*;

public class UserData {
    private String directoryName;
    private String fileName;

    public UserData(String directoryName, String fileName) {
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

    public User usernamepasswordCheck(String username, String password) {
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
}
