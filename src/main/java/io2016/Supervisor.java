package io2016;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by ishfi on 14.12.2016.
 */
public class Supervisor {
    //private int groupId = 1; //TODO: need to obtain from db (after login?) IS IT NEEDED?
    private int userId = 6; //TODO: need to obtain from db (after login?)
    private boolean student; //TODO: need to obtain from db (after login?)

    private ObservableList<String> roomsList;
    private ArrayList<ObservableList<Integer>> hoursPreferences;
    private ObservableList<String> roomsPreferences;

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public void setHoursPreferences(ArrayList<ObservableList<Integer>> hoursPreferences) {
        this.hoursPreferences = hoursPreferences;
    }

    public void setRoomsPreferences(ObservableList<String> roomsPreferences) {
        this.roomsPreferences = roomsPreferences;
    }

    public void login(String index, String lastname,  Consumer<Boolean> callback) throws SQLException {
        Pair<Integer, Boolean> verifiedCredentials;

        DataAccessLayer dataAccessLayer = new DataAccessLayer();
        verifiedCredentials = dataAccessLayer.verifyLoginCredentials(index, lastname, student);

        userId = verifiedCredentials.getKey();
        callback.accept(verifiedCredentials.getValue());
    }

    // TODO: It needs to get rooms from db
    public ObservableList<String> getRoomsList() {
        roomsList = FXCollections.observableArrayList("200", "100", "010", "010", "301");
        return roomsList;
    }

    // TODO: this should handle also gen json
    public void save() throws SQLException, IOException {
        DataAccessLayer dataAccessLayer = new DataAccessLayer();
        if(student){
            dataAccessLayer.savePreferencesToDB(hoursPreferences,userId);
        }else{
            dataAccessLayer.savePreferencesToDB(hoursPreferences,roomsPreferences,userId);
        }

        dataAccessLayer.saveToJsonFile();
    }
}
