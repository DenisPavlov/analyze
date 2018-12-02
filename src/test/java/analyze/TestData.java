package analyze;

import analyze.model.Interval;
import analyze.model.Record;

public class TestData {
    public static String LINE = "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 144.510983 \"-\" \"@list-item-updater\" prio:0\n";

    public static Record RECORD_0 = new Record("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 144.510983 \"-\" \"@list-item-updater\" prio:0\n", 0);
    public static Record RECORD_1 = new Record("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=7ae28555 HTTP/1.1\" 200 2 23.251219 \"-\" \"@list-item-updater\" prio:0\n", 1);
    public static Record RECORD_2 = new Record("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=e356713 HTTP/1.1\" 200 2 30.164372 \"-\" \"@list-item-updater\" prio:0\n", 2);

    public static Interval INTERVAL_1 = new Interval(RECORD_0, 100.0d);
}
