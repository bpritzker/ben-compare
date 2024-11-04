package org.ben.bc;

import org.ben.bc.data.BcCompareResults;
import org.ben.bc.data.conf.BcCompareConfig;
import org.ben.bc.data.conf.BcConfig;
import org.ben.bc.module.BcComparator;
import org.ben.bc.module.BcReporter;

import java.util.Collection;


public class BcMain {



    public static void runSimpleStatic(String collectionName1, Collection<String> collection1,
                                       String collectionName2, Collection<String> collection2) {
        BcMain main = new BcMain();
        main.runSimple(collectionName1, collection1, collectionName2, collection2);
    }


    /**
     * This is the simple method to call the compare utility.
     * This program will compare collection 1 to collection 2 and display the results.
     * @param collectionName1 - This is the display Name for Collection 1
     * @param collection1 -
     * @param collectionName2 - This is the display name for Collection 2
     * @param collection2 -
     */
    public void runSimple(
            String collectionName1, Collection<String> collection1,
            String collectionName2, Collection<String> collection2) {

        BcConfig config = buildConfig();
        BcCompareResults compareResults = compareResults(collectionName1, collection1, collectionName2, collection2, config.getCompareConfig());
        reportResults(compareResults, config);


    }




    protected BcConfig buildConfig() {
        BcConfig resultConfig = BcUtils.buildDefaultConfig();
        return resultConfig;
    }

    protected BcCompareResults compareResults(
            String collectionName1, Collection<String> collection1,
            String collectionName2, Collection<String> collection2,
            BcCompareConfig config) {
        BcComparator comparator = new BcComparator(config);
        BcCompareResults result = comparator.runCompare(collectionName1, collection1, collectionName2, collection2);
        return result;
    }


    protected void reportResults(BcCompareResults compareResults, BcConfig config) {
        BcReporter reporter = new BcReporter(config);
        reporter.defaultReport(compareResults);
    }



}
