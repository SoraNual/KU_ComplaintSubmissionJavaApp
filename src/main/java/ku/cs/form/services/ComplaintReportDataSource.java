package ku.cs.form.services;
import ku.cs.form.models.ComplaintReport;
import ku.cs.form.models.ComplaintReportHashMap;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ComplaintReportDataSource implements DataSource<ComplaintReportHashMap> {

    private String directoryName;
    private String fileName;

    public ComplaintReportDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
    }

    @Override
    public ComplaintReportHashMap readData() {

        ComplaintReportHashMap complaintHashMap = new ComplaintReportHashMap();
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

                    ComplaintReport complaintReport = new ComplaintReport(data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            data[3].trim());
                    complaintHashMap.addReport(complaintReport);

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
        return complaintHashMap;
    }

    @Override
    public void writeData(ComplaintReportHashMap complaintReportHashMap) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            for (String k : complaintReportHashMap.getAllReports().keySet()){
                ComplaintReport complaintReport = complaintReportHashMap.getAllReports().get(k);

                String line = complaintReport.getTopic() + ","
                        + complaintReport.getSubmitTime() + ","
                        + complaintReport.getComplaintCategory() + ","
                        + complaintReport.getComplaintDetail();

                buffer.write(line);
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
