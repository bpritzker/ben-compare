package net.benp.bc.examples;
import net.benp.bc.examples.other.BcRunCsvMatchAndMerge;

/**
 * This is the version of the program I (and think you will to) want to use most of the time.
 * It will load a CSV from a directory, then match and merge the data.
 * You only need to tell it what column to focus on.
 * It will create all the difference reports AND create a "merge" file.
 * </br>
 * Side note: I SOOOOOOO want to call this a "marge" file instead of "merge" file LOL
 *
 */
public class BcRunCsvMatchAndMergeMain extends BcRunCsvMatchAndMerge {

    // REQUIRED.....
    // NOTE: Use -1 if you want the whole line loaded. (Think TXT file)
    private static final int FILE_COLUMN_INDEX_ZERO_BASED_1 = 0;
    private static final int FILE_COLUMN_INDEX_ZERO_BASED_2 = 1;


    // OPTIONAL... (I don't think you will change these as much but you might)
    private static final boolean IGNORE_CASE = true;
    private static final boolean TOP_ROW_ARE_HEADERS = false;




    // ONCE - In General I think you will set this once and leave it.
    private static final String WORKING_DIR = "C:/Temp/Comparator";




    // OPTIONAL DISPLAY
    private static final boolean DISPLAY_VALUES_BOTH = false;
    private static final boolean DISPLAY_DIFF_VALUES_1 = false;
    private static final boolean DISPLAY_DIFF_VALUES_2 = false;





































    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////// Below is just the main method, nothing to change.
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////






    public static void main(String[] args) {
        BcRunCsvMatchAndMergeMain runCsvMatchAndMerge = new BcRunCsvMatchAndMergeMain();
        try {
            runCsvMatchAndMerge.run(
                    FILE_COLUMN_INDEX_ZERO_BASED_1, FILE_COLUMN_INDEX_ZERO_BASED_2,
                    WORKING_DIR,
                    IGNORE_CASE, TOP_ROW_ARE_HEADERS,
                    DISPLAY_VALUES_BOTH, DISPLAY_DIFF_VALUES_1, DISPLAY_DIFF_VALUES_2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
