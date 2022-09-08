package ku.cs.form.services;

import ku.cs.form.models.Nisit;
import ku.cs.form.models.UserList;

import java.io.*;

public class NisitRegistration implements Registration {

    private String directoryName;

    private String fileName;

    public NisitRegistration(String directoryName, String fileName) {
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
    @Override
    public String registrationCheck(String name, String username, String password, String confirmPassword) {
        String error = "";
        if(name.isBlank())   error += "ใส่ชื่อมาด้วย!\n";
        if(username.isBlank())   error += "อย่าปล่อยให้ช่อง Username ว่างสิ!\n";
        if(password.isBlank())   error += "ใส่รหัสผ่านมาด้วย!\n";
        if(confirmPassword.isBlank())   error += "โปรดยืนยันรหัสผ่าน!\n";

        if(!password.isBlank() && !confirmPassword.isBlank() && confirmationPasswordCheck(password, confirmPassword))
            error += "รหัสผ่านไม่ตรงกัน!\n";
        if(usernameValidationCheck(username))     error += "username ของคุณซ้ำ!\n";

        return error;
    }
    @Override
    public boolean usernameValidationCheck(String newUserName) {
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
                if(data[0].equals(newUserName)) return true;
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

        return false;
    }
    @Override
    public boolean confirmationPasswordCheck(String password, String confirmationPassword){
        if(password.equals(confirmationPassword)) return false;
        return true;
    }

    public void addNisit(Nisit nisit) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file,true);
            buffer = new BufferedWriter(writer);

            String line = nisit.getUsername() + ","
                        + nisit.getPassword() + ",nisit,"
                        + nisit.getName() + ","
                        + nisit.getUserStatus() + ","
                        + nisit.getLoginAttempt();
            buffer.append(line);
            buffer.newLine();

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
