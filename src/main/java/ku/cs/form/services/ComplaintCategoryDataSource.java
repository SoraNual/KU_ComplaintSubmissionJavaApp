package ku.cs.form.services;

import ku.cs.form.models.ComplaintCategory;
import ku.cs.form.models.ComplaintCategoryList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ComplaintCategoryDataSource implements DataSource<ComplaintCategoryList>{
    private String directoryName;
    private String fileName;

    public ComplaintCategoryDataSource(String directoryName, String fileName) {
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
                ComplaintCategory complaintCategory = new ComplaintCategory(data[0], data[1], Boolean.valueOf(data[2]), data[3]);

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
    public void writeData(ComplaintCategoryList complaintCategoryList) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            for (ComplaintCategory category : complaintCategoryList.getAllCategories()){
                String line;
                line = category.getName() + ","
                        + category.getAdditionalDetailTitle() + ","
                        + category.getImageNeeded() + ","
                        + category.getAdditionalImageTitle();
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
