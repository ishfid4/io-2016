package io2016.Controllers;

import io2016.Supervisor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ishfi on 14.12.2016.
 */
public class LecturerPreferencesController {
    @FXML private ListView<String> moListView;
    @FXML private ListView<String> tuListView;
    @FXML private ListView<String> weListView;
    @FXML private ListView<String> thListView;
    @FXML private ListView<String> frListView;
    @FXML private ListView<String> roomListView;
    @FXML private Button saveButton;
    @FXML private Button logoutButton;
    @FXML private HBox preferencesBox;
    private Supervisor supervisor;

    //This should not be hard coded, probably
    private ObservableList<String> hourSections = FXCollections.observableArrayList(
            "8:00 -> 9:00","9:00 -> 10:00","10:00 -> 11:00","11:00 -> 12:00","12:00 -> 13:00",
            "13:00 -> 14:00","14:00 -> 15:00","15:00 -> 16:00","16:00 -> 17:00",
            "17:00 -> 18:00","18:00 -> 19:00","19:00 -> 20:00","20:00 -> 21:00");
    private ObservableList<String> roomList = FXCollections.observableArrayList();

    public void initialize() {
        moListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        moListView.setItems(hourSections);
        tuListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tuListView.setItems(hourSections);
        weListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        weListView.setItems(hourSections);
        thListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        thListView.setItems(hourSections);
        frListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        frListView.setItems(hourSections);
    }

    @FXML
    private void logoutClicked()  {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader loader;
        Parent root = null;

        loader = new FXMLLoader(getClass().getResource("/layout/loginWindow.fxml"));
        try {
            root = loader.load();

            LoginController controller = loader.getController();
            controller.setSupervisor(supervisor);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        //Disabling further edition of preferences and saving them in this session
        saveButton.setDisable(true);
        preferencesBox.setDisable(true);

        supervisor.save();  //TODO: add some sort of popup if or sth if successfully saved

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