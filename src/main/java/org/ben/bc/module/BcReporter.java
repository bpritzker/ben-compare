package org.ben.bc.module;

import org.ben.bc.data.BcCompareResults;
import org.ben.bc.data.conf.BcConfig;

import java.util.*;

public class BcReporter {


    private BcConfig config;
    private char lineSeparatorChar = '#';

    public BcReporter(BcConfig config) {
        this.config = config;
    }


    public void defaultReport(BcCompareResults compareResults) {

        List<String> reportData = buildReportData(compareResults);

        systemoutReportData(reportData);

//        toFile(reportData);



    }

    protected List<String> buildReportData(BcCompareResults compareResults) {

        // This will be the list of all lines we want to return to report on
        List<String> result = new ArrayList<>();

        // Add separators to indicate the start..
        result.add(lineSeparator());
        result.add(lineSeparator());
        result.add(lineSeparator());

        // Add the config info
        List<String> configInfo = buildConfigOutput();
        result.addAll(configInfo);
        result.add(lineSeparator());

        // Add the basic results
        List<String> basicCompareInfo = buildBasicCompareInfo(compareResults);
        result.addAll(basicCompareInfo);




        // Add separators to show where the report ends.
        result.add("");
        result.add(lineSeparator());
        result.add(lineSeparator());
        result.add(lineSeparator());


        return result;
    }

    private List<String> buildBasicCompareInfo(BcCompareResults compareResults) {
        List<String> result = new ArrayList<>();

        // Since the name is uses everywhere this makes it easier to read the code.
        String name1 = compareResults.getCollectionName1();
        String name2 = compareResults.getCollectionName2();

        String totalItemsStr = "Total Items in '%s'(%d) : %d";
        result.add(String.format(totalItemsStr, name1, 1, compareResults.getCollection1().size()));
        result.add(String.format(totalItemsStr, name2, 2, compareResults.getCollection2().size()));

        String duplicates = "Duplicates (%s): %d";
        Map<String, List<String>> duplicates1 = getDuplicates(compareResults.getNormalized1());
        result.add(String.format(duplicates, name1, duplicates1.size()));

        Map<String, List<String>> duplicates2 = getDuplicates(compareResults.getNormalized2());
        result.add(String.format(duplicates, name2, duplicates2.size()));


        result.add("");
        result.add("In BOTH Collections: " + compareResults.getInBoth().size());
        result.add("   " + truncateCollection(compareResults.getInBoth()));

        String differences = "Total Items in '%s' that are NOT in '%s': %d";
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
     * @param normalized
     * @return
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
        for (int i=0; i< 100; i++) {
            result.append(lineSeparatorChar);
        }
        return result.toString();
    }


}
