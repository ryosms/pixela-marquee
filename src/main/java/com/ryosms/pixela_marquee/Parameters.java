/*
 * Copyright 2018 ryosms
 */

package com.ryosms.pixela_marquee;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * @author ryosms
 */
class Parameters {

    @Option(name = "-u", aliases = {"--user-name"}, metaVar = "<username>",
            usage = "username who owns graph")
    private String userName;

    @Option(name = "-g", aliases = {"--graph"}, metaVar = "<graph-id>",
            usage = "graph id that will be converted")
    private String graph;

    @Option(name = "-h", aliases = {"--help"}, usage = "show help")
    private Boolean help = false;

    private boolean parseArg(String[] args) throws CmdLineException {
        CmdLineParser parser = new CmdLineParser(this);
        if (args.length == 0) {
            parser.printUsage(System.err);
            return false;
        }

        parser.parseArgument(args);
        if (help) {
            parser.printUsage(System.err);
            return false;
        }
        return true;
    }

    static Parameters parse(String[] args) throws CmdLineException {
        Parameters parameters = new Parameters();
        if (!parameters.parseArg(args)) return null;
        return parameters;
    }


}
