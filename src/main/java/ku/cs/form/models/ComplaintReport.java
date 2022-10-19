package ku.cs.form.models;

public class ComplaintReport {
        private String topic;
        private String submitTime;
        private String complaintCategory;
        private String complaintDetail;

        public ComplaintReport(String topic, String submitTime, String complaintCategory, String complaintDetail) {
                this.topic = topic;
                this.complaintCategory = complaintCategory;
                this.complaintDetail = complaintDetail;
                this.submitTime = submitTime;
        }

        public String getTopic() {
                return topic;
        }

        public String getComplaintCategory() {
                return complaintCategory;
        }

        public String getComplaintDetail() {
                return complaintDetail;
        }

        public String getSubmitTime() {
                return submitTime;
        }

        @Override
        public String toString() {
                return "ComplaintReport{" +
                        "topic='" + topic + '\'' +
                        ", submitTime='" + submitTime + '\'' +
                        ", complaintCategory='" + complaintCategory + '\'' +
                        ", complaintDetail='" + complaintDetail + '\'' +
                        '}';
        }
}
