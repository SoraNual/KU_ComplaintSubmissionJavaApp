package ku.cs.form.models;

import java.util.ArrayList;

public class ReportCategoryList {
    private ArrayList<ReportCategory> reportCategories;

    public ReportCategoryList() {
        this.reportCategories = new ArrayList<>();
    }
    public void addCategory(ReportCategory reportCategory) {
        reportCategories.add(reportCategory);
    }
    public ArrayList<ReportCategory> getAllCategories() {
        return reportCategories;
    }

    @Override
    public String toString() {
        return "ReportCategoryList{" +
                "reportCategories=" + reportCategories +
                '}';
    }
}
