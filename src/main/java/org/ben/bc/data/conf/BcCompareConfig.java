package org.ben.bc.data.conf;

public class BcCompareConfig {

    private boolean ignoreCase;
    private boolean trim;


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


}
