package ku.cs.form.services;

import ku.cs.form.models.Complaint;
import ku.cs.form.models.ComplaintList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ComplaintFileDataSource implements DataSource<ComplaintList> {
    private String directoryName;
    private String fileName;

    public ComplaintFileDataSource(String directoryName, String fileName) {
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
    public ComplaintList readData() {
        ComplaintList complaintList = new ComplaintList();

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
                // Submit time, Topic, username, basic details, category, additional details, status,vote points, solution
                // public Report(String topic, String complainantUsername,
                // String basicDetail, String category, String additionalDetail,String status, int votePoint, String solution)
                Complaint complaint = new Complaint(
                        data[1].trim(),
                        data[2].trim(),
                        data[4].trim(),
                        data[6].trim(),
                        Integer.parseInt(data[7].trim()));
                complaint.setBasicDetail(data[3].trim());
                complaint.setAdditionalDetail(data[5].trim());
                complaint.setSubmitTime(data[0].trim());
                complaint.setSolution(data[8].trim());
                complaintList.addComplaint(complaint);
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

        return complaintList;
    }

    public void updateData(Complaint newComplaint, String solution, String status) {
        ComplaintList complaintList = readData();
        for (Complaint complaint : complaintList.getAllComplaints()) {
            if (complaint.getComplainantUsername().equals(newComplaint.getComplainantUsername()) &&
                        complaint.getTopic().equals(newComplaint.getTopic()))
            {
                complaint.setSolution(solution);
                complaint.setStatus(status);

                writeData(complaintList);
                break;
            }
        }
    }

    @Override
    public void writeData(ComplaintList complaintList) {
        String filePath = "data" + File.separator + "complaints.csv";
        File file = new File(filePath);
        checkFileIsExisted();

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);
            // Submit time, Topic, username, basic details, category, additional details, status, vote points, solution
            for(Complaint complaint : complaintList.getAllComplaints()){
                String line = complaint.getSubmitTime() + "," +
                        complaint.getTopic() + "," +
                        complaint.getComplainantUsername() + "," +
                        complaint.getBasicDetail()+ "," +
                        complaint.getCategory() + "," +
                        complaint.getAdditionalDetail() + "," +
                        complaint.getStatus() + "," +
                        complaint.getVotePoint() + "," +
                        complaint.getSolution().replace("\n", "NEWLINE");
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
