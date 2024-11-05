package org.ben.bc.module;

import org.ben.bc.data.BcCompareResults;
import org.ben.bc.data.conf.BcCompareConfig;

import java.util.*;


public class BcComparator {

    private final BcCompareConfig config;

    public BcComparator(BcCompareConfig config) {
        this.config = config;
    }


    public BcCompareResults runCompare(
            Collection<String> collection1,
             Collection<String> collection2) {

        Map<String, List<String>> normalized1 = normalizeCollection(collection1);
        Map<String, List<String>> normalized2 = normalizeCollection(collection2);

        Set<String> matches = new HashSet<>(normalized1.keySet());
        matches.retainAll(normalized2.keySet());


        Set<String> in1NotIn2 = new HashSet<>(normalized1.keySet());
        in1NotIn2.removeAll(normalized2.keySet());

        Set<String> in2NotIn1 = new HashSet<>(normalized2.keySet());
        in2NotIn1.removeAll(normalized1.keySet());


        // Now, set the result object with all values...
        BcCompareResults result = new BcCompareResults();
        result.setCollection1(collection1);
        result.setCollection2(collection2);

        //
        result.setInBoth(matches);
        result.setInC1NotInC2(in1NotIn2);
        result.setInC2NotInC1(in2NotIn1);

        result.setNormalized1(normalized1);
        result.setNormalized2(normalized2);

        return result;

    }

    protected Map<String, List<String>> normalizeCollection(Collection<String> collection) {

        Map<String, List<String>> result = new HashMap<>();
        for (String currStartingValue : collection) {

            String normalized = normalizeString(currStartingValue);

            List<String> mergedValues = result.get(normalized);
            if (mergedValues == null) {
                mergedValues = new ArrayList<>();
            }
            mergedValues.add(currStartingValue);
            result.put(normalized, mergedValues);
        }
        return result;
    }

    protected String normalizeString(String staringValue) {

        String result = staringValue;

        if (config.isIgnoreCase()) {
            result = result.toUpperCase(Locale.ROOT);
        }

        if (config.isTrim()) {
            result = result.trim();
        }

        return result;
    }


}
