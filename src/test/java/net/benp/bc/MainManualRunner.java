package net.benp.bc;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * This is a super simple test to show how to use the program....
 * For the version you are most likely going to want to use, see the test/org.ben.bc.examples.BcRunCsvMatchAndMergeMain
 */
public class MainManualRunner {


    public static void main(String[] args) {

        Set<String> set1 = new HashSet<>(Arrays.asList("Homer", "Bart", "Lisa"));
        Set<String> set2 = new HashSet<>(Arrays.asList("Homer", "Wayland"));

        BcMain.runCompare("List 1", set1, "List 2", set2);

    }



}
