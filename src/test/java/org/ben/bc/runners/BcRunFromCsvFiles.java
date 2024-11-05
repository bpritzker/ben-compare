package org.ben.bc.runners;


import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.ben.bc.BcMain;
import org.ben.bc.BcUtils;
import org.ben.bc.data.conf.BcConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
        } catch (IOException | CsvValidationException e) {
            logger.severe(e.getMessage());
        }
    }

    private void run() throws IOException, CsvValidationException {

        Collection<String> collection1 = loadCollectionFromCsvFile(ABSOLUTE_PATH_FILE_1, COLUMN_1);
        Collection<String> collection2 = loadCollectionFromCsvFile(ABSOLUTE_PATH_FILE_2, COLUMN_2);

        // REQUIRED.....
        String COLLECTION_NAME_1 = "";
        runSimple(COLLECTION_NAME_1, collection1, COLLECTION_NAME_2, collection2);
    }



    protected Collection<String> loadCollectionFromCsvFile(String absolutePathFile, int columnToLoad) throws IOException, CsvValidationException {

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(absolutePathFile)).build()) {
            Collection<String> result = new ArrayList<>();
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (keepFilter(nextLine)) {
                    result.add(nextLine[columnToLoad]);
                }
            }
            return result;
        }
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
        BcConfig resultConfig = BcUtils.buildDefaultConfig();
        resultConfig.getCompareConfig().setIgnoreCase(IGNORE_CASE);
        resultConfig.getCompareConfig().setTrim(TRIM);
        return resultConfig;
    }

}
