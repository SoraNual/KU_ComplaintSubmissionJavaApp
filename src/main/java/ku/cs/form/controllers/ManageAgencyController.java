package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import ku.cs.form.models.*;
import ku.cs.form.services.AgencyDataSource;
import ku.cs.form.services.ComplaintCategoryDataSource;
import ku.cs.form.services.SetTheme;
import ku.cs.form.services.UserDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ManageAgencyController {

    @FXML private AnchorPane manageAgencyAnchorPane;
    @FXML private Label agencyLabel;
    @FXML private TextField newAgencyTextField;
    @FXML private TextField changeAgencyTextField;
    @FXML private ListView<String> agencyListView;
    @FXML private ListView<String> staffListView;
    @FXML private ComboBox<String> agenciesComboBox;
    @FXML private ComboBox<String> setStaffAgencyComboBox;
    @FXML private ComboBox<String> staffComboBox;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<String> editCategoryAgencyComboBox;
    @FXML private ComboBox<String> editCategoryComboBox;
    @FXML private Label addAgencyErrorLabel;
    @FXML private Label editAgencyNameErrorLabel;
    @FXML private Label setStaffAgencyErrorLabel;
    @FXML private Label editCategoryErrorLabel;
    private UserList usersList;
    private UserDataSource userDataSource;
    private ComplaintCategoryDataSource complaintCategoryDataSource;
    private ComplaintCategoryList complainCategoryList;
    private AgencyList agencies;
    private AgencyDataSource agencyDataSource;
    private HashMap<String, Agency> agencyHashMap;
    private HashMap<String, Staff> staffHashMap;

    @FXML public void initialize() {
        SetTheme setTheme = new SetTheme("admin");
        setTheme.setting();
        manageAgencyAnchorPane.getStylesheets().setAll("file:src/main/resources/ku/cs/styles/styles.css");

        userDataSource = new UserDataSource("data","users.csv");
        usersList = userDataSource.readData();
        agencyDataSource = new AgencyDataSource("data","agency.csv");
        agencies = agencyDataSource.readData();
        complaintCategoryDataSource = new ComplaintCategoryDataSource("data","complaintCategories.csv");
        complainCategoryList = complaintCategoryDataSource.readData();

        agencyHashMap = new HashMap<>();
        staffHashMap = new HashMap<>();
        setAgencyHashMap();
        setStaffHashMap();

        addAgencyInComboBox(agenciesComboBox);
        addAgencyInComboBox(setStaffAgencyComboBox);
        addCategoryComboBox();
        addEditCategoryAgencyComboBox();

        addStaffListComboBox();
        showAgencyListView();
        handleSelectedAgencyListView();
    }

    private void setAgencyHashMap() {
        for(Agency agency : agencies.getAgencies())
            agencyHashMap.put(agency.getName(),agency);
    }

    private void setStaffHashMap() {
        for(User user : usersList.getAllUsers()){
            if(user instanceof Staff) staffHashMap.put(user.getUsername(),(Staff) user);
        }
    }
    private void addCategoryComboBox() {
        for(ComplaintCategory category : complainCategoryList.getAllCategories())
            categoryComboBox.getItems().add(category.getName());
    }

    private void addEditCategoryAgencyComboBox(){
        for(Agency agency : agencies.getAgencies())
            editCategoryAgencyComboBox.getItems().add(agency.getName());
    }

    private void showAgencyListView() {
        agencyListView.getItems().clear();
        for(Agency agency : agencies.getAgencies())
            agencyListView.getItems().add(agency.getName()+
                    "\n[หมวดหมู่ที่รับผิดชอบ]\n" + agency.getCategory());
        agencyListView.refresh();
    }

    @FXML
    private void handleAddAgencyBtn(ActionEvent actionEvent) {
        String newAgencyName = newAgencyTextField.getText();
        String category = categoryComboBox.getValue();

        String error = "";

        if(agencyHashMap.containsKey(newAgencyName)) {
            System.out.println(agencyHashMap.containsKey(newAgencyName));
            error += "ชื่อหน่วยงาานถูกใช้ไปแล้ว\n";
        } if(newAgencyName.equals("")) {
            error += "กรุณาใส่ชื่อหน่วยงาน\n";
        } if(category == null) {
            error += "กรุณาใส่หมวดหมู่\n";
        } if(error.equals("")) {
            Agency newAgency = new Agency(newAgencyName,category);
            agencies.addAgency(newAgency);
            agencyHashMap.put(newAgencyName,newAgency);
            agencyDataSource.writeData(agencies);
            addAgencyInComboBox(agenciesComboBox);
            newAgencyTextField.setText("");
            showAgencyListView();
        }
        addAgencyErrorLabel.setText(error);

    }

    @FXML
    private void handleChangeAgencyNameBtn(ActionEvent actionEvent) {
        String oldAgencyName = agenciesComboBox.getValue();
        String newAgencyName = changeAgencyTextField.getText();

        String error = "";

        if(agencyHashMap.containsKey(newAgencyName)) {
            error += "ชื่อหน่วยงานถูกใช้ไปแล้ว\n";
        } if(newAgencyName.equals("")) {
            error += "กรุณาใส่ชื่อหน่วยงานใหม่\n";
        } if(oldAgencyName == null) {
            error += "กรุณาใส่ชื่อหน่วยงานที่จะเปลี่ยน\n";
        } if(error.equals("")) {
            Agency targetAgency = agencyHashMap.get(oldAgencyName);
            targetAgency.setName(newAgencyName);

            for(Staff staff : staffHashMap.values()){
                if(staff.getAgency().equals(oldAgencyName))
                    staff.setAgency(newAgencyName);

            }

            userDataSource.writeData(usersList);
            changeAgencyTextField.setText("");
            agencyDataSource.writeData(agencies);
            agenciesComboBox.getItems().clear();
            addAgencyInComboBox(agenciesComboBox);
            showAgencyListView();

        }

        editAgencyNameErrorLabel.setText(error);
    }

    @FXML private void handleBackButton() {
        try {
            com.github.saacsos.FXRouter.goTo("admin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleSelectedAgencyListView() {

        agencyListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String>
                                                observable,
                                        String oldValue, String newValue) {
                        showStaffListView(newValue.split("\n")[0]);
                        agencyLabel.setText(newValue.split("\n")[0]);
                    }
                });
    }

    private void showStaffListView(String agency) {
        staffListView.getItems().clear();
        for(User user : usersList.getAllUsers()){
            if(user instanceof Staff && ((Staff) user).getAgency().equals(agency)){
                staffListView.getItems().add(user.getUsername());
            }
        }
    }

    private void addStaffListComboBox() {
        for(User user : usersList.getAllUsers()){
            if(user instanceof Staff)
                staffComboBox.getItems().add(user.getUsername());
        }
    }

    @FXML private void handleStaffComboBox(ActionEvent actionEvent){

        if(!staffComboBox.getValue().equals("")){
            String staffAgency = staffHashMap.get(staffComboBox.getValue()).getAgency();

            setStaffAgencyComboBox.getItems().clear();
            for(Agency agency : agencies.getAgencies()){
                if(!agency.getName().equals(staffAgency)) {
                    setStaffAgencyComboBox.getItems().add(agency.getName());
                }
            }
            setStaffAgencyComboBox.setValue(staffAgency);
        }

    }

    @FXML private void handleSetAgencyToStaffBtn(ActionEvent actionEvent){
        String staff = staffComboBox.getValue();
        String agency = setStaffAgencyComboBox.getValue();

        String error = "";
        if(staff == null){
            error += "กรุณาเลือกเจ้าหน้าที่\n";
        } if(agency == null) {
            error += "กรุณาเลือกหน่วยงาน\n";
        } if(error.equals("")) {
                staffHashMap.get(staff).setAgency(agency);
                userDataSource.writeData(usersList);
                staffComboBox.setValue("");
                setStaffAgencyComboBox.setValue("");
            }
        setStaffAgencyErrorLabel.setText(error);
    }

    private void addAgencyInComboBox(ComboBox<String> comboBox) {
        comboBox.getItems().clear();
        for(Agency agency : agencies.getAgencies())
            comboBox.getItems().add(agency.getName());
    }

    @FXML private void handleEditCategoryAgencyComboBox(ActionEvent actionEvent) {

        if(!editCategoryAgencyComboBox.getValue().equals("")){
            Agency agency = agencyHashMap.get(editCategoryAgencyComboBox.getValue());
            String category = agency.getCategory();

            editCategoryComboBox.getItems().clear();
            for(ComplaintCategory c : complainCategoryList.getAllCategories()){
                if(!c.getName().equals(category)) {
                    editCategoryComboBox.getItems().add(c.getName());
                }
            }
            editCategoryComboBox.setValue(category);
        }
    }

    @FXML private void handleEditCategoryBtn(ActionEvent actionEvent) {
        Agency agency = agencyHashMap.get(editCategoryAgencyComboBox.getValue());
        String category = editCategoryComboBox.getValue();

        String error = "";

        if(agency == null) {
            error += "กรุณาเลือกหน่วยงาน\n";
        } if (category == null) {
            error += "กรุณาเลือกหมวดหมู่\n";
        } if(error.equals("")) {
            agency.setCategory(category);
            agencyDataSource.writeData(agencies);
            editCategoryAgencyComboBox.setValue("");
            editCategoryComboBox.setValue("");
        }
        editCategoryErrorLabel.setText(error);

    }

}
