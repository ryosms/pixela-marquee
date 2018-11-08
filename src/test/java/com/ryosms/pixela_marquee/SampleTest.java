package com.ryosms.pixela_marquee;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SampleTest {
    @Test
    void sample() throws Exception {
        assertAll("sample",
                () -> assertEquals(2, 1 + 1),
                () -> assertTrue(true));
    }
}
