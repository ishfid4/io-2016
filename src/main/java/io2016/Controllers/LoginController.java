package io2016.Controllers;

import io2016.Supervisor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by ishfi on 14.12.2016.
 */
public class LoginController {
    @FXML private TextField loginTextField;
    @FXML private PasswordField passwordField;
    @FXML private Label loginLabelStatus;
    @FXML private Button buttonSignIn;
    private Supervisor supervisor;

    @FXML
    public void loginClicked(){
        // TODO: boolean student should be taken? from db
        loggedIn(true, supervisor.isStudent());
    }

    private void loggedIn(Boolean success, Boolean student){
        if(success){
            Platform.runLater(() -> {
                Stage stage = (Stage)buttonSignIn.getScene().getWindow();
                FXMLLoader loader;
                Parent root = null;

                try {
                    if (student){
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
                    //TODO: eliminate nullptr exception
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }else{
            Platform.runLater(() -> loginLabelStatus.setText("Nie poprawne dane logowania"));
        }
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
}
