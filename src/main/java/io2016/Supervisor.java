package io2016;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ishfi on 14.12.2016.
 */
public class Supervisor {
    private int groupId = 1; //TODO: need to obtain from db (after login?)
    private int userId = 2; //TODO: need to obtain from db (after login?)
    private boolean student = true; //TODO: need to obtain from db (after login?)

    private ObservableList<String> roomsList;
    private ArrayList<ObservableList<Integer>> hoursPreferences;
    private ObservableList<String> roomsPreferences;

    public boolean isStudent() {
        return student;
    }

    public void setHoursPreferences(ArrayList<ObservableList<Integer>> hoursPreferences) {
        this.hoursPreferences = hoursPreferences;
    }

    public void setRoomsPreferences(ObservableList<String> roomsPreferences) {
        this.roomsPreferences = roomsPreferences;
    }

    // TODO: It needs to get rooms from db
    public ObservableList<String> getRoomsList() {
        roomsList = FXCollections.observableArrayList("200", "100", "010", "010", "301");
        return roomsList;
    }

    // TODO: this should handle - save/update to db + gen json
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
