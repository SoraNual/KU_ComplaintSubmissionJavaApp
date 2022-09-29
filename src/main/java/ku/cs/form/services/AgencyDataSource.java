package ku.cs.form.services;

import ku.cs.form.models.*;

import java.io.*;
import java.util.ArrayList;

public class AgencyDataSource implements DataSource {

    private String directoryName;
    private String fileName;

    public AgencyDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
    }

    @Override
    public AgencyList readData() {

        AgencyList agencies = new AgencyList();
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);
            String line = "";
            while ((line = buffer.readLine()) != null) {
                agencies.addAgency(line);
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
        return agencies;
    }

    @Override
    public void writeData(Object o) {

    }
}
