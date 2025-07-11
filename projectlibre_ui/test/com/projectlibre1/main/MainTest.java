package com.projectlibre1.main;

import org.junit.jupiter.api.Test;

import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    void testGetUserPreferences_NotNull() {
        Preferences prefs = Main.getUserPreferences();
        assertNotNull(prefs, "Preferences should not be null");
    }

    @Test
    void testGetStoredRunNumber_NotNegative() {
        int runNumber = Main.getStoredRunNumber();
        assertTrue(runNumber >= 0, "runNumber should be non-negative");
    }

    @Test
    void testGetStoredFirstRun_ValidTimestamp() {
        long firstRun = Main.getStoredFirstRun();
        long now = System.currentTimeMillis();
        assertTrue(firstRun <= now, "firstRun timestamp should be in the past or now");
    }

    @Test
    void testGetProjectLibreRunNumber_NonNegative() {
        int projectLibreRunNumber = Main.getProjectLibreRunNumber();
        assertTrue(projectLibreRunNumber >= 0, "projectLibreRunNumber should be non-negative");
    }

    @Test
    void testGetProjectLibreFirstRun_ValidTimestamp() {
        long projectLibreFirstRun = Main.getProjectLibreFirstRun();
        long now = System.currentTimeMillis();
        assertTrue(projectLibreFirstRun <= now, "projectLibreFirstRun timestamp should be in the past or now");
    }

    @Test
    void testGetRunSinceMessage_NotEmpty() {
        String message = Main.getRunSinceMessage();
        assertNotNull(message);
        assertFalse(message.trim().isEmpty(), "runSince message should not be empty");
    }
}