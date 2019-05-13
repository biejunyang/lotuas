package com.lotuas.samples.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JclLoggingTest {

    public static final Log logger= LogFactory.getLog(JclLoggingTest.class);

    public static void main(String[] args){
        logger.info("xxxx");
        System.out.println("xxxddddx");
    }

}
