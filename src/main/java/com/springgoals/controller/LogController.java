package com.springgoals.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Rest Controller to print various log level messages
@RestController
public class LogController {

    // creating a logger
    private static final Logger logger = LogManager.getLogger(LogController.class);

    @RequestMapping("/log") public String log()
    {
        // Logging various log level messages
        if(logger.isTraceEnabled())
         logger.trace("Log level from LogController: TRACE");
        if(logger.isInfoEnabled())
         logger.info("Log level from LogController: INFO");
        if(logger.isDebugEnabled())
         logger.debug("Log level from LogController: DEBUG");
        if(logger.isErrorEnabled())
         logger.error("Log level from LogController: ERROR");
        if(logger.isWarnEnabled())
         logger.warn("Log level from LogController: WARN");

        return "Hey! You can check the output in the logs";
    }
}
