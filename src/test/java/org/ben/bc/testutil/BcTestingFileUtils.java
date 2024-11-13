package org.ben.bc.testutil;

import org.ben.bc.module.BcReporter;

import java.io.*;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BcTestingFileUtils {

    private static final Logger logger = Logger.getLogger(BcTestingFileUtils.class.getName());

    /**
     *
     * @param directoryToZip
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



}
