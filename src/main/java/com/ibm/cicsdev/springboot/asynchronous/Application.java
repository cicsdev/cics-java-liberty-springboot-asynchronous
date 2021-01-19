/*                                                                        */
/* (c) Copyright IBM Corp. 2021 All Rights Reserved                       */
/*                                                                        */

package com.ibm.cicsdev.springboot.asynchronous;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.DefaultManagedTaskExecutor;


/**
 * This Application demonstrates the use of the @Asnyc annotation to run methods
 * on separate threads asynchronously.
 * 
 * You must mark the application as eligible for Spring asynchronous operations
 * with the @EnableAysnc annotation.
 *
 * The application must implement the AsnycConfigurer interface and provide 
 * an implementation of the getAsyncExecutor() and getAsyncUncaughtExceptionHandler()
 * methods.
 * 
 * Ensure you return an instance of the DefaultManagedTaskExecutor in the getAsyncExecutor() 
 * method as this uses JNDI to lookup the application server's (Liberty) concurrent managed Executor 
 * to obtains new threads from it. Threads served from that Executor are CICS enabled threads.  
 *
 */
@SpringBootApplication
@EnableAsync()
public class Application extends SpringBootServletInitializer implements AsyncConfigurer 
{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) 
	{
		return application.sources(Application.class);
	}

	
	public static void main(String[] args) 
	{
		SpringApplication.run(Application.class, args);
	}
	
	
	// Annotation names the Executor (for use by the AsyncService class)
	@Override
    @Bean(name = "ConTaskExecutor")
    public Executor getAsyncExecutor() 
	{    		 
		// By default this Spring class will use JNDI to look for a standard Java EE compliant
		// executor, which in our environment is the Liberty one provided by the concurrent-1.0
		// feature, this in turn uses Liberty' defaultExecutor that has been modified in a 
		// CICS environment to return CICS enabled threads.
    	return new DefaultManagedTaskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() 
    {
        return new AsyncExceptionHandler();
    }	
}
