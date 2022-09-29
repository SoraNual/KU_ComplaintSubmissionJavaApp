package ku.cs.form.services;

import ku.cs.form.models.Report;
import ku.cs.form.models.ReportList;

import java.io.*;

public class ReportFileDataSource implements DataSource {
    private String dirName;
    private String fileName;

    public ReportFileDataSource(String dirName, String fileName) {
        this.dirName = dirName;
        this.fileName = fileName;
    }

    private void checkFileIsExisted() {
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

    @Override
    public ReportList readData() {
        ReportList reportList = new ReportList();

        String filePath = dirName + File.separator + fileName;
        File file = new File(filePath);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line  = "";
            while ( (line = buffer.readLine()) != null ) {
                String[] data = line.split(",");
                Report report = new Report(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        Integer.parseInt(data[4].trim())
                );
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
    public void writeData(Object o) {
        Report report = (Report) o;

        String path = dirName + File.separator + fileName;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;
        try {
            writer = new FileWriter(file, true);
            buffer = new BufferedWriter(writer);

            // reports.csv - topic, category, detail, status, vote-point
            String line = report.getTopic() + ","
                        + report.getCategory() + ","
                        + report.getDetail() + ","
                        + report.getStatus() + ","
                        + report.getVotePoint();
        } catch (IOException e) {
            throw  new RuntimeException(e);
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
