package org.benp.bc.examples.other;

import org.benp.bc.BcMain;
import org.benp.bc.testutil.BcTestingFileUtils;
import org.benp.bc.util.BcFileUtils;

import java.io.File;
import java.util.List;
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

        List<String> list1 = BcTestingFileUtils.loadListFromCsvFile(file1.getAbsolutePath(), FILE_COLUMN_INDEX_ZERO_BASED_1);
        List<String> list2 = BcTestingFileUtils.loadListFromCsvFile(file2.getAbsolutePath(), FILE_COLUMN_INDEX_ZERO_BASED_2);

        BcMain.runCompare(BcFileUtils.cleanFileName(file1.getName()), list1,
                BcFileUtils.cleanFileName(file2.getName()), list2);

        File zipFile = new File(WORKING_DIR + "/Reports.zip");
        BcTestingFileUtils.zipDirectory(new File(WORKING_DIR + "/reports"), zipFile);

    }



    private File getFileInDirectory(String workingDir, int fileNumber) {
        File inputFileDir = new File(workingDir + "/Input-File-" + fileNumber);

        if (! inputFileDir.exists()) {
            logger.severe("Directory for File NOT FOUND! (It was created for you)");
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
        return filesInDir[0];
    }




}
