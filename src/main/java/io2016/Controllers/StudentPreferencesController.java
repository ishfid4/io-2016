package io2016.Controllers;

import io2016.Supervisor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;


import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ishfi on 13.12.2016.
 */
public class StudentPreferencesController {
    @FXML private ListView<String> moListView;
    @FXML private ListView<String> tuListView;
    @FXML private ListView<String> weListView;
    @FXML private ListView<String> thListView;
    @FXML private ListView<String> frListView;
    @FXML private Button saveButton;
    private Supervisor supervisor;

    //This should not be hard coded, probably
    private ObservableList<String> hourSections = FXCollections.observableArrayList(
            "8:00 -> 9:00","9:00 -> 10:00","10:00 -> 11:00","11:00 -> 12:00","12:00 -> 13:00",
            "13:00 -> 14:00","14:00 -> 15:00","15:00 -> 16:00","16:00 -> 17:00",
            "17:00 -> 18:00","18:00 -> 19:00","19:00 -> 20:00","20:00 -> 21:00");

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
    private void saveClicked() throws IOException, SQLException {
        //TODO: add sth to prevent multiple saves (one click = add records to db and generate output files)
        ArrayList<ObservableList<Integer>> preferredHours = new ArrayList<>();
        preferredHours.add(moListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(tuListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(weListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(thListView.getSelectionModel().getSelectedIndices());
        preferredHours.add(frListView.getSelectionModel().getSelectedIndices());

        supervisor.setHoursPreferences(preferredHours);
        supervisor.save(); //TODO: add some sort of popup if or sth if successfully saved
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
}