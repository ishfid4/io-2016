package io2016;

import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ishfi on 14.12.2016.
 */
public class DataAccessLayer {

    public void savePreferencesToDB(ArrayList<ObservableList<Integer>> hoursPreferences,
                                    ObservableList<String> roomsPreferences, int userId) throws SQLException {
        InternalDB internalDB = new InternalDB();
        ArrayList<ArrayList<Integer>> preferredHours = convertPreferetHours(hoursPreferences);

        internalDB.addLecturersPreferences(preferredHours, roomsPreferences, userId);
    }

    public void savePreferencesToDB(ArrayList<ObservableList<Integer>> hoursPreferences, int userId) throws SQLException {
        InternalDB internalDB = new InternalDB();
        ArrayList<ArrayList<Integer>> preferredHours = convertPreferetHours(hoursPreferences);

        internalDB.addStudentsPreferences(preferredHours, userId);
    }

    public Pair<Integer, Boolean> verifyLoginCredentials(String index, String lastname, Boolean student) throws SQLException {
        Pair<Integer, Boolean> verifiedCredentials;
        InternalDB internalDB = new InternalDB();

        verifiedCredentials = internalDB.checkUserCredentials(index,lastname,student);

        return verifiedCredentials;
    }

    public ObservableList<String> obtainRoomsFromDB() throws SQLException {
        ObservableList<String> roomList;
        InternalDB internalDB = new InternalDB();

        roomList = internalDB.getRoomList();

        return roomList;
    }

    //TODO: crap in this function should be in another class (spaghetti)
    public void saveToJsonFile() throws IOException, SQLException {
      //  JsonOutput jsonOutput = new JsonOutput();
        InternalDB internalDB = new InternalDB();
       // jsonOutput.saveAggregatedLecturer(internalDB.selectAggregatedLecturer());
        internalDB.selectAggregatedLecturer();
        internalDB.selectAggregatedRooms();
        internalDB.selectAggregatedStudents();

    }

    //TODO: Why?
    private ArrayList<ArrayList<Integer>> convertPreferetHours(ArrayList<ObservableList<Integer>> hoursPreferences){
        ArrayList<ArrayList<Integer>> preferedHours = new ArrayList<>(5);

        for (int i = 0; i < 5; ++i){
            int index = 0;
            preferedHours.add(new ArrayList<Integer>());
            for (int j = 0; j < 13; ++j){
                if (hoursPreferences.get(i).get(index) == j){
                    preferedHours.get(i).add(j);
                    index++;
                }else{
                    preferedHours.get(i).add(0);
                }
            }
        }

        return preferedHours;
    }
}
