package net.benp.bc.module.report;


import net.benp.bc.data.BcCompareResult;
import net.benp.bc.data.conf.BcCompareConfig;
import net.benp.bc.data.conf.BcReportConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This class builds the Summary report.
 * It shows everything that happened at a high level (details can be enabled).
 * Most of the time this is what you want to just look at.
 *
 */
public class BcSummaryReport {

    private final BcReportConfig reportConfig;


    public BcSummaryReport(BcReportConfig reportConfig) {
        this.reportConfig = reportConfig;
    }


    public List<String> buildSummaryReport(String collectionName1, String collectionName2, BcCompareResult compareResults, BcCompareConfig compareConfig) {

        // This will be the list of all lines we want to return to report on

        // Add the basic results
        List<String> basicCompareInfo = buildBasicCompareInfo(collectionName1, collectionName2, compareResults);
        List<String> result = new ArrayList<>(basicCompareInfo);

        return result;
    }



    private List<String> buildBasicCompareInfo(String name1, String name2, BcCompareResult compareResults) {
        List<String> result = new ArrayList<>();
        result.add("Comparison Results...");


        // Add in the Total for the input collection
        result.addAll(buildTotalStarting(name1, compareResults.getCollection1(), reportConfig.isDisplayValuesForTotal(), reportConfig.getValuesDisplayMaxLength()));
        result.addAll(buildTotalStarting(name2, compareResults.getCollection2(), reportConfig.isDisplayValuesForTotal(), reportConfig.getValuesDisplayMaxLength()));


        // Add in the duplicates
        result.add("");
        result.addAll(buildDuplicates(name1, compareResults.getNormalized1(), reportConfig.isDisplayValuesForDuplicates(), reportConfig.getValuesDisplayMaxLength()));
        result.addAll(buildDuplicates(name2, compareResults.getNormalized2(), reportConfig.isDisplayValuesForDuplicates(), reportConfig.getValuesDisplayMaxLength()));


        // Add in blanks
        result.add("");
        result.addAll(buildBlanks(name1, compareResults.getBlankValueToCount1()));
        result.addAll(buildBlanks(name2, compareResults.getBlankValueToCount2()));

        // Add in values that match for Both
        result.add("");
        result.add("");
        result.add("In BOTH: " + compareResults.getInBoth().size());
        if (reportConfig.isDisplayValuesForBoth()) {
            result.add("   " + collectionForDisplay(compareResults.getInBoth(), reportConfig.getValuesDisplayMaxLength()));
        }

        // Add in values in one but NOT the other collection
        result.addAll(buildDiff(name1, name2, compareResults.getInC1NotInC2(), reportConfig.isDisplayValuesForDiff1(), reportConfig.getValuesDisplayMaxLength()));
        result.addAll(buildDiff(name2, name1, compareResults.getInC2NotInC1(), reportConfig.isDisplayValuesForDiff2(), reportConfig.getValuesDisplayMaxLength()));


        return result;
    }

    private Collection<String> buildBlanks(String name, Map<String, Integer> blankValueToCount) {
        int blanksRemovedCount = 0;

        for (String currBlankValue : blankValueToCount.keySet()) {
            blanksRemovedCount += blankValueToCount.get(currBlankValue);
        }

        String blanksStr = "Blanks Removed (%s): %d";
        String tempDuplicateMain = String.format(blanksStr, name, blanksRemovedCount);
        List<String> result = new ArrayList<>();
        result.add(tempDuplicateMain);
        return result;


    }

    private Collection<String> buildDiff(String inCollectionName, String notInCollectionName, Collection<String> collection, boolean isDisplayValues, Long valuesDisplayMaxLength) {

        List<String> result = new ArrayList<>();
        String differencesString = "Total Items in (%s) that are NOT in (%s): %d";
        result.add("");
        result.add(String.format(differencesString, inCollectionName, notInCollectionName, collection.size()));
        if (isDisplayValues) {
            result.add("   " + collectionForDisplay(collection, valuesDisplayMaxLength));
        }
        return result;
    }

    private List<String> buildTotalStarting(String name,Collection<String> collection, boolean isDisplayValuesForTotal, Long valuesForMaxDisplay) {

        List<String> result = new ArrayList<>();
        String totalItemsStr = "Total Items in (%s) : %d";
        result.add(String.format(totalItemsStr, name, collection.size()));
        if (isDisplayValuesForTotal) {
            result.add(collectionForDisplay(collection, valuesForMaxDisplay));
        }
        return result;
    }

    protected List<String> buildDuplicates(String name, Map<String, List<String>> normalized, boolean displayValuesForDupes, long displayMaxLength) {

        int duplicatesRemovedCount = 0;
        StringBuilder duplicatesDisplay =  new StringBuilder("{");
        String separator = "";
        for (String currNormalizedValue : normalized.keySet()) {
            // Note, this needs to be a List because we need to know how many total values were deduped out
            List<String> values = new ArrayList<>(normalized.get(currNormalizedValue));
            if (values.size() > 1) {
                // These are the ones we care about...
                // NOTE: When counting duplicates removed, we want the total in the list -1 because the first one is counted
                //    in other parts of the report
                duplicatesRemovedCount += (values.size() - 1);
                String tempListStr = currNormalizedValue + ":" + collectionForDisplay(normalized.get(currNormalizedValue), displayMaxLength);
                duplicatesDisplay.append(separator).append(tempListStr);

                separator = ",";
            }
        }
        duplicatesDisplay.append("}");

        List<String> resultDuplicatesDisplay = new ArrayList<>();
        String duplicatesStr = "Duplicates Removed (%s): %d";
        String tempDuplicateMain = String.format(duplicatesStr, name, duplicatesRemovedCount);
        resultDuplicatesDisplay.add(tempDuplicateMain);

        // Don't display if nothing has been removed.
        if (displayValuesForDupes && duplicatesRemovedCount != 0) {
            resultDuplicatesDisplay.add("   " + duplicatesDisplay);
        }

        return resultDuplicatesDisplay;
    }

    protected String collectionForDisplay(Collection<String> collection, Long maxValuesDisplayLength) {

        StringBuilder sb = new StringBuilder();

        if (collection == null || collection.size() == 0) {
            return "";
        }

        String separator = "[";
        for (String currValue : collection) {
            sb.append(separator).append(currValue);

            // This needs to be above the max test.
            //  See unit test on why.
            separator = ", ";

            if (maxValuesDisplayLength != null
                    && maxValuesDisplayLength != -1
                    && sb.length() > maxValuesDisplayLength) {
                sb.append(separator).append("...");
                break;
            }
        }
        sb.append("]");
        return sb.toString();
    }


}
