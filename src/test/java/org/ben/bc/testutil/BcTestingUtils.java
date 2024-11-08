package org.ben.bc.testutil;

import java.io.File;
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







    @SuppressWarnings("all") // can supress all cause testing code.
    public static void printOpenDirLink(File file) {
        printOpenDirLink(file.toString());
    }
    public static void printOpenDirLink(String inAbsolutePath) {
        if (inAbsolutePath == null) {
            logger.warning("Passed 'null' to 'printOpenDirLogLink'");
            System.out.println("Passed 'null' to 'printOpenDirLogLink'");
            return;
        }

        String sysPropName = System.getProperty("os.name");

        if (! sysPropName.toUpperCase().contains("WIN")) {
            logger.warning("DOES NOT SUPPORT MAC OS YET.");
            return;
        }

        // Handle the case we pass in "/Users/ben/temp/compare/reports"
        //  For that case we want to have the file start at "C:"
        String cleanAbsolutePath;
        if (inAbsolutePath.startsWith("/")) {
            cleanAbsolutePath = "C:" + inAbsolutePath;
        } else {
            cleanAbsolutePath = inAbsolutePath;
        }

        // For windows... not sure if we need to move this outside for MAC as well....
        String pathToOpen;
        File tempFile = new File(cleanAbsolutePath);

        if (tempFile.isDirectory()) {
            pathToOpen = cleanAbsolutePath;
        } else {
            pathToOpen = tempFile.getParentFile().getAbsolutePath();
        }

        System.out.println("Clink Link to open to directory: file:///" + pathToOpen.replace("\\", "/"));

    }


}
