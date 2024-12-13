package net.benp.bc.examples.other;


import net.benp.bc.BcMain;
import net.benp.bc.data.conf.BcConfig;
import net.benp.bc.testutil.BcTestingFileUtils;

import java.util.List;
import java.util.logging.Logger;


/**
 *
 * Sample runner to load data from columns in a CSV files.
 *
 */
public class BcRunFromCsvFiles extends BcMain {

    private static final String ABSOLUTE_PATH_FILE_1  = "";
    private static final int COLUMN_1 = -1;

    private static final String COLLECTION_NAME_2 = "";
    private static final String ABSOLUTE_PATH_FILE_2  = "";
    private static final int COLUMN_2 = -1;





    // OPTIONAL...
    private static final boolean IGNORE_CASE = false;
    private static final boolean TRIM = true;



    private static final Logger logger = Logger.getLogger(BcRunFromCsvFiles.class.getName());



    public static void main(String[] args) {
        BcRunFromCsvFiles runFromCsvFiles = new BcRunFromCsvFiles();
        try {
            runFromCsvFiles.run();
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    private void run() throws Exception {

        List<String> list1 = BcTestingFileUtils.loadListFromCsvFile(ABSOLUTE_PATH_FILE_1, COLUMN_1);
        List<String> list2 = BcTestingFileUtils.loadListFromCsvFile(ABSOLUTE_PATH_FILE_2, COLUMN_2);

        // REQUIRED.....
        String COLLECTION_NAME_1 = "";
        runCompare(COLLECTION_NAME_1, list1, COLLECTION_NAME_2, list2);
    }





    /**
     * This is a custom filter that you can use when loading the data....
     */
    @SuppressWarnings("all")
    private boolean keepFilter(String[] nextLine) {

        // Code to filter out rows that have "WORK" in one of the columns would look like this...
//        if (nextLine[1].equals("WORK")) {
//            return  false;
//        }

        return true;

    }


    @Override
    protected BcConfig buildConfig() {
        BcConfig resultConfig = BcConfig.buildDefaultConfig();
        resultConfig.getCompareConfig().setIgnoreCase(IGNORE_CASE);
        resultConfig.getCompareConfig().setTrim(TRIM);
        return resultConfig;
    }

}
