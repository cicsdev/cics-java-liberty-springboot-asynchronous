# cics-java-liberty-springboot-asynchronous
[![Build](https://github.com/cicsdev/cics-java-liberty-springboot-asynchronous/actions/workflows/build.yaml/badge.svg)](https://github.com/cicsdev/cics-java-liberty-springboot-asynchronous/actions/workflows/build.yaml)
[![License](https://img.shields.io/badge/License-EPL%202.0-red.svg)](https://www.eclipse.org/legal/epl-2.0/)

## Overview

This sample provides a Spring Boot application that demonstrates asynchronous operations running on CICS-enabled threads. The sample shows how to use Spring's `@Async` annotation to execute methods asynchronously while maintaining CICS transaction context, making it ideal for deployment inside an IBM CICS Liberty JVM server.

## Key Features

- **Asynchronous Execution**: Spring Boot `@Async` annotation for concurrent operations
- **CICS Thread Management**: Maintains CICS transaction context across async threads
- **JCICS API Integration**: Uses JCICS to write results to Temporary Storage Queue (TSQ)
- **Thread Pool Configuration**: Proper configuration for CICS environments
- **Error Handling**: Examples of exception handling in asynchronous operations

## Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [Requirements](#requirements)
- [Downloading](#downloading)
- [Building the Sample](#building-the-sample)
- [Deploying to a CICS Liberty JVM server](#deploying-to-a-cics-liberty-jvm-server)
- [Running the Sample](#running-the-sample)
- [License](#license)
- [Additional Resources](#additional-resources)
- [Contributing](#contributing)

The sample is structured as a multi-module project with:
- **cics-java-liberty-springboot-asynchronous-app** - The Spring Boot application module
- **cics-java-liberty-springboot-asynchronous-cicsbundle** - The CICS bundle module for deployment

---

## Requirements

### Workstation Requirements
- **Java:** Java SE 17 or later (required for Spring Boot 3.x)
- **Build Tools:**
  - **Gradle:** Version 7.3+ (Java 17 support) - Recommended: 8.0+ - included via wrapper
  - **Maven:** Version 3.8.1+ (Java 17 support) - Recommended: 3.9.0+ - included via wrapper
- **IDE (Optional):**
  - Eclipse with IBM CICS SDK for Java EE, Jakarta EE and Liberty
  - IntelliJ IDEA, VS Code, or any IDE with Gradle/Maven support
  - Command line (no IDE required if using wrappers)

### z/OS Requirements
- **CICS TS:** V6.1 or later
- **WebSphere Liberty:** Included with CICS
- **Java:** IBM Semeru Runtime 17 or later on z/OS
- **Jakarta EE:** 10 or later

---

## Downloading

- Clone the repository using your IDEs support, such as the Eclipse Git plugin
- **or**, download the sample as a [ZIP](https://github.com/cicsdev/cics-java-liberty-springboot-asynchronous/archive/main.zip) and unzip onto the workstation

>*Tip: Eclipse Git provides an 'Import existing Projects' check-box when cloning a repository.*

### Check dependencies
 
Before building this sample, you should verify that the correct CICS TS bill of materials (BOM) is specified for your target release of CICS. The BOM specifies a consistent set of artifacts, and adds information about their scope. In the example below the version specified is compatible with CICS TS V6.1 with JCICS APAR PH63856, or newer. That is, the Java byte codes built by compiling against this version of JCICS will be compatible with later CICS TS versions and subsequent JCICS APARs.

You can browse the published versions of the CICS BOM at [Maven Central.](https://mvnrepository.com/artifact/com.ibm.cics/com.ibm.cics.ts.bom)
 
Gradle (build.gradle):

`compileOnly enforcedPlatform("com.ibm.cics:com.ibm.cics.ts.bom:6.1-20250812133513-PH63856")`

Maven (POM.xml):

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.ibm.cics</groupId>
      <artifactId>com.ibm.cics.ts.bom</artifactId>
      <version>6.1-20250812133513-PH63856</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

## Building the Sample

You can build using Gradle, Maven, or Eclipse. The wrappers are pre-configured with compatible versions.

### Option 1: Building with Gradle

**From the root directory:**

Linux/Mac:
```bash
./gradlew clean build
```

Windows:
```cmd
gradlew.bat clean build
```

**Output:**
- WAR file: `cics-java-liberty-springboot-asynchronous-app/build/libs/cics-java-liberty-springboot-asynchronous-0.1.0.war`
- CICS bundle ZIP: `cics-java-liberty-springboot-asynchronous-cicsbundle/build/distributions/cics-java-liberty-springboot-asynchronous-cicsbundle-0.1.0.zip`

**Note:**
- In Eclipse, the `build` directory may be hidden. To view it: Package Explorer → ⋮ menu → Filters → Uncheck "Gradle build folder"

---

### Option 2: Building with Maven

**From the root directory:**

Linux/Mac:
```bash
./mvnw clean verify
```

Windows:
```cmd
mvnw.cmd clean verify
```

**Output:**
- WAR file: `cics-java-liberty-springboot-asynchronous-app/target/cics-java-liberty-springboot-asynchronous-0.1.0.war`
- CICS bundle ZIP: `cics-java-liberty-springboot-asynchronous-cicsbundle/target/cics-java-liberty-springboot-asynchronous-cicsbundle-0.1.0.zip`

---

### Option 3: Building with Eclipse

1. Import the project into Eclipse (File → Import → Existing Projects)
2. Right-click on the root project
3. Select either:
   - **Gradle → Refresh Gradle Project** then **Run As → Gradle Build** (specify `clean build`)
   - **Maven → Update Project** then **Run As → Maven build** (specify `clean verify`)

**Note:** When building a WAR file for deployment to Liberty it is good practice to exclude Tomcat from the final runtime artifact. We demonstrate this in the pom.xml with the *provided* scope, and in build.gradle with the *providedRuntime()* dependency.

---

## Deploying to a CICS Liberty JVM server

Ensure you have the following features defined in your Liberty `server.xml`:

- `servlet-6.0` (required for Spring Boot 3.x and Jakarta EE 10)
- `concurrent-3.0`
- `cicsts:security-1.0` if CICS security is enabled
  
A template `server.xml` is provided [here](./etc/config/liberty/server.xml).

---

### Method 1: CICS Bundle Plugin Deployment (Recommended)

This method uses the cics-bundle-gradle-plugin or cics-bundle-maven-plugin to automatically generate a CICS bundle.

**Configure your JVM server name:**

Gradle (`cics-java-liberty-springboot-asynchronous-cicsbundle/build.gradle`):
```gradle
cics.jvmserver = 'YOUR_JVMSERVER_NAME'  // e.g., 'DFHWLP'
```

Maven (`cics-java-liberty-springboot-asynchronous-cicsbundle/pom.xml`):
```xml
<cics.jvmserver>YOUR_JVMSERVER_NAME</cics.jvmserver>  <!-- e.g., DFHWLP -->
```

**Deploy the bundle:**

1. Upload the CICS bundle ZIP file to zFS:
   - Gradle: `cics-java-liberty-springboot-asynchronous-cicsbundle/build/distributions/cics-java-liberty-springboot-asynchronous-cicsbundle-0.1.0.zip`
   - Maven: `cics-java-liberty-springboot-asynchronous-cicsbundle/target/cics-java-liberty-springboot-asynchronous-cicsbundle-0.1.0.zip`

2. Unzip the bundle on zFS

3. Create a CICS BUNDLE resource definition:
   ```
   CEDA DEFINE BUNDLE(ASYNCAPP) GROUP(MYGROUP) BUNDLEDIR(/path/to/bundle)
   ```

4. Install the bundle:
   ```
   CEDA INSTALL BUNDLE(ASYNCAPP) GROUP(MYGROUP)
   ```

---

### Method 2: CICS Explorer SDK Deployment

1. Copy the built WAR from your *target* or *build/libs* directory into an Eclipse CICS Bundle Project
2. Create a new WAR bundlepart that references the WAR file
3. Deploy the CICS Bundle Project from CICS Explorer using the **Export Bundle Project to z/OS UNIX File System** wizard

---

### Method 3: Direct Liberty Application Deployment

Manually upload the WAR file to zFS and add an `<application>` element to the Liberty server.xml:

```xml
<application id="cics-java-liberty-springboot-asynchronous-0.1.0"
    location="${server.config.dir}/springapps/cics-java-liberty-springboot-asynchronous-0.1.0.war"
    name="cics-java-liberty-springboot-asynchronous-0.1.0" type="war">
    <application-bnd>
        <security-role name="cicsAllAuthenticated">
            <special-subject type="ALL_AUTHENTICATED_USERS"/>
        </security-role>
    </application-bnd>
</application>
```

---

## Running the Sample

### Testing the Application

1. **Verify Deployment:**
   
   Ensure the web application started successfully in Liberty by checking for msg `CWWKT0016I` in the Liberty messages.log:
   ```
   CWWKT0016I: Web application available (default_host): http://myzos.mycompany.com:httpPort/cics-java-liberty-springboot-asynchronous-0.1.0
   SRVE0292I: Servlet Message - [cics-java-liberty-springboot-asynchronous-0.1.0]:.2 Spring WebApplicationInitializers detected on classpath
   ```

2. **Trigger Asynchronous Operations:**
   
   Access the test endpoint:
   ```
   http://myzos.mycompany.com:httpPort/cics-java-liberty-springboot-asynchronous-0.1.0/test
   ```

3. **Verify Results:**
   
   The application will spawn 10 asynchronous requests (5 to each of two services). Check the output by:
   - Viewing the TSQ called `SPRINGTHREADS` using the CICS command: `CEBR SPRINGTHREADS`
   - Checking Liberty messages.log
   
   You should see entries like: `"Task <number>: Hello from asynchronous service<no>(<thread>)"`
   
   Although each service is spawned 5 times in round-robin fashion, the execution is asynchronous on separate CICS-enabled threads, so the TSQ writes will be in unpredictable order.

---

## License
This project is licensed under [Eclipse Public License - v 2.0](LICENSE).

## Additional Resources

- [CICS TS for z/OS Documentation](https://www.ibm.com/docs/en/cics-ts)
- [Spring Framework Async Documentation](https://docs.spring.io/spring-framework/reference/integration/scheduling.html)
- [CICS Java Development](https://www.ibm.com/docs/en/cics-ts/latest?topic=programming-java-applications)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

## Contributing

Contributions are welcome! Please read our [contributing guidelines](https://github.com/cicsdev/.github/blob/main/CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.
