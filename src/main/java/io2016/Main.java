package io2016;

import io2016.Controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by ishfi on 13.12.2016.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Supervisor supervisor = new Supervisor();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/loginWindow.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.setSupervisor(supervisor);

        primaryStage.setTitle("IO 2016");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
