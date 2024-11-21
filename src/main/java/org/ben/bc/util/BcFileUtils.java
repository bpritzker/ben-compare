package org.ben.bc.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

public class BcFileUtils {

    private static final Logger logger = Logger.getLogger(BcFileUtils.class.getName());


    public static String getCleanFileNameForDisplay(String inFileName) {
        int lastPeriodIndex = inFileName.lastIndexOf('.');
        String noExtension = inFileName;
        if (lastPeriodIndex != -1) {
            noExtension = inFileName.substring(0, lastPeriodIndex);
        }
        return BcFileUtils.cleanFileName(noExtension);
    }



    public static void writeToFile(Collection<String> collection, File outputFile) {

        // If here, we want to create the File reports.
        BcFileUtils.mkDirs(outputFile.getParentFile());

        try (FileWriter writer = new FileWriter(outputFile)) {
            for(String str: collection) {
                writer.write(str + System.lineSeparator());
            }
        } catch (IOException e) {
            logger.severe("Unable to create file at: " + outputFile.getAbsolutePath());
            throw new RuntimeException(e);
        }
    }


    /**
     * Created this to mostly suppress 'unchecked' warning lol
     * The File.mkdirs() is what I want to use, but it returns false if the directly already exists.
     * So, to get rid of the 'unchecked' warning I need all this :(
     */
    public static void mkDirs(File file) {
        if (! file.exists()) {
            boolean wasCreated = file.mkdirs();
            if (!wasCreated) {
                logger.warning("Unable To Create Directory: <" + file.getAbsolutePath() + ">");
            }
        }
    }



    /**
     * This will replace all NON a-z A-Z and 0-9 characters with underscores.
     * It will also replace any double underscores with a single one.
     * Eventually we can make this even smarter if we want.
     */
    public static String cleanFileName(String fileName) {
        if (fileName == null) {
            return null;
        }

        String result = fileName.replaceAll("[^a-zA-Z0-9.-]", "_");

        // Handle double underscore
        result = result.replace("__", "_");

        // Space Dash Space " - " should be "-" instead of "_-_"
        result = result.replace("_-_", "-");

        return result;
    }



}
