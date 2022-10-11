package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ku.cs.form.models.AgencyList;
import ku.cs.form.models.UserList;
import ku.cs.form.services.AgencyDataSource;
import ku.cs.form.services.UserDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class ManageAgencyController {
    @FXML private TextField newAgencyTextField;
    @FXML private TextField changeAgencyTextField;
    @FXML private ListView<String> agencyListView;

    private UserList usersList;
    private UserDataSource userDataSource;
    private AgencyList agencies;
    private AgencyDataSource agencyDataSource;
    @FXML private ChoiceBox<String> agenciesChoiceBox;

    @FXML public void initialize() {
        userDataSource = new UserDataSource("data","users.csv");
        usersList = userDataSource.readData();
        agencyDataSource = new AgencyDataSource("data","agency.csv");
        agencies = agencyDataSource.readData();
        agenciesChoiceBox.getItems().addAll(agencies.getAgencies());
        showAgencyListView();
    }

    private void showAgencyListView() {
        agencyListView.getItems().clear();
        agencyListView.getItems().addAll(agencies.getAgencies());
        agencyListView.refresh();
    }

    @FXML
    private void handleAddAgencyBtn(ActionEvent actionEvent) {
        String newAgency = newAgencyTextField.getText();
        addAgency(newAgency);
        System.out.println(agencies.getAgencies());
        agencyDataSource.writeData(agencies);
        agenciesChoiceBox.getItems().clear();
        agenciesChoiceBox.getItems().addAll(agencies.getAgencies());
        newAgencyTextField.setText("");
        showAgencyListView();
    }



    @FXML
    private void handleChangeAgencyNameBtn(ActionEvent actionEvent) {
        String oldAgency = agenciesChoiceBox.getValue();
        String newAgency = changeAgencyTextField.getText();
        agencies.changeAgency(oldAgency,newAgency);
        changeAgencyTextField.setText("");
        agencyDataSource.writeData(agencies);
        agenciesChoiceBox.getItems().clear();
        agenciesChoiceBox.getItems().addAll(agencies.getAgencies());
        showAgencyListView();
    }

    private void addAgency(String agencyName) {
        agencies.addAgency(agencyName);
    }

    @FXML private void handleBackButton() {
        try {
            com.github.saacsos.FXRouter.goTo("admin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
