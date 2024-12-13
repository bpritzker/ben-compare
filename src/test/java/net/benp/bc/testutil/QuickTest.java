package net.benp.bc.testutil;


import java.util.logging.Logger;

public class QuickTest {




    private static final Logger logger = Logger.getLogger(QuickTest.class.getName());



    public static void main(String[] args) {

        runLoggingTest();



        System.out.println("Here");

    }

    private static void runLoggingTest() {
        System.out.println("Staring logs...");
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info");
        logger.fine("fine");
        logger.finer("finer");
        System.out.println("End Logs");
    }


}
