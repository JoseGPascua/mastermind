# Mastermind
A guessing game where a user tries to solve a 4-digit number combination! The user has 10 attempts at guessing the number combination, with each attempt having a penalty of reducing their overall score.

While playing the game, the user has 5 different options:
  - Create New Game
  - Make a Guess
  - Check Previous Feedback
  - Provide Rules
  - Exit

The options provide a workflow for the game where a user can continuously send guesses to an existing game. After each option is executed, the user is brought back to the 5 different options, while in a current game, try not to create a new game otherwise the previous game will be overridden!

This Project consists of a CLI implementation and a Springboot Application that acts as the backend service.
To properly play the game there are a few requirements and prerequisites.

## Before being able to run this project:
### Project Requirements:
- `Java Version 21`
- `MySQL Version 8.0`
- `Maven 3.9`


## To run this project
### 1. Installation of the project:
In the terminal, change directories to where you want to install the project and then run the command:
```
git clone https://github.com/JoseGPascua/mastermind.git
```

### 2. Set up the backend service
While in the current terminal, change directories to the Springboot application for mastermind
```
cd mastermind/mastermind
```
To set up the database, enter the follow commands, replace `root` with your MySQL username. After entering the command, it will prompt you for your MySQL password:
```
mysql -u root -p
```

You will now be working in MySQL, enter the follow commands:
```
CREATE DATABASE mastermind;
use mastermind;
SOURCE src/main/resources/schema.sql;
```

The database along with the tables will have been created! To confirm this, you can run this optional command otherwise just type in `exit` to close MySQL:
```
SHOW TABLES;
exit
```
Open the mastermind project in your own IDE. Make sure you open the mastermind folder that is inside the mastermind project. Then go into the resources folder. It can be accessed by clicking the `src` folder, then clicking into `main` folder, then finally clicking into `resources` folder. Go into the `application.properties` file and replace `${MY_USERNAME}` and `${MY_PASSWORD}` with your own MySQL credentials. The file should look like this:
```properties 
spring.application.name=mastermind
spring.datasource.url=jdbc:mysql://localhost:3306/mastermind
spring.datasource.username=${MY_USERNAME}
spring.datasource.password=${MY_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
The database is now set up, you will simply run the following commands in the terminal to start the Springboot Application:
```
mvn clean install
mvn spring-boot:run
```
The Springboot Application should now be running! To confirm, this message should appear in the terminal:
``` 
Welcome to Mastermind's Backend Service!
```

### 3. Set up the Game Interface
In another terminal, change directories to mastermindCLI folder. For example:
```
cd mastermind/mastermindCLI
```

Then run the following:
```
mvn clean install
mvn dependency:copy-dependencies
java -cp "target/classes;target/dependency/*" MastermindCLI
```
For Mac Users, if the Java Command doesn't work, either try running MastermindCLI.java on an IDE ur run this command:
```
java -cp "target/classes:target/dependency/*" MastermindCLI
```

The previous commands will create a target folder and compile the necessary classes. It will then copy the necessary dependencies. The last command will start the MastermindCLI

## Project Development Process
### Developer Notes

Built with:
  - Java (Springboot)
  - Maven
  - MySQL
  - Git and Github

The process of creating this project initially started as a pure backend service. Developing the backend service consisted of my IDE, Postman and MySQL. My workflow began with first initializing the the Springboot application with the proper dependencies, for example the MySQL connector dependency. After having initialized the Springboot application and successfully connecting the application to MySQL, I began on working on the actual code.

First I needed to create an entity of what I wanted to represent the "Game" as, next was creating a Repository to hold any instances of the "Game". Afterwards, most of the development phase became a repeating process of:
  - Creating an endpoint
  - Adding a service to that endpoint
  - Running the services with good validation

The project structure is important to note as it allows for clearly defining what classes go in where. Having a controller, services, models, and repository package made it clear and gave purpose to the structure. There were other packages as well such as utils, and exceptions, but in the end having a clean path made it easier to work with.

With a working backend service with successful testing through Postman and Unit Testing, the next step was to create a CLI so that users can play the game with more structure than pinging endpoints on postman. The CLI simply gives the user's options, and the options control the flow of the game. The CLI sends requests to the backend service and then the CLI takes in the responses.

### Extensions
The Game features an option to change the difficulty. The difficulty is described by three different options:
  - 1 is Easy (The number combination can only be numbers 0-7)
  - 2 is Medium (The number combination can only be numbers 0-8)
  - 3 is Hard (The number combination can only be numbers 0-9)

The game also features a scoring system. Every game will start with a score of 1000, if the user makes an incorrect guess, that score will be reduced... Try to get the highest score you can!

### Author
Jose Pascua
