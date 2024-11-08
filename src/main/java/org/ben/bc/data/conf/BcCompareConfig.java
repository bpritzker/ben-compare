package org.ben.bc.data.conf;

public class BcCompareConfig {

    // These are the values you most likely want to change.
    private boolean ignoreCase;




    // I strongly suggest setting this to true.
    private boolean handleBlanksSeparately = true;
    private boolean trim = true;





    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////// End of Variable Definitions. Below should be mostly getters and setters.
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////




    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public boolean isTrim() {
        return trim;
    }

    public void setTrim(boolean trim) {
        this.trim = trim;
    }


    public boolean isHandleBlanksSeparately() {
        return handleBlanksSeparately;
    }

    public void setHandleBlanksSeparately(boolean handleBlanksSeparately) {
        this.handleBlanksSeparately = handleBlanksSeparately;
    }
}
