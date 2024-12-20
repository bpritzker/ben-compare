package net.benp.bc.examples.other;

import net.benp.bc.BcCompareMain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.logging.Logger;

public class BcCompareRunFrom2Files extends BcCompareMain {

    // REQUIRED.....
    private static final String COLLECTION_NAME_1 = "";
    private static final String COLLECTION_NAME_2 = "";
    private static final String ABSOLUTE_PATH_FILE_1  = "";
    private static final String ABSOLUTE_PATH_FILE_2  = "";


    // OPTIONAL...


    private static final Logger logger = Logger.getLogger(BcCompareRunFrom2Files.class.getName());


    public static void main(String[] args) {
        BcCompareRunFrom2Files runFrom2Files = new BcCompareRunFrom2Files();
        try {
            runFrom2Files.run();
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    private void run() throws IOException{
        Collection<String> collection1 = loadCollectionFromFile(ABSOLUTE_PATH_FILE_1);
        Collection<String> collection2 = loadCollectionFromFile(ABSOLUTE_PATH_FILE_2);

        BcCompareMain.runCompare(COLLECTION_NAME_1, collection1, COLLECTION_NAME_2, collection2);
    }



    protected Collection<String> loadCollectionFromFile(String absolutePathFile) throws IOException {
        Path path = Paths.get(absolutePathFile);
        Collection<String> result = Files.readAllLines(path);
        return result;
    }


}
