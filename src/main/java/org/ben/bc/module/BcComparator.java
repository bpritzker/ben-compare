package org.ben.bc.module;

import org.ben.bc.BcUtils;
import org.ben.bc.data.BcCompareResult;
import org.ben.bc.data.conf.BcCompareConfig;

import java.util.*;

/**
 * This class is used to take all the data in the 2 Collections and
 * normalized and do the comparisons.
 * </br>
 * NOTE: After a lot of thought I decided to handle null/blanks separately since they are kind of unique.
 *
 */
public class BcComparator {

    private final BcCompareConfig config;

    public BcComparator(BcCompareConfig config) {
        this.config = config;
    }


    public BcCompareResult runCompare(
            Collection<String> collection1,
             Collection<String> collection2) {

        NormalizedResult normalizedResult1 = normalizeCollection(collection1);
        NormalizedResult normalizedResult2 = normalizeCollection(collection2);

        Map<String, List<String>> normalized1 = normalizedResult1.getNormalizedValueToOrigValues();
        Map<String, List<String>> normalized2 = normalizedResult2.getNormalizedValueToOrigValues();

        Set<String> matches = new HashSet<>(normalized1.keySet());
        matches.retainAll(normalized2.keySet());


        Set<String> in1NotIn2 = new HashSet<>(normalized1.keySet());
        in1NotIn2.removeAll(normalized2.keySet());

        Set<String> in2NotIn1 = new HashSet<>(normalized2.keySet());
        in2NotIn1.removeAll(normalized1.keySet());


        // Now, set the result object with all values...
        BcCompareResult result = new BcCompareResult();
        result.setCollection1(collection1);
        result.setCollection2(collection2);

        // set all the actual diff stuff
        result.setInBoth(matches);
        result.setInC1NotInC2(in1NotIn2);
        result.setInC2NotInC1(in2NotIn1);

        result.setNormalized1(normalized1);
        result.setNormalized2(normalized2);

        result.setBlankValueToCount1(normalizedResult1.getBlankValueToCount());
        result.setBlankValueToCount2(normalizedResult2.getBlankValueToCount());

        return result;

    }

    protected NormalizedResult normalizeCollection(Collection<String> collection) {

        Map<String, List<String>> normalizedValueToOrigValues = new HashMap<>();
        Map<String, Integer> blankToCount = new HashMap<>();
        for (String currStartingValue : collection) {

            // Handle blanks separately!
            if (config.isHandleBlanksSeparately() && BcUtils.isBlank(currStartingValue)) {
                Integer tempCount = blankToCount.get(currStartingValue);
                if (tempCount == null) {
                    tempCount = 0;
                }
                tempCount++;
                blankToCount.put(currStartingValue, tempCount);
            } else {  // else, non blank so normalize
                String normalized = normalizeString(currStartingValue);

                List<String> mergedValues = normalizedValueToOrigValues.get(normalized);
                if (mergedValues == null) {
                    mergedValues = new ArrayList<>();
                }
                mergedValues.add(currStartingValue);
                normalizedValueToOrigValues.put(normalized, mergedValues);
            }
        }

        NormalizedResult result = new NormalizedResult();
        result.setNormalizedValueToOrigValues(normalizedValueToOrigValues);
        result.setBlankValueToCount(blankToCount);
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


    protected static class NormalizedResult {
        private Map<String, List<String>> normalizedValueToOrigValues;
        private Map<String, Integer> blankValueToCount;

        public Map<String, List<String>> getNormalizedValueToOrigValues() {
            return normalizedValueToOrigValues;
        }

        public void setNormalizedValueToOrigValues(Map<String, List<String>> normalizedValueToOrigValues) {
            this.normalizedValueToOrigValues = normalizedValueToOrigValues;
        }

        public Map<String, Integer> getBlankValueToCount() {
            return blankValueToCount;
        }

        public void setBlankValueToCount(Map<String, Integer> blankValueToCount) {
            this.blankValueToCount = blankValueToCount;
        }
    }


}
