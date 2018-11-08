/*
 * Copyright 2018 ryosms
 */
package com.ryosms.pixela_marquee;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ryosms
 */
class SampleTest {
    @Test
    void sample() throws Exception {
        assertAll("sample",
                () -> assertEquals(2, 1 + 1),
                () -> assertTrue(true));
    }
}
