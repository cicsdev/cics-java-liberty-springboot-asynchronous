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

import java.lang.reflect.Method;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

/**
 * 
 * This class implements an AsyncUncaughtExceptionHandler
 * It is just a simple implementation for demonstration purposes.
 */
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler 
{
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) 
    {
        System.out.println("Exception Cause - " + throwable.getMessage());
        System.out.println("Method name - " + method.getName());
        for (Object param : obj) 
        {
            System.out.println("Parameter value - " + param);
        }
    }
}