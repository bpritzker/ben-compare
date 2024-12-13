package net.benp.bc.util;

import java.util.logging.Logger;

/**
 * These are more generic utils that are NOT Compare specific.
 * I'm sure most of these are in other utils, but I didn't want to pull in
 * more dependencies for just a few very simple methods.
 */
public class BcUtils {

    private static final Logger logger = Logger.getLogger(BcUtils.class.getName());




    public static String defaultNotEmptyValue(String inString, String defaultValue) {
        if (BcUtils.isBlank(inString)) {
            return defaultValue;
        }

        return inString;
    }





    public static boolean isBlank(CharSequence cs) {
        if (cs == null) {
            return true;
        }

        int strLen = cs.length();
        if (strLen != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

        }
        return true;
    }


}
