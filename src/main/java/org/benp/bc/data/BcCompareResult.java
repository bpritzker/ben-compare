package org.benp.bc.data;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BcCompareResult {

    private Collection<String> collection1;

    private Collection<String> collection2;


    private Map<String, List<String>> normalized1;
    private Map<String, List<String>> normalized2;

    private Map<String, Integer> blankValueToCount1;
    private Map<String, Integer> blankValueToCount2;

    private Collection<String> inC1NotInC2;
    private Collection<String> inC2NotInC1;
    private Collection<String> inBoth;



    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////  Getters and Setters
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    public Collection<String> getCollection1() {
        return collection1;
    }

    public void setCollection1(Collection<String> collection1) {
        this.collection1 = collection1;
    }


    public Collection<String> getCollection2() {
        return collection2;
    }

    public void setCollection2(Collection<String> collection2) {
        this.collection2 = collection2;
    }

    public Map<String, List<String>> getNormalized1() {
        return normalized1;
    }

    public void setNormalized1(Map<String, List<String>> normalized1) {
        this.normalized1 = normalized1;
    }

    public Map<String, List<String>> getNormalized2() {
        return normalized2;
    }

    public void setNormalized2(Map<String, List<String>> normalized2) {
        this.normalized2 = normalized2;
    }


    public Map<String, Integer> getBlankValueToCount1() {
        return blankValueToCount1;
    }

    public void setBlankValueToCount1(Map<String, Integer> blankValueToCount1) {
        this.blankValueToCount1 = blankValueToCount1;
    }

    public Map<String, Integer> getBlankValueToCount2() {
        return blankValueToCount2;
    }

    public void setBlankValueToCount2(Map<String, Integer> blankValueToCount2) {
        this.blankValueToCount2 = blankValueToCount2;
    }

    public Collection<String> getInC1NotInC2() {
        return inC1NotInC2;
    }

    public void setInC1NotInC2(Collection<String> inC1NotInC2) {
        this.inC1NotInC2 = inC1NotInC2;
    }

    public Collection<String> getInC2NotInC1() {
        return inC2NotInC1;
    }

    public void setInC2NotInC1(Collection<String> inC2NotInC1) {
        this.inC2NotInC1 = inC2NotInC1;
    }

    public Collection<String> getInBoth() {
        return inBoth;
    }

    public void setInBoth(Collection<String> inBoth) {
        this.inBoth = inBoth;
    }
}
