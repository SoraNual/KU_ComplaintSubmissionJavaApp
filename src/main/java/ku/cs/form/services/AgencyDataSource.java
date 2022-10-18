package ku.cs.form.services;

import ku.cs.form.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class AgencyDataSource implements DataSource<AgencyList> {

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
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);
            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                Agency agency = new Agency(data[0].trim(), data[1].trim());
                agencies.addAgency(agency);
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
    public void writeData(AgencyList agencies) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            for (Agency agency : agencies.getAgencies()){
                String line = agency.getName() + "," + agency.getCategory();
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
