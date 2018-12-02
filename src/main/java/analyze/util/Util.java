package analyze.util;

import analyze.model.Record;

// TODO: 01.12.18 add description
public class Util {
    public static boolean isRecordValid(Record record, double responseTime){
        return (record.getCode() < 500 || record.getCode() >= 600) && record.getRequestTime() <= responseTime;
    }
}
