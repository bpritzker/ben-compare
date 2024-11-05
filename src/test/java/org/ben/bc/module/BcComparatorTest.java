package org.ben.bc.module;

import org.ben.bc.BcUtils;
import org.ben.bc.data.BcCompareResults;
import org.ben.bc.data.conf.BcCompareConfig;
import org.ben.bc.testutil.BcTestingUtils;
import org.junit.jupiter.api.Test;

import java.util.*;

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

        BcCompareResults actual = comparator.runCompare(set1, set2);
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

        assertEquals(3, actual.getNormalized1().size());
        assertEquals(2, actual.getNormalized2().size());

    }


    /**
     * This is a much more comprehensive test that will try to include a lot
     * of little tests such as multiple
     */
    @Test
    public void runCompareComplexTest() {

        BcCompareConfig config = BcUtils.buildDefaultConfig().getCompareConfig();
        BcComparator comparator = new BcComparator(config);
        List<String> set1 = BcTestingUtils.getComplexList1();
        List<String> set2 = BcTestingUtils.getComplexList2();

        BcCompareResults actual = comparator.runCompare(set1, set2);
        Set<String> expected;

        assertEquals(set1, actual.getCollection1());
        assertEquals(set2, actual.getCollection2());

        expected = new HashSet<>(Arrays.asList("KARL", "MARGE", "HOMER", "LENNY"));
        assertEquals(expected, actual.getInBoth());


        expected = new HashSet<>(Arrays.asList("LISA", "MAGGIE", "BART"));
        assertEquals(expected, actual.getInC1NotInC2());

        expected = new HashSet<>(Arrays.asList("WAYLAND", "BURNS"));
        assertEquals(expected, actual.getInC2NotInC1());

        assertEquals(7, actual.getNormalized1().size());
        assertEquals(3, actual.getNormalized1().get("HOMER").size());

        assertEquals(6, actual.getNormalized2().size());

    }



}