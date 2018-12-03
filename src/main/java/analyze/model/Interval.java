package analyze.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static analyze.util.Util.isRecordValid;

@Data
public class Interval {
    private LocalDateTime start;
    private LocalDateTime end;
    private double availabilityLevel;
    private int goodCount;
    private int badCount;

    private int lastIndex;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");

    public Interval(Record record, double responseTime) {
        this.start = this.end = record.getDateTime();
        incrementCount(record, responseTime);
        changeAvailabilityLevel();
    }

    public Interval() {
    }

    public void addRecord(Record record, double responseTime) {
        if (start == null) start = record.getDateTime();
        incrementCount(record, responseTime);
        changeAvailabilityLevel();
        end = record.getDateTime();
    }

    private void incrementCount(Record record, double responseTime){
        if (isRecordValid(record, responseTime)) {
            goodCount++;
        } else {
            badCount++;
        }
    }

    public Interval addInterval(Interval interval) {
        if (interval != null) {
            start = start.isBefore(interval.getStart()) ? start : interval.getStart();
            end = end.isAfter(interval.getEnd()) ? end : interval.getEnd();
            badCount = badCount + interval.getBadCount();
            goodCount = goodCount + interval.getGoodCount();
            lastIndex = interval.getLastIndex();
            changeAvailabilityLevel();
        }
        return this;
    }

    //percent
    private void changeAvailabilityLevel(){
        availabilityLevel = 100.0d - ((double)badCount/(badCount + goodCount)) * 100d;
    }

    @Override
    public String toString() {
        return start.format(formatter) + " " + end.format(formatter) + " " + String.format("%.1f", availabilityLevel);
    }
}
