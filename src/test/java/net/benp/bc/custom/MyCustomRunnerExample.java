package net.benp.bc.custom;


import net.benp.bc.BcCompareMain;
import net.benp.bc.data.conf.BcConfig;
import net.benp.bc.testutil.BcTestingFileUtils;
import net.benp.bc.testutil.BcTestingUtils;

import java.util.List;

/**
 * Put your custom Runners here.....
 * I will never put anything else in this directory so you can store your runners without me overwriting them
 *
 */
public class MyCustomRunnerExample extends BcCompareMain {



    public static void main(String[] args) {
        MyCustomRunnerExample myCustomRunnerExample = new MyCustomRunnerExample();
        myCustomRunnerExample.run();
    }



    private void run() {

        List<String> list1 = BcTestingUtils.getComplexList1();
        List<String> list2 = BcTestingUtils.getComplexList2();


        String reportDir = "C:/Temp/compare/reports";
        BcConfig config = BcConfig.buildDefaultConfig();
        config.getCompareConfig().setIgnoreCase(false);
        config.getReportConfig().setReportDir(reportDir);

        // This is the magic line...
        run("List 1", list1, "List 2", list2, config);

        System.out.println("\n\n");
        BcTestingFileUtils.printOpenDirLink(reportDir);
    }

}
