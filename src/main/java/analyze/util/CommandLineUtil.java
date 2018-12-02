package analyze.util;

import org.apache.commons.cli.*;
import org.slf4j.Logger;

import java.io.PrintWriter;

import static org.slf4j.LoggerFactory.getLogger;

// TODO: 26.11.18 add description and tests
public class CommandLineUtil {
    private static final Logger log = getLogger(CommandLineUtil.class);

    // TODO: 26.11.18 add description variables
    public static double MIN_DEGREE;
    public static double RESPONSE_TIME;

    private static Options posixOptions;

    static {
        log.info("Read command line arguments");
        posixOptions = new Options();

        //MIN_DEGREE
        Option minDegreeOption = new Option("u", true, "минимально допустимый уровень доступности (проценты. Например, 99.9)");
        minDegreeOption.setArgs(1);
        minDegreeOption.setRequired(true);
        minDegreeOption.setArgName("min_degree");

        //RESPONSE_TIME
        Option responseTimeOption = new Option("t", true, "приемлемое время ответа (миллисекунды. Например, 45)");
        responseTimeOption.setArgs(1);
        responseTimeOption.setRequired(true);
        responseTimeOption.setArgName("min_response_time");

        posixOptions.addOption(minDegreeOption);
        posixOptions.addOption(responseTimeOption);
    }

    public static void parse(String[] args) throws ParseException {
        // TODO: 26.11.18 add logs
        if (args.length == 0) throw new ParseException("args not found");

        CommandLineParser cmdLinePosixParser = new PosixParser();// создаем Posix парсер
        CommandLine commandLine = cmdLinePosixParser.parse(posixOptions, args);// парсим командную строку

        //MIN_DEGREE
        if (commandLine.hasOption("u")) { // проверяем, передавали ли нам команду start, сравнение будет идти с первым представлением опции, в нашем случаее это было однобуквенное представление start
            String argument = commandLine.getOptionValue("u");
            MIN_DEGREE = Double.parseDouble(argument);
            log.info("Min degree - {}", MIN_DEGREE);
        }

        //RESPONSE_TIME
        if (commandLine.hasOption("t")) {
            String argument = commandLine.getOptionValue("t");
            RESPONSE_TIME = Double.parseDouble(argument);
            log.info("Response time - {}", RESPONSE_TIME);
        }

    }

    public static void printHelp() {
        final HelpFormatter helpFormatter = new HelpFormatter();// создаем объект для вывода help`а

        PrintWriter writer = new PrintWriter(System.out);// TODO: 02.12.18 maybe my writer ???
        helpFormatter.printHelp(
                writer,// куда печатаем help,
                120, // ширина строки вывода
                "java -jar analyze",//подсказка по запуску самой программы
                "Options", // строка предшествующая выводу
                posixOptions, // опции по которым составляем help
                3, // число пробелов перед выводом опции
                5, // число пробелов перед выводом опцисания опции
                "Example: java -jar analyze -u 99.9 -t 45", // строка следующая за выводом
                true // выводить ли в строке usage список команд
        );
        writer.flush(); // вывод
    }
}
