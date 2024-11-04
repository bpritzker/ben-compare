package org.ben.bc.testutil;

import org.ben.bc.MainManualRunner;

import java.util.logging.Logger;

public class QuickTest {




    private static Logger logger = Logger.getLogger(MainManualRunner.class.getName());



    public static void main(String[] args) {

        runLoggingTest();



        System.out.println("Here");

    }

    private static void runLoggingTest() {
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info");
        logger.fine("fine");
    }


}
