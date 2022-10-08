package ku.cs.form.services;

import ku.cs.form.models.ComplaintCategory;
import ku.cs.form.models.ComplaintCategoryList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ReportCategoryDataSource implements DataSource{
    private String directoryName;
    private String fileName;

    public ReportCategoryDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
    }

    @Override
    public ComplaintCategoryList readData() {
        ComplaintCategoryList reportCategories = new ComplaintCategoryList();
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
                System.out.println(data);
                ComplaintCategory complaintCategory = new ComplaintCategory(data[0], data[1], data[3], Boolean.valueOf(data[2]));

                reportCategories.addCategory(complaintCategory);
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
        System.out.println(reportCategories);
        return reportCategories;
    }

    @Override
    public void writeData(Object o) {

    }
}
