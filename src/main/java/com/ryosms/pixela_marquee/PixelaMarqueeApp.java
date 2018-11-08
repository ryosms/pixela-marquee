/*
 * Copyright 2018 ryosms
 */

package com.ryosms.pixela_marquee;

/**
 * @author ryosms
 */
public class PixelaMarqueeApp {

    public static void main(String[] args) throws Exception {
        Parameters parameters = Parameters.parse(args);
        if (parameters == null) return;
        System.out.println("Hello Pixela Marquee!");
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}
