/*
 * Copyright 2018 ryosms
 */

package com.ryosms.pixela_marquee;

import org.apache.batik.transcoder.TranscoderException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author ryosms
 */
public class PixelaMarqueeApp {

    public static void main(String[] args) throws Exception {
        new PixelaMarqueeApp().run(args);
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private void run(String[] args) throws Exception {
        Parameters parameters = Parameters.parse(args, formatter);

        List<String> svgs = loadSvgs(parameters);
        if (svgs.isEmpty()) return;
        createGif(parameters, svgs);
    }

    private List<String> loadSvgs(Parameters parameters) throws IOException {
        LocalDate target = parameters.getStartDate();
        final List<String> svgs = new ArrayList<>();
        final String endpoint = buildUrl(parameters);
        final PixelaClient client = new PixelaClient();
        while (target.isBefore(parameters.getEndDate())
                || target.isEqual(parameters.getEndDate())) {
            String url = endpoint + formatter.format(target);
            System.out.println(url);
            PixelaResponse response = client.get(endpoint + formatter.format(target));
            if (response.getStatusCode() != 200) {
                System.err.println("Err! Status Code: " + response.getStatusCode());
                System.err.println(response.getResponse());
                return Collections.emptyList();
            }
            svgs.add(response.getResponse());
            target = target.plusDays(7 * parameters.getIntervalWeeks());
        }
        return svgs;
    }

    private String buildUrl(Parameters parameters) {
        String endpoint = String.format("https://pixe.la/v1/users/%s/graphs/%s?",
                parameters.getUserName(), parameters.getGraphName());
        if (parameters.getMode() == PixelaImageConverter.MODE.SHORT) {
            endpoint += "mode=short&";
        }
        endpoint += "date=";
        return endpoint;
    }

    private void createGif(Parameters parameters, List<String> svgs) throws TranscoderException, IOException {
        ImageWriter writer = getWriter();
        File file = new File(parameters.getFilePath());
        PixelaImageConverter converter = new PixelaImageConverter();
        try (ImageOutputStream stream = ImageIO.createImageOutputStream(file)) {
            writer.setOutput(stream);
            writer.prepareWriteSequence(null);

            for (String svg : svgs) {
                BufferedImage image = converter.convert(svg, parameters.getMode());
                writer.writeToSequence(new IIOImage(image, null, null), null);
            }

            writer.endWriteSequence();
        }
    }

    private ImageWriter getWriter() throws IOException {
        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("gif");
        if (!it.hasNext()) throw new IOException("Cannot find ImageWriter");
        return it.next();
    }
}
