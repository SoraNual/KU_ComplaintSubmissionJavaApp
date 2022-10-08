package ku.cs.form.services;

import ku.cs.form.models.Report;
import ku.cs.form.models.ReportList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ReportFileDataSource implements DataSource<ReportList> {
    private String directoryName;
    private String fileName;

    public ReportFileDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
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
    public ReportList readData() {
        ReportList reportList = new ReportList();

        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);

            String line  = "";
            while ( (line = buffer.readLine()) != null ) {
                String[] data = line.split(",");
                // Submit time, Topic, username, basic details, category, additional details, status,vote points
                // public Report(String topic, String complainantUsername,
                // String basicDetail, String category, String additionalDetail,String status, int votePoint)
                Report report = new Report(
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim(),
                        data[5].trim(),
                        data[6].trim(),
                        Integer.parseInt(data[7].trim())
                );
                report.setSubmitTime(data[0].trim());
                reportList.addReport(report);
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

        return reportList;
    }

    @Override
    public void writeData(ReportList reportList) {
        String filePath = "data" + File.separator + "reports.csv";
        File file = new File(filePath);
        checkFileIsExisted();

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);
            // Submit time, Topic, username, basic details, category, additional details, status, vote points
            for(Report report : reportList.getAllReports()){
                String line = report.getSubmitTime() + "," +
                        report.getTopic() + "," +
                        report.getComplainantUsername() + "," +
                        report.getBasicDetail()+ "," +
                        report.getCategory() + "," +
                        report.getAdditionalDetail() + "," +
                        report.getStatus() + "," +
                        report.getVotePoint();
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
