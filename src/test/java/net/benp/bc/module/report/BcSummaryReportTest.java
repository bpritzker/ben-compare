package net.benp.bc.module.report;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BcSummaryReportTest {


    @Test
    public void collectionForDisplayTest() {

        BcSummaryReport bcSummaryReporter = new BcSummaryReport(null);

        String actual;

        List<String> small = Arrays.asList("Homer", "Bart", "Lisa");

        // Simple test, see length below max
        actual = bcSummaryReporter.collectionForDisplay(small, 100L);
        assertEquals("[Homer, Bart, Lisa]", actual);

        // Simple test, see length above max
        actual = bcSummaryReporter.collectionForDisplay(small, 10L);
        assertEquals("[Homer, Bart, ...]", actual);

        // Simple test, see length above max
        // This will test to make sure the separator is handled correctly for a single item
        actual = bcSummaryReporter.collectionForDisplay(small, 1L);
        assertEquals("[Homer, ...]", actual);


        // Simple test, -1
        actual = bcSummaryReporter.collectionForDisplay(small, -1L);
        assertEquals("[Homer, Bart, Lisa]", actual);

    }


    @Test
    public void buildDuplicatesTest() {

        BcSummaryReport bcSummaryReporter = new BcSummaryReport(null);

        List<String> actual;

        Map<String, List<String>> testingNormalizedMap = new HashMap<>();
        testingNormalizedMap.put("HOMER", Arrays.asList("Homer", "homer", "Homer"));
        testingNormalizedMap.put("BART", Arrays.asList(" Bart", "Bart"));


        actual = bcSummaryReporter.buildDuplicates("Test 1", testingNormalizedMap, true, 100L);
        assertEquals("Duplicates Removed (Test 1): 3", actual.get(0));
        assertEquals("   {HOMER:[Homer, homer, Homer],BART:[ Bart, Bart]}", actual.get(1));

    }


}