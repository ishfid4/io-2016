package io2016;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import javafx.util.Pair;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ishfi on 15.12.2016.
 */
public class JsonOutput {

    public void saveAggregatedStudentsPreferrations(ArrayList<GroupPreferences> groupsPreferences) throws IOException {
        PrintWriter printWriter = null;
        printWriter = new PrintWriter("groupsPreferredHours.json");

        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(printWriter);
        jGenerator.writeStartArray();

        for (GroupPreferences groupPreferences : groupsPreferences) {
            HashMap<Pair<Integer, Integer>, Integer> preferdHours;
            preferdHours = groupPreferences.getPreferredHoursInSpecifiedDay();

            Iterator iterator = preferdHours.entrySet().iterator();
            while (iterator.hasNext()) {
                jGenerator.writeStartObject();

                HashMap.Entry<Pair<Integer, Integer>, Integer> entry = (HashMap.Entry<Pair<Integer, Integer>, Integer>) iterator.next();

                jGenerator.writeFieldName("group_id");
                jGenerator.writeNumber(groupPreferences.getGroupId());

                jGenerator.writeFieldName("day_id");
                jGenerator.writeNumber(entry.getKey().getKey());

                jGenerator.writeFieldName("hour_id");
                jGenerator.writeNumber(entry.getKey().getValue());

                jGenerator.writeFieldName("count");
                jGenerator.writeNumber(entry.getValue());

                jGenerator.writeEndObject();
            }
        }
        jGenerator.writeEndArray();
        jGenerator.flush();
    }

    public void saveAggregatedLecturerPreferrences(ArrayList<LecturerPreferences> lecturersPreferences) throws IOException {
        PrintWriter printWriter = null;
        printWriter = new PrintWriter("lecturersPreferredHours.json");

        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(printWriter);
        jGenerator.writeStartArray();

        for (LecturerPreferences lecturerPreferences : lecturersPreferences) {
            HashMap<Pair<Integer, Integer>, Integer> preferdHours;
            preferdHours = lecturerPreferences.getPreferredHoursInSpecifiedDay();

            Iterator iterator = preferdHours.entrySet().iterator();
            while (iterator.hasNext()) {
                jGenerator.writeStartObject();

                HashMap.Entry<Pair<Integer, Integer>, Integer> entry = (HashMap.Entry<Pair<Integer, Integer>, Integer>) iterator.next();

                jGenerator.writeFieldName("lecturer_id");
                jGenerator.writeNumber(lecturerPreferences.getLecturerId());

                jGenerator.writeFieldName("day_id");
                jGenerator.writeNumber(entry.getKey().getKey());

                jGenerator.writeFieldName("hour_id");
                jGenerator.writeNumber(entry.getKey().getValue());

                jGenerator.writeFieldName("count");
                jGenerator.writeNumber(entry.getValue());

                jGenerator.writeEndObject();
            }
        }
        jGenerator.writeEndArray();
        jGenerator.flush();
    }

    public void saveAggregatedRoomPreferrences(ArrayList<LecturerPreferences> lecturersPreferences) throws IOException {
        PrintWriter printWriter = null;
        printWriter = new PrintWriter("roomPreferences.json");

        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(printWriter);
        jGenerator.writeStartArray();

        for (LecturerPreferences lecturerPreferences : lecturersPreferences) {
            ArrayList<Integer> roomList = lecturerPreferences.getRoomList();

            for (Integer room_number: roomList) {
                jGenerator.writeStartObject();

                jGenerator.writeFieldName("lecturer_id");
                jGenerator.writeNumber(lecturerPreferences.getLecturerId());

                jGenerator.writeFieldName("room_number");
                jGenerator.writeNumber(room_number);

                jGenerator.writeEndObject();
            }
        }
        jGenerator.writeEndArray();
        jGenerator.flush();
    }
}
