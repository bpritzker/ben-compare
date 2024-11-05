package org.ben.bc.testutil;


import java.util.logging.Logger;

public class QuickTest {




    private static final Logger logger = Logger.getLogger(QuickTest.class.getName());



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
