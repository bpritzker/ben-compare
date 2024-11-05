package org.ben.bc.module;

import org.ben.bc.data.BcCompareResults;
import org.ben.bc.data.conf.BcConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class BcReporter {

    private static final Logger logger = Logger.getLogger(BcReporter.class.getName());


    private final BcConfig config;

    public BcReporter(BcConfig config) {
        this.config = config;
    }


    public void defaultReport(String collectionName1, String collectionName2, BcCompareResults compareResults) {

        List<String> reportData = buildReportData(collectionName1, collectionName2, compareResults);

        toFile(reportData);

        systemoutReportData(reportData);


    }

    private void toFile(List<String> reportData) {

        String reportDirStr = config.getReportConfig().getReportDir();
        if (reportDirStr == null) {
            logger.fine("File Reporting is disabled. To enable, set the ReportDirStr on the Report Config.");
            return;
        }

        // If here, we want to create the File reports.
        if (! new File(reportDirStr).mkdirs()) {
            logger.warning("Unable to create directory at: " + reportDirStr);
        }

        File summaryReportFile = new File(reportDirStr + "/SummaryReportFile.txt");
        try (FileWriter writer = new FileWriter(summaryReportFile)) {
            for(String str: reportData) {
                writer.write(str + System.lineSeparator());
            }
        } catch (IOException e) {
            logger.severe("Unable to create file at: " + summaryReportFile.getAbsolutePath());
            throw new RuntimeException(e);
        }


    }

    protected List<String> buildReportData(String collectionName1, String collectionName2, BcCompareResults compareResults) {

        // This will be the list of all lines we want to return to report on
        List<String> result = new ArrayList<>();

        // Add separators to indicate the start...
        result.add(lineSeparator());
        result.add(lineSeparator());
        result.add(lineSeparator());

        // Add the config info
        List<String> configInfo = buildConfigOutput();
        result.addAll(configInfo);
        result.add(lineSeparator());

        // Add the basic results
        List<String> basicCompareInfo = buildBasicCompareInfo(collectionName1, collectionName2, compareResults);
        result.addAll(basicCompareInfo);




        // Add separators to show where the report ends.
        result.add("");
        result.add(lineSeparator());
        result.add(lineSeparator());
        result.add(lineSeparator());


        return result;
    }

    private List<String> buildBasicCompareInfo(String name1, String name2, BcCompareResults compareResults) {
        List<String> result = new ArrayList<>();
        result.add("Comparison Results...");

        String totalItemsStr = "Total Items in (%s) : %d";
        result.add(String.format(totalItemsStr, name1, compareResults.getCollection1().size()));
        result.add(String.format(totalItemsStr, name2, compareResults.getCollection2().size()));

        result.add("");
        String duplicates = "Duplicates (%s): %d";
        Map<String, List<String>> duplicates1 = getDuplicates(compareResults.getNormalized1());
        result.add(String.format(duplicates, name1, duplicates1.size()));

        Map<String, List<String>> duplicates2 = getDuplicates(compareResults.getNormalized2());
        result.add(String.format(duplicates, name2, duplicates2.size()));

        result.add("");

        result.add("");
        result.add("In BOTH: " + compareResults.getInBoth().size());
        result.add("   " + truncateCollection(compareResults.getInBoth()));

        String differences = "Total Items in (%s) that are NOT in (%s): %d";
        result.add("");
        result.add(String.format(differences, name1,
                name2, compareResults.getInC1NotInC2().size()));
        result.add("   " + truncateCollection(compareResults.getInC1NotInC2()));

        result.add("");
        result.add(String.format(differences, name2,
                name1,  compareResults.getInC2NotInC1().size()));
        result.add("   " + truncateCollection(compareResults.getInC2NotInC1()));


        return result;
    }

    private String truncateCollection(Collection<String> collection) {

        StringBuilder sb = new StringBuilder();
        String separator = "";
        for (String currValue : collection) {
            sb.append(separator).append(currValue);
            if (sb.length() > 64) {
                sb.append(separator).append("...");
            }
            separator = ", ";
        }

        return sb.toString();
    }


    /**
     * Duplicates are anything that are NOT 1 to 1... so anything with more than 1 value.
     * If you more than one value, that means that after normalization, two values were the same.
     */
    private Map<String, List<String>> getDuplicates(Map<String, List<String>> normalized) {

        Map<String, List<String>> result = new HashMap<>();
        for (String currNormalizedValue : normalized.keySet()) {
            List<String> values = normalized.get(currNormalizedValue);
            if (values.size() > 1) {
                result.put(currNormalizedValue, values);
            }
        }
        return result;
    }

    private List<String> buildConfigOutput() {
        List<String> result = new ArrayList<>();
        result.add("Configuration Settings...");
        result.add("Ignore Case (Compare): " + config.getCompareConfig().isIgnoreCase());
        result.add("Trim        (Compare): " + config.getCompareConfig().isTrim());
        return result;
    }

    private void systemoutReportData(List<String> reportData) {
        for (String currData : reportData) {
            System.out.println(currData);
        }
    }


    protected String lineSeparator() {
        StringBuilder result = new StringBuilder();
        for (int i=0; i< config.getReportConfig().getLineSeparatorLength(); i++) {
            result.append(config.getReportConfig().getLineSeparatorChar());
        }
        return result.toString();
    }


}
