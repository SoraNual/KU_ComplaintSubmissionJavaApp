package ku.cs.form.services;

import ku.cs.form.models.Complaint;
import ku.cs.form.models.ComplaintList;
import ku.cs.form.models.User;
import ku.cs.form.models.UserList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
                complaint.setBasicDetail(data[3].trim().replace("NEWLINE", "\n"));
                complaint.setAdditionalDetail(data[5].trim().replace("NEWLINE", "\n"));
                complaint.setSubmitTime(data[0].trim());
                complaint.setSolution(data[8].trim().replace("NEWLINE", "\n"));
                if(data.length>=10){
                    complaint.addPositive(data[9].trim().split("#"));
                }
                if(data.length>=11){
                    complaint.addNegative(data[10].trim().split("#"));
                }
                if(data.length >= 12){
                    ArrayList<String> assignedStaff = new ArrayList<>();
                    assignedStaff.addAll(List.of(data[11].trim().split("#")));
                    complaint.setAssignedStaffs(assignedStaff);
                }
                complaintList.addComplaint(complaint);
            }
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
                ArrayList<String> newAssignedStaffs = new ArrayList<>();
                newAssignedStaffs.addAll(List.of(newComplaint.getAssignedStaff().split("#")));
                complaint.setSolution(solution);
                complaint.setStatus(status);
                complaint.setAssignedStaffs(newAssignedStaffs);

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
                        complaint.getBasicDetail().replace("\n", "NEWLINE") + "," +
                        complaint.getCategory() + "," +
                        complaint.getAdditionalDetail().replace("\n", "NEWLINE") + "," +
                        complaint.getStatus() + "," +
                        complaint.getVotePoint() + "," +
                        complaint.getSolution().replace("\n", "NEWLINE") +","+
                        complaint.getPositiveVoter() + "," +
                        complaint.getNegativeVoter() + "," +
                        complaint.getAssignedStaff();
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
    public void changeData(Complaint changedComplaint){
        ComplaintList complaintList = readData();
        int index = 0;
        for(Complaint complaint : complaintList.getAllComplaints()){
            if((complaint.getTopic()+complaint.getCategory()+complaint.getBasicDetail()+complaint.getComplainantUsername()).equals(changedComplaint.getTopic()+changedComplaint.getCategory()+changedComplaint.getBasicDetail()+changedComplaint.getComplainantUsername())){
                complaintList.getAllComplaints().set(index,changedComplaint);
                writeData(complaintList);
                break;
            }
            index++;
        }
    }
}
