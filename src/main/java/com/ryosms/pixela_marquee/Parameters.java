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

    @Option(name = "--username", aliases = {"-u"}, metaVar = "<username>",
            usage = "username who owns graph")
    private String userName;

    @Option(name = "--graph", usage = "graph name that will be converted")
    private String graph;

    @Option(name = "--help", aliases = {"-h"}, usage = "show help", help = true)
    private Boolean help = false;

    private boolean parseArg(String[] args) throws CmdLineException {
        CmdLineParser parser = new CmdLineParser(this);

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
