package org.benp.bc.util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * These are utils that work specifically on Compare Objects
 */
public class BcCompareUtils {


    /**
     * This is used to find all the values that have more than 2 mapping.
     * I figured this would be a common thing I will need to do, so I put it into a util.
     */
    public static Map<String, List<String>> findDuplicates(Map<String, List<String>> normalizedMapping) {

        Map<String, List<String>> result = new HashMap<>();
        for (String currKey : normalizedMapping.keySet()) {
            List<String> tempValues = normalizedMapping.get(currKey);
            if (tempValues != null && tempValues.size() > 1) {
                result.put(currKey, tempValues);
            }
        }
        return result;
    }


}
