/*
 * Copyright 2018 ryosms
 */

package com.ryosms.pixela_marquee;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author ryosms
 */
class Parameters {
    private static final int DEFAULT_INTERVAL_WEEK = 3;

    @Option(name = "-u", aliases = {"--user-name"}, metaVar = "<username>",
            usage = "username who owns the graph", required = true)
    private String userName;

    @Option(name = "-g", aliases = {"--graph-id"}, metaVar = "<graph-id>",
            usage = "graph id that will be converted", required = true)
    private String graphName;

    @Option(name = "-s", aliases = {"--start-date"}, metaVar = "<yyyyMMdd>",
            usage = "animation start date in 'yyyyMMdd' format.")
    private String startDateString;

    @Option(name = "-e", aliases = {"--end-date"}, metaVar = "<yyyyMMdd>",
            usage = "animation end date in 'yyyyMMdd' format.")
    private String endDateString;

    @Option(name = "-f", aliases = {"--file"}, metaVar = "<pixela.gif>",
            hidden = true, usage = "set output file path.")
    private String filePath;

    @Option(name = "-i", aliases = {"--interval"}, metaVar = "n",
            hidden = true, usage = "set week count that is interval of animation")
    private Integer intervalWeeks = DEFAULT_INTERVAL_WEEK;

    @Option(name = "-h", aliases = {"--help"}, usage = "show help", help = true)
    private Boolean help = false;

    @Option(name = "-m", aliases = {"--mode"}, metaVar = "short",
            hidden = true, usage = "set 'short' if you want create small image.")
    private String displayMode;

    private LocalDate startDate;
    private LocalDate endDate;
    private PixelaImageConverter.MODE mode = PixelaImageConverter.MODE.NORMAL;

    private final DateTimeFormatter formatter;

    private Parameters(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    private void parseArg(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getLocalizedMessage() + "\n");
            printUsageAndErrorExit(parser);
        }

        if (help) {
            printUsageAndExit(parser, 0);
        }

        startDate = parseDate(startDateString);
        endDate = parseDate(endDateString);

        if (filePath == null || filePath.isEmpty()) {
            filePath = String.format("pixela_%s.gif", formatter.format(LocalDate.now()));
        }

        if (intervalWeeks == null || intervalWeeks <= 0) {
            intervalWeeks = DEFAULT_INTERVAL_WEEK;
        }

        if (displayMode != null) {
            switch (displayMode) {
                case "short":
                    mode = PixelaImageConverter.MODE.SHORT;
                    break;
            }
        }
    }

    private void printUsageAndErrorExit(CmdLineParser parser) {
        printUsageAndExit(parser, 1);
    }

    private void printUsageAndExit(CmdLineParser parser, int exitCode) {
        System.err.println("pixela-marquee");
        System.err.println("  Convert pixela graph to gif (animation) file.");
        System.err.println("  For details of date options, see Pixela API Document.\n");
        System.err.println("Options:");
        parser.printUsage(System.err);

        System.exit(exitCode);
    }

    private LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) return LocalDate.now();
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException ignore) {
            return LocalDate.now();
        }
    }

    static Parameters parse(String[] args, DateTimeFormatter formatter) {
        Parameters parameters = new Parameters(formatter);
        parameters.parseArg(args);
        return parameters;
    }

    String getUserName() {
        return userName;
    }

    String getGraphName() {
        return graphName;
    }

    String getFilePath() {
        return filePath;
    }

    LocalDate getStartDate() {
        return startDate;
    }

    LocalDate getEndDate() {
        return endDate;
    }

    Integer getIntervalWeeks() {
        return intervalWeeks;
    }

    PixelaImageConverter.MODE getMode() {
        return mode;
    }
}
