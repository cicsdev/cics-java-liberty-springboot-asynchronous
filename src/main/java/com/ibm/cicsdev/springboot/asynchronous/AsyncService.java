/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Copyright IBM Corp. 2019 All Rights Reserved   
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.cicsdev.springboot.asynchronous;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ibm.cics.server.TSQ;

@Service
@Async("ConTaskExecutor")
public class AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);
   
    public void executeAsync1() {
    	
        logger.info("Anne: start executeAsync1");
        try{
        	System.out.println("Anne: Now TSQ writing begin from executeAsync1.");
        	
        	        	
        	TSQ tsq = new TSQ();
            tsq.setName("SPAYCICS");
            tsq.writeString("Anne: Hello AsyncService1 from Spring Boot.");
           
            System.out.println("Anne: Now TSQ writing end. Please check TSQ ANNE.");

        	      
        }catch(Exception e){
            e.printStackTrace();
        }
        logger.info("Anne: end executeAsync1");
    }
    
    public void executeAsync2() {
    	
        logger.info("Anne: start executeAsync2");
        try{
        	System.out.println("Anne: Now TSQ writing begin from executeAsync2.");
        	
        	TSQ tsq = new TSQ();
            tsq.setName("SPAYCICS");
            tsq.writeString("Anne: Hello AsyncService2 from Spring Boot.");
           
            System.out.println("Anne: Now TSQ writing end. Please check TSQ ANNE.");

        	      
        }catch(Exception e){
            e.printStackTrace();
        }
        logger.info("Anne: end executeAsync2");
    }
}