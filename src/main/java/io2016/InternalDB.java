package io2016;

import io2016.Controllers.PreferencesController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ishfi on 14.12.2016.
 */
public class InternalDB {
    private Connection connection;

    public InternalDB() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/io2?user=user&password=user");
        } catch (SQLException ex) {
            //handle errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void addStudentsPreferences(ArrayList<ArrayList<Integer>> preferredHours, int studentId) throws SQLException {
        for (int i = 0; i < preferredHours.size(); ++i){
            for (int j = 0; j < preferredHours.get(i).size(); ++j){
                if (preferredHours.get(i).get(j) != 0){
                    addStudentPreferences(studentId, i+1, preferredHours.get(i).get(j));
                }
            }
        }
    }

    public void addLecturersPreferences(ArrayList<ArrayList<Integer>> preferredHours,
                                       ObservableList<String> roomsPreferences, int lecturerId) throws SQLException {
        for (int i = 0; i < preferredHours.size(); ++i){
            for (int j = 0; j < preferredHours.get(i).size(); ++j){
                if (preferredHours.get(i).get(j) != 0){
                    addLecturerPreferences(lecturerId, i+1, preferredHours.get(i).get(j));
                }
            }
        }

        for (String roomId: roomsPreferences) {
            addRoomPreferences(lecturerId, Integer.parseInt(roomId));
        }
    }

    private void addStudentPreferences(int studentId, int dayId, int hourId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                "`preferred_hours_students` (`user_id`, `day_id`, `hour_id`) VALUES (?,?,?)");
        preparedStatement.setInt(1,studentId);
        preparedStatement.setInt(2,dayId);
        preparedStatement.setInt(3,hourId);
        preparedStatement.executeUpdate();
    }

    private void addLecturerPreferences(int lecturerId, int dayId, int hourId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                "`preferred_hours_lecturers` (`lecturer_id`, `day_id`, `hour_id`) VALUES (?,?,?)");
        preparedStatement.setInt(1,lecturerId);
        preparedStatement.setInt(2,dayId);
        preparedStatement.setInt(3,hourId);
        preparedStatement.executeUpdate();
    }

    private void addRoomPreferences(int lecturerId, int roomNumber) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                "`preferred_rooms`(`room_number`, `lecturer_id`) VALUES (?,?)");
        preparedStatement.setInt(1,roomNumber);
        preparedStatement.setInt(2,lecturerId);
        preparedStatement.executeUpdate();
    }

    public void removeAllStudentPreferencesRecords(int studentId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `preferred_hours_students` " +
                "WHERE `user_id`=(?)");
        preparedStatement.setInt(1,studentId);
        preparedStatement.executeUpdate();
    }

    public void removeAllLecturerRoomsPreferencesRecords(int lecturerId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `preferred_rooms` " +
                "WHERE `lecturer_id`=(?)");
        preparedStatement.setInt(1,lecturerId);
        preparedStatement.executeUpdate();
    }

    public void removeAllLecturerPreferencesRecords(int lecturerId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `preferred_hours_lecturers` " +
                "WHERE `lecturer_id`=(?)");
        preparedStatement.setInt(1,lecturerId);
        preparedStatement.executeUpdate();
    }

    public ArrayList<Pair<Integer,Integer>> getUserPreferredHours(int userId, Boolean student) throws SQLException {
        ArrayList<Pair<Integer,Integer>> preferredHours = new ArrayList<>();
        PreparedStatement preparedStatement;

        if (student){
            preparedStatement = connection.prepareStatement("SELECT `day_id`, `hour_id` " +
                    "FROM `preferred_hours_students` WHERE `user_id`=(?)");
            preparedStatement.setInt(1, userId);
        }else{
            preparedStatement = connection.prepareStatement("SELECT `day_id`, `hour_id` " +
                    "FROM `preferred_hours_lecturers` WHERE `lecturer_id`=(?)");
            preparedStatement.setInt(1, userId);
        }

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                preferredHours.add(new Pair<>(resultSet.getInt(1), resultSet.getInt(2)));
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return  preferredHours;
    }

    public ArrayList<String> getLecturerPreferredRooms(int lecturerId) throws SQLException {
        ArrayList<String> preferredRooms = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT `room_number` FROM " +
                "`preferred_rooms` WHERE `lecturer_id`=(?)");
        preparedStatement.setInt(1, lecturerId);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                preferredRooms.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return  preferredRooms;
    }

    public Pair<Integer,Boolean> checkUserCredentials(String index, String lastname, Boolean student) throws SQLException {
        Boolean success = false;
        int userId = 0;
        Statement statement = connection.createStatement();

        if (student){
            try (ResultSet resultSet = statement.executeQuery("SELECT `id`, `lastname` FROM `students`")) {
                while (resultSet.next()) {
                    userId = resultSet.getInt(1);
                    if ((Integer.parseInt(index) == userId)
                            && (lastname.equals(resultSet.getString(2)))){
                       success = true;
                       break;
                    }
                }
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }else{
            try (ResultSet resultSet = statement.executeQuery("SELECT `id`, `lastname` FROM `lecturers`")) {
                while (resultSet.next()) {
                    userId = resultSet.getInt(1);
                    if ((Integer.parseInt(index) == userId)
                            && (lastname.equals(resultSet.getString(2)))){
                        success = true;
                        break;
                    }
                }
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
        return new Pair<Integer,Boolean>(userId,success);
    }

    public ObservableList<String> getRoomList() throws SQLException {
        List<String> list = new ArrayList<String>();
        ObservableList<String> roomList = FXCollections.observableList(list);
        Statement statement = connection.createStatement();

        try (ResultSet resultSet = statement.executeQuery("SELECT `room_number` FROM `room`")) {
            while (resultSet.next()) {
               roomList.add(String.valueOf(resultSet.getInt(1)));
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return roomList;
    }

    //--------------------------------For Json------------------------------
    public  ArrayList<GroupPreferences> selectAggregatedStudents() throws SQLException, IOException {
        Statement statement = connection.createStatement();
        ArrayList<GroupPreferences> groupsPreferences = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery(
                "SELECT group_id, day_id, hour_id, count(user_id) as count " +
                        "FROM students JOIN preferred_hours_students " +
                        "ON students.id = preferred_hours_students.user_id " +
                        "GROUP BY group_id, day_id, hour_id")) {

            while (resultSet.next()) {
                int groupIndex = -1;

                if (groupsPreferences.size() == 0){
                    groupsPreferences.add(new GroupPreferences(resultSet.getInt(1)));
                    groupIndex = groupsPreferences.size() - 1;
                }else{
                    for (int i = 0; i < groupsPreferences.size(); ++i){
                        if (groupsPreferences.get(i).getGroupId() == resultSet.getInt(1)){
                            groupIndex = i;
                        }
                    }
                    if (groupIndex == -1){
                        groupsPreferences.add(new GroupPreferences(resultSet.getInt(1)));
                        groupIndex = groupsPreferences.size() - 1;
                    }
                }

                groupsPreferences.get(groupIndex).getPreferredHoursInSpecifiedDay().put(new Pair<Integer,Integer>
                                (resultSet.getInt(2), resultSet.getInt(3))
                            , resultSet.getInt(4));
            }
        }
        return groupsPreferences;
    }

    public ArrayList<LecturerPreferences> selectAggregatedLecturer() throws SQLException, IOException {
        Statement statement = connection.createStatement();
        ArrayList<LecturerPreferences> lecturersPreferences = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery(
                "SELECT lecturer_id, day_id, hour_id, count(lecturer_id) as count FROM lecturers " +
                        "JOIN preferred_hours_lecturers ON lecturers.id = preferred_hours_lecturers.lecturer_id " +
                        "GROUP BY lecturer_id, day_id, hour_id")){
            while (resultSet.next()) {
                int groupIndex = -1;

                if (lecturersPreferences.size() == 0){
                    lecturersPreferences.add(new LecturerPreferences(resultSet.getInt(1)));
                    groupIndex = lecturersPreferences.size() - 1;
                }else{
                    for (int i = 0; i < lecturersPreferences.size(); ++i){
                        if (lecturersPreferences.get(i).getLecturerId() == resultSet.getInt(1)){
                            groupIndex = i;
                        }
                    }
                    if (groupIndex == -1){
                        lecturersPreferences.add(new LecturerPreferences(resultSet.getInt(1)));
                        groupIndex = lecturersPreferences.size() - 1;
                    }
                }

                lecturersPreferences.get(groupIndex).getPreferredHoursInSpecifiedDay().put(new Pair<Integer,Integer>
                                (resultSet.getInt(2), resultSet.getInt(3))
                        , resultSet.getInt(4));
            }
        }
        return lecturersPreferences;
    }

    public void selectAggregatedRooms(ArrayList<LecturerPreferences> lecturersPreferences) throws SQLException, IOException {
        Statement statement = connection.createStatement();

        try (ResultSet resultSet = statement.executeQuery(
                "SELECT `lecturer_id`, `room_number` FROM `preferred_rooms` ORDER BY `lecturer_id` ASC")) {
            while (resultSet.next()) {
                int index = -1;
                for (int i = 0; i < lecturersPreferences.size(); ++i){
                    if (lecturersPreferences.get(i).getLecturerId() == resultSet.getInt(1)){
                        index = i;
                        break;
                    }
                }

                if (index == -1){
                    lecturersPreferences.add(new LecturerPreferences(resultSet.getInt(1)));
                    index = lecturersPreferences.size() - 1;
                }

                lecturersPreferences.get(index).getRoomList().add(resultSet.getInt(2));
            }
        }
    }

}