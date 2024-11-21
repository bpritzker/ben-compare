package org.ben.bc.testutil;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BcTestingFileUtils {

    private static final Logger logger = Logger.getLogger(BcTestingFileUtils.class.getName());






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



    /**
     *
     * @param inZipFile - You can pass in the file you want to create OR pass in null and it will create a zip file with the directory name.
     */
    public static void zipDirectory(File directoryToZip, File inZipFile) throws Exception {

        File zipFile;
        if (inZipFile == null) {
            zipFile =  new File(directoryToZip.getAbsolutePath() + ".zip");
        } else {
            zipFile = inZipFile;
        }

        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);
            addDirToZipArchive(zos, new File(directoryToZip.getAbsolutePath()), null);

//        } catch (FileNotFoundException fnfe) {
//            throw new PsEverbridgeException("Error opening file for zipping.", fnfe, "directoryToZip", directoryToZip.getAbsolutePath(), "zipFile", zipFile.getAbsolutePath());
//        } catch (IOException ioe) {
//            throw new PsEverbridgeException("Io Exception file for zipping.", ioe, "directoryToZip", directoryToZip.getAbsolutePath(), "zipFile", zipFile.getAbsolutePath());

        } finally {
            try {
                if (zos != null) {
                    zos.flush();
                }
                if (fos != null) {
                    fos.flush();
                }
                if (zos != null) {
                    zos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                System.out.println("Nothing to do here! ");
                logger.severe("Error trying to close the FileOutputStream or ZipOutputStream while archiving file");
//                throw new PsEverbridgeException("Error trying to close the FileOutputStream or ZipOutputStream while archiving file", e);
            }
        }

    }

    private static void addDirToZipArchive(ZipOutputStream zos, File fileToZip, String parrentDirectoryName) throws IOException {
        if (fileToZip == null || !fileToZip.exists()) {
            return;
        }

        String zipEntryName = fileToZip.getName();
        if (parrentDirectoryName!=null && !parrentDirectoryName.isEmpty()) {
            zipEntryName = parrentDirectoryName + "/" + fileToZip.getName();
        }

        if (fileToZip.isDirectory()) {
            logger.fine("Zipping: +" + zipEntryName);
            for (File file : fileToZip.listFiles()) {
                addDirToZipArchive(zos, file, zipEntryName);
            }
        } else {
            logger.fine("Zipping:    " + zipEntryName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(fileToZip);
            zos.putNextEntry(new ZipEntry(zipEntryName));
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
            fis.close();
        }
    }




    public static List<List<String>> loadDataFromCsvFile(File fileToLoad) throws IOException {

        try (Reader reader = new FileReader(fileToLoad);
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT)) {


            List<List<String>> resultRecords = new ArrayList<>();

            for (CSVRecord record : parser) {
                List<String> row = new ArrayList<>();
                for (String value : record) {
                    row.add(value);
                }
                resultRecords.add(row);
            }
            return resultRecords;
        }
    }




    public static List<String> loadListFromCsvFile(String absolutePathFile, int columnToLoad) throws Exception {

        List<List<String>> fileData = BcTestingFileUtils.loadDataFromCsvFile(new File(absolutePathFile));
        List<String> result = new ArrayList<>();
        for (List<String> currLine : fileData) {
            result.add(currLine.get(columnToLoad));
        }
        return result;
    }













}
