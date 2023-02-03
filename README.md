# Spring-Rest
MVC- Spring-REST

Description

This is a MVC-Spring project for testing REST controllers with CRUD operations on a database using JDBC. Using DAO Layer with JDBC and Singleton

Requirements

JDK 1.8
Spring MVC
MySQL DB
Data Model

MySQL database named education with 5 tables: university, faculty, professor, student and subject. These tables are represented accordingly in the project with the suitable model java classes.

Connecting to Database

Active local MySql connection is needed (e.g. using mysql workbench). The connection is done with the singleton desigh pattern, using the class SingletonConnection.java where the user, the password and the database url are provided. A maven dependency is added in pom.xml: mysql-connector-java.

Data Access Object Layer

The DAO pattern is used to perform CRUD operations on the database. For each of the model classes there is a DAO interface with the basic CRUD methods and a DAO implementation class for that interface.

Service Layer

The Service Layer is represented with service interface classes for each DAO interface class and its own implementing class.

Controller Layer

 For each of the model classes there is a controller class for REST API endpoints access.
 
Cloning and setting the project 

Clone the repository in a local directory.
Open the chosen local directory as an intelliJ project.
Wait till the indexing of the files from intellij finish.
There should be active local mysql connection of an identical database.
Provide the database user, password and url into the SingletonConnection.java class.
A tomcat server is used for running the servlets, locally.
The project can be run with adding a run configuration and these are the steps:
from the RUN menu choose Edit configuration
from the Run/Debug Configurations window left panel, click add new and from the submenu choose Smart Tomcat.
write the name of the local tomcat server
under Tomcat server: browse to location of the Tomcat server folder (download)
deployment directory should remain webapp
context path can be just a /
server port = 8080 and admin port = 8005
before lunch should remain just Bulid
click ok.

Running the project

Run/Debug from the RUN menu ,the configuration of the local tomcat server (shift+F10/F9)2
open http://localhost:8080/Spring-Rest in browser
there should be running index.jsp with a welcome message.
There are different API endpoints such as:/faculty/all,faculty/id etc.
