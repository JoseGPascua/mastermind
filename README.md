# Mastermind

<h2>A guessing game where a user tries to solve a 4-digit number combination</h2>

This Project consists of a CLI implementation and a Springboot Application that acts as the backend service.
To properly play the game there are a few requirements and prerequisites.

<h3>Before being able to run this project:</h3>
Project Requirements
- `Java Version 21` or up installed
- `MySQL Version 8.0`

Project Prerequisites: 
- MySQL is required to set up a database... // TODO: Expand on the instructions



<h3>To run this project</h3>
- You will need to open two terminals, one will be to run the backend service and the other to run the CLI
- In the first terminal, change directories to the mastermind folder then run: 
  - `mvn spring-boot:run`
- In the second terminal, change directories to mastermindCLI folder then run the following:
  - `mvn clean install`
  - `cd target classes`
  - `java MastermindCLI`