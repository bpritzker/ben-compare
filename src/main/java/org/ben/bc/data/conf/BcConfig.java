package org.ben.bc.data.conf;

public class BcConfig {

    private BcCompareConfig compareConfig;
    private BcReportConfig reportConfig;


    public BcCompareConfig getCompareConfig() {
        return compareConfig;
    }

    public void setCompareConfig(BcCompareConfig compareConfig) {
        this.compareConfig = compareConfig;
    }

    public BcReportConfig getReportConfig() {
        return reportConfig;
    }

    public void setReportConfig(BcReportConfig reportConfig) {
        this.reportConfig = reportConfig;
    }
}
