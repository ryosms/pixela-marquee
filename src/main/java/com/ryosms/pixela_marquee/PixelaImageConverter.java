/*
 * Copyright 2018 ryosms
 */

package com.ryosms.pixela_marquee;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Convert Pixela SVG String to PNG Buffered Image
 *
 * @author ryosms
 */
class PixelaImageConverter {
    enum MODE {
        NORMAL(720, 135),
        SHORT(220, 135);

        private final int width;
        private final int height;

        MODE(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    BufferedImage convert(String svg, MODE mode) throws TranscoderException, IOException {
        InputStream is = new ByteArrayInputStream(svg.getBytes());
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        TranscoderInput input = new TranscoderInput(is);
        TranscoderOutput output = new TranscoderOutput(os);

        PNGTranscoder converter = new PNGTranscoder();

        Rectangle rect = new Rectangle(0, 0, mode.width, mode.height);
        converter.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) mode.width);
        converter.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, (float) mode.height);
        converter.addTranscodingHint(PNGTranscoder.KEY_AOI, rect);

        converter.transcode(input, output);
        return ImageIO.read(new ByteArrayInputStream(os.toByteArray()));
    }


}
