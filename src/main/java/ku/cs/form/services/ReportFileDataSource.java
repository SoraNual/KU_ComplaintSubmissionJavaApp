package ku.cs.form.services;

import ku.cs.form.models.Report;
import ku.cs.form.models.ReportList;

import java.io.*;

public class ReportFileDataSource implements DataSource<ReportList> {
    private String dirName;
    private String fileName;

    public ReportFileDataSource(String dirName, String fileName) {
        this.dirName = dirName;
        this.fileName = fileName;
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
    public void writeData(ReportList reportList) {}
}
