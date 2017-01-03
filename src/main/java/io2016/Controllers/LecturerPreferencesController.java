package io2016.Controllers;

import io2016.Supervisor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ishfi on 14.12.2016.
 */
public class LecturerPreferencesController extends PreferencesController {
    @FXML private Button saveButton;
    @FXML private HBox preferencesBox;

    private ObservableList<String> roomList = FXCollections.observableArrayList();

    @FXML
    private void saveClicked() throws IOException, SQLException {
        ObservableList<String> prederedRooms = null;
        prederedRooms = roomListView.getSelectionModel().getSelectedItems();

        ArrayList<ObservableList<Integer>> preferredHours = new ArrayList<>();
        preferredHours.add(moListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(tuListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(weListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(thListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(frListView.getSelectionModel().getSelectedIndices());

        supervisor.setRoomsPreferences(prederedRooms);
        supervisor.setHoursPreferences(preferredHours);
        try{
            supervisor.save();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Poprawnie zapisano preferencje godzin oraz sal!");
            alert.showAndWait();

            //Disabling further edition of preferences and saving them in this session
            saveButton.setDisable(true);
            preferencesBox.setDisable(true);

        } catch (SQLException | IOException e) {
            errorAlert(e);
        }
    }

    public void setSupervisor(Supervisor supervisor) throws SQLException {
        this.supervisor = supervisor;

        // TODO: consider whether this should be here?
        //filling room list with data
        roomList = supervisor.getRoomsList();
        roomListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        roomListView.setItems(roomList);
    }
}