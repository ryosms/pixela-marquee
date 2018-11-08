/*
 * Copyright 2018 ryosms
 */

package com.ryosms.pixela_marquee;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.awt.*;
import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author ryosms
 */
class SvgTranscoder {

    void transcode(String svg) throws TranscoderException, FileNotFoundException {
        InputStream inputStream = new ByteArrayInputStream(svg.getBytes(UTF_8));
        //OutputStream outputStream = new ByteArrayOutputStream();
        OutputStream outputStream = new FileOutputStream("pixela.png");

        TranscoderInput input = new TranscoderInput(inputStream);
        TranscoderOutput output = new TranscoderOutput(outputStream);

        PNGTranscoder transcoder = new PNGTranscoder();

        final int width = 720;
        final int height = 135;
        Rectangle rect = new Rectangle(0, 0, width, height);
        transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) rect.width);
        transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, (float) rect.height);
        transcoder.addTranscodingHint(PNGTranscoder.KEY_AOI, rect);

        // 変換
        transcoder.transcode(input, output);
    }

}
