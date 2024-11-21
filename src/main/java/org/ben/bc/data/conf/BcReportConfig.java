package org.ben.bc.data.conf;


/**
 * This class controls how the results of the compare are displayed.
 * </br>
 * I tried to organize it where the items at the top are the MOST used.
 */
public class BcReportConfig {

    // You will want to set this most of the time!
    private String reportDir = null;


    // The below values control if the VALUES of the Collection will be displayed in the console.
    // These values still available in the reports.
    //  The user might turn these on and off depending on the list size.
    private boolean displayValuesForTotal;
    private boolean displayValuesForDuplicates;
    private boolean displayValuesForBoth;
    private boolean displayValuesForDiff1;
    private boolean displayValuesForDiff2;


    // These are things you rarely want to change. Almost always leaved them
    private boolean printToConsole = true;
    private Long valuesDisplayMaxLength = 512L; // Set this to -1 if you want ALL items
    private boolean deleteExistingReports;


    // Almost never change these! (There were added just because I could configure them).
    private char lineSeparatorChar = '#';
    private int lineSeparatorLength = 100;


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////// End of Variable Definitions. Below should be mostly getters and setters.
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


    public String getReportDir() {
        return reportDir;
    }

    public void setReportDir(String reportDir) {
        this.reportDir = reportDir;
    }


    public char getLineSeparatorChar() {
        return lineSeparatorChar;
    }

    public void setLineSeparatorChar(char lineSeparatorChar) {
        this.lineSeparatorChar = lineSeparatorChar;
    }

    public int getLineSeparatorLength() {
        return lineSeparatorLength;
    }

    public void setLineSeparatorLength(int lineSeparatorLength) {
        this.lineSeparatorLength = lineSeparatorLength;
    }

    public boolean isPrintToConsole() {
        return printToConsole;
    }

    public void setPrintToConsole(boolean printToConsole) {
        this.printToConsole = printToConsole;
    }


    public Long getValuesDisplayMaxLength() {
        return valuesDisplayMaxLength;
    }

    public void setValuesDisplayMaxLength(Long valuesDisplayMaxLength) {
        this.valuesDisplayMaxLength = valuesDisplayMaxLength;
    }

    public boolean isDisplayValuesForTotal() {
        return displayValuesForTotal;
    }

    public void setDisplayValuesForTotal(boolean displayValuesForTotal) {
        this.displayValuesForTotal = displayValuesForTotal;
    }

    public boolean isDisplayValuesForDuplicates() {
        return displayValuesForDuplicates;
    }

    public void setDisplayValuesForDuplicates(boolean displayValuesForDuplicates) {
        this.displayValuesForDuplicates = displayValuesForDuplicates;
    }

    public boolean isDisplayValuesForBoth() {
        return displayValuesForBoth;
    }

    public void setDisplayValuesForBoth(boolean displayValuesForBoth) {
        this.displayValuesForBoth = displayValuesForBoth;
    }

    public boolean isDisplayValuesForDiff1() {
        return displayValuesForDiff1;
    }

    public void setDisplayValuesForDiff1(boolean displayValuesForDiff1) {
        this.displayValuesForDiff1 = displayValuesForDiff1;
    }

    public boolean isDisplayValuesForDiff2() {
        return displayValuesForDiff2;
    }

    public void setDisplayValuesForDiff2(boolean displayValuesForDiff2) {
        this.displayValuesForDiff2 = displayValuesForDiff2;
    }

    public boolean isDeleteExistingReports() {
        return deleteExistingReports;
    }

    public void setDeleteExistingReports(boolean deleteExistingReports) {
        this.deleteExistingReports = deleteExistingReports;
    }
}
