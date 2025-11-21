package net.benp.bc;

import net.benp.bc.data.BcCompareResult;
import net.benp.bc.data.conf.BcCompareConfig;
import net.benp.bc.data.conf.BcConfig;
import net.benp.bc.module.BcComparator;
import net.benp.bc.module.BcReporter;
import net.benp.bc.util.BcUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;


public class BcCompareMain {

    private static final Logger logger = Logger.getLogger(BcCompareMain.class.getName());


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////  Below are all the methods to run/start the comparator program.
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * This is probably the easiest way to get started. Just call this from inside any program
     */
    public static BcCompareResult runCompare(String collectionName1, Collection<String> collection1,
                                             String collectionName2, Collection<String> collection2) {
        BcCompareMain main = new BcCompareMain();
        return main.runCompareMain(collectionName1, collection1, collectionName2, collection2, null);
    }


    /**
     * This is a simple way to just get the compare and get the results object.
     * It should not print anything in the console.
     */
    public static BcCompareResult runCompare(Collection<String> collection1, Collection<String> collection2) {
        BcCompareMain main = new BcCompareMain();
        BcConfig config = BcConfig.buildDefaultConfig();
        config.setReportConfig(null);
        return main.run(null, collection1, null, collection2, config);
    }



    /**
     * This is what I think I would use most of the time.
     * It sets all the default compare setting but also creates the reports.
     *
     */
    @SuppressWarnings("unused")
    public static BcCompareResult runCompare(String collectionName1, Collection<String> collection1,
                                  String collectionName2, Collection<String> collection2,
                                  String reportDir) {
        BcCompareMain main = new BcCompareMain();
        return main.runCompareMain(collectionName1, collection1, collectionName2, collection2, reportDir);
    }



    /**
     * This is the simple method to call the compare utility.
     * This program will compare collection 1 to collection 2 and display the results.
     * @param collectionName1 - This is the display Name for Collection 1
     * @param collection1 -
     * @param collectionName2 - This is the display name for Collection 2
     * @param collection2 -
     */
    @SuppressWarnings("unused")
    public static BcCompareResult runCompare(String collectionName1, Collection<String> collection1,
                                       String collectionName2, Collection<String> collection2,
                                       BcConfig config) {
        BcCompareMain main = new BcCompareMain();
        return main.run(collectionName1, collection1, collectionName2, collection2, config);
    }





    public BcCompareResult runCompareSimple(
            String collectionName1, Collection<String> collection1,
            String collectionName2, Collection<String> collection2) {

        BcConfig config = buildConfig();
        return run(collectionName1, collection1, collectionName2, collection2, config);
    }


    @SuppressWarnings("unused")
    public BcCompareResult runCompareMain(
            String collectionName1, Collection<String> collection1,
            String collectionName2, Collection<String> collection2,
            String reportDir) {

        BcConfig config = buildConfig();
        config.getReportConfig().setReportDir(reportDir);
        return run(collectionName1, collection1, collectionName2, collection2, config);
    }

    /**
     * This method should be the final one to be called. It checks the data to make sure it's valid.
     */
    public BcCompareResult run(
            String collectionName1, Collection<String> collection1,
            String collectionName2, Collection<String> collection2,
            BcConfig inConfig) {

        String cleanCollectionName1 = BcUtils.defaultNotEmptyValue(collectionName1, "List 1");
        String cleanCollectionName2 = BcUtils.defaultNotEmptyValue(collectionName2, "List 2");

        // The program does allow null and empty. No reason to error when we can just generate the reports.
        Collection<String> cleanCollection1 = collection1;
        if (collection1 == null) {
            cleanCollection1 = new ArrayList<>();
        }

        Collection<String> cleanCollection2 = collection2;
        if (collection2 == null) {
            cleanCollection2 = new ArrayList<>();
        }

        BcCompareResult compareResults = compareResults(cleanCollection1, cleanCollection2, inConfig.getCompareConfig());
        reportResults(cleanCollectionName1, cleanCollectionName2, compareResults, inConfig);
        return compareResults;
    }




    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////  Override methods below if you want to customize how the program runs. See Examples.
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////


    protected BcConfig buildConfig() {
        BcConfig resultConfig;
        resultConfig = BcConfig.buildDefaultConfig();
        return resultConfig;
    }

    protected BcCompareResult compareResults(
            Collection<String> collection1,
            Collection<String> collection2,
            BcCompareConfig config) {
        BcComparator comparator = new BcComparator(config);
        BcCompareResult result;
        result = comparator.runCompare( collection1,  collection2);
        return result;
    }


    protected void reportResults(String collectionName1, String collectionName2, BcCompareResult compareResults, BcConfig config) {
        if (config.getReportConfig() == null) {
            logger.fine("BcCompcare.ReportConfig is 'null', skipping reporting.");
        }
        BcReporter reporter = new BcReporter(config.getReportConfig());
        reporter.defaultReport(collectionName1, collectionName2, compareResults, config.getCompareConfig());
    }



}
