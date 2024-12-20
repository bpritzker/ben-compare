package net.benp.bc.examples;


import net.benp.bc.BcCompareMain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * This is a super simple test to show how to use the program....
 * For the version you are most likely going to want to use, see the test/org.ben.bc.examples.BcRunCsvMatchAndMergeMain
 */
public class SuperSimpleExample {


    public static void main(String[] args) {

        Set<String> set1 = new HashSet<>(Arrays.asList("Homer", "homer", "Bart", "Lisa"));
        Set<String> set2 = new HashSet<>(Arrays.asList("Homer", "Wayland", ""));

        BcCompareMain.runCompare("List 1", set1, "List 2", set2);
    }



}
