# Backend part of ToDo Tracker Web App

- This Project serves as Backend Dependency for "[TODO_Tracker_Frontend](https://github.com/shrivatsam13/TODO_Tracker_Frontend)".
- Contains all the APIs to add, delete, edit, archive task along with marking it as completing.
- This also serves as an authentication layer by providing only access to the authorised users.
- This contains APIs to register, login and authenticate users for keeping track of the tasks.

## Prerequisites:
- IntelliJ
- VSCode
- Postman (Optional to check BE APIs response without FE)
- MongoDB setup (https://www.youtube.com/watch?v=s1WQ0eEpqqg&t=39s)
- MySQL setup

## Steps to Run Server
- Run Eureka Server: Keeps tracks of all the servers that are running.
- Run SpringCloudAPIGateway: Gateway for distinct API calls from Frontend (single entry point like http://localhost:9003/*) and redirects them accordingly to the config file.
- Run UserAuthenticationApp: Uses MySQL for adding and verifying users.
- Run UserTaskApp: Uses MongoDB for any task related operations.

## Fix for Errors possible While Running servers

- If you get "MongoNetworkError: connect ECONNREFUSED 127.0.0.1:27017" then run: "mongod --config /opt/homebrew/etc/mongod.conf --fork" and then "mongosh" in order to make sure that the mongoDB Server is running on your Mac system, if installed using HomeBrew.
- Run MySQL server on your system before running any server from this repository (from System Settings).
