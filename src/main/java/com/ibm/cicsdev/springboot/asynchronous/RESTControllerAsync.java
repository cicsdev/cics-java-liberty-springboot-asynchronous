/* Licensed Materials - Property of IBM                                   */
/*                                                                        */
/* SAMPLE                                                                 */
/*                                                                        */
/* (c) Copyright IBM Corp. 2020 All Rights Reserved                       */
/*                                                                        */
/* US Government Users Restricted Rights - Use, duplication or disclosure */
/* restricted by GSA ADP Schedule Contract with IBM Corp                  */
/*                                                                        */

package com.ibm.cicsdev.springboot.asynchronous;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is the Rest Endpoint and uses the AsyncService class to run separate tasks.
 * 
 * @RestController: build a Restful controller
 * @Autowired: drive Dependency Injection
 * @RequestMapping: write a Request URI method
 */
@RestController
public class RESTControllerAsync 
{
    private static final Logger logger = LoggerFactory.getLogger(RESTControllerAsync.class);

    @Autowired
    private AsyncService asyncService;

   
    /**
     * @return 
     */
    @RequestMapping({"/", "/test"})
    public String submit()
    {   
    	logger.info("Submitting asynchronous services...");
    	
    	// Spawn 5 instances of each asynchronous service
    	for(int i = 0; i < 5; i++)
    	{
    		asyncService.executeAsync1(i);
    		logger.info("Spawned asynchronous Service 1, instance " + i);
    		
    		asyncService.executeAsync2(i);
            logger.info("Spawned asynchronous Service 2, instance " + i);
    	}               

        return "Successfully spawned asynchronous operations, see messages.log for results.";
    }
}