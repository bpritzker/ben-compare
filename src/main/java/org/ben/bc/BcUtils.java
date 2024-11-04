package org.ben.bc;

import org.ben.bc.data.conf.BcCompareConfig;
import org.ben.bc.data.conf.BcConfig;
import org.ben.bc.data.conf.BcReportConfig;

public class BcUtils {

    public static BcConfig buildDefaultConfig() {

        BcConfig resultConfig = new BcConfig();

        BcCompareConfig compareConfig = new BcCompareConfig();
        compareConfig.setIgnoreCase(true);
        compareConfig.setTrim(true);
        resultConfig.setCompareConfig(compareConfig);

        BcReportConfig reportConfig = new BcReportConfig();
        reportConfig.setReportDir(null);
        resultConfig.setReportConfig(reportConfig);

        return resultConfig;
    }


}
