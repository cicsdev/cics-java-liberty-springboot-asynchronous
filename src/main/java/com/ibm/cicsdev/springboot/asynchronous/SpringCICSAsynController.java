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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringCICSAsynController {

    private static final Logger logger = LoggerFactory.getLogger(SpringCICSAsynController.class);

    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/springCICSAsynTest")
    public String submit(){
    	
        logger.info("Anne: start submit");
        
        //Invoke task from service
        asyncService.executeAsync1();
        System.out.println("Anne: AsyncService1 is running.");
        
        asyncService.executeAsync2();
        System.out.println("Anne: AsyncService2 is running.");

        logger.info("Anne: end submit");

        return "Anne: Spring Boot with Asynchronous success!";
    }
}