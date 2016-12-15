package io2016;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ishfi on 15.12.2016.
 */
public class JsonOutput {

    //TODO: EVERYTHING

    public void saveAggregatedLecturer(ResultSet resultSet) throws IOException, SQLException {
        PrintWriter printWriter = null;
        printWriter = new PrintWriter("lecturerHoursPreferences.json");

        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(printWriter);
        jGenerator.writeStartArray();

        //przeglądnięcie obiektu typu ResultSet element po elemencie
        while (resultSet.next()) {
            //Wybranie pierwszej kolumny w postaci Stringa
            jGenerator.writeStartObject();

            jGenerator.writeFieldName("day_id");
            jGenerator.writeString(resultSet.getString(1));

            jGenerator.writeFieldName("hour_id");
            jGenerator.writeNumber(resultSet.getInt(2));

            jGenerator.writeFieldName("lecturer_id");
            jGenerator.writeNumber(resultSet.getInt(3));

            jGenerator.writeFieldName("count");
            jGenerator.writeNumber(resultSet.getInt(4));

            jGenerator.writeEndObject();
        }

        jGenerator.writeEndArray();
        jGenerator.flush();

        jGenerator.writeStartArray();
    }
}
