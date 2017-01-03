package io2016.Controllers;

import io2016.Supervisor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by ishfi on 03.01.2017.
 */
public class PreferencesController {
    @FXML protected ListView<String> moListView;
    @FXML protected ListView<String> tuListView;
    @FXML protected ListView<String> weListView;
    @FXML protected ListView<String> thListView;
    @FXML protected ListView<String> frListView;
    @FXML protected ListView<String> roomListView;
    @FXML protected Button logoutButton;
    protected Supervisor supervisor;

    protected ObservableList<String> hourSections = FXCollections.observableArrayList(
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
    protected void logoutClicked()  {
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

    protected void errorAlert(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");
        TextArea textArea = new TextArea(exceptionText);
    }
}
