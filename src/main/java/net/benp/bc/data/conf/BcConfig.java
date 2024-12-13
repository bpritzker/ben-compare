package net.benp.bc.data.conf;

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


    public static BcConfig buildDefaultConfig() {

        BcConfig resultConfig = new BcConfig();

        BcCompareConfig compareConfig = new BcCompareConfig();
        compareConfig.setIgnoreCase(true);
        compareConfig.setTrim(true);
        resultConfig.setCompareConfig(compareConfig);

        BcReportConfig reportConfig = new BcReportConfig();
        reportConfig.setReportDir(null);
        resultConfig.setReportConfig(reportConfig);
        reportConfig.setDeleteExistingReports(true);

        return resultConfig;
    }


}
