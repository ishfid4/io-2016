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

    private DataAccessLayer dataAccessLayer = new DataAccessLayer();
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
        verifiedCredentials = dataAccessLayer.verifyLoginCredentials(index, lastname, student);

        userId = verifiedCredentials.getKey();
        callback.accept(verifiedCredentials.getValue());
    }

    public ObservableList<String> getRoomsList() throws SQLException {
        roomsList = dataAccessLayer.obtainRoomsFromDB();

        return roomsList;
    }

    public ArrayList<Pair<Integer,Integer>> getPreviousPreferedHours() throws SQLException {
        return dataAccessLayer.getPreferedHoursFromDB(userId,student);
    }

    public ArrayList<String> getPreviousPreferedRooms() throws SQLException {
        return dataAccessLayer.getPreferedRoomsFromDB(userId);
    }

    public void removePreviousUserPreferences() throws SQLException {
        dataAccessLayer.removeAllUserPreferences(userId,student);
    }

    public void save() throws SQLException, IOException {
        if(student){
            dataAccessLayer.savePreferencesToDB(hoursPreferences,userId);
        }else{
            dataAccessLayer.savePreferencesToDB(hoursPreferences,roomsPreferences,userId);
        }

        dataAccessLayer.saveToJsonFile();
    }
}
