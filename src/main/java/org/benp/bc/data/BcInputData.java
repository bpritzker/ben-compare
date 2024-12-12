package org.benp.bc.data;


import java.util.Collection;

/**
 * A simple object that makes it easier to pass all 4 parameters through the program.
 * NOTE: I looked at using this, but then it would make some of the other methods harder to understand and I would be
 *    putting items in this object just to pull them out.
 *    I'm going to leave it as Deprecated until I figure out if I really want to use it and how.
 *
 */
@Deprecated
@SuppressWarnings("all") // This is deprecated so we don't care.
public class BcInputData {


    private String collectionName1;
    private Collection<String> collection1;

    private String collectionName2;
    private Collection<String> collection2;

    public BcInputData(String collectionName1, Collection<String> collection1, String collectionName2, Collection<String> collection2) {
        this.collectionName1 = collectionName1;
        this.collection1 = collection1;
        this.collectionName2 = collectionName2;
        this.collection2 = collection2;
    }


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
}
