package io2016;

import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created by ishfi on 14.12.2016.
 */
public class Supervisor {
    private int userId;
    private boolean student;

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

    public ObservableList<String> getRoomsList() throws SQLException {
        DataAccessLayer dataAccessLayer = new DataAccessLayer();
        roomsList = dataAccessLayer.obtainRoomsFromDB();

        return roomsList;
    }

    public void removePreviousUserPreferences() throws SQLException {
        DataAccessLayer dataAccessLayer = new DataAccessLayer();
        dataAccessLayer.removeAllUserPreferences(userId,student);
    }

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
