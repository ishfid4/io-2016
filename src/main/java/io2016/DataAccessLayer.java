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
    private InternalDB internalDB;
    private JsonOutput jsonOutput;

    public DataAccessLayer() {
        this.internalDB = new InternalDB();
        this.jsonOutput = new JsonOutput();
    }

    public void savePreferencesToDB(ArrayList<ObservableList<Integer>> hoursPreferences,
                                    ObservableList<String> roomsPreferences, int userId) throws SQLException {
        ArrayList<ArrayList<Integer>> preferredHours = convertPreferetHours(hoursPreferences);

        internalDB.addLecturersPreferences(preferredHours, roomsPreferences, userId);
    }

    public void savePreferencesToDB(ArrayList<ObservableList<Integer>> hoursPreferences, int userId) throws SQLException {
        ArrayList<ArrayList<Integer>> preferredHours = convertPreferetHours(hoursPreferences);

        internalDB.addStudentsPreferences(preferredHours, userId);
    }

    public Pair<Integer, Boolean> verifyLoginCredentials(String index, String lastname, Boolean student) throws SQLException {
        Pair<Integer, Boolean> verifiedCredentials;

        verifiedCredentials = internalDB.checkUserCredentials(index,lastname,student);

        return verifiedCredentials;
    }

    public ObservableList<String> obtainRoomsFromDB() throws SQLException {
        ObservableList<String> roomList;

        roomList = internalDB.getRoomList();

        return roomList;
    }

    public void saveToJsonFile() throws IOException, SQLException {
        ArrayList<LecturerPreferences> lecturersPreferences = new ArrayList<LecturerPreferences>();
        ArrayList<GroupPreferences> groupsPreferences = new ArrayList<GroupPreferences>();

        groupsPreferences = internalDB.selectAggregatedStudents();
        jsonOutput.saveAggregatedStudentsPreferrations(groupsPreferences);

        lecturersPreferences = internalDB.selectAggregatedLecturer();
        jsonOutput.saveAggregatedLecturerPreferrences(lecturersPreferences);

        internalDB.selectAggregatedRooms(lecturersPreferences);
        jsonOutput.saveAggregatedRoomPreferrences(lecturersPreferences);
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
