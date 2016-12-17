package io2016;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ishfi on 16.12.2016.
 */
public class LecturerPreferences {
    private int lecturerId;
    private HashMap< Pair<Integer,Integer>, Integer> preferredHoursInSpecifiedDay;
    private ArrayList<Integer> roomList;

    public LecturerPreferences(int lecturerId) {
        this.lecturerId = lecturerId;
        this.preferredHoursInSpecifiedDay = new HashMap<Pair<Integer,Integer>, Integer>();
        this.roomList = new ArrayList<Integer>();
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public HashMap<Pair<Integer, Integer>, Integer> getPreferredHoursInSpecifiedDay() {
        return preferredHoursInSpecifiedDay;
    }
    public ArrayList<Integer> getRoomList() {
        return roomList;
    }
}
