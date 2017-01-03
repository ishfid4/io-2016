package io2016.Controllers;

import io2016.Supervisor;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by ishfi on 14.12.2016.
 */
public class LoginController {
    @FXML private TextField indexTextField;
    @FXML private TextField lastnameTextField;
    @FXML private Label loginLabelStatus;
    @FXML private Button buttonSignIn;
    @FXML private CheckBox lecturerCheck;
    private Supervisor supervisor;

    @FXML
    public void loginClicked() throws SQLException {
        supervisor.setStudent(!lecturerCheck.isSelected());

        if (indexTextField.getText().isEmpty()){
            Platform.runLater(() -> loginLabelStatus.setText("Prosze wprowadzic numer indeksu"));
        }else if (lastnameTextField.getText().isEmpty()){
            Platform.runLater(() -> loginLabelStatus.setText("Prosze wprowadzic swoje nazwisko"));
        }else{
            supervisor.login(indexTextField.getText(), lastnameTextField.getText(), this::loggedIn);
        }
    }

    private void loggedIn(Boolean success){
        if(success){
            Stage stage = (Stage)buttonSignIn.getScene().getWindow();
            FXMLLoader loader;
            Parent root = null;

            //Removing previous preferences if they exist
            try {
                supervisor.removePreviousUserPreferences();
            } catch (SQLException ex) {
                //handle errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

            try {
                    if (supervisor.isStudent()){
                        loader = new FXMLLoader(getClass().getResource("/layout/studentWindow.fxml"));
                        root = loader.load();

                        StudentPreferencesController controller = loader.getController();
                        controller.setSupervisor(supervisor);
                    }else{
                        loader = new FXMLLoader(getClass().getResource("/layout/lecturerWindow.fxml"));
                        root = loader.load();

                        LecturerPreferencesController controller = loader.getController();
                        controller.setSupervisor(supervisor);
                    }

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
        }else{
            Platform.runLater(() -> loginLabelStatus.setText("Niepoprawne dane logowania"));
        }
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
}
