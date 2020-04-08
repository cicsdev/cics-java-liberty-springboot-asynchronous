# cics-java-liberty-springboot-jcics

This sample project demonstrates a Spring Boot with Asynchronous that can be deployed to an IBM CICS Liberty JVM server. 

## Prerequisites

  - CICS TS V5.5 or later
  - A configured Liberty JVM server 
  - Java SE 1.8 or later on the z/OS system
  - Java SE 1.8 or later on the workstation
  - Either Gradle or Apache Maven on the workstation

## Building 

You can choose to build the project using Gradle or Maven. They will produce the same results. The project includes the Gradle and Maven wrappers, which will automatically download the correct version of the tools if not present on your workstation.

Notice: After you import the project to your IDE, such as Eclipse, if you choose Maven, please right-click on Project, select Maven -> "Update Project..." to fix the compile errors; if you choose Gradle, please right-click on Project, select Gradle -> "Refresh Gradle Project" to fix the compile errors.

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

1. Transfer the WAR file to zFS for example using FTP. 

2. Ensure you have the following features in `server.xml`:

    - servlet-3.1 
    - concurrent-1.0 
    - cicsts:security-1.0 

3. Copy and paste WAR from build project into a CICS bundle project and create WARbundlepart. Deploy the Spring Boot application by this CICS bundle.

## Trying out the sample

1. Find the URL for the application in messages.log e.g. `http://myzos.mycompany.com:httpPort/com.ibm.cicsdev.springboot.asynchronous-0.1.0`. 

2. From the browser you can visit the URL:`http://myzos.mycompany.com:httpPort/com.ibm.cicsdev.springboot.asynchronous-0.1.0/springCICSAsynTest`. Then you will find the browser prompts for a basic authentication, typing your userid and password.

3. Check if the specified TSQ has the information you expected by executing the CICS command "CEBR SPAYCICS". For this example, you should see two items `Anne: Hello AsyncService2 from Spring Boot.``Anne: Hello AsyncService1 from Spring Boot.` in TSQ SPGJCICS.
    
## License
This project is licensed under [Apache License Version 2.0](LICENSE). 
     