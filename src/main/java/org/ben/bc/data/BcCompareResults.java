package org.ben.bc.data;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BcCompareResults {

    private String collectionName1;
    private Collection<String> collection1;

    private String collectionName2;
    private Collection<String> collection2;


    private Map<String, List<String>> normalized1;
    private Map<String, List<String>> normalized2;

    private Collection<String> inC1NotInC2;
    private Collection<String> inC2NotInC1;
    private Collection<String> inBoth;





    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////  Getters and Setters
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    public String getCollectionName1() {
        return collectionName1;
    }

    public void setCollectionName1(String collectionName1) {
        this.collectionName1 = collectionName1;
    }

    public Collection<String> getCollection1() {
        return collection1;
    }

    public void setCollection1(Collection<String> collection1) {
        this.collection1 = collection1;
    }

    public String getCollectionName2() {
        return collectionName2;
    }

    public void setCollectionName2(String collectionName2) {
        this.collectionName2 = collectionName2;
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
