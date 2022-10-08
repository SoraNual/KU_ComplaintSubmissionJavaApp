package ku.cs.form.models;

import java.util.ArrayList;

public class ComplaintCategoryList {
    private ArrayList<ComplaintCategory> reportCategories;

    public ComplaintCategoryList() {
        this.reportCategories = new ArrayList<>();
    }
    public void addCategory(ComplaintCategory complaintCategory) {
        reportCategories.add(complaintCategory);
    }
    public ArrayList<ComplaintCategory> getAllCategories() {
        return reportCategories;
    }

    @Override
    public String toString() {
        return "ReportCategoryList{" +
                "reportCategories=" + reportCategories +
                '}';
    }
}
