package com.lotuas.samples.logging.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {


    public String welcome(){

        Log log= LogFactory.getLog(WelcomeController.class);
        log.info("welcome!!!");
        return "welcome!!!";
    }
}
