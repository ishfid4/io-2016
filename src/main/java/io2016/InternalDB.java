package io2016;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.BufferPoolMXBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringJoiner;

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
                    addStudentPreferences(studentId, i+1, preferredHours.get(i).get(j)+1);
                }
            }
        }
    }

    public void addLecturersPreferences(ArrayList<ArrayList<Integer>> preferredHours,
                                       ObservableList<String> roomsPreferences, int lecturerId) throws SQLException {
        for (int i = 0; i < preferredHours.size(); ++i){
            for (int j = 0; j < preferredHours.get(i).size(); ++j){
                if (preferredHours.get(i).get(j) != 0){
                    addLecturerPreferences(lecturerId, i+1, preferredHours.get(i).get(j)+1);
                }
            }
        }

        for (String roomId: roomsPreferences) {
            addRoomPreferences(lecturerId, Integer.parseInt(roomId));
        }
    }

    private void addStudentPreferences(int studentId, int dayId, int hourId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `preferred_hours_students` (`user_id`, `day_id`, `hour_id`) VALUES (?,?,?)");
        preparedStatement.setInt(1,studentId);
        preparedStatement.setInt(2,dayId);
        preparedStatement.setInt(3,hourId);
        preparedStatement.executeUpdate();
    }

    private void addLecturerPreferences(int lecturerId, int dayId, int hourId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `preferred_hours_lecturers` (`lecturer_id`, `day_id`, `hour_id`) VALUES (?,?,?)");
        preparedStatement.setInt(1,lecturerId);
        preparedStatement.setInt(2,dayId);
        preparedStatement.setInt(3,hourId);
        preparedStatement.executeUpdate();
    }

    private void addRoomPreferences(int lecturerId, int roomNumber) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `preferred_rooms`(`room_number`, `lecturer_id`) VALUES (?,?)");
        preparedStatement.setInt(1,roomNumber);
        preparedStatement.setInt(2,lecturerId);
        preparedStatement.executeUpdate();
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


//    public ResultSet selectAggregatedLecturer() throws SQLException {
//        Statement statement = connection.createStatement();
//
//        try (ResultSet rs = statement.executeQuery(
//                "SELECT day_id, hour_id, lecturer_id, count(lecturer_id)" +
//                        " as count FROM lecturers JOIN preferred_hours_lecturers ON" +
//                        " lecturers.id = preferred_hours_lecturers.lecturer_id " +
//                        "GROUP BY day_id, hour_id")) {
//            return rs;
//        }
//    }


//    --------------------------P L S ----- D O N T---------------------------------------------------------------------
    //TODO: WTF this is crap, part (saving to file) of this should be in other class
    public void selectAggregatedStudents() throws SQLException, IOException {
        PrintWriter printWriter = null;
        printWriter = new PrintWriter("studentHoursPreferences.json");

        Statement statement = connection.createStatement();
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(printWriter);
        jGenerator.writeStartArray();

        try (ResultSet rs = statement.executeQuery(
                "SELECT day_id, hour_id, group_id, count(user_id) as count " +
                        "FROM students JOIN preferred_hours_students " +
                        "ON students.id = preferred_hours_students.user_id " +
                        "GROUP BY day_id, hour_id, group_id")) {

            //przeglądnięcie obiektu typu ResultSet element po elemencie
            while (rs.next()) {
                //Wybranie pierwszej kolumny w postaci Stringa
                jGenerator.writeStartObject();

                jGenerator.writeFieldName("day_id");
                jGenerator.writeString(rs.getString(1));

                jGenerator.writeFieldName("hour_id");
                jGenerator.writeNumber(rs.getInt(2));

                jGenerator.writeFieldName("group_id");
                jGenerator.writeNumber(rs.getInt(3));

                jGenerator.writeFieldName("count");
                jGenerator.writeNumber(rs.getInt(4));

                jGenerator.writeEndObject();
            }

            jGenerator.writeEndArray();
            jGenerator.flush();
        }
        jGenerator.writeStartArray();
    }

    public void selectAggregatedLecturer() throws SQLException, IOException {
        Statement statement = connection.createStatement();
        PrintWriter printWriter = null;
        printWriter = new PrintWriter("lecturerHoursPreferences.json");

        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(printWriter);
        jGenerator.writeStartArray();

        try (ResultSet rs = statement.executeQuery(
                "SELECT day_id, hour_id, lecturer_id, count(lecturer_id)" +
                        " as count FROM lecturers JOIN preferred_hours_lecturers ON" +
                        " lecturers.id = preferred_hours_lecturers.lecturer_id " +
                        "GROUP BY day_id, hour_id")){
            //przeglądnięcie obiektu typu ResultSet element po elemencie
            while (rs.next()) {
                //Wybranie pierwszej kolumny w postaci Stringa
                jGenerator.writeStartObject();

                jGenerator.writeFieldName("day_id");
                jGenerator.writeString(rs.getString(1));

                jGenerator.writeFieldName("hour_id");
                jGenerator.writeNumber(rs.getInt(2));

                jGenerator.writeFieldName("lecturer_id");
                jGenerator.writeNumber(rs.getInt(3));

                jGenerator.writeFieldName("count");
                jGenerator.writeNumber(rs.getInt(4));

                jGenerator.writeEndObject();
            }

            jGenerator.writeEndArray();
            jGenerator.flush();

            jGenerator.writeStartArray();
        }

    }



    public void selectAggregatedRooms() throws SQLException, IOException {
        PrintWriter printWriter = null;
        printWriter = new PrintWriter("roomPreferences.json");

        Statement statement = connection.createStatement();
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(printWriter);
        jGenerator.writeStartArray();

        try (ResultSet rs = statement.executeQuery(
                "SELECT `room_number`, `lecturer_id` FROM `preferred_rooms`")) {
            //przeglądnięcie obiektu typu ResultSet element po elemencie
            while (rs.next()) {
                //Wybranie pierwszej kolumny w postaci Stringa
                jGenerator.writeStartObject();

                jGenerator.writeFieldName("room_number");
                jGenerator.writeString(rs.getString(1));

                jGenerator.writeFieldName("lecturer_id");
                jGenerator.writeNumber(rs.getInt(2));

                jGenerator.writeEndObject();
            }

            jGenerator.writeEndArray();
            jGenerator.flush();
        }
        jGenerator.writeStartArray();
    }

}