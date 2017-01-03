package io2016.Controllers;

import io2016.Supervisor;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ishfi on 13.12.2016.
 */
public class StudentPreferencesController extends PreferencesController{
    @FXML private Button saveButton;
    @FXML private HBox preferencesBox;

    @FXML
    private void saveClicked() {
        ArrayList<ObservableList<Integer>> preferredHours = new ArrayList<>();
        preferredHours.add(moListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(tuListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(weListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(thListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(frListView.getSelectionModel().getSelectedIndices());

        supervisor.setHoursPreferences(preferredHours);
        try{
            supervisor.save();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Poprawnie zapisano preferencje godzin!");
            alert.showAndWait();

            //Disabling further edition of preferences and saving them in this session
            saveButton.setDisable(true);
            preferencesBox.setDisable(true);
        } catch (SQLException | IOException e) {
            errorAlert(e);
        }
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
}