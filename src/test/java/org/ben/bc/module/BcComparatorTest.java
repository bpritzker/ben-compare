package org.ben.bc.module;

import org.ben.bc.BcUtils;
import org.ben.bc.data.BcCompareResults;
import org.ben.bc.data.conf.BcCompareConfig;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BcComparatorTest {


    /**
     * This is the most basic test to just make sure everything is being populated and works in a very simple case.
     */
    @Test
    public void runCompareTest() {

        BcCompareConfig config = BcUtils.buildDefaultConfig().getCompareConfig();
        BcComparator comparator = new BcComparator(config);
        Set<String> set1 = new HashSet<>(Arrays.asList("Homer", "Bart", "Lisa"));
        Set<String> set2 = new HashSet<>(Arrays.asList("Homer", "Wayland"));

        BcCompareResults actual = comparator.runCompare("Name 1", set1, "Name 2", set2);
        Set<String> expected;

        assertEquals(set1, actual.getCollection1());
        assertEquals(set2, actual.getCollection2());

        // TODO: Is this how we want it to work? Do we want to put the original value back in the result?
        //   Not sure because it could cause ambiguity if there was more than one match.
        expected= new HashSet<>(Collections.singletonList("HOMER"));
        assertEquals(expected, actual.getInBoth());


        expected= new HashSet<>(Arrays.asList("BART", "LISA"));
        assertEquals(expected, actual.getInC1NotInC2());

        expected= new HashSet<>(Collections.singletonList("WAYLAND"));
        assertEquals(expected, actual.getInC2NotInC1());


        assertEquals("Name 1", actual.getCollectionName1());
        assertEquals("Name 2", actual.getCollectionName2());

        assertEquals(3, actual.getNormalized1().size());
        assertEquals(2, actual.getNormalized2().size());

    }




}