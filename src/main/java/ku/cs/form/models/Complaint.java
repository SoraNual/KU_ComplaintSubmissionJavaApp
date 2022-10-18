package ku.cs.form.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Complaint {
    private String topic;
    private String complainantUsername; //ผู้ร้องเรียน
    private LocalDateTime submitTime;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private String basicDetail;
    private String basicDetailWithNewLine;
    private String category;
    private String additionalDetail;
    private String additionalDetailWithNewLine;;
    private String status; // 3 status : pending, in progress, finish
    private int votePoint;
    private ArrayList<String> positiveVoter;
    private ArrayList<String> negativeVoter;
    private String solution;
    private String solutionWithNewLine;

    public Complaint(String topic, String complainantUsername, String category, String status, int votePoint) {
        this.topic = topic;
        this.complainantUsername = complainantUsername;
        this.submitTime = LocalDateTime.now();
        this.category = category;
        this.status = status;
        this.votePoint = votePoint;
        this.solution = "กำลังตรวจสอบ";
        positiveVoter = new ArrayList<>();
        negativeVoter = new ArrayList<>();
    }

    public Complaint(String topic, String complainantUsername, String category) {
        this(topic, complainantUsername, category, "รอการตรวจสอบจากเจ้าหน้าที่", 0);
    }


    public void addNegative(String[] negative){
        negativeVoter.addAll(List.of(negative));
    }
    public void addPositive(String[] positive){
        positiveVoter.addAll(List.of(positive));
    }

    public String getTopic() { return topic; }
    public String getCategory() { return category; }
    public String getBasicDetail() { return basicDetail; }
    public String getStatus() { return status; }
    public int getVotePoint() { return votePoint; }

    public String getComplainantUsername() {
        return complainantUsername;
    }

    public String getSubmitTime() {
        return submitTime.format(format);
    }
    public LocalDateTime getLiteralSubmitTime(){
        return submitTime;
    }
    public String getAdditionalDetail() {
        return additionalDetail;
    }

    public String getSolution() {
        return solution;
    }

    public String getBasicDetailWithNewLine() {
        return basicDetailWithNewLine;
    }

    public String getAdditionalDetailWithNewLine() {
        return additionalDetailWithNewLine;
    }

    public void setBasicDetail(String basicDetail) {
        this.basicDetail = basicDetail;
        setBasicDetailWithNewLine(basicDetail.replace("NEWLINE","\n"));
    }

    public void setAdditionalDetail(String additionalDetail) {
        this.additionalDetail = additionalDetail;
        setAdditionalDetailWithNewLine(additionalDetail.replace("NEWLINE","\n"));

    }

    public void setBasicDetailWithNewLine(String basicDetailWithNewLine) {
        this.basicDetailWithNewLine = basicDetailWithNewLine;
    }

    public void setAdditionalDetailWithNewLine(String additionalDetailWithNewLine) {
        this.additionalDetailWithNewLine = additionalDetailWithNewLine;
    }

    public void setSolution(String solution) {
        this.solution = solution;
        setSolutionWithNewLine(solution.replace("NEWLINE","\n"));
    }

    public void setSolutionWithNewLine(String solutionWithNewLine) {
        this.solutionWithNewLine = solutionWithNewLine;
    }

    public void setSubmitTime(LocalDateTime submitTime) {
        this.submitTime = submitTime;
    }

    public void setSubmitTime(String s) {
        //change string to LocalDateTime
        this.submitTime = LocalDateTime.parse(s,format);
    }

    public void addVotePoint() { votePoint++; }

//    @Override
//    public String toString() {
//        return "<[ " + getVotePoint() + " ]> " + getTopic();
//    }

    @Override
    public String toString() {
        //FOR TESTING
        return topic + " " + complainantUsername + " " + submitTime + " VotePoint:" + votePoint;
    }

    public void addPositiveVote(User voter){
        if(checkIfVotedAlready(voter,positiveVoter)) {
            votePoint--;
            positiveVoter.remove(voter.getUsername());
        }
        else if (checkIfVotedAlready(voter,negativeVoter)) {
            //ไม่เจอคนโหวตในคนแง่ลบดังนั้นโหวตได้
            votePoint+=2;
            positiveVoter.add(voter.getUsername());
            negativeVoter.remove(voter.getUsername());
        } else if (!checkIfVotedAlready(voter,positiveVoter)) {
            votePoint++;
            positiveVoter.add(voter.getUsername());
        }
    }

    public void addNegativeVote(User voter){
        if(checkIfVotedAlready(voter,negativeVoter)) {
            votePoint++;
            negativeVoter.remove(voter.getUsername());
        }
        else if (checkIfVotedAlready(voter,positiveVoter)) {
            votePoint-=2;
            negativeVoter.add(voter.getUsername());
            positiveVoter.remove(voter.getUsername());
        } else if (!checkIfVotedAlready(voter,negativeVoter)) {
            votePoint--;
            negativeVoter.add(voter.getUsername());
        }
    }

    private boolean checkIfVotedAlready(User voter,ArrayList<String> voters){
        for(String voted: voters){
            if(voted.equals(voter.getUsername())) return true;
        }
        return false;
    }

    public String getPositiveVoter() {
        if(!positiveVoter.isEmpty()) {
            String returnPosVal = positiveVoter.get(0);
            if(positiveVoter.size()>1) {
                for (int i = 1; i<positiveVoter.size(); i++) {
                    returnPosVal = returnPosVal+ "#" + positiveVoter.get(i);
                }
            }
            return returnPosVal;
        }
        return "";
    }

    public String getNegativeVoter() {
        if (!negativeVoter.isEmpty()) {
            String returnNegVal = negativeVoter.get(0);
            if(negativeVoter.size()>1) {
                for (int i = 1; i<negativeVoter.size(); i++) {
                    returnNegVal = returnNegVal + "#" + negativeVoter.get(i);
                }
            }
            return returnNegVal;
        }
        return "";
    }
}
