package io2016;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ishfi on 16.12.2016.
 */
public class GroupPreferences {
    private int groupId;
    private HashMap< Pair<Integer,Integer>, Integer> preferredHoursInSpecifiedDay;

    public GroupPreferences(int groupId) {
        this.groupId = groupId;
        this.preferredHoursInSpecifiedDay = new HashMap<Pair<Integer,Integer>, Integer>();
    }

    public int getGroupId() {
        return groupId;
    }

    public HashMap<Pair<Integer, Integer>, Integer> getPreferredHoursInSpecifiedDay() {
        return preferredHoursInSpecifiedDay;
    }
}
