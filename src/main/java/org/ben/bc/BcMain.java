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

    @SuppressWarnings("unused")
    public static void runStatic(String collectionName1, Collection<String> collection1,
                                       String collectionName2, Collection<String> collection2,
                                       BcConfig config) {
        BcMain main = new BcMain();
        main.run(collectionName1, collection1, collectionName2, collection2, config);
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
        run(collectionName1, collection1, collectionName2, collection2, config);
    }


    @SuppressWarnings("unused")
    public void runCommon(
            String collectionName1, Collection<String> collection1,
            String collectionName2, Collection<String> collection2,
            String reportDir) {

        BcConfig config = buildConfig();
        config.getReportConfig().setReportDir(reportDir);
        run(collectionName1, collection1, collectionName2, collection2, config);
    }

    /**
     * This method allows you to override the config.
     */
    public void run(
            String collectionName1, Collection<String> collection1,
            String collectionName2, Collection<String> collection2,
            BcConfig inConfig) {

        BcCompareResults compareResults = compareResults(collection1, collection2, inConfig.getCompareConfig());
        reportResults(collectionName1, collectionName2, compareResults, inConfig);
    }




    protected BcConfig buildConfig() {
        BcConfig resultConfig = BcUtils.buildDefaultConfig();
        return resultConfig;
    }

    protected BcCompareResults compareResults(
            Collection<String> collection1,
            Collection<String> collection2,
            BcCompareConfig config) {
        BcComparator comparator = new BcComparator(config);
        BcCompareResults result = comparator.runCompare( collection1,  collection2);
        return result;
    }


    protected void reportResults(String collectionName1, String collectionName2, BcCompareResults compareResults, BcConfig config) {
        BcReporter reporter = new BcReporter(config);
        reporter.defaultReport(collectionName1, collectionName2, compareResults);
    }



}
