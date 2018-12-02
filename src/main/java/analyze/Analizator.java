package analyze;

import analyze.model.Interval;
import analyze.model.Record;
import analyze.service.InputOutputService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static analyze.util.Util.isRecordValid;

public class Analizator {
    private double minDegree;
    private double responseTime;
    private InputOutputService ioService;

    private static final int COUNT = 99; // TODO: 01.12.18 add description
    private static final Logger log = LoggerFactory.getLogger(Analizator.class);


    public Analizator(double minDegree, double responseTime, InputOutputService ioService) {
        this.minDegree = minDegree;
        this.responseTime = responseTime;
        this.ioService = ioService;
    }

    public void analyze() {
        log.info("Start analyze");
        try { // TODO: 02.12.18 refactor try catch
            Record record = ioService.read(0);
            for (int index = 1; record != null; record = ioService.read(index++)) {
                log.debug("record index = {}, req = {}, code = {}", record.getIndex(), record.getRequestTime(), record.getCode());

                if (isRecordValid(record, responseTime)) {
                    continue;
                } else {
                    Interval badInterval = getBadInterval(record.getIndex());
                    if (badInterval == null) {
                        continue;
                    } else {
                        ioService.write(badInterval.toString());
                        index = badInterval.getLastIndex();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("End analyze");
    }

    private Interval getBadInterval(int recordIndex) throws IOException {
        log.debug("recordIndex = {}", recordIndex);
        List<Record> records = ioService.read(recordIndex, COUNT+recordIndex);//records gets incorrect

        Interval interval = records.stream().collect(Interval::new,
                ((interval1, record1) -> interval1.addRecord(record1, responseTime)), Interval::addInterval);

        log.debug("interval ({} - {}) has level {}", recordIndex, COUNT+recordIndex, interval.getAvailabilityLevel());

        Interval badInterval = new Interval();
        if (interval.getAvailabilityLevel() < minDegree) {
            badInterval.addRecord(records.get(0), responseTime);
            for (int i = 1; i < records.size(); i++) {
                Record record = records.get(i);
                if (!isRecordValid(record, responseTime)) {
                    badInterval.addRecord(record, responseTime);
                    recordIndex++;
                } else {
                    break;
                }
            }
            Interval badIntervalRec = getBadInterval(++recordIndex);
            if (badIntervalRec == null) {
                badInterval.setLastIndex(recordIndex);
            }
            badInterval.addInterval(badIntervalRec);
        } else  {
            return null;
        }

        return badInterval;
    }
}