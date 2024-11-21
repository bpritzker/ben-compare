package org.ben.bc.examples;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.ben.bc.BcMain;
import org.ben.bc.BcUtils;
import org.ben.bc.data.BcCompareResult;
import org.ben.bc.data.conf.BcCompareConfig;
import org.ben.bc.data.conf.BcConfig;
import org.ben.bc.module.BcComparator;
import org.ben.bc.testutil.BcTestingFileUtils;
import org.ben.bc.testutil.BcTestingUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.logging.Logger;

/**
 * This is the version of the program I want to use most of the time.
 * It will load a CSV from a directory, then match and merge the data.
 *
 */
public class BcRunCsvMatchAndMerge {



    // REQUIRED.....
    // NOTE: Use -1 if you want the whole line loaded.
    private static final int FILE_COLUMN_INDEX_ZERO_BASED_1 = 0;
    private static final int FILE_COLUMN_INDEX_ZERO_BASED_2 = 1;
    private static final String WORKING_DIR = "C:/Temp/Comparator";





    // OPTIONAL...
    private static final boolean IGNORE_CASE = true;
    private static final boolean TOP_ROW_ARE_HEADERS = false;

    // OPTIONAL DISPLAY
    private static final boolean DISPLAY_VALUES_BOTH = false;
    private static final boolean DISPLAY_DIFF_VALUES_1 = false;
    private static final boolean DISPLAY_DIFF_VALUES_2 = false;



    private static final Logger logger = Logger.getLogger(BcRunCsvMatchAndMerge.class.getName());


    public static void main(String[] args) {
        BcRunCsvMatchAndMerge runCsvMatchAndMerge = new BcRunCsvMatchAndMerge();
        try {
            runCsvMatchAndMerge.run();
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
        }
    }


    private void run() throws Exception {


        File file1 = getFileInDirectory(WORKING_DIR, 1);
        File file2 = getFileInDirectory(WORKING_DIR, 2);

        if (file1 == null || file2 == null) {
            BcTestingUtils.printOpenDirLink(WORKING_DIR);
            throw new RuntimeException("Error getting Input file at: <" + WORKING_DIR + ">. See Log for details.");
        }

        List<List<String>> data1 = BcTestingFileUtils.loadDataFromCsvFile(file1);
        List<List<String>> data2 = BcTestingFileUtils.loadDataFromCsvFile(file2);

        Collection<String> collection1 = getColumnData(data1, FILE_COLUMN_INDEX_ZERO_BASED_1);
        Collection<String> collection2 = getColumnData(data2, FILE_COLUMN_INDEX_ZERO_BASED_2);

        BcConfig config = BcConfig.buildDefaultConfig();
        config.getCompareConfig().setIgnoreCase(IGNORE_CASE);
        config.getReportConfig().setReportDir(WORKING_DIR + "/reports");
        config.getReportConfig().setDisplayValuesForBoth(DISPLAY_VALUES_BOTH);
        config.getReportConfig().setDisplayValuesForDiff1(DISPLAY_DIFF_VALUES_1);
        config.getReportConfig().setDisplayValuesForDiff2(DISPLAY_DIFF_VALUES_2);

        BcCompareResult compareResult = BcMain.runCompare(
                BcUtils.cleanFileName(file1.getName()), collection1,
                BcUtils.cleanFileName(file2.getName()), collection2,
                config);

        createMergeFile(compareResult, data1, data2, FILE_COLUMN_INDEX_ZERO_BASED_1, FILE_COLUMN_INDEX_ZERO_BASED_2, WORKING_DIR, config);

        File zipFile = new File(WORKING_DIR + "/Reports.zip");
        BcTestingFileUtils.zipDirectory(new File(WORKING_DIR + "/reports"), zipFile);

    }

    @SuppressWarnings("All")
    private void createMergeFile(BcCompareResult compareResult, List<List<String>> data1, List<List<String>> data2, int fileColumnIndexZeroBased1, int fileColumnIndexZeroBased2, String workingDir, BcConfig config) throws Exception {


        Map<String, List<List<String>>> dataMap1 = buildDataMap(data1, fileColumnIndexZeroBased1, config.getCompareConfig());
        Map<String, List<List<String>>> dataMap2 = buildDataMap(data2, fileColumnIndexZeroBased2, config.getCompareConfig());


        List<List<String>> reportData = new ArrayList<>();
        List<String> headerRow = new ArrayList<>();
        headerRow.add("Match Value");
        headerRow.add("Is Exact Match");
        headerRow.addAll(data1.get(0));
        headerRow.addAll(data2.get(0));
        reportData.add(headerRow);

        for (String currMatchValue : compareResult.getInBoth()) {
            List<List<String>> matchValues1 = dataMap1.get(currMatchValue);
            List<List<String>> matchValues2 = dataMap2.get(currMatchValue);
            String matchValue = (matchValues1.size() == 1 && matchValues2.size() == 1) ? "Yes" : "No";
            List<String> matchResult = new ArrayList<>();
            matchResult.add(currMatchValue);
            matchResult.add(matchValue);
            matchResult.addAll(matchValues1.get(0));
            matchResult.addAll(matchValues2.get(0));
            reportData.add(matchResult);
        }


        File reportMergedDataFile = new File(workingDir + "/reports/MergedMatches.csv");
        createCsvFile(reportData, reportMergedDataFile);
    }

    private void createCsvFile(List<List<String>> csvData, File file) throws Exception {

        CSVPrinter csvFilePrinter;
        CSVFormat csvFileFormat = CSVFormat.EXCEL;
        FileWriter fileWriter = new FileWriter(file);
        csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

        csvFilePrinter.printRecords(csvData);


        fileWriter.flush();
        fileWriter.close();
        csvFilePrinter.close();


    }


    /**
     * This will collect all the lines by normalized values.
     * @return Map with the key being the normalized value and the List<List>> being the list of lines that matched.
     */
    private Map<String, List<List<String>>> buildDataMap(List<List<String>> data, int fileColumnIndexZeroBased, BcCompareConfig compareConfig) {
        BcComparator tempComparator = new BcComparator(compareConfig);

        Map<String, List<List<String>>> result = new HashMap<>();
        for (List<String> currData : data) {


            String normalizedKey = tempComparator.normalizeValue(currData.get(fileColumnIndexZeroBased), compareConfig);
            List<List<String>> tempList = result.get(normalizedKey);
            if (tempList == null) {
                tempList = new ArrayList<>();
            }
            tempList.add(currData);
            result.put(normalizedKey, tempList);
        }
        return result;
    }


    private Collection<String> getColumnData(List<List<String>> data, int fileColumnIndexZeroBased) {
        Collection<String> result = new ArrayList<>();
        boolean isHeaderRow = true;
        for (List<String> currLine : data) {

            // Skip first row

            if (! (TOP_ROW_ARE_HEADERS && isHeaderRow)) {
                result.add(currLine.get(fileColumnIndexZeroBased));
            }
            isHeaderRow = false;
        }
        return result;
    }


    private File getFileInDirectory(String workingDir, int fileNumber) {
        File inputFileDir = new File(workingDir + "/Input-File-" + fileNumber);

        if (! inputFileDir.exists()) {
            logger.severe("Directory for File NOT FOUND! <" + inputFileDir.getAbsolutePath() + "> (It was created for you)");
            BcUtils.mkDirs(inputFileDir);
            return null;
        }

        File[] filesInDir = inputFileDir.listFiles();
        if (filesInDir == null || filesInDir.length == 0) {
            logger.severe("NO FILE FOUND in the File <" + fileNumber + "> input Directory! <" + inputFileDir.getAbsolutePath() + ">");
            return null;
        }

        if (filesInDir.length > 1) {
            logger.severe("MORE THAN 1 FILE FOUND IN Input Files Dir for File <" + fileNumber + ">. There MUST be exactly 1 file.  <" + inputFileDir.getAbsolutePath() + ">");
            return null;
        }

        logger.fine("Found Exactly One File <" + filesInDir[0].getAbsolutePath() + ">");
        System.out.println("Found File <" + fileNumber + "> -- <" + filesInDir[0].getAbsolutePath() + ">");
        return filesInDir[0];
    }


}
