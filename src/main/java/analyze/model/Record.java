package analyze.model;

import analyze.exception.RecordCreateException;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

// TODO: 26.11.18 add description
@Data
public class Record {
    private final int code;
    private final double requestTime;
    private final LocalDateTime dateTime;
    private final int index;

    public Record(String line, int index) {
        try {
            String[] args = line.split(" ");
            this.code = Integer.parseInt(args[8]);
            this.requestTime = Double.parseDouble(args[10]);
            this.dateTime = LocalDateTime.parse(args[3].substring(1), DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss"));
            this.index = index;
        } catch (Exception e) {
            throw new RecordCreateException("Can not create record from line - " + line);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record)) return false;
        Record record = (Record) o;
        return getCode() == record.getCode() &&
                Double.compare(record.getRequestTime(), getRequestTime()) == 0 &&
                getIndex() == record.getIndex() &&
                Objects.equals(getDateTime(), record.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getRequestTime(), getDateTime(), getIndex());
    }
}
