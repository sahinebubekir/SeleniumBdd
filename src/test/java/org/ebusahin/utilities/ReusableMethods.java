package org.ebusahin.utilities;

public class ReusableMethods {

    public static void hardWait(int seconds){
        try {
            Thread.sleep(seconds*1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
