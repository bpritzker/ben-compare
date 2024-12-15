package net.benp.bc.examples.other;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import net.benp.bc.BcMain;
import net.benp.bc.data.BcCompareResult;
import net.benp.bc.data.conf.BcCompareConfig;
import net.benp.bc.data.conf.BcConfig;
import net.benp.bc.module.BcComparator;
import net.benp.bc.testutil.BcTestingFileUtils;
import net.benp.bc.util.BcFileUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.logging.Logger;

/**
 * This is the version of the program I (and think you will to) want to use most of the time.
 * It will load a CSV from a directory, then match and merge the data.
 * You only need to tell it what column to focus on.
 * It will create all the difference reports AND create a "merge" file.
 *
 */
public class BcRunCsvMatchAndMerge {



    // REQUIRED.....
    // NOTE: Use -1 if you want the whole line loaded. (Think TXT file)
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
            runCsvMatchAndMerge.run(FILE_COLUMN_INDEX_ZERO_BASED_1, FILE_COLUMN_INDEX_ZERO_BASED_2,
                    WORKING_DIR, IGNORE_CASE, TOP_ROW_ARE_HEADERS, DISPLAY_VALUES_BOTH, DISPLAY_DIFF_VALUES_1, DISPLAY_DIFF_VALUES_2);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
        }
    }


    public void run(
            int fileColumnIndexZeroBased1, int fileColumnIndexZeroBased2,
            String workingDir,
            boolean ignoreCase,
            boolean topRowsAreHeaders,
            boolean displayValuesBoth,
            boolean displayDiffValues1,
            boolean displayDiffValues2) throws Exception {


        File file1 = getFileInDirectory(workingDir, 1);
        File file2 = getFileInDirectory(workingDir, 2);

        if (file1 == null || file2 == null) {
            fileNotFoundError(workingDir, file1, file2);
            // Not sure if I want to throw an Exception or just return.
            // I'm leaning towards a very detailed error message and just return.
            // The program did NOT crash.
            return;
//            throw new RuntimeException("Error getting Input file at: <" + workingDir + ">. See Log for details.");
        }

        List<List<String>> data1 = BcTestingFileUtils.loadDataFromCsvFile(file1);
        List<List<String>> data2 = BcTestingFileUtils.loadDataFromCsvFile(file2);

        Collection<String> collection1 = getColumnData(data1, fileColumnIndexZeroBased1, topRowsAreHeaders);
        Collection<String> collection2 = getColumnData(data2, fileColumnIndexZeroBased2, topRowsAreHeaders);

        BcConfig config = BcConfig.buildDefaultConfig();
        config.getCompareConfig().setIgnoreCase(ignoreCase);
        config.getReportConfig().setReportDir(workingDir + "/reports");
        config.getReportConfig().setDisplayValuesForBoth(displayValuesBoth);
        config.getReportConfig().setDisplayValuesForDiff1(displayDiffValues1);
        config.getReportConfig().setDisplayValuesForDiff2(displayDiffValues2);

        String collectionName1 = BcFileUtils.getCleanFileNameForDisplay(file1.getName());
        String collectionName2 = BcFileUtils.getCleanFileNameForDisplay(file2.getName());

        BcCompareResult compareResult = BcMain.runCompare(
                collectionName1, collection1,
                collectionName2, collection2,
                config);

        // Merge file here...
        createMergeFile(
                compareResult,
                collectionName1, data1, fileColumnIndexZeroBased1,
                collectionName2, data2,  fileColumnIndexZeroBased2,
                workingDir, config, topRowsAreHeaders);

        File zipFile = new File(workingDir + "/Reports.zip");
        BcTestingFileUtils.zipDirectory(new File(workingDir + "/reports"), zipFile);
        BcTestingFileUtils.printOpenDirLink(zipFile);

    }


    /**
     * This should be the most common error people will see when first trying to use the program.
     * </br>
     * I want this error to be as verbose and easy to figure out and fix as possible!
     */
    private void fileNotFoundError(String workingDir, File file1, File file2) {
        File fileInputDir1 = getInputFileDir(workingDir, 1);
        File fileInputDir2 = getInputFileDir(workingDir, 2);
        System.out.println();
        System.out.println();
        System.out.println("************************* READ BELOW TO FIX THE ERROR ********************");
        System.out.println("************************* READ BELOW TO FIX THE ERROR ********************");
        System.out.println("************************* READ BELOW TO FIX THE ERROR ********************");
        System.out.println("There was an ERROR getting the input files.");
        System.out.println("Either the Input directories do not exist or the files were not found!");
        System.out.println("Expected to find a file in the following directories:");
//        System.out.println("Input File Location: " + workingDir);
        System.out.println("Input File Directory 1: <" + fileInputDir1.getAbsolutePath() + ">");
        System.out.println("Input File Directory 2: <" + fileInputDir2.getAbsolutePath() + ">");

        // At this point, the directories should already have been created
        if (file1 == null) {
            System.out.println("You need to put a file in the input *1* Directory.");
        }

        if (file2 == null) {
            System.out.println("You need to put a file in the input *2* Directory.");
        }

        System.out.println(); // Add space between main error message and open dir link
        BcTestingFileUtils.printOpenDirLink(workingDir);
        System.out.println(); // Add space between main error message and open dir link
        System.out.println("****************************************************************************");
        System.out.println(); // Add space between main error message and open dir link

    }


    @SuppressWarnings("All")
    protected void createMergeFile(BcCompareResult compareResult,
                                   String dataName1,
                                   List<List<String>> data1,
                                   int fileColumnIndexZeroBased1,
                                   String dataName2,
                                   List<List<String>> data2,
                                   int fileColumnIndexZeroBased2,
                                   String workingDir, BcConfig config,
                                   boolean topRowsAreHeaders) throws Exception {


        Map<String, List<List<String>>> dataMap1 = buildDataMap(data1, fileColumnIndexZeroBased1, config.getCompareConfig());
        Map<String, List<List<String>>> dataMap2 = buildDataMap(data2, fileColumnIndexZeroBased2, config.getCompareConfig());


        List<List<String>> reportData = new ArrayList<>();
        List<String> headerRow = new ArrayList<>();
        headerRow.add("Match Value");
        headerRow.add("Is Exact Match");

        headerRow.add("BEGIN '" + dataName1 + "'");
        List<String> headersData1 = buildDataHeaders(data1, 1, topRowsAreHeaders);
        headerRow.addAll(headersData1);

        headerRow.add("BEGIN '" + dataName2 + "'");
        List<String> headersData2 = buildDataHeaders(data2, 2, topRowsAreHeaders);
        headerRow.addAll(headersData2);

        reportData.add(headerRow);

        for (String currMatchValue : compareResult.getInBoth()) {
            List<List<String>> matchValues1 = dataMap1.get(currMatchValue);
            List<List<String>> matchValues2 = dataMap2.get(currMatchValue);
            String matchValue = (matchValues1.size() == 1 && matchValues2.size() == 1) ? "Yes" : "No";
            List<String> matchResult = new ArrayList<>();
            matchResult.add(currMatchValue);
            matchResult.add(matchValue);
            matchResult.add(""); // Balnk separator for data set 1
            matchResult.addAll(matchValues1.get(0));
            matchResult.add(""); // Balnk separator for data set 2
            matchResult.addAll(matchValues2.get(0));
            reportData.add(matchResult);
        }


        File reportMergedDataFile = new File(workingDir + "/reports/MergedMatches.csv");
        createCsvFile(reportData, reportMergedDataFile);
    }

    protected List<String> buildDataHeaders(List<List<String>> data, int dataNumber, boolean topRowsAreHeaders) {

        List<String> result = new ArrayList<>();
        if (topRowsAreHeaders) {
            result.addAll(data.get(0));
        } else {
            // for this, we want the length of a data line. We should always have at least 1 data point so just hard code 0
            for (int i=0; i< data.get(0).size(); i++) {
                result.add("Header " + dataNumber +"-" + (i+1));
            }
        }
        return result;
    }

    protected void createCsvFile(List<List<String>> csvData, File file) throws Exception {

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
    protected Map<String, List<List<String>>> buildDataMap(List<List<String>> data, int fileColumnIndexZeroBased, BcCompareConfig compareConfig) {
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


    protected Collection<String> getColumnData(List<List<String>> data, int fileColumnIndexZeroBased, boolean topRowsAreHeaders) {
        Collection<String> result = new ArrayList<>();
        boolean isHeaderRow = true;
        for (List<String> currLine : data) {

            // Skip first row

            if (! (topRowsAreHeaders && isHeaderRow)) {
                result.add(currLine.get(fileColumnIndexZeroBased));
            }
            isHeaderRow = false;
        }
        return result;
    }


    protected File getFileInDirectory(String workingDir, int fileNumber) {

        File inputFileDir = getInputFileDir(workingDir, fileNumber);

        if (! inputFileDir.exists()) {
            logger.severe("Directory for File NOT FOUND! <" + inputFileDir.getAbsolutePath() + "> (It was created for you)");
            BcFileUtils.mkDirs(inputFileDir);
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

    private File getInputFileDir(String workingDir, int fileNumber) {
        return new File(workingDir + "/Input-File-" + fileNumber);
    }


}
