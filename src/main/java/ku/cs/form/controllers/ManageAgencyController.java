package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ku.cs.form.models.*;
import ku.cs.form.services.AgencyDataSource;
import ku.cs.form.services.UserDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class ManageAgencyController {
    @FXML private Label agencyLabel;
    @FXML private TextField newAgencyTextField;
    @FXML private TextField changeAgencyTextField;
    @FXML private ListView<String> agencyListView;
    @FXML private ListView<String> staffListView;
    @FXML private ChoiceBox<String> agenciesChoiceBox;
    @FXML private ChoiceBox<String> setStaffAgencyChoiceBox;
    @FXML private ChoiceBox<String> staffChoiceBox;
    private UserList usersList;
    private UserDataSource userDataSource;
    private AgencyList agencies;
    private AgencyDataSource agencyDataSource;

    @FXML public void initialize() {
        userDataSource = new UserDataSource("data","users.csv");
        usersList = userDataSource.readData();
        agencyDataSource = new AgencyDataSource("data","agency.csv");
        agencies = agencyDataSource.readData();
        agenciesChoiceBox.getItems().addAll(agencies.getAgencies());
        setStaffAgencyChoiceBox.getItems().addAll(agencies.getAgencies());
        addStaffListChoiceBox();
        showAgencyListView();
        handleSelectedAgencyListView();
    }

    private void showAgencyListView() {
        agencyListView.getItems().clear();
        agencyListView.getItems().addAll(agencies.getAgencies());
        agencyListView.refresh();
    }

    @FXML
    private void handleAddAgencyBtn(ActionEvent actionEvent) {
        String newAgency = newAgencyTextField.getText();
        if(!(newAgency.equals(""))){
            agencies.addAgency(newAgency);
            System.out.println(agencies.getAgencies());
            agencyDataSource.writeData(agencies);
            agenciesChoiceBox.getItems().clear();
            agenciesChoiceBox.getItems().addAll(agencies.getAgencies());
            newAgencyTextField.setText("");
            showAgencyListView();
        }
    }



    @FXML
    private void handleChangeAgencyNameBtn(ActionEvent actionEvent) {
        String oldAgency = agenciesChoiceBox.getValue();
        String newAgency = changeAgencyTextField.getText();
        if(!(newAgency.equals(""))){
            agencies.changeAgency(oldAgency,newAgency);

            for(User user : usersList.getAllUsers()){
                if(user instanceof Staff && ((Staff) user).getAgency().equals(oldAgency))
                    ((Staff) user).setAgency(newAgency);
            }

            userDataSource.writeData(usersList);
            changeAgencyTextField.setText("");
            agencyDataSource.writeData(agencies);
            agenciesChoiceBox.getItems().clear();
            agenciesChoiceBox.getItems().addAll(agencies.getAgencies());
            showAgencyListView();
        }
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
                        showStaffListView(newValue);
                        agencyLabel.setText(newValue);
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

    private void addStaffListChoiceBox() {
        for(User user : usersList.getAllUsers()){
            if(user instanceof Staff)
                staffChoiceBox.getItems().add(user.getUsername());
        }
    }

    @FXML private void handleSetAgencyToStaffBtn(ActionEvent actionEvent){
        String staff = staffChoiceBox.getValue();
        String agency = setStaffAgencyChoiceBox.getValue();
        if(!(staff.equals("") && agency.equals(""))){
            for(User user : usersList.getAllUsers()){
                if(user.getUsername().equals(staff)) ((Staff) user).setAgency(agency);
                userDataSource.writeData(usersList);
                staffChoiceBox.setValue("");
                setStaffAgencyChoiceBox.setValue("");
            }
        }
    }





}
