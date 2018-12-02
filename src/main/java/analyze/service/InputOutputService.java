package analyze.service;

import analyze.model.Record;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

// TODO: 01.12.18 add description and tests
public class InputOutputService implements AutoCloseable {
    private final LineNumberReader reader;
    private final BufferedWriter writer;
    private final Buffer buffer;

    private static final Logger log = LoggerFactory.getLogger(InputOutputService.class);

    public InputOutputService() throws IOException {
        this(System.in, System.out);
    }

    public InputOutputService(InputStream inputStream, OutputStream outputStream) throws IOException {
        this.reader = new LineNumberReader(new BufferedReader(new InputStreamReader(inputStream)));
        this.writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        buffer = new Buffer(new HashMap<>(), 0, 0, 100);// TODO: 02.12.18 capacity
        for (int i = 0; i < buffer.capacity; i++){
            read();
        }
    }

    private Record read() throws IOException {
        int index = reader.getLineNumber();
        String line = reader.readLine();
        if (line == null) return null;

        Record record = new Record(line, index);
        buffer.add(record);
        return record;
    }

    // TODO: 01.12.18 add description and tests, check to buffer capacity
    public List<Record> read(int from, int to) throws IOException {

        List<Record> records = buffer.getRecords().stream()
                .filter(record -> record.getIndex() >= from && record.getIndex() <= to)
                .collect(Collectors.toList());

        int index = records.isEmpty() ? from : records.get(records.size() - 1).getIndex();
        for (int i = index; i < to; i++) {
            Record record = read();
            records.add(record);
        }

        log.debug("return {} records", records.size());
        return records;
    }

    // TODO: 02.12.18 refactor method
    public Record read(int index) throws IOException {
        List<Record> recordList = read(index, index+1);
        return recordList.get(0);
    }

    public void write(String string) throws IOException {
        writer.write(string);
        writer.newLine();
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        reader.close();
        writer.close();
    }

    @Data
    private class Buffer {
        private Map<Integer, Record> buffer;
        private int firstIndex;
        private int lastIndex;
        private int capacity;

        public Buffer(Map<Integer, Record> buffer, int firstIndex, int lastIndex, int capacity) {
            this.buffer = buffer;
            this.firstIndex = firstIndex;
            this.lastIndex = lastIndex;
            this.capacity = capacity;
        }

        public void add(Record record) {
            if (buffer.size() == capacity) buffer.remove(firstIndex++);
            lastIndex = record.getIndex();
            buffer.put(lastIndex, record);
        }

        public List<Record> getRecords() {
            return new ArrayList<>(buffer.values());
        }

    }
}
