package org.ben.bc.data.conf;

public class BcReportConfig {

    // You will want to set this most of the time!
    private String reportDir = null;


    // Rarely change these!
    private char lineSeparatorChar = '#';
    private int lineSeparatorLength = 100;


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
}
