/*                                                                        */
/* (c) Copyright IBM Corp. 2021 All Rights Reserved                       */
/*                                                                        */

package com.ibm.cicsdev.springboot.asynchronous;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ibm.cics.server.TSQ;
import com.ibm.cics.server.Task;

/**
 *
 * This class demonstrates the use of the @Async() annotation at the class level
 * All public methods in the class will be run on separate threads
 *
 */
@Service
@Async("ConTaskExecutor")
public class AsyncService
{
    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    /**
     * executeAsync1, calls to this method will run in separate threads
     */
    public void executeAsync1(int instance)
    {
        logger.info("ExecuteAsync1(" + instance + "): start");

        try
        {
            TSQ tsq = new TSQ();
            tsq.setName("SPRINGTHREADS");
            tsq.writeString("Task " + Task.getTask().getTaskNumber() + ": Hello from asynchronous service1(" + instance + ")");
            logger.info("ExecuteAsnyc1(" + instance + "): writing to TSQ");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        logger.info("ExecuteAsyc1(" + instance + "): complete. Please check TSQ SPRINGTHREADS.");
    }


    /**
     * executeAsync2, calls to this method will run in a separate threads
     */
    public void executeAsync2(int instance)
    {
        logger.info("ExecuteAsync2(" + instance + "): start");
        try
        {
            TSQ tsq = new TSQ();
            tsq.setName("SPRINGTHREADS");
            tsq.writeString("Task " + Task.getTask().getTaskNumber() + ": Hello from asynchronous service2(" + instance + ")");
            logger.info("ExecuteAsnyc2(" + instance + "): writing to TSQ");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        logger.info("ExecuteAsyc2(" + instance + "): complete. Please check TSQ SPRINGTHREADS.");
    }
}
