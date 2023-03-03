Multi-Module Project

This is a multi-module project with two modules:

    web module, responsible for serving the web application
    admin module, responsible for providing administrative functionality

Build and Run

This project can be built and run using either Maven or Gradle build tools.
Maven

To build and run the project using Maven, navigate to the root directory of the project and run the following command:

shell
mvn clean package tomcat7:run

This will compile the project, package it into a WAR file, and deploy it to an embedded Tomcat 7 server.

To run the tests for the project, run the following command:

shell
mvn test

Gradle

To build and run the project using Gradle, navigate to the root directory of the project and run the following command:

shell
gradle clean build tomcatRun

This will compile the project, package it into a WAR file, and deploy it to an embedded Tomcat server.

To run the tests for the project, run the following command:

shell
gradle test

Accessing the application

Once the application is running, you can access it by navigating to http://localhost:8080/web in your web browser.
Structure

The project is structured as follows:

css
multi-module-project/
  admin/
    src/
      main/
        java/
            admin/
                MyAdmin.java
  web/
    src/
      main/
        java/
          com/
            epam/
              web/
                MyServlet.java
        webapp/
          WEB-INF/
            web.xml
     
  README.md