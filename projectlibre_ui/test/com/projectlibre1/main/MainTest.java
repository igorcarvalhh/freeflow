package com.projectlibre1.main;

import org.junit.jupiter.api.Test;

import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testRunNumberStorageAndRetrieval() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);

        // Simula um número de execuções
        prefs.putInt("projectlibreRunNumber", 42);
        assertEquals(42, Main.getRunNumber());
    }

    @Test
    public void testFirstRunStorageAndRetrieval() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        long now = System.currentTimeMillis();

        prefs.putLong("projectlibreFirstRun", now);
        assertEquals(now, Main.getFirstRun());
    }

    @Test
    public void testProjectLibreRunNumber() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);

        prefs.putInt("runNumber", 5);
        assertEquals(5, Main.getProjectLibreRunNumber());
    }

    @Test
    public void testProjectLibreFirstRun() {
        long now = System.currentTimeMillis();
        Preferences.userNodeForPackage(Main.class).putLong("firstRun", now);
        assertEquals(now, Main.getProjectLibreFirstRun());
    }

    @Test
    public void testRunSinceMessageFormat() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        prefs.putInt("projectlibreRunNumber", 3);
        prefs.putLong("projectlibreFirstRun", 1650000000000L); // Uma data qualquer

        String message = Main.getRunSinceMessage();
        assertTrue(message.contains("3")); // Verifica se o número de execuções está na mensagem
    }
}