package ku.cs.form.services;

import ku.cs.form.models.UserComplaint;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class UserReportDataSource implements DataSource<HashMap<String, UserComplaint>>{

    private String directoryName;
    private String fileName;

    public UserReportDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
    }

    @Override
    public HashMap<String, UserComplaint> readData() {
        HashMap<String, UserComplaint> userComplaintHashMap = new HashMap<>();

        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);
            String line = "";
            while ((line = buffer.readLine()) != null) {
                UserComplaint userComplaint = null;
                String[] data = line.split(",");
                if(data.length == 4){
                    userComplaint = new UserComplaint(data[0].trim(),data[1].trim(),data[2].trim(),data[3].trim());
                } else {
                    userComplaint = new UserComplaint(data[0].trim(),data[1].trim(),data[2].trim(),null);
                }
                userComplaintHashMap.put(data[0].trim(),userComplaint);
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
        return userComplaintHashMap;

    }

    @Override
    public void writeData(HashMap<String, UserComplaint> userComplaintHashMap) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            for (String key : userComplaintHashMap.keySet()){
                UserComplaint userComplaint = userComplaintHashMap.get(key);
                String data = key + "," + userComplaint.getComplaint_category() + "," + userComplaint.getComplaint_detail();
                if(userComplaint.getRequest_permission_detail() != null)
                    data += userComplaint.getRequest_permission_detail();
                buffer.write(data);
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
