package org.ben.bc.module.report;

import org.ben.bc.data.BcCompareResult;
import org.ben.bc.util.BcFileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BcDetailsReports {

    public static String getDetailsDir(String reportsDir) {
        return reportsDir + "/details/";
    }

    public void runDetailReports(String collectionName1, String collectionName2, BcCompareResult compareResults, String reportDirStr) {


        File reportDetailsDir = new File(getDetailsDir(reportDirStr));
        BcFileUtils.mkDirs(reportDetailsDir);

        String cleanCollectionName1 = "'" + BcFileUtils.cleanFileName(collectionName1) + "'";
        String cleanCollectionName2 = "'" + BcFileUtils.cleanFileName(collectionName2) + "'";


        File collectionFile1 = new File(reportDetailsDir + "/" + "AllValues-" + cleanCollectionName1 + ".txt");
        BcFileUtils.writeToFile(compareResults.getCollection1(), collectionFile1);

        File collectionFile2 = new File(reportDetailsDir + "/" + "AllValues-" + cleanCollectionName2 + ".txt");
        BcFileUtils.writeToFile(compareResults.getCollection2(), collectionFile2);

        File normalizedFile1 = new File(reportDetailsDir + "/" + "CleanValues-" + cleanCollectionName1 + ".txt");
        List<String> normalizedData = getNormalizedData(compareResults.getNormalized1());
        BcFileUtils.writeToFile(normalizedData, normalizedFile1);

        File normalizedFile2 = new File(reportDetailsDir + "/" + "CleanValues-" + cleanCollectionName2 + ".txt");
        normalizedData = getNormalizedData(compareResults.getNormalized2());
        BcFileUtils.writeToFile(normalizedData, normalizedFile2);


        File inC1NotInC2File = new File(reportDetailsDir + "/" + "In--" + cleanCollectionName1 + "--NotIn--" + cleanCollectionName2 + ".txt");
        BcFileUtils.writeToFile(compareResults.getInC1NotInC2(), inC1NotInC2File);

        File inC2NotInC1File = new File(reportDetailsDir + "/" + "In--" + cleanCollectionName2 + "--NotIn--" + cleanCollectionName1 + ".txt");
        BcFileUtils.writeToFile(compareResults.getInC2NotInC1(), inC2NotInC1File);


        File bothFile = new File(reportDetailsDir + "/" + "Matches" + ".txt");
        BcFileUtils.writeToFile(compareResults.getInBoth(), bothFile);







    }


    protected List<String> getNormalizedData(Map<String, List<String>> normalized) {

        List<List<String>> csvNormalizedOutputData = getCsvNormalizedData(normalized);
        List<String> result = new ArrayList<>();
        for (List<String> currLine : csvNormalizedOutputData) {
            StringBuilder stringBuilder = new StringBuilder();
            String separator = "";
            for (String currValue : currLine) {
                stringBuilder.append(separator).append(currValue);
                separator = ",";
            }
            result.add(stringBuilder.toString());
        }
        return result;
    }

    private List<List<String>> getCsvNormalizedData(Map<String, List<String>> normalized) {
        List<List<String>> result = new ArrayList<>();
        int maxValuesLength = getMaxListLength(normalized.values());
        for (Map.Entry<String, List<String>> entry : normalized.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();

            List<String> tempOutputLine = new ArrayList<>();
            tempOutputLine.add(key);
            tempOutputLine.add("" + values.size());
            List<String> paddedLine = buildPaddedLine(values, maxValuesLength);
            tempOutputLine.addAll(paddedLine);
            result.add(tempOutputLine);
        }
        return result;
    }

    private List<String> buildPaddedLine(List<String> values, int maxValuesLength) {
        List<String> result = new ArrayList<>(values);
        for (int i=0; i < maxValuesLength - values.size(); i++) {
            result.add("");
        }
        return result;
    }

    private int getMaxListLength(Collection<List<String>> values) {
        int result = 0;
        for (List<String> currLine : values) {

            if (currLine.size() > result) {
                result = currLine.size();
            }
        }
        return result;
    }


}
