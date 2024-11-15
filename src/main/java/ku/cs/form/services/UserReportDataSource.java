package ku.cs.form.services;

import ku.cs.form.models.UserReport;
import ku.cs.form.models.UserReportHashMap;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class UserReportDataSource implements DataSource<UserReportHashMap>{

    private String directoryName;
    private String fileName;

    public UserReportDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
    }

    @Override
    public UserReportHashMap readData() {
        UserReportHashMap userComplaintHashMap = new UserReportHashMap();

        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);
            String line = "";
            while ((line = buffer.readLine()) != null) {
                UserReport userReport = null;
                String[] data = line.split(",");
                    if(data.length == 4){
                        userReport = new UserReport(data[0].trim(),data[1].trim(),data[2].trim(),data[3].trim());
                    } else {
                        userReport = new UserReport(data[0].trim(),data[1].trim(),data[2].trim(),null);
                    }
                    userComplaintHashMap.addReport(userReport);

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
    public void writeData(UserReportHashMap userComplaintHashMap) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            for (String key : userComplaintHashMap.getAllReports().keySet()){
                UserReport userReport = userComplaintHashMap.getAllReports().get(key);
                String data = key + "," + userReport.getComplaintCategory() + "," + userReport.getComplaintDetail();
                if(userReport.getRequest_permission_detail() != null)
                    data += "," + userReport.getRequest_permission_detail();
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
