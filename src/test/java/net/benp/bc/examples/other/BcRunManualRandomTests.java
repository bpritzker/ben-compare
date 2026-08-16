package net.benp.bc.examples.other;


import net.benp.bc.BcCompareMain;
import net.benp.bc.data.BcCompareResult;

import java.util.Arrays;
import java.util.List;

public class BcRunManualRandomTests {


    public static void main(String[] args) {

        List<String> list1 = Arrays.asList("Homer", "homer", "Bart", "Lisa");
        List<String> list2 = Arrays.asList("Homer", "Wayland", "");
        BcCompareResult result;
        result = BcCompareMain.runCompare("List 1", list1, "List 2", list2);
//        result = BcCompareMain.runCompare(list1, list2);


        System.out.println("Here");


    }



}