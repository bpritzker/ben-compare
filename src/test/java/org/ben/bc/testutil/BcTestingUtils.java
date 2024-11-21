package org.ben.bc.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BcTestingUtils {


    private static final Logger logger = Logger.getLogger(BcTestingUtils.class.getName());



    public static List<String> getComplexList1() {
        List<String> result = new ArrayList<>();
        // Homer is in the first list 3 times with 2 different cases
        result.add("Homer");
        result.add("homer");
        result.add("Homer");

        // Bart in twice with leading space
        result.add(" Bart");
        result.add("Bart");


        result.add(" Lenny ");
        result.add("karl");
        result.add("Marge");


        // Not in the second set
        result.add("Lisa");
        result.add("Maggie");

        // Add Blanks, 0 and 3 spaces
        result.add("");
        result.add("   ");
        result.add("   ");

        return result;

    }

    public static List<String> getComplexList2() {

        List<String> result = new ArrayList<>();
        // Homer is in the list twice.
        result.add("Homer");
        result.add("Homer");

        result.add("Marge");
        result.add("Lenny");
        result.add("Karl");


        result.add("Wayland");
        result.add("Burns");

        // Add 0 and 2 blanks, that matches on the 0 but NOT on the 2 with what is in first list
        result.add("");
        result.add("  ");

        return result;
    }









}
