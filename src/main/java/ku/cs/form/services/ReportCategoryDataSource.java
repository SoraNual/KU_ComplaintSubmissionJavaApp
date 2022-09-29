package ku.cs.form.services;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ReportCategoryDataSource implements DataSource{
    private String directoryName;
    private String fileName;

    public ReportCategoryDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
    }

    @Override
    public ArrayList<String> readData() {

        ArrayList<String> reportCategory = new ArrayList<String>();
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);
            String line = "";
            while ((line = buffer.readLine()) != null) {
                reportCategory.add(line);
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
        return reportCategory;
    }

    @Override
    public void writeData(Object o) {

    }
}
