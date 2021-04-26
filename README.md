# Points

**Prerequisites**

 - Must have java jdk installed on computer.
	 - This project is running on Java version 1.8 there are different ways to install it by platform here are the main ones:
		 - Windows - https://adoptopenjdk.net/?variant=openjdk8&jvmVariant=hotspot
		 - Mac - https://mkyong.com/java/how-to-install-java-on-mac-osx/
		 - Linux - https://openjdk.java.net/install/
 - Must have maven installed on computer. 
	 - Instructions for installation process can be found here. https://maven.apache.org/install.html
	 - After installation check that maven is installed by using the terminal/console to run mvn -v. It should return the version number of maven and java version installed on the computer.

**Installation**
To get started using the application download the zip of the project and extract to a location.
Using the terminal navigate to the inside of the extracted folder.
Once inside the folder from the terminal run the command `mvn spring-boot:run`
After downloading and the necessary files the server should start up with something that looks like the following: 

      .   ____          _            __ _ _
     /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
     \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
      '  |____| .__|_| |_|_| |_\__, | / / / /
     =========|_|==============|___/=/_/_/_/
     :: Spring Boot ::                (v2.4.5)
    
    2021-04-26 14:34:17.361  INFO 9440 --- [           main] com.project.app.App                      : Starting App using Java 1.8.0_282 on DESKTOP-87DM91A with PID 9440 (C:\Users\kevin\IdeaProjects\Points\target\classes started by kevin in C:\Users\kevin\IdeaProjects\Points)
    2021-04-26 14:34:17.362  INFO 9440 --- [           main] com.project.app.App                      : No active profile set, falling back to default profiles: default
    2021-04-26 14:34:17.924  INFO 9440 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
    2021-04-26 14:34:17.930  INFO 9440 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
    2021-04-26 14:34:17.930  INFO 9440 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.45]
    2021-04-26 14:34:17.970  INFO 9440 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
    2021-04-26 14:34:17.971  INFO 9440 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 584 ms
    2021-04-26 14:34:18.074  INFO 9440 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
    2021-04-26 14:34:18.280  INFO 9440 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
    2021-04-26 14:34:18.286  INFO 9440 --- [           main] com.project.app.App                      : Started App in 1.13 seconds (JVM running for 1.349)

You can then start sending requests to the server. By using http://localhost:8080/ as the base for the requests.
Also you can visit the UI and send requests that way if you wish by going to http://localhost:8080/swagger-ui.html after the server has started.

**Requests**
There are three main requests that you can send to the server and those are:

 - /points - POST
	 - This endpoint allows us to create different transactions with payer info, points and when the transaction took place.
	 - Expects the body to resemble something like this 
    	 
		    {
		      "payer": "string",
		      "points": 0,
		      "timestamp": "2021-04-26T19:48:08.551Z"
		    }
 

 - /balance - GET
	 - This endpoint allows us to get all the different payer's in the system and see how many points they have left.
	 - No request body is needed for this request.
 - /spend - POST
	 - This endpoint allows us to spend the points that have been sent to our system. 
		 - The logic for this follows as such:
			 - Oldest points get spent first. (Based on transaction timestamp, not when it was added to the system)
			 - No payer's points can go negative.
		 - Expects the body to resemble something like this: 
		 
			      {
			      "payer": "string",
			      "points": 0,
			      "timestamp": "2021-04-26T19:50:52.344Z"
			    }

There are same barebones tests that are included with this project that simply test the posting of the data and getting the balance. More tests could be added on to check for failures and spending of points.
