package org.ben.bc;

import org.ben.bc.data.conf.BcCompareConfig;
import org.ben.bc.data.conf.BcConfig;
import org.ben.bc.data.conf.BcReportConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

public class BcUtils {

    private static final Logger logger = Logger.getLogger(BcUtils.class.getName());


    public static BcConfig buildDefaultConfig() {

        BcConfig resultConfig = new BcConfig();

        BcCompareConfig compareConfig = new BcCompareConfig();
        compareConfig.setIgnoreCase(true);
        compareConfig.setTrim(true);
        resultConfig.setCompareConfig(compareConfig);

        BcReportConfig reportConfig = new BcReportConfig();
        reportConfig.setReportDir(null);
        resultConfig.setReportConfig(reportConfig);

        return resultConfig;
    }

    public static String defaultNotEmptyValue(String inString, String defaultValue) {
        if (BcUtils.isBlank(inString)) {
            return defaultValue;
        }

        return inString;
    }


    public static void writeToFile(Collection<String> collection, File outputFile) {

        // If here, we want to create the File reports.
        BcUtils.mkDirs(outputFile.getParentFile());

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





    public static boolean isBlank(CharSequence cs) {
        if (cs == null) {
            return true;
        }


        int strLen = cs.length();
        if (strLen == 0) {
            return true;
        } else {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }


}
