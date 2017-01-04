package io2016.Controllers;

import io2016.Supervisor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

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
    private ArrayList<String> previousPreferredRooms = new ArrayList<>();

    @FXML
    private void saveClicked() {
        ObservableList<String> preferredRooms = null;
        preferredRooms = roomListView.getSelectionModel().getSelectedItems();

        ArrayList<ObservableList<Integer>> preferredHours = new ArrayList<>();
        preferredHours.add(moListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(tuListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(weListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(thListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(frListView.getSelectionModel().getSelectedIndices());

        supervisor.setRoomsPreferences(preferredRooms);
        supervisor.setHoursPreferences(preferredHours);

        Boolean sthSelected = false;
        for(int i = 0; i < preferredHours.size(); ++i){
            if (preferredHours.get(i).size() != 0){
                sthSelected = true;
            }
        }
        if (preferredRooms.size() != 0){
            sthSelected = true;
        }

        if (sthSelected) {
            try{
                //Removing previous preferences if they exist and sth in list is selected
                supervisor.removePreviousUserPreferences();

                //Saving
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
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Brak wybranych preferencji!");
            alert.setContentText("Prosze wybrac preferowane godziny i sale!");

            alert.showAndWait();
        }
    }

    public void setSupervisor(Supervisor supervisor) throws SQLException {
        this.supervisor = supervisor;

        // TODO: consider whether this should be here?
        setUpRoomsView();
        setUpDaysViewWithPreviousPreferedHours();
    }

    private void setUpRoomsView() throws SQLException {
        //filling room list with data
        roomList = supervisor.getRoomsList();
        roomListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        roomListView.setItems(roomList);

        //Setting previous preferences if they exist
        previousPreferredRooms = supervisor.getPreviousPreferedRooms();

        if (previousPreferredRooms.size() != 0){
            for (String preferedRoom: previousPreferredRooms) {
                for(int i = 0; i < roomList.size(); ++i){
                    if (preferedRoom.equals(roomList.get(i))){
                        roomListView.getSelectionModel().select(i);
                    }
                }
            }
        }
    }

}