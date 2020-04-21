# cics-java-liberty-springboot-asynchronous

This sample project demonstrates a Spring Boot application running asynchronous operations on CICS-enabled threads. It is intended for deployment inside an IBM CICS Liberty JVM server.

## Prerequisites

  - CICS TS V5.3 or later
  - A configured Liberty JVM server 
  - Java SE 1.8 or later on the z/OS system
  - Java SE 1.8 or later on the workstation
  - Either Gradle or Apache Maven on the workstation

## Building 

You can choose to build the project using Gradle or Maven. The project includes both Gradle and Maven wrappers, these wrappers will automatically download required components from your chosen build tool; if not already present on your workstation.

You can build the sample project through plug-in tooling of your chosen IDE. Both Gradle *buildship* and Maven *m2e* will integrate with Eclipse's "Run As..." capability allowing you to specify the required build-tasks. There are typically `clean bootWar` for Gradle and `clean package` for Maven, as reflected in the command line approach below.

**Note:** If you import the project to an IDE of your choice, you might experience local project compile errors. To resolve these errors you should refresh your IDEs configuration. For example, in Eclipse: for Gradle, right-click on "Project", select "Gradle -> Refresh Gradle Project", or for Maven, right-click on "Project", select "Maven -> Update Project...".

### Gradle

Run the following in a local command prompt:

On Linux or Mac:

```shell
./gradlew clean bootWar
```
On Windows:

```shell
gradlew.bat clean bootWar
```

This creates a WAR file inside the `build/libs` directory.

### Maven


Run the following in a local command prompt:

On Linux or Mac:

```shell
./mvnw clean package
```

On Windows:

```shell
mvnw.cmd clean package
```

This creates a WAR file inside the `target` directory.


## Deploying

1. Ensure you have the following features in `server.xml`:

    - servlet-3.1 
    - concurrent-1.0 
    - cicsts:security-1.0 

2. Copy and paste the WAR from your target or build/libs directory into a CICS bundle project and create a new WARbundlepart for that WAR file. Deploy the CICS bundle project as normal. For example in Eclipse, select "Export Bundle Project to z/OS UNIX File System". 

## Trying out the sample

1. Find the base URL for the application in the Liberty messages.log e.g.  `http://myzos.mycompany.com:httpPort/com.ibm.cicsdev.springboot.asynchronous-0.1.0`.

2. Past the base URL along with the REST service suffix 'springCICSAsynTest' into the browser  e.g. `http://myzos.mycompany.com:httpPort/com.ibm.cicsdev.springboot.asynchronous-0.1.0/springCICSAsynTest`.  
The browser will prompt for basic authentication. Enter a valid userid and password - according to the configured registry for your target Liberty JVM server.

3. If successful, the application will run two methods asynchronously. You can check the output from these asynchronous methods by viewing the TSQ called SPAYCICS (SPring Boot AYsynchronous). One way to achieve this is through the CICS command "CEBR SPAYCICS". You should see two entries:  `"Anne: Hello AsyncService2 from Spring Boot."`  `"Anne: Hello AsyncService1 from Spring Boot."`
    
## License
This project is licensed under [Apache License Version 2.0](LICENSE). 
     