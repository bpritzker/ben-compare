package org.ben.bc.module;

import org.ben.bc.data.BcCompareResult;
import org.ben.bc.data.conf.BcCompareConfig;
import org.ben.bc.data.conf.BcReportConfig;
import org.ben.bc.module.report.BcDetailsReports;
import org.ben.bc.module.report.BcSummaryReport;
import org.ben.bc.util.BcFileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BcReporter {

    private static final Logger logger = Logger.getLogger(BcReporter.class.getName());

    private final BcReportConfig reportConfig;

    public BcReporter(BcReportConfig reportConfig) {
        this.reportConfig = reportConfig;
    }


    public void defaultReport(String collectionName1, String collectionName2, BcCompareResult compareResults, BcCompareConfig compareConfig) {

        deleteExistingReports(reportConfig.getReportDir(), reportConfig.isDeleteExistingReports());

        List<String> reportData = buildReportData(collectionName1, collectionName2, compareResults, compareConfig);
        String reportDirStr = reportConfig.getReportDir();

        printToConsoleReportData(reportData);

        if (reportDirStr == null) {
            logger.fine("File Reporting is disabled. To enable, set the ReportDirStr on the Report Config.");
        } else {
            toFile(reportData, reportDirStr);
            BcDetailsReports detailsReports = new BcDetailsReports();
            detailsReports.runDetailReports(collectionName1, collectionName2, compareResults, reportDirStr);
            System.out.println("\n   *** The Report files are located at: <" + reportDirStr + ">   ***");

        }

    }

    private void deleteExistingReports(String reportDir, boolean deleteExistingReports) {
        if (reportDir == null || (! deleteExistingReports)) {
            // Do nothing.
            return;
        }

        File reportDirFile = new File(reportDir);
        if (reportDirFile.exists()) {
            for (File file : reportDirFile.listFiles()) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
        }

        reportDirFile = new File(BcDetailsReports.getDetailsDir(reportDir));
        if (reportDirFile.exists()) {
            for (File file : reportDirFile.listFiles()) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
        }

    }

    private void toFile(List<String> reportData, String reportDirStr) {

        // If here, we want to create the File reports.
        BcFileUtils.mkDirs(new File(reportDirStr));


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

    protected List<String> buildReportData(String collectionName1, String collectionName2, BcCompareResult compareResults, BcCompareConfig compareConfig) {

        BcSummaryReport summaryReport = new BcSummaryReport(reportConfig);

        // Add the config info
        List<String> configInfo = buildConfigOutput(compareConfig);
        List<String> result = new ArrayList<>(configInfo);
        result.add(lineSeparator());

        List<String> summaryReportData = summaryReport.buildSummaryReport(collectionName1, collectionName2, compareResults, compareConfig);
        result.addAll(summaryReportData);
        return result;

    }



    private void printToConsoleReportData(List<String> reportData) {

        if (! reportConfig.isPrintToConsole()) {
            logger.fine("Print to System.out is disabled!");
            return;
        }

        // Add separators to indicate the start...
        System.out.println(lineSeparator());
        System.out.println(lineSeparator());
        System.out.println(lineSeparator());

        for (String currData : reportData) {
            System.out.println(currData);
        }

        // Add separators to show where the report ends.
        System.out.println();
        System.out.println(lineSeparator());
        System.out.println(lineSeparator());
        System.out.println(lineSeparator());

    }

    protected String lineSeparator() {
        StringBuilder result = new StringBuilder();
        for (int i=0; i< reportConfig.getLineSeparatorLength(); i++) {
            result.append(reportConfig.getLineSeparatorChar());
        }
        return result.toString();
    }

    private List<String> buildConfigOutput(BcCompareConfig compareConfig) {
        List<String> result = new ArrayList<>();
        result.add("Configuration Settings...");
        result.add("Ignore Case (Compare): " + compareConfig.isIgnoreCase());
        result.add("Trim        (Compare): " + compareConfig.isTrim());
        return result;
    }




}
