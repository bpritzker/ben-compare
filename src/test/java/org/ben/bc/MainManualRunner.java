package org.ben.bc;

import org.ben.bc.data.conf.BcCompareConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * This is a super simple test to show how to use the program....
 */
public class MainManualRunner {


    public static void main(String[] args) {

        Set<String> set1 = new HashSet<>(Arrays.asList("Homer", "Bart", "Lisa"));
        Set<String> set2 = new HashSet<>(Arrays.asList("Homer", "Wayland"));


        BcCompareConfig config = BcUtils.buildDefaultConfig().getCompareConfig();
        BcMain.runSimpleStatic("Family", set1, "Work", set2);

    }



}
