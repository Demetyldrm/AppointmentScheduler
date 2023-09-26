package yildirim.dbclientapp.utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtility {
    private static ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> endTimes = FXCollections.observableArrayList();

    private static void loadTimeLists() {
        ZonedDateTime easternStartTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        ZonedDateTime localStartTime = easternStartTime.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime start = localStartTime.toLocalTime();
        LocalTime end = start.plusHours(14);

        while (start.isBefore(end)) {
            startTimes.add(start);
            start = start.plusMinutes(15);
            endTimes.add(start);
        }
    }

    public static ObservableList<LocalTime> getStartTimes() {
        if(startTimes.isEmpty()){
            loadTimeLists();
        }
        return startTimes;
    }

    public static ObservableList<LocalTime> getEndTimes() {
        if(endTimes.isEmpty()){
            loadTimeLists();
        }
        return endTimes;
    }
}
