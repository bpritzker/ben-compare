package org.ben.bc.examples;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.ben.bc.BcMain;
import org.ben.bc.BcUtils;
import org.ben.bc.testutil.BcTestingFileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;


/**
 * If you want to run this from Files on an ongoing basis
 * this might be the easiest.
 * You just drop the file in a directory and this picks it up.
 *
 */
public class BcRunFromDirectories {



    // REQUIRED.....
    // NOTE: Use -1 if you want the whole line loaded.
    private static final int FILE_COLUMN_INDEX_ZERO_BASED_1 = 0;
    private static final int FILE_COLUMN_INDEX_ZERO_BASED_2 = 0;
    private static final String WORKING_DIR = "C:/Temp/Comparator";


    // OPTIONAL...


    private static final Logger logger = Logger.getLogger(BcRunFromDirectories.class.getName());


    public static void main(String[] args) {
        BcRunFromDirectories runFrom2Directories = new BcRunFromDirectories();
        try {
            runFrom2Directories.run();
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
        }
    }

    private void run() throws Exception {
        File file1 = getFileInDirectory(WORKING_DIR, 1);
        File file2 = getFileInDirectory(WORKING_DIR, 2);

        if (file1 == null || file2 == null) {
            throw new RuntimeException("Error getting Input file at: <" + WORKING_DIR + ">. See Log for details.");
        }

        Collection<String> collection1 = loadCollectionFromFile(file1, FILE_COLUMN_INDEX_ZERO_BASED_1);
        Collection<String> collection2 = loadCollectionFromFile(file2, FILE_COLUMN_INDEX_ZERO_BASED_2);

        BcMain.runCompare(BcUtils.cleanFileName(file1.getName()), collection1,
                BcUtils.cleanFileName(file2.getName()), collection2);

        File zipFile = new File(WORKING_DIR + "/Reports.zip");
        BcTestingFileUtils.zipDirectory(new File(WORKING_DIR + "/reports"), zipFile);

    }



    private File getFileInDirectory(String workingDir, int fileNumber) {
        File inputFileDir = new File(workingDir + "/Input-File-" + fileNumber);

        if (! inputFileDir.exists()) {
            logger.severe("Directory for File NOT FOUND! (It was created for you)");
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
        return filesInDir[0];
    }


    protected Collection<String> loadCollectionFromFile(File inputFile, int columnToLoad) throws IOException, CsvValidationException {

        Collection<String> result;
        if (columnToLoad == -1) {
            Path path = Paths.get(inputFile.getAbsolutePath());
            result = Files.readAllLines(path);
        } else {
            result = loadCollectionFromCsvFile(inputFile.getAbsolutePath(), columnToLoad);
        }

        return result;
    }


    /**
     * I know this is duplicate code. I'm not sure how I want to handle the duplicate code yet...
     */
    protected Collection<String> loadCollectionFromCsvFile(String absolutePathFile, int columnToLoad) throws IOException, CsvValidationException {

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(absolutePathFile)).build()) {
            Collection<String> result = new ArrayList<>();
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                result.add(nextLine[columnToLoad]);
            }
            return result;
        }
    }

}
