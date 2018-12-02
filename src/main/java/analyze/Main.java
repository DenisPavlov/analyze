package analyze;

import analyze.service.InputOutputService;
import analyze.util.CommandLineUtil;
import org.apache.commons.cli.ParseException;

import java.io.FileInputStream;

import static analyze.util.CommandLineUtil.*;

public class Main {
    public static void main(String[] args) {
        try {
            CommandLineUtil.parse(args);
        } catch (ParseException e) {
            CommandLineUtil.printHelp();
            System.exit(0);
        }

//        try(InputOutputService ioService = new InputOutputService(new FileInputStream("access.log"), System.out)) {
        try(InputOutputService ioService = new InputOutputService()) {
            Analizator analizator = new Analizator(MIN_DEGREE, RESPONSE_TIME, ioService);
            analizator.analyze();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
